package org.dasein.cloud.azurepack.compute.vm;

import org.apache.commons.collections.Closure;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.dasein.cloud.CloudException;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.OperationNotSupportedException;
import org.dasein.cloud.azurepack.AzurePackCloud;
import org.dasein.cloud.azurepack.compute.image.model.WAPVhdModel;
import org.dasein.cloud.azurepack.compute.image.model.WAPVhdsModel;
import org.dasein.cloud.azurepack.compute.vm.model.WAPDiskDriveModel;
import org.dasein.cloud.azurepack.compute.vm.model.WAPVirtualMachineModel;
import org.dasein.cloud.azurepack.compute.vm.model.WAPVirtualMachinesModel;
import org.dasein.cloud.azurepack.utils.AzurePackRequester;
import org.dasein.cloud.compute.*;
import org.dasein.util.uom.storage.Megabyte;
import org.dasein.util.uom.storage.Storage;
import org.joda.time.DateTime;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by vmunthiu on 2/1/2016.
 */
public class AzurePackVolumeSupport extends AbstractVolumeSupport<AzurePackCloud> {
    public AzurePackVolumeSupport(AzurePackCloud provider) {
        super(provider);
    }

    @Override
    public VolumeCapabilities getCapabilities() throws CloudException, InternalException {
        return new AzurePackVolumeCapabilities();
    }

    @Override
    public @Nonnull String createVolume(@Nonnull VolumeCreateOptions options) throws InternalException, CloudException {
        if(options == null)
            throw new InternalException("VolumeCreateOptions parameter cannot be null");

        if(options.getProviderVirtualMachineId() == null)
            throw new InternalException("Virtual Machine ID cannot be null");
        if(options.getVolumeProductId() == null)
            throw new InternalException("Volume Product ID cannot be null");

        WAPVirtualMachineModel wapVirtualMachineModel = getVirtualMachineModel(options.getProviderVirtualMachineId());

        if(wapVirtualMachineModel == null)
            throw new InternalException("Invalid virtual machine id parameter. Virtual machine doesn't exists.");

        if(!wapVirtualMachineModel.getStatusString().equalsIgnoreCase("Stopped"))
            throw new InternalException("Virtual machine should be stopped when attaching a volume");


        String stampId = this.getProvider().getDataCenterServices().listDataCenters(this.getProvider().getContext().getRegionId()).iterator().next().getProviderDataCenterId();
        String diskName;
        if(options.getName() != null) {
            diskName = options.getName();
        } else {
            diskName = wapVirtualMachineModel.getName() + "_" + (wapVirtualMachineModel.getVirtualDiskDrives().size() + 1);
        }
        Integer diskLun = getMaxLunFromVMDrives(wapVirtualMachineModel) + 1;

        WAPDiskDriveModel wapDiskDriveModel = new WAPDiskDriveModel();
        wapDiskDriveModel.setName(diskName);
        wapDiskDriveModel.setFileName(diskName);
        wapDiskDriveModel.setStampId(stampId);
        wapDiskDriveModel.setVmId(options.getProviderVirtualMachineId());
        wapDiskDriveModel.setVirtualHardDiskId(options.getVolumeProductId());
        wapDiskDriveModel.setScsi("true");
        wapDiskDriveModel.setLun(diskLun.toString());
        wapDiskDriveModel.setBus("0");

        WAPDiskDriveModel wapDiskDriveModelResult = new AzurePackRequester(this.getProvider(),
                new AzurePackVolumeRequests(this.getProvider()).createDiskDriver(wapDiskDriveModel).build())
                .withJsonProcessor(WAPDiskDriveModel.class).execute();

        return wapDiskDriveModelResult.getId();
    }

    @Override
    public void attach(@Nonnull String volumeId, @Nonnull String toServer, @Nonnull String deviceId) throws InternalException, CloudException {
        throw new OperationNotSupportedException("Volume creation is not currently implemented for " + getProvider().getCloudName());
    }

    private Integer getMaxLunFromVMDrives(WAPVirtualMachineModel wapVirtualMachineModel) {
        ArrayList<Integer> usedLun = new ArrayList<>();
        for (WAPDiskDriveModel wapDiskDriveModel : wapVirtualMachineModel.getVirtualDiskDrives()) {
            usedLun.add(Integer.parseInt(wapDiskDriveModel.getLun()));
        }

        if(usedLun.isEmpty())
            return 0;

        return Collections.max(usedLun);
    }

    private WAPVirtualMachineModel getVirtualMachineModel(final String virtualMachineId) throws CloudException {
        WAPVirtualMachinesModel virtualMachinesModel =
                new AzurePackRequester(this.getProvider(), new AzurePackVolumeRequests(this.getProvider()).listVMsWithVDD().build())
                        .withJsonProcessor(WAPVirtualMachinesModel.class).execute();

        return (WAPVirtualMachineModel)CollectionUtils.find(virtualMachinesModel.getVirtualMachines(), new Predicate() {
            @Override
            public boolean evaluate(Object object) {
                return ((WAPVirtualMachineModel)object).getId().equalsIgnoreCase(virtualMachineId);
            }
        });
    }

    @Override
    public void detach(@Nonnull String volumeId, boolean force) throws InternalException, CloudException {
        String stampId = this.getProvider().getDataCenterServices().listDataCenters(this.getProvider().getContext().getRegionId()).iterator().next().getProviderDataCenterId();

        new AzurePackRequester(this.getProvider(), new AzurePackVolumeRequests(this.getProvider()).deleteDiskDrive(volumeId, stampId).build()).execute();
    }

    @Nonnull
    @Override
    public Iterable<Volume> listVolumes() throws InternalException, CloudException {
        return getVolumesFromVMs();
    }

    @Override
    public @Nonnull Iterable<VolumeProduct> listVolumeProducts() throws InternalException, CloudException {
        return getVolumeProductsFromVhds();
    }

    private ArrayList<VolumeProduct> getVolumeProductsFromVhds() throws CloudException {
        WAPVhdsModel wapVhdsModel =
                new AzurePackRequester(this.getProvider(), new AzurePackVolumeRequests(this.getProvider()).listVirtualDisks().build()).withJsonProcessor(WAPVhdsModel.class).execute();

        final ArrayList<VolumeProduct> volumes = new ArrayList<>();
        CollectionUtils.forAllDo(wapVhdsModel.getVhds(), new Closure() {
            @Override
            public void execute(Object input) {
                WAPVhdModel wapVhdModel = (WAPVhdModel)input;
                if(wapVhdModel.getOperatingSystem().equalsIgnoreCase("none")) {
                    volumes.add(VolumeProduct.getInstance(wapVhdModel.getId(), wapVhdModel.getName(),
                            wapVhdModel.getName(),
                            VolumeType.HDD,
                            new Storage<Megabyte>(Integer.parseInt(wapVhdModel.getSize()), Storage.MEGABYTE)));
                }
            }
        });
        return volumes;
    }

    private ArrayList<Volume> getVolumesFromVMs() throws CloudException {
        WAPVirtualMachinesModel virtualMachinesModel =
                new AzurePackRequester(this.getProvider(), new AzurePackVolumeRequests(this.getProvider()).listVMsWithVDD().build())
                        .withJsonProcessor(WAPVirtualMachinesModel.class).execute();
        final ArrayList<Volume> volumes = new ArrayList<>();
        CollectionUtils.forAllDo(virtualMachinesModel.getVirtualMachines(), new Closure() {
            @Override
            public void execute(Object input) {
                WAPVirtualMachineModel virtualMachineModel = (WAPVirtualMachineModel)input;
                for (WAPDiskDriveModel wapDiskDriveModel : virtualMachineModel.getVirtualDiskDrives()) {
                    volumes.add(volumeFrom(wapDiskDriveModel, virtualMachineModel.getId()));
                }
            }
        });
        return volumes;
    }

    private Volume volumeFrom(WAPDiskDriveModel wapDiskDriveModel, String vmId) {
        Volume volume = new Volume();
        volume.setName(wapDiskDriveModel.getName());
        volume.setProviderRegionId(this.getProvider().getContext().getRegionId());
        volume.setProviderDataCenterId(wapDiskDriveModel.getStampId());
        volume.setProviderVolumeId(wapDiskDriveModel.getId());
        volume.setProviderVirtualMachineId(vmId);
        volume.setCreationTimestamp(new DateTime(wapDiskDriveModel.getAddedTime()).getMillis());
        volume.setCurrentState(VolumeState.AVAILABLE);
        return volume;
    }

    private Volume volumeFrom(WAPVhdModel wapVhdModel) {
        Volume volume = new Volume();
        volume.setName(wapVhdModel.getName());
        volume.setProviderRegionId(this.getProvider().getContext().getRegionId());
        volume.setProviderDataCenterId(wapVhdModel.getStampId());
        volume.setProviderVolumeId(wapVhdModel.getId());
        volume.setCurrentState(VolumeState.AVAILABLE);
        volume.setSize(new Storage<Megabyte>(Integer.parseInt(wapVhdModel.getSize()), Storage.MEGABYTE));
        return volume;
    }

    @Override
    public boolean isSubscribed() throws CloudException, InternalException {
        return true;
    }

    @Override
    public void remove(@Nonnull String volumeId) throws InternalException, CloudException {

    }
}
