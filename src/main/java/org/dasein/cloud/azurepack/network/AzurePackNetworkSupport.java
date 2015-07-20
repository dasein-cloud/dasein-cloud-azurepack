package org.dasein.cloud.azurepack.network;

import org.apache.commons.collections.Closure;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.collections.Predicate;
import org.apache.http.client.methods.HttpUriRequest;
import org.dasein.cloud.CloudException;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.azurepack.AzurePackCloud;
import org.dasein.cloud.azurepack.network.model.*;
import org.dasein.cloud.azurepack.utils.AzurePackRequester;
import org.dasein.cloud.dc.DataCenter;
import org.dasein.cloud.network.*;
import org.dasein.cloud.util.requester.DriverToCoreMapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

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
        WAPLogicalNetsModel logicalNets = new AzurePackRequester(provider, listLogicalNetRequest).withJsonProcessor(WAPLogicalNetsModel.class).execute();

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

    @Override
    public VLAN getVlan(final String vlanId) throws InternalException, CloudException {
        if(vlanId == null)
            throw new InternalException("Parameter vlanId cannot be null.");

        return (VLAN) CollectionUtils.find((ArrayList<VLAN>) listVlans(), new Predicate() {
            @Override
            public boolean evaluate(Object object) {
                return ((VLAN) object).getProviderVlanId().equalsIgnoreCase(vlanId);
            }
        });
    }
    @Override
    public String createInternetGateway(@Nonnull final String vlanId) throws CloudException, InternalException {
        VLAN vlan = getVlan(vlanId);
        if(vlan == null)
            throw new InternalException("Parameter vlanId does not belong to an existing network");

        WAPVMNetworkGatewayModel wapvmNetworkGatewayModel = new WAPVMNetworkGatewayModel();
        wapvmNetworkGatewayModel.setName(vlan.getName() + "Gateway");
        wapvmNetworkGatewayModel.setDescription(vlan.getDescription() + "Gateway");
        wapvmNetworkGatewayModel.setStampId(vlan.getProviderDataCenterId());
        wapvmNetworkGatewayModel.setVmNetworkId(vlan.getProviderVlanId());
        wapvmNetworkGatewayModel.setRequiresNAT("true");

        HttpUriRequest httpUriRequest = new AzurePackNetworkRequests(provider).createInternetGateway(wapvmNetworkGatewayModel).build();

        WAPVMNetworkGatewayModel wapvmNetworkGatewayResult =
                new AzurePackRequester(provider, httpUriRequest).withJsonProcessor(WAPVMNetworkGatewayModel.class).execute();


        WAPNatConnectionModel wapNatConnectionModel = new WAPNatConnectionModel();
        wapNatConnectionModel.setName(vlan.getName() + "NAT Connection");
        wapNatConnectionModel.setStampId(vlan.getProviderDataCenterId());
        wapNatConnectionModel.setVmNetworkGatewayId(wapvmNetworkGatewayResult.getId());

        HttpUriRequest natCreateUriRequest = new AzurePackNetworkRequests(provider).createNatConnection(wapNatConnectionModel).build();

        try {
            new AzurePackRequester(provider, natCreateUriRequest).execute();
        } catch (Exception ex) {
            //delete created internet gateway
            HttpUriRequest deleteGatewayRequest = new AzurePackNetworkRequests(provider).deleteInternetGateway(wapvmNetworkGatewayResult).build();
            new AzurePackRequester(provider, deleteGatewayRequest).execute();
            throw new CloudException(ex.getMessage());
        }

        return wapvmNetworkGatewayResult.getId();
    }

    @Override
    public void removeInternetGateway(@Nonnull final String vlanId) throws CloudException, InternalException {
        VLAN vlan = getVlan(vlanId);
        if(vlan == null)
            throw new InternalException("Parameter vlanId does not belong to an existing network");

        final WAPVMNetworkGatewayModel wapvmNetworkGatewayModel = getInternetGateway(vlan);

        if(wapvmNetworkGatewayModel == null)
            throw new InternalException("There is no internet gateway on the provide vlanId");

        HttpUriRequest listNatConnectionRequest = new AzurePackNetworkRequests(provider).listNatConnections(wapvmNetworkGatewayModel.getId(), wapvmNetworkGatewayModel.getStampId()).build();
        WAPNatConnectionsModel wapNatConnectionModels = new AzurePackRequester(provider, listNatConnectionRequest).withJsonProcessor(WAPNatConnectionsModel.class).execute();

        if(wapNatConnectionModels != null && wapNatConnectionModels.getConnections() != null){
            for (WAPNatConnectionModel wapNatConnectionModel : wapNatConnectionModels.getConnections()) {
                HttpUriRequest deleteNatRequest = new AzurePackNetworkRequests(provider).deleteNatConnection(wapNatConnectionModel).build();
                new AzurePackRequester(provider, deleteNatRequest).execute();
            }
        }

        HttpUriRequest deleteGatewayRequest = new AzurePackNetworkRequests(provider).deleteInternetGateway(wapvmNetworkGatewayModel).build();
        new AzurePackRequester(provider, deleteGatewayRequest).execute();
    }

    @Override
    public boolean isConnectedViaInternetGateway(@Nonnull final String vlanId) throws CloudException, InternalException {
        VLAN vlan = getVlan(vlanId);
        if(vlan == null)
            throw new InternalException("Parameter vlanId does not belong to an existing network");

        WAPVMNetworkGatewayModel wapvmNetworkGatewayModel = getInternetGateway(vlan);

        if(wapvmNetworkGatewayModel == null)
            return false;

        return true;
    }

    private WAPVMNetworkGatewayModel getInternetGateway(final VLAN vlan) throws CloudException, InternalException {
        HttpUriRequest listGatewaysRequest = new AzurePackNetworkRequests(provider).listGateways(vlan).build();
        WAPVMNetworkGatewaysModel wapvmNetworkGatewayModels = new AzurePackRequester(provider, listGatewaysRequest).withJsonProcessor(WAPVMNetworkGatewaysModel.class).execute();
        if(wapvmNetworkGatewayModels.getGateways() == null || wapvmNetworkGatewayModels.getGateways().size() == 0)
            return null;

        return wapvmNetworkGatewayModels.getGateways().get(0);
    }

    public @Nullable String getAttachedInternetGatewayId(@Nonnull String vlanId) throws CloudException, InternalException {
        VLAN vlan = getVlan(vlanId);
        if(vlan == null)
            throw new InternalException("Parameter vlanId does not belong to an existing network");

        WAPVMNetworkGatewayModel wapvmNetworkGatewayModel = getInternetGateway(vlan);

        if(wapvmNetworkGatewayModel == null)
            return null;

        return wapvmNetworkGatewayModel.getId();
    }

    public @Nullable InternetGateway getInternetGatewayById(@Nonnull final String gatewayId) throws CloudException, InternalException {
        if(gatewayId == null)
            throw new InternalException("Parameter gatewayId cannot be null");

        String stampId = provider.getDataCenterServices().listDataCenters(provider.getContext().getRegionId()).iterator().next().getProviderDataCenterId();
        HttpUriRequest getGatewayRequest = new AzurePackNetworkRequests(provider).getGateway(gatewayId, stampId).build();
        try {
            WAPVMNetworkGatewayModel wapvmNetworkGatewayModel = new AzurePackRequester(provider, getGatewayRequest).withJsonProcessor(WAPVMNetworkGatewayModel.class).execute();

            if( wapvmNetworkGatewayModel == null)
                return null;

            InternetGateway internetGateway = new InternetGateway();
            internetGateway.setProviderInternetGatewayId(wapvmNetworkGatewayModel.getId());
            internetGateway.setProviderVlanId(wapvmNetworkGatewayModel.getVmNetworkId());
            internetGateway.setProviderRegionId(provider.getContext().getRegionId());
            internetGateway.setProviderOwnerId(provider.getContext().getAccountNumber());
            return internetGateway;
        } catch (CloudException ex) {
            if(ex.getProviderCode().equalsIgnoreCase("Not found")) {
                return null;
            }
            throw ex;
        }
    }
}
