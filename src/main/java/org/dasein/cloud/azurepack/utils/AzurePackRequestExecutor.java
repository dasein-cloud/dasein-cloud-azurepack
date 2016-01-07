package org.dasein.cloud.azurepack.utils;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.dasein.cloud.CloudException;
import org.dasein.cloud.CloudProvider;
import org.dasein.cloud.azurepack.model.WAPErrorModel;
import org.dasein.cloud.util.requester.CloudResponseException;
import org.dasein.cloud.util.requester.DaseinRequestExecutor;
import org.dasein.cloud.util.requester.streamprocessors.XmlStreamToObjectProcessor;

/**
 * Created by vmunthiu on 1/7/2016.
 */
public class AzurePackRequestExecutor<T> extends DaseinRequestExecutor<T> {

    public AzurePackRequestExecutor(CloudProvider provider, HttpClientBuilder httpClientBuilder, HttpUriRequest httpUriRequest, ResponseHandler<T> responseHandler) {
        super(provider, httpClientBuilder, httpUriRequest, responseHandler);
    }

    @Override
    public CloudException translateException(Exception exception) {
        if(exception instanceof CloudResponseException) {
            CloudResponseException e = (CloudResponseException) exception;
            String errorMessage = tryGetErrorMessage(e.getMessage());
            if(errorMessage == null) {
                errorMessage = e.getMessage();
            }

            return new CloudException(e.getErrorType(), e.getHttpCode(), e.getProviderCode(), errorMessage);
        } else {
            return new CloudException(exception.getMessage());
        }
    }

    private String tryGetErrorMessage(String errorMessageAsXML) {
        try {
            WAPErrorModel errorModel = new XmlStreamToObjectProcessor<WAPErrorModel>().read(org.apache.commons.io.IOUtils.toInputStream(errorMessageAsXML), WAPErrorModel.class);
            return errorModel.getMessage();
        } catch (Exception ex ) {
            return null;
        }
    }
}
