package org.dasein.cloud.azurepack.compute.image;

import org.apache.http.client.methods.RequestBuilder;
import org.dasein.cloud.azurepack.AzurePackCloud;

/**
 * Created by vmunthiu on 3/3/2015.
 */
public class AzurePackImageRequests {
    private final String VHD_RESOURCES = "%s/%s/services/systemcenter/vmm/VirtualHardDisks";
    private final String VMTEMPLATES_RESOURCES = "%s/%s/services/systemcenter/vmm/VMTemplates";

    private AzurePackCloud provider;

    public AzurePackImageRequests(AzurePackCloud provider){
        this.provider = provider;
    }

    public RequestBuilder listVirtualDisks(){
        RequestBuilder requestBuilder = RequestBuilder.get();
        addCommonHeaders(requestBuilder);
        requestBuilder.setUri(String.format(VHD_RESOURCES, this.provider.getContext().getEndpoint(), this.provider.getContext().getAccountNumber()));
        return requestBuilder;
    }

    public RequestBuilder listTemplates(){
        RequestBuilder requestBuilder = RequestBuilder.get();
        addCommonHeaders(requestBuilder);
        requestBuilder.setUri(String.format(VMTEMPLATES_RESOURCES, this.provider.getContext().getEndpoint(), this.provider.getContext().getAccountNumber()));
        return requestBuilder;
    }

    private void addCommonHeaders(RequestBuilder requestBuilder) {
        requestBuilder.addHeader("x-ms-version", "2014-02-01");
        requestBuilder.addHeader("Accept", "application/json");
    }
}
