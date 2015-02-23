package org.dasein.cloud.azurepack;

import org.apache.http.client.methods.RequestBuilder;

/**
 * Created by vmunthiu on 2/20/2015.
 */
public class AzurePackDataCenterRequests {

    private final String CLOUD_RESOURCES = "%s/%s/services/systemcenter/vmm/Clouds";
    private final String STAMP_RESOURCES = "";

    private AzurePackCloud provider;

    public AzurePackDataCenterRequests(AzurePackCloud provider){
        this.provider = provider;
    }

    public RequestBuilder listClouds(){
        RequestBuilder requestBuilder = RequestBuilder.get();
        addCommonHeaders(requestBuilder);
        requestBuilder.setUri(String.format(CLOUD_RESOURCES, this.provider.getContext().getEndpoint(), this.provider.getContext().getAccountNumber()));
        return requestBuilder;
    }

    private void addCommonHeaders(RequestBuilder requestBuilder) {
        requestBuilder.addHeader("x-ms-version", "2014-02-01");
        requestBuilder.addHeader("Accept", "application/json");
    }
}
