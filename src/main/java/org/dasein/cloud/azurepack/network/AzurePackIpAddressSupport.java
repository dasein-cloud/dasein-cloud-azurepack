/**
 * Copyright (C) 2009-2015 Dell, Inc
 * See annotations for authorship information
 *
 * ====================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ====================================================================
 */

package org.dasein.cloud.azurepack.network;

import org.apache.commons.collections.Closure;
import org.apache.commons.collections.CollectionUtils;
import org.apache.http.client.methods.HttpUriRequest;
import org.dasein.cloud.CloudException;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.ResourceStatus;
import org.dasein.cloud.azurepack.AzurePackCloud;
import org.dasein.cloud.azurepack.compute.vm.AzurePackVirtualMachineSupport;
import org.dasein.cloud.azurepack.network.model.WAPNatConnectionModel;
import org.dasein.cloud.azurepack.network.model.WAPNatConnectionsModel;
import org.dasein.cloud.azurepack.network.model.WAPNatRuleModel;
import org.dasein.cloud.azurepack.network.model.WAPNatRulesModel;
import org.dasein.cloud.azurepack.utils.AzurePackRequester;
import org.dasein.cloud.network.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by vmunthiu on 7/15/2015.
 */
public class AzurePackIpAddressSupport extends AbstractIpAddressSupport<AzurePackCloud> {
    private AzurePackCloud provider;
    protected AzurePackIpAddressSupport(AzurePackCloud provider) {
        super(provider);
        this.provider = provider;
    }

    @Override
    public void assign(String addressId, String serverId) throws InternalException, CloudException {

    }

    @Override
    public void assignToNetworkInterface(String addressId, String nicId) throws InternalException, CloudException {

    }

    @Nonnull
    @Override
    public String forward(String addressId, int publicPort, Protocol protocol, int privatePort, String onServerId) throws InternalException, CloudException {
        String stampId = provider.getDataCenterServices().listDataCenters(provider.getContext().getRegionId()).iterator().next().getProviderDataCenterId();
        WAPNatConnectionModel wapNatConnectionModel = getNatConnectionForServer(onServerId, stampId);
        if(wapNatConnectionModel == null)
            throw new InternalException("The network attached to the specified server does not have NAT connections");

        WAPNatRuleModel natRuleModel = new WAPNatRuleModel();
        natRuleModel.setName(wapNatConnectionModel.getName());
        natRuleModel.setInternalPort(String.valueOf(privatePort));
        natRuleModel.setInternalIPAddress(addressId);
        natRuleModel.setExternalPort(String.valueOf(publicPort));
        natRuleModel.setStampId(stampId);
        natRuleModel.setNatConnectionId(wapNatConnectionModel.getId());
        if(protocol != null)
            natRuleModel.setProtocol(protocol.toString());

        HttpUriRequest createRuleRequest = new AzurePackNetworkRequests(provider).createNatRule(natRuleModel).build();
        WAPNatRuleModel natRuleResultModel = new AzurePackRequester(provider, createRuleRequest).withJsonProcessor(WAPNatRuleModel.class).execute();

        return natRuleResultModel.getId();
    }

    private WAPNatConnectionModel getNatConnectionForServer(String serverId, String stampId) throws CloudException, InternalException {
        AzurePackVirtualMachineSupport.VirtualMachineNetworkData virtualMachineNetworkData = ((AzurePackVirtualMachineSupport)this.provider.getComputeServices().getVirtualMachineSupport()).tryGetVMNetworkId(serverId, stampId);

        String internetGatewayId = this.provider.getNetworkServices().getVlanSupport().getAttachedInternetGatewayId(virtualMachineNetworkData.getVlanId());
        if(internetGatewayId == null)
            throw new InternalException("The network attached to the specified server does not have a internet gateway");

        HttpUriRequest listNatConnectionRequest = new AzurePackNetworkRequests(provider).listNatConnections(internetGatewayId, stampId).build();
        WAPNatConnectionsModel wapNatConnectionModels = new AzurePackRequester(provider, listNatConnectionRequest).withJsonProcessor(WAPNatConnectionsModel.class).execute();

        if(wapNatConnectionModels == null || wapNatConnectionModels.getConnections().size() == 0)
            return null;

        return wapNatConnectionModels.getConnections().get(0);
    }

    @Override
    public void stopForward(@Nonnull String ruleId) throws InternalException, CloudException{
        if(ruleId == null)
            throw new InternalException("Parameter ruleId cannot be null");

        String stampId = provider.getDataCenterServices().listDataCenters(provider.getContext().getRegionId()).iterator().next().getProviderDataCenterId();

        HttpUriRequest deleteRequest = new AzurePackNetworkRequests(provider).deleteNatRule(ruleId, stampId).build();
        new AzurePackRequester(provider, deleteRequest).execute();
    }

    @Override
    public @Nonnull Iterable<IpForwardingRule> listRulesForServer(@Nonnull final String serverId) throws InternalException, CloudException{
        if(serverId == null)
            throw new InternalException("Parameter serverId cannot be null");

        String stampId = provider.getDataCenterServices().listDataCenters(provider.getContext().getRegionId()).iterator().next().getProviderDataCenterId();
        WAPNatConnectionModel wapNatConnectionModel = getNatConnectionForServer(serverId, stampId);
        if(wapNatConnectionModel == null)
            return Collections.emptyList();

        HttpUriRequest listRulesRequest = new AzurePackNetworkRequests(provider).listRulesForConnection(wapNatConnectionModel.getId(), stampId).build();

        WAPNatRulesModel wapNatRulesModel = new AzurePackRequester(provider, listRulesRequest).withJsonProcessor(WAPNatRulesModel.class).execute();

        final ArrayList<IpForwardingRule> rules = new ArrayList<IpForwardingRule>();
        CollectionUtils.forAllDo(wapNatRulesModel.getRules(), new Closure() {
            @Override
            public void execute(Object input) {
                WAPNatRuleModel wapNatRuleModel = (WAPNatRuleModel)input;
                IpForwardingRule rule = new IpForwardingRule();
                rule.setServerId(serverId);
                rule.setProviderRuleId(wapNatRuleModel.getId());
                rule.setPrivatePort(Integer.valueOf(wapNatRuleModel.getInternalPort()));
                rule.setPublicPort(Integer.valueOf(wapNatRuleModel.getExternalPort()));
                if(wapNatRuleModel.getProtocol() != null)
                    rule.setProtocol(Protocol.valueOf(wapNatRuleModel.getProtocol().toUpperCase()));

                rules.add(rule);
            }
        });

        return rules;
    }

    @Nonnull
    @Override
    public IPAddressCapabilities getCapabilities() throws CloudException, InternalException {
        return new AzurePackIpAddressCapabilities(this.provider);
    }

    @Nullable
    @Override
    public IpAddress getIpAddress(@Nonnull String addressId) throws InternalException, CloudException {
        return null;
    }

    @Override
    public boolean isSubscribed() throws CloudException, InternalException {
        return false;
    }

    @Nonnull
    @Override
    public Iterable<IpAddress> listIpPool(@Nonnull IPVersion version, boolean unassignedOnly) throws InternalException, CloudException {
        return null;
    }

    @Nonnull
    @Override
    public Iterable<ResourceStatus> listIpPoolStatus(@Nonnull IPVersion version) throws InternalException, CloudException {
        return null;
    }

    @Override
    public void releaseFromPool(@Nonnull String addressId) throws InternalException, CloudException {

    }

    @Override
    public void releaseFromServer(@Nonnull String addressId) throws InternalException, CloudException {

    }

    @Nonnull
    @Override
    public String request(@Nonnull IPVersion version) throws InternalException, CloudException {
        return null;
    }

    @Nonnull
    @Override
    public String requestForVLAN(@Nonnull IPVersion version) throws InternalException, CloudException {
        return null;
    }

    @Nonnull
    @Override
    public String requestForVLAN(@Nonnull IPVersion version, @Nonnull String vlanId) throws InternalException, CloudException {
        return null;
    }
}
