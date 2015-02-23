package org.dasein.cloud.azurepack.utils;

import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.dasein.cloud.CloudException;
import org.dasein.cloud.CloudProvider;
import org.dasein.cloud.azurepack.AzurePackCloud;
import org.dasein.cloud.util.requester.fluent.DaseinRequest;

import javax.net.ssl.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by vmunthiu on 2/20/2015.
 */
public class AzurePackRequester extends DaseinRequest {
    public AzurePackRequester(AzurePackCloud provider, HttpUriRequest httpUriRequest) throws CloudException {
        this(provider, provider.getAzureClientBuilder(), httpUriRequest);
    }
    public AzurePackRequester(CloudProvider provider, HttpClientBuilder httpClientBuilder, HttpUriRequest httpUriRequestBuilder) {
        super(provider, httpClientBuilder, httpUriRequestBuilder);
    }
}
