package org.dasein.cloud.azurepack.network;

import org.apache.commons.collections.Closure;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.collections.Predicate;
import org.apache.http.client.methods.HttpUriRequest;
import org.dasein.cloud.CloudException;
import org.dasein.cloud.CloudProvider;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.OperationNotSupportedException;
import org.dasein.cloud.azurepack.AzurePackCloud;
import org.dasein.cloud.azurepack.network.model.*;
import org.dasein.cloud.azurepack.utils.AzurePackRequester;
import org.dasein.cloud.dc.DataCenter;
import org.dasein.cloud.network.*;
import org.dasein.cloud.util.requester.DriverToCoreMapper;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * Created by vmunthiu on 3/26/2015.
 */
public class AzurePackNetworkSupport extends AbstractVLANSupport<AzurePackCloud> {
    private AzurePackCloud provider;

    public AzurePackNetworkSupport(@Nonnull AzurePackCloud provider) {
        super(provider);
        this.provider = provider;
    }

    @Override
    public @Nonnull Subnet createSubnet(@Nonnull SubnetCreateOptions options) throws CloudException, InternalException {
        if(options == null)
            throw new InternalException("SubnetCreateOptions parameter cannot be null");

        VLAN vlan = this.getVlan(options.getProviderVlanId());
        if(vlan == null)
            throw new InternalException("Invalid network id provided");

        return createSubnetInternal(options.getName(), vlan.getProviderDataCenterId(), options.getCidr(), options.getProviderVlanId());
    }

    private Subnet createSubnetInternal(String name, String stampId, String cidr, String vmNetworkId) throws CloudException {
        WAPSubnetModel subnetModel = new WAPSubnetModel();
        subnetModel.setName(name);
        subnetModel.setStampId(stampId);
        subnetModel.setSubnet(cidr);
        subnetModel.setVmNetworkId(vmNetworkId);

        HttpUriRequest createSubnetRequest = new AzurePackNetworkRequests(provider).createSubnet(subnetModel).build();

        return new AzurePackRequester(provider, createSubnetRequest).withJsonProcessor(new DriverToCoreMapper<WAPSubnetModel, Subnet>() {
            @Override
            public Subnet mapFrom(WAPSubnetModel entity) {
                return Subnet.getInstance(provider.getContext().getAccountNumber(), provider.getContext().getRegionId(),
                        entity.getVmNetworkId(),
                        entity.getId(),
                        SubnetState.AVAILABLE,
                        entity.getName(),
                        entity.getName(),
                        entity.getSubnet());
            }
        }, WAPSubnetModel.class).execute();
    }


    @Override
    public @Nonnull Iterable<Subnet> listSubnets(@Nonnull final String vlanId) throws CloudException, InternalException {
        List<DataCenter> dataCenters = new ArrayList(IteratorUtils.toList(this.provider.getDataCenterServices().listDataCenters(this.provider.getContext().getRegionId()).iterator()));
        String dataCenterId = dataCenters.get(0).getProviderDataCenterId();

        HttpUriRequest listSubnetsRequest = new AzurePackNetworkRequests(provider).listSubnets(dataCenterId).build();

        return new AzurePackRequester(provider, listSubnetsRequest).withJsonProcessor(new DriverToCoreMapper<WAPSubnetsModel, List<Subnet>>() {
            @Override
            public List<Subnet> mapFrom(WAPSubnetsModel entity) {
                final ArrayList<Subnet> subnets = new ArrayList<Subnet>();
                CollectionUtils.forAllDo(entity.getSubnets(), new Closure() {
                    @Override
                    public void execute(Object input) {
                        WAPSubnetModel subnetModel = (WAPSubnetModel) input;
                        if(subnetModel.getVmNetworkId().equalsIgnoreCase(vlanId)) {
                            subnets.add(Subnet.getInstance(provider.getContext().getAccountNumber(), provider.getContext().getRegionId(),
                                    subnetModel.getVmNetworkId(),
                                    subnetModel.getId(),
                                    SubnetState.AVAILABLE,
                                    subnetModel.getName(),
                                    subnetModel.getName(),
                                    subnetModel.getSubnet()));
                        }
                    }
                });

                return subnets;
            }
        }, WAPSubnetsModel.class).execute();
    }

    @Override
    public void removeSubnet(final String providerSubnetId) throws CloudException, InternalException {
        List<DataCenter> dataCenters = new ArrayList(IteratorUtils.toList(this.provider.getDataCenterServices().listDataCenters(this.provider.getContext().getRegionId()).iterator()));
        String dataCenterId = dataCenters.get(0).getProviderDataCenterId();

        WAPSubnetsModel subnetsModel = new AzurePackRequester(provider, new AzurePackNetworkRequests(provider).listSubnets(dataCenterId).build()).withJsonProcessor(WAPSubnetsModel.class).execute();

        WAPSubnetModel foundSubnetModel = (WAPSubnetModel)CollectionUtils.find(subnetsModel.getSubnets(), new Predicate() {
            @Override
            public boolean evaluate(Object object) {
                return ((WAPSubnetModel) object).getId().equalsIgnoreCase(providerSubnetId);
            }
        });

        if(foundSubnetModel == null)
            throw new InternalException("Invalid subnet providerSubnetId provided");

        new AzurePackRequester(provider, new AzurePackNetworkRequests(provider).deleteSubnet(foundSubnetModel).build()).execute();
    }

    @Override
    public @Nonnull VLAN createVlan(@Nonnull VlanCreateOptions vco) throws CloudException, InternalException {
        if(vco == null)
            throw new InternalException("SubnetCreateOptions parameter cannot be null");

        WAPLogicalNetModel logicalNetwork = getFirstLogicalNetwork();

        WAPVMNetworkModel networkModel = new WAPVMNetworkModel();
        networkModel.setName(vco.getName());
        networkModel.setStampId(logicalNetwork.getStampId());
        networkModel.setLogicalNetworkId(logicalNetwork.getId());
        networkModel.setDescription(vco.getDescription());

        WAPVMNetworkModel resultNetwork = new AzurePackRequester(provider,
                new AzurePackNetworkRequests(provider).createVMNetwork(networkModel).build()).withJsonProcessor(WAPVMNetworkModel.class).execute();

        createSubnetInternal(vco.getName(), logicalNetwork.getStampId(), vco.getCidr(), resultNetwork.getId());

        return vlanFrom(resultNetwork);
    }

    public void removeVlan(final String vlanId) throws CloudException, InternalException {
        WAPVMNetworksModel networksModel = new AzurePackRequester(provider,
                new AzurePackNetworkRequests(provider).listVMNetworks().build()).withJsonProcessor(WAPVMNetworksModel.class).execute();

        WAPVMNetworkModel foundNetwork = (WAPVMNetworkModel)CollectionUtils.find(networksModel.getVirtualMachineNetworks(), new Predicate() {
            @Override
            public boolean evaluate(Object object) {
                return ((WAPVMNetworkModel)object).getId().equalsIgnoreCase(vlanId);
            }
        });

        if(foundNetwork == null)
            throw new InternalException("Invalid network id provided for deletion");

        new AzurePackRequester(provider, new AzurePackNetworkRequests(provider).deleteVMNetwork(foundNetwork).build()).execute();
    }

    @Override
    public @Nonnull Iterable<VLAN> listVlans() throws CloudException, InternalException {
        HttpUriRequest listNetworksRequest = new AzurePackNetworkRequests(provider).listVMNetworks().build();

        return new AzurePackRequester(provider, listNetworksRequest).withJsonProcessor(new DriverToCoreMapper<WAPVMNetworksModel, List<VLAN>>() {
            @Override
            public List<VLAN> mapFrom(WAPVMNetworksModel entity) {
                final ArrayList<VLAN> vlans = new ArrayList<VLAN>();
                CollectionUtils.forAllDo(entity.getVirtualMachineNetworks(), new Closure() {
                    @Override
                    public void execute(Object input) {
                        vlans.add(vlanFrom((WAPVMNetworkModel) input));
                    }
                });

                return vlans;
            }
        }, WAPVMNetworksModel.class).execute();
    }

    private VLAN vlanFrom(WAPVMNetworkModel networkModel){
        VLAN vlan = new VLAN();
        vlan.setName(networkModel.getName());
        vlan.setDescription(networkModel.getDescription());
        vlan.setProviderVlanId(networkModel.getId());
        vlan.setProviderDataCenterId(networkModel.getStampId());
        vlan.setProviderRegionId(provider.getContext().getRegionId());
        vlan.setProviderOwnerId(networkModel.getOwner().getRoleID() != null ? networkModel.getOwner().getRoleID() : provider.getContext().getAccountNumber());
        vlan.setCurrentState("true".equalsIgnoreCase(networkModel.getEnabled()) ? VLANState.AVAILABLE : VLANState.PENDING);
        return vlan;
    }

    private WAPLogicalNetModel getFirstLogicalNetwork() throws CloudException {
        HttpUriRequest listLogicalNetRequest = new AzurePackNetworkRequests(provider).listLogicalNets().build();
        WAPLogicalNetsMode logicalNets = new AzurePackRequester(provider, listLogicalNetRequest).withJsonProcessor(WAPLogicalNetsMode.class).execute();

        if(logicalNets == null || logicalNets.getLogicalNets().size() == 0)
            return null;

        CollectionUtils.filter(logicalNets.getLogicalNets(), new Predicate() {
            @Override
            public boolean evaluate(Object object) {
                return ((WAPLogicalNetModel) object).getNetworkVirtualizationEnabled().equalsIgnoreCase("true") ? true : false;
            }
        });
        return logicalNets.getLogicalNets().get(0);
    }

    @Override
    public VLANCapabilities getCapabilities() throws CloudException, InternalException {
        return new AzurePackNetworkCapabilities(provider);
    }

    @Nonnull
    @Override
    public String getProviderTermForNetworkInterface(Locale locale) {
        return null;
    }

    @Nonnull
    @Override
    public String getProviderTermForSubnet(@Nonnull Locale locale) {
        return null;
    }

    @Nonnull
    @Override
    public String getProviderTermForVlan(@Nonnull Locale locale) {
        return null;
    }

    @Override
    public boolean isSubscribed() throws CloudException, InternalException {
        return true;
    }
}
