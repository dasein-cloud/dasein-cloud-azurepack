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

import org.apache.http.client.methods.RequestBuilder;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.azurepack.AzurePackCloud;
import org.dasein.cloud.azurepack.network.model.*;
import org.dasein.cloud.network.VLAN;
import org.dasein.cloud.util.requester.entities.DaseinObjectToJsonEntity;

import java.net.URI;
import java.net.URL;

/**
 * Created by vmunthiu on 3/26/2015.
 */
public class AzurePackNetworkRequests {
    private final String VM_NETWORKS = "%s/%s/services/systemcenter/vmm/VMNetworks";
    private final String VM_NETWORK = "%s/%s/services/systemcenter/vmm/VMNetworks(ID=Guid'%s',StampId=Guid'%s')";
    private final String VM_SUBNETS = "%s/%s/services/systemcenter/vmm/VMSubnets";
    private final String LIST_VM_SUBNETS = "%s/%s/services/systemcenter/vmm/VMSubnets()?$filter=StampId eq guid'%s'";
    private final String VM_SUBNET = "%s/%s/services/systemcenter/vmm/VMSubnets(ID=Guid'%s',StampId=Guid'%s')";
    private final String LOGICAL_NETS = "%s/%s/services/systemcenter/vmm/LogicalNetworks";
    private final String VM_NET_GATEWAYS = "%s/%s/services/systemcenter/vmm/VMNetworkGateways";
    private final String LIST_NET_GATEWAYS = "%s/%s/services/systemcenter/vmm/VMNetworkGateways()?$filter=VMNetworkId eq guid'%s' and StampId eq guid'%s'";
    private final String VM_NET_GATEWAY = "%s/%s/services/systemcenter/vmm/VMNetworkGateways(ID=Guid'%s',StampId=Guid'%s')";
    private final String NET_NAT_CONNECTIONS = "%s/%s/services/systemcenter/vmm/NATConnections";
    private final String NET_NAT_CONNECTION = "%s/%s/services/systemcenter/vmm/NATConnections(ID=Guid'%s',StampId=Guid'%s')";
    private final String GATEWAY_NAT_CONNECTIONS = "%s/%s/services/systemcenter/vmm/NATConnections()?$filter=StampId eq guid'%s' and VMNetworkGatewayId eq guid'%s'";
    private final String NAT_RULES = "%s/%s/services/systemcenter/vmm/NATRules";
    private final String NAT_RULE = "%s/%s/services/systemcenter/vmm/NATRules(ID=guid'%s',StampId=guid'%s')";
    private final String NAT_RULES_FOR_CONNECTION = "%s/%s/services/systemcenter/vmm/NATRules()?$filter=StampId eq guid'%s' and NATConnectionId eq guid'%s'";

    private AzurePackCloud provider;

    public AzurePackNetworkRequests(AzurePackCloud provider){
           this.provider = provider;
    }

    public RequestBuilder createSubnet(WAPSubnetModel subnetModel)
    {
        RequestBuilder requestBuilder = RequestBuilder.post();
        addCommonHeaders(requestBuilder);
        requestBuilder.setUri(String.format(VM_SUBNETS, this.provider.getContext().getEndpoint(), this.provider.getContext().getAccountNumber()));
        requestBuilder.setEntity(new DaseinObjectToJsonEntity<WAPSubnetModel>(subnetModel));
        return requestBuilder;
    }

    public RequestBuilder listSubnets(String dataCenterId) throws InternalException {
        RequestBuilder requestBuilder = RequestBuilder.get();
        addCommonHeaders(requestBuilder);
        requestBuilder.setUri(getEncodedUri(String.format(LIST_VM_SUBNETS, this.provider.getContext().getEndpoint(), this.provider.getContext().getAccountNumber(), dataCenterId)));
        return requestBuilder;
    }

    public RequestBuilder listLogicalNets()
    {
        RequestBuilder requestBuilder = RequestBuilder.get();
        addCommonHeaders(requestBuilder);
        requestBuilder.setUri(String.format(LOGICAL_NETS, this.provider.getContext().getEndpoint(), this.provider.getContext().getAccountNumber()));
        return requestBuilder;
    }

    public RequestBuilder deleteSubnet(WAPSubnetModel subnetModel){
        RequestBuilder requestBuilder = RequestBuilder.delete();
        addCommonHeaders(requestBuilder);
        requestBuilder.setUri(String.format(VM_SUBNET, this.provider.getContext().getEndpoint(), this.provider.getContext().getAccountNumber(), subnetModel.getId(), subnetModel.getStampId()));
        return requestBuilder;
    }

    public RequestBuilder listVMNetworks(){
        RequestBuilder requestBuilder = RequestBuilder.get();
        addCommonHeaders(requestBuilder);
        requestBuilder.setUri(String.format(VM_NETWORKS, this.provider.getContext().getEndpoint(), this.provider.getContext().getAccountNumber()));
        return requestBuilder;
    }

    public RequestBuilder createVMNetwork(WAPVMNetworkModel networkModel){
        RequestBuilder requestBuilder = RequestBuilder.post();
        addCommonHeaders(requestBuilder);
        requestBuilder.setUri(String.format(VM_NETWORKS, this.provider.getContext().getEndpoint(), this.provider.getContext().getAccountNumber()));
        requestBuilder.setEntity(new DaseinObjectToJsonEntity<WAPVMNetworkModel>(networkModel));
        return requestBuilder;
    }

    public RequestBuilder deleteVMNetwork(WAPVMNetworkModel networkModel){
        RequestBuilder requestBuilder = RequestBuilder.delete();
        addCommonHeaders(requestBuilder);
        requestBuilder.setUri(String.format(VM_NETWORK, this.provider.getContext().getEndpoint(), this.provider.getContext().getAccountNumber(), networkModel.getId(), networkModel.getStampId()));
        return requestBuilder;
    }

    public RequestBuilder createInternetGateway(WAPVMNetworkGatewayModel wapvmNetworkGatewayModel){
        RequestBuilder requestBuilder = RequestBuilder.post();
        addCommonHeaders(requestBuilder);
        requestBuilder.setUri(String.format(VM_NET_GATEWAYS, this.provider.getContext().getEndpoint(), this.provider.getContext().getAccountNumber()));
        requestBuilder.setEntity(new DaseinObjectToJsonEntity<WAPVMNetworkGatewayModel>(wapvmNetworkGatewayModel));
        return requestBuilder;
    }

    public RequestBuilder listGateways(VLAN vlan) throws InternalException {
        RequestBuilder requestBuilder = RequestBuilder.get();
        addCommonHeaders(requestBuilder);
        requestBuilder.setUri(getEncodedUri(String.format(LIST_NET_GATEWAYS, this.provider.getContext().getEndpoint(), this.provider.getContext().getAccountNumber(), vlan.getProviderVlanId(), vlan.getProviderDataCenterId())));
        return requestBuilder;
    }

    public RequestBuilder getGateway(String gatewayId, String stampId) throws InternalException {
        RequestBuilder requestBuilder = RequestBuilder.get();
        addCommonHeaders(requestBuilder);
        requestBuilder.setUri(getEncodedUri(String.format(VM_NET_GATEWAY, this.provider.getContext().getEndpoint(), this.provider.getContext().getAccountNumber(), gatewayId, stampId)));
        return requestBuilder;
    }

    public RequestBuilder deleteInternetGateway(WAPVMNetworkGatewayModel wapvmNetworkGatewayModel) throws InternalException {
        RequestBuilder requestBuilder = RequestBuilder.delete();
        addCommonHeaders(requestBuilder);
        requestBuilder.setUri(getEncodedUri(String.format(VM_NET_GATEWAY, this.provider.getContext().getEndpoint(), this.provider.getContext().getAccountNumber(), wapvmNetworkGatewayModel.getId(), wapvmNetworkGatewayModel.getStampId())));
        return requestBuilder;
    }

    public RequestBuilder createNatConnection(WAPNatConnectionModel wapNatConnectionModel){
        RequestBuilder requestBuilder = RequestBuilder.post();
        addCommonHeaders(requestBuilder);
        requestBuilder.setUri(String.format(NET_NAT_CONNECTIONS, this.provider.getContext().getEndpoint(), this.provider.getContext().getAccountNumber()));
        requestBuilder.setEntity(new DaseinObjectToJsonEntity<WAPNatConnectionModel>(wapNatConnectionModel));
        return requestBuilder;
    }

    public RequestBuilder listNatConnections(String gatewayId, String stampId) throws InternalException {
        RequestBuilder requestBuilder = RequestBuilder.get();
        addCommonHeaders(requestBuilder);
        requestBuilder.setUri(getEncodedUri(String.format(GATEWAY_NAT_CONNECTIONS, this.provider.getContext().getEndpoint(), this.provider.getContext().getAccountNumber(), stampId, gatewayId)));
        return requestBuilder;
    }

    public RequestBuilder deleteNatConnection(WAPNatConnectionModel wapNatConnectionModel){
        RequestBuilder requestBuilder = RequestBuilder.delete();
        addCommonHeaders(requestBuilder);
        requestBuilder.setUri(String.format(NET_NAT_CONNECTION, this.provider.getContext().getEndpoint(), this.provider.getContext().getAccountNumber(), wapNatConnectionModel.getId(), wapNatConnectionModel.getStampId()));
        return requestBuilder;
    }

    public RequestBuilder createNatRule(WAPNatRuleModel wapNatRuleModel){
        RequestBuilder requestBuilder = RequestBuilder.post();
        addCommonHeaders(requestBuilder);
        requestBuilder.setUri(String.format(NAT_RULES, this.provider.getContext().getEndpoint(), this.provider.getContext().getAccountNumber()));
        requestBuilder.setEntity(new DaseinObjectToJsonEntity<WAPNatRuleModel>(wapNatRuleModel));
        return requestBuilder;
    }

    public RequestBuilder deleteNatRule(String ruleId, String stampId) throws InternalException {
        RequestBuilder requestBuilder = RequestBuilder.delete();
        addCommonHeaders(requestBuilder);
        requestBuilder.setUri(getEncodedUri(String.format(NAT_RULE, this.provider.getContext().getCloud().getEndpoint(), this.provider.getContext().getAccountNumber(), ruleId, stampId)));
        return requestBuilder;
    }

    public RequestBuilder listRulesForConnection(String natConnectionId, String stampId) throws InternalException {
        RequestBuilder requestBuilder = RequestBuilder.get();
        addCommonHeaders(requestBuilder);
        requestBuilder.setUri(getEncodedUri(String.format(NAT_RULES_FOR_CONNECTION, this.provider.getContext().getCloud().getEndpoint(), this.provider.getContext().getAccountNumber(), stampId, natConnectionId)));
        return requestBuilder;
    }

    private String getEncodedUri(String urlString) throws InternalException {
        try {
            URL url = new URL(urlString);
            return new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef()).toString();
        } catch (Exception e) {
            throw new InternalException(e.getMessage());
        }
    }

    private void addCommonHeaders(RequestBuilder requestBuilder) {
        requestBuilder.addHeader("x-ms-version", "2014-02-01");
        requestBuilder.addHeader("Accept", "application/json");
    }
}
