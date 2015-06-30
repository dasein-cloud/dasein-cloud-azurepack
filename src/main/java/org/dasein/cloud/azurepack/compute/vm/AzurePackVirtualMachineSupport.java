package org.dasein.cloud.azurepack.compute.vm;

import org.apache.commons.collections.*;
import org.apache.http.client.methods.HttpUriRequest;
import org.dasein.cloud.CloudException;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.azurepack.AzurePackCloud;
import org.dasein.cloud.azurepack.compute.image.AzurePackImageCapabilities;
import org.dasein.cloud.azurepack.compute.image.AzurePackImageRequests;
import org.dasein.cloud.azurepack.compute.image.AzurePackImageSupport;
import org.dasein.cloud.azurepack.compute.image.model.WAPTemplateModel;
import org.dasein.cloud.azurepack.compute.image.model.WAPTemplatesModel;
import org.dasein.cloud.azurepack.compute.vm.model.*;
import org.dasein.cloud.azurepack.utils.AzurePackRequester;
import org.dasein.cloud.compute.*;
import org.dasein.cloud.dc.DataCenter;
import org.dasein.cloud.network.RawAddress;
import org.dasein.cloud.network.VLAN;
import org.dasein.cloud.network.VlanCreateOptions;
import org.dasein.cloud.util.requester.DriverToCoreMapper;
import org.dasein.util.uom.storage.Megabyte;
import org.dasein.util.uom.storage.Storage;
import org.dasein.util.uom.storage.StorageUnit;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by vmunthiu on 3/4/2015.
 */
public class AzurePackVirtualMachineSupport extends AbstractVMSupport<AzurePackCloud> {
    private AzurePackCloud provider;

    public AzurePackVirtualMachineSupport(@Nonnull AzurePackCloud provider) {
        super(provider);
        this.provider = provider;
    }

    @Nonnull
    @Override
    public VirtualMachineCapabilities getCapabilities() throws InternalException, CloudException {
        return new AzurePackVirtualMachineCapabilities(provider);
    }

    @Override
    public boolean isSubscribed() throws CloudException, InternalException {
        return true;
    }

    @Nonnull
    @Override
    public VirtualMachine launch(@Nonnull VMLaunchOptions withLaunchOptions) throws CloudException, InternalException {
        MachineImage image = provider.getComputeServices().getImageSupport().getImage(withLaunchOptions.getMachineImageId());
        if(image == null)
            throw new InternalException("Invalid machine image id");

        VLAN vlan = null;
        if(withLaunchOptions.getVlanId() != null) {
            vlan = this.provider.getNetworkServices().getVlanSupport().getVlan(withLaunchOptions.getVlanId());
            if (vlan == null)
                throw new InternalException("Invalid vlan id provided");
        }

        String imageType = (String)image.getTag("type");
        WAPVirtualMachineModel virtualMachineModel = new WAPVirtualMachineModel();
        virtualMachineModel.setName(withLaunchOptions.getFriendlyName());
        virtualMachineModel.setCloudId(provider.getContext().getRegionId());
        virtualMachineModel.setStampId(withLaunchOptions.getDataCenterId());

        if(imageType.equalsIgnoreCase("vhd")){
            virtualMachineModel.setVirtualHardDiskId(withLaunchOptions.getMachineImageId());
            virtualMachineModel.setHardwareProfileId(withLaunchOptions.getStandardProductId());
        } else {
            virtualMachineModel.setVmTemplateId(withLaunchOptions.getMachineImageId());
        }

        if(imageType.equalsIgnoreCase("template")) {
            if(image.getPlatform().isWindows() && withLaunchOptions.getWinProductSerialNum() != null) {
                virtualMachineModel.setProductKey(withLaunchOptions.getWinProductSerialNum());
            } else if (!image.getPlatform().isWindows() && withLaunchOptions.getBootstrapKey() != null) {
                virtualMachineModel.setLinuxAdministratorSSHKeyString(withLaunchOptions.getBootstrapKey());
            }

            if (withLaunchOptions.getBootstrapPassword() != null && withLaunchOptions.getBootstrapUser() != null) {
                virtualMachineModel.setLocalAdminPassword(withLaunchOptions.getBootstrapPassword());
                if (image.getPlatform().isWindows()) {
                    virtualMachineModel.setLocalAdminUserName("administrator");
                    //virtualMachineModel.setLocalAdminUserName((withLaunchOptions.getBootstrapUser() == null || withLaunchOptions.getBootstrapUser().trim().length() == 0 || withLaunchOptions.getBootstrapUser().equalsIgnoreCase("root") || withLaunchOptions.getBootstrapUser().equalsIgnoreCase("admin") || withLaunchOptions.getBootstrapUser().equalsIgnoreCase("administrator") ? "dasein" : withLaunchOptions.getBootstrapUser()));
                } else {
                    virtualMachineModel.setLocalAdminUserName("root");
                    //virtualMachineModel.setLocalAdminUserName((withLaunchOptions.getBootstrapUser() == null || withLaunchOptions.getBootstrapUser().trim().length() == 0 || withLaunchOptions.getBootstrapUser().equals("root") ? "dasein" : withLaunchOptions.getBootstrapUser()));
                }
            }
        }

        if (withLaunchOptions.getVlanId() != null) {
            ArrayList<WAPNewAdapterModel> adapters = new ArrayList<WAPNewAdapterModel>();
            WAPNewAdapterModel newAdapterModel = new WAPNewAdapterModel();
            newAdapterModel.setVmNetworkName(vlan.getName());
            adapters.add(newAdapterModel);
            virtualMachineModel.setNewVirtualNetworkAdapterInput(adapters);
        }

        HttpUriRequest createRequest = new AzurePackVMRequests(provider).createVirtualMachine(virtualMachineModel).build();
        VirtualMachine virtualMachine = new AzurePackRequester(provider, createRequest).withJsonProcessor(new DriverToCoreMapper<WAPVirtualMachineModel, VirtualMachine>() {
            @Override
            public VirtualMachine mapFrom(WAPVirtualMachineModel entity) {
                return virtualMachineFrom(entity);
            }
        }, WAPVirtualMachineModel.class).execute();

        waitForVMOperation("Creating", virtualMachine.getProviderVirtualMachineId(), virtualMachine.getProviderDataCenterId());
        start(virtualMachine.getProviderVirtualMachineId());
        return virtualMachine;
    }

    private void waitForVMOperation(String operationToWaitFor, String vmId, String dataCenterId) throws CloudException, InternalException {
        HttpUriRequest getVMRequest = new AzurePackVMRequests(provider).getVirtualMachine(vmId, dataCenterId).build();
        WAPVirtualMachineModel virtualMachineModel = new AzurePackRequester(this.provider, getVMRequest).withJsonProcessor(WAPVirtualMachineModel.class).execute();

        if(!virtualMachineModel.getStatusString().contains(operationToWaitFor))
            return;

        try { Thread.sleep(15000L); }
        catch( InterruptedException ignore ) { }
        waitForVMOperation(operationToWaitFor, vmId, dataCenterId);
    }

    @Override
    public void stop( @Nonnull String vmId, boolean force ) throws InternalException, CloudException {
        updateVMState(vmId, "Shutdown");
    }

    @Override
    public void start( @Nonnull String vmId ) throws InternalException, CloudException {
        updateVMState(vmId, "Start");
    }

    @Override
    public void terminate(@Nonnull String vmId, String explanation) throws InternalException, CloudException {
        VirtualMachine virtualMachine = getVirtualMachine(vmId);
        if(virtualMachine == null)
            throw new CloudException("Virtual machine does not exist");

        HttpUriRequest deleteRequest = new AzurePackVMRequests(provider).deleteVirtualMachine(virtualMachine.getProviderVirtualMachineId(), virtualMachine.getProviderDataCenterId()).build();
        new AzurePackRequester(provider, deleteRequest).execute();
    }

    @Override
    public @Nullable VirtualMachine getVirtualMachine( @Nonnull String vmId ) throws InternalException, CloudException {
        if(vmId == null)
            throw new InternalException("Invalid virtual machine id.");

        List<DataCenter> dataCenters = IteratorUtils.toList(this.provider.getDataCenterServices().listDataCenters(this.provider.getContext().getRegionId()).iterator());

        HttpUriRequest getVMRequest = new AzurePackVMRequests(provider).getVirtualMachine(vmId, dataCenters.get(0).getProviderDataCenterId()).build();

        return new AzurePackRequester(this.provider, getVMRequest).withJsonProcessor(new DriverToCoreMapper<WAPVirtualMachineModel, VirtualMachine>() {
            @Override
            public VirtualMachine mapFrom(WAPVirtualMachineModel entity) {
                return virtualMachineFrom(entity);
            }
        },WAPVirtualMachineModel.class).execute();
    }

    @Override
    public @Nonnull Iterable<VirtualMachine> listVirtualMachines() throws InternalException, CloudException {
        HttpUriRequest listVMRequest = new AzurePackVMRequests(provider).listVirtualMachines().build();
        WAPVirtualMachinesModel virtualMachinesModel = new AzurePackRequester(this.provider, listVMRequest).withJsonProcessor(WAPVirtualMachinesModel.class).execute();

        final List<VirtualMachine> virtualMachines = new ArrayList<VirtualMachine>();
        final String regionId = getContext().getRegionId();

        CollectionUtils.forAllDo(virtualMachinesModel.getVirtualMachines(), new Closure() {
            @Override
            public void execute(Object input) {
                WAPVirtualMachineModel virtualMachineModel = (WAPVirtualMachineModel) input;
                if(regionId.equalsIgnoreCase(virtualMachineModel.getCloudId())) {
                    virtualMachines.add(virtualMachineFrom(virtualMachineModel));
                }
            }
        });

        return virtualMachines;
    }

    @Override
    public @Nonnull Iterable<VirtualMachineProduct> listProducts(@Nonnull final String machineImageId) throws InternalException, CloudException {
        return listProducts(machineImageId, VirtualMachineProductFilterOptions.getInstance());
    }

    @Override
    public @Nonnull Iterable<VirtualMachineProduct> listProducts(@Nonnull final String machineImageId, @Nonnull VirtualMachineProductFilterOptions options) throws InternalException, CloudException{
        MachineImage image = provider.getComputeServices().getImageSupport().getImage(machineImageId);
        if(image == null)
            throw new InternalException("Invalid machine image id");

        if(options == null)
            throw new InternalException("VirtualMachineProductFilterOptions parameter cannot be null");

        String imageType = (String)image.getTag("type");
        if(imageType.equalsIgnoreCase("vhd"))
            return listProducts(VirtualMachineProductFilterOptions.getInstance());

        HttpUriRequest listTemplatesRequest = new AzurePackImageRequests(this.provider).listTemplates().build();
        WAPTemplatesModel templatesModel = new AzurePackRequester(this.provider, listTemplatesRequest).withJsonProcessor(WAPTemplatesModel.class).execute();

        WAPTemplateModel template = (WAPTemplateModel)CollectionUtils.find(templatesModel.getTemplates(), new Predicate() {
            @Override
            public boolean evaluate(Object object) {
                WAPTemplateModel templateModel = (WAPTemplateModel)object;
                return machineImageId.equalsIgnoreCase(templateModel.getId());
            }
        });

        //for templates add a default dummy product
        List<VirtualMachineProduct> products = new ArrayList<VirtualMachineProduct>();
        VirtualMachineProduct vmProduct = new VirtualMachineProduct();
        vmProduct.setName("Default");
        vmProduct.setProviderProductId("default");
        vmProduct.setDescription("Default");
        vmProduct.setCpuCount(Integer.parseInt(template.getCpuCount()));
        vmProduct.setRamSize(new Storage<Megabyte>(Integer.parseInt(template.getMemory()), Storage.MEGABYTE));

        if(options.matches(vmProduct))
            products.add(vmProduct);

        return products;
    }

    @Override
    public @Nonnull Iterable<VirtualMachineProduct> listProducts(@Nonnull final VirtualMachineProductFilterOptions options, @Nullable Architecture architecture) throws InternalException, CloudException {
        HttpUriRequest listProfilesRequest = new AzurePackVMRequests(provider).listHardwareProfiles().build();

        WAPHardwareProfilesModel hardwareProfilesModel = new AzurePackRequester(provider, listProfilesRequest).withJsonProcessor(WAPHardwareProfilesModel.class).execute();

        final List<VirtualMachineProduct> products = new ArrayList<VirtualMachineProduct>();
        CollectionUtils.forAllDo(hardwareProfilesModel.getHardwareProfiles(), new Closure() {
            @Override
            public void execute(Object input) {
                WAPHardwareProfileModel hardwareProfileModel = (WAPHardwareProfileModel) input;
                VirtualMachineProduct vmProduct = new VirtualMachineProduct();
                vmProduct.setName(hardwareProfileModel.getName());
                vmProduct.setCpuCount(Integer.parseInt(hardwareProfileModel.getCpuCount()));
                vmProduct.setDescription(hardwareProfileModel.getDescription());
                vmProduct.setRamSize(new Storage<Megabyte>(Integer.parseInt(hardwareProfileModel.getMemory()), Storage.MEGABYTE));
                vmProduct.setProviderProductId(hardwareProfileModel.getId());
                vmProduct.setDataCenterId(hardwareProfileModel.getStampId());
                products.add(vmProduct);
            }
        });

        if(options != null) {
            CollectionUtils.filter(products, new Predicate() {
                @Override
                public boolean evaluate(Object object) {
                    return options.matches((VirtualMachineProduct)object);
                }
            });
        }

        return products;
    }

    private VirtualMachine virtualMachineFrom(WAPVirtualMachineModel virtualMachineModel) {
        VirtualMachine virtualMachine = new VirtualMachine();
        virtualMachine.setProviderVirtualMachineId(virtualMachineModel.getId());
        virtualMachine.setProviderRegionId(virtualMachineModel.getCloudId());
        virtualMachine.setProviderDataCenterId(virtualMachineModel.getStampId());
        virtualMachine.setName(virtualMachineModel.getName());
        virtualMachine.setCurrentState(getVmState(virtualMachineModel.getStatusString()));
        virtualMachine.setProviderOwnerId(virtualMachineModel.getOwner().getRoleID());

        VirtualMachineNetworkData virtualMachineNetworkData = tryGetVMNetworkId(virtualMachineModel.getId(), virtualMachineModel.getStampId());
        if(virtualMachineNetworkData != null) {
            virtualMachine.setProviderVlanId(virtualMachineNetworkData.getVlanId());
            Collection<RawAddress> addresses = CollectionUtils.collect(virtualMachineNetworkData.getIpAddresses(), new Transformer() {
                @Override
                public Object transform(Object input) {
                    String address = (String) input;
                    return new RawAddress(address);
                }
            });

            if(!addresses.isEmpty() && addresses.size() == 1) {
                virtualMachine.setPublicAddresses(addresses.iterator().next());
            } else if((!addresses.isEmpty() && addresses.size() != 1)) {
                virtualMachine.setPublicAddresses(addresses.toArray(new RawAddress[addresses.size()]));
            }
        }

        String productId = tryGetProductId(virtualMachineModel.getCpuCount(), virtualMachineModel.getMemory());
        if(productId != null)
            virtualMachine.setProductId(productId);

        return virtualMachine;
    }

    private VirtualMachineNetworkData tryGetVMNetworkId(String virtualMachineId, String stampId) {
        try {
            HttpUriRequest listAdapters = new AzurePackVMRequests(provider).listVirtualMachineNetAdapters(virtualMachineId, stampId).build();
            WAPVirtualNetworkAdapters virtualNetworkAdapters = new AzurePackRequester(provider, listAdapters).withJsonProcessor(WAPVirtualNetworkAdapters.class).execute();

            WAPVirtualNetworkAdapter networkAdapter = (WAPVirtualNetworkAdapter) CollectionUtils.find(virtualNetworkAdapters.getVirtualNetworkAdapters(), new Predicate() {
                @Override
                public boolean evaluate(Object object) {
                    WAPVirtualNetworkAdapter networkAdapter = (WAPVirtualNetworkAdapter) object;
                    return networkAdapter.getVmNetworkId() != null;
                }
            });

            if (networkAdapter == null)
                return null;

            VirtualMachineNetworkData data = new VirtualMachineNetworkData();
            data.setVlanId(networkAdapter.getVmNetworkId());
            data.setIpAddresses(networkAdapter.getIpv4Addresses());
            return data;
        }
        catch (Exception ex) {
            return null;
        }
    }

    private String tryGetProductId(final String cpuCount, final String memory) {
        try {
            HttpUriRequest listProfilesRequest = new AzurePackVMRequests(provider).listHardwareProfiles().build();
            WAPHardwareProfilesModel hardwareProfilesModel = new AzurePackRequester(provider, listProfilesRequest).withJsonProcessor(WAPHardwareProfilesModel.class).execute();

            WAPHardwareProfileModel profileModel = (WAPHardwareProfileModel) CollectionUtils.find(hardwareProfilesModel.getHardwareProfiles(), new Predicate() {
                @Override
                public boolean evaluate(Object object) {
                    WAPHardwareProfileModel hardwareProfileModel = (WAPHardwareProfileModel) object;
                    return hardwareProfileModel.getCpuCount().equalsIgnoreCase(cpuCount) && hardwareProfileModel.getMemory().equalsIgnoreCase(memory);
                }
            });

            if (profileModel == null)
                return null;

            return profileModel.getId();
        }
        catch (Exception ex){
            return null;
        }
    }

    private VmState getVmState(String state){
        try {
            if("Update Failed".equalsIgnoreCase(state)) {
                return VmState.ERROR;
            }

            if("Creating...".equalsIgnoreCase(state)) {
                return VmState.PENDING;
            }

            return VmState.valueOf(state.toUpperCase());
        } catch (Exception ex) {
            return VmState.PENDING;
        }
    }

    private void updateVMState(String vmId, String operation) throws InternalException, CloudException {
        List<DataCenter> dataCenters = IteratorUtils.toList(this.provider.getDataCenterServices().listDataCenters(this.provider.getContext().getRegionId()).iterator());
        String dataCenterId = dataCenters.get(0).getProviderDataCenterId();

        HttpUriRequest getVMRequest = new AzurePackVMRequests(provider).getVirtualMachine(vmId, dataCenterId).build();

        WAPVirtualMachineModel virtualMachineModel = new AzurePackRequester(this.provider, getVMRequest).withJsonProcessor(WAPVirtualMachineModel.class).execute();
        virtualMachineModel.setOperation(operation);

        HttpUriRequest updateVMRequest = new AzurePackVMRequests(provider).updateVirtualMachine(vmId, dataCenterId, virtualMachineModel).build();
        new AzurePackRequester(provider, updateVMRequest).execute();
    }

    private class VirtualMachineNetworkData {
        private String vlanId;
        private List<String> ipAddresses;

        public String getVlanId() {
            return vlanId;
        }

        public void setVlanId(String vlanId) {
            this.vlanId = vlanId;
        }

        public List<String> getIpAddresses() {
            return ipAddresses;
        }

        public void setIpAddresses(List<String> ipAddresses) {
            this.ipAddresses = ipAddresses;
        }
    }
}
