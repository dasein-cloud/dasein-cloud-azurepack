package org.dasein.cloud.azurepack.network;

import org.apache.http.client.methods.RequestBuilder;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.azurepack.AzurePackCloud;
import org.dasein.cloud.azurepack.network.model.WAPSubnetModel;
import org.dasein.cloud.azurepack.network.model.WAPVMNetworkModel;
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
    private final String VM_SUBNET = "%s/%s/services/systemcenter/vmm/VMSubnets(ID=Guid'%s',StampId=Guid'%s')";
    private final String LOGICAL_NETS = "%s/%s/services/systemcenter/vmm/LogicalNetworks";

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

    public RequestBuilder listSubnets()
    {
        RequestBuilder requestBuilder = RequestBuilder.get();
        addCommonHeaders(requestBuilder);
        requestBuilder.setUri(String.format(VM_SUBNETS, this.provider.getContext().getEndpoint(), this.provider.getContext().getAccountNumber()));
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
