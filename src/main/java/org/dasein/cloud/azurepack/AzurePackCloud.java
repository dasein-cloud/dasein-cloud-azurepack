package org.dasein.cloud.azurepack;

import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.log4j.Logger;
import org.dasein.cloud.AbstractCloud;
import org.dasein.cloud.CloudException;
import org.dasein.cloud.ContextRequirements;
import org.dasein.cloud.ProviderContext;
import org.dasein.cloud.azurepack.compute.AzurePackComputeService;
import org.dasein.cloud.azurepack.utils.AzureSSLSocketFactory;
import org.dasein.cloud.azurepack.utils.AzureX509;
import org.dasein.cloud.azurepack.utils.LoggerUtils;
import org.dasein.cloud.dc.DataCenterServices;

import javax.annotation.Nonnull;
import javax.net.ssl.*;
import java.security.cert.X509Certificate;
import java.util.Properties;

/**
 * Created by vmunthiu on 1/28/2015.
 */
public class AzurePackCloud extends AbstractCloud {
    static private final Logger logger = LoggerUtils.getLogger(AzurePackCloud.class);

    @Nonnull
    @Override
    public String getCloudName() { return "Windows Azure Pack"; }

    @Nonnull
    @Override
    public DataCenterServices getDataCenterServices() {
        return new AzurePackDataCenterService(this);
    }

    @Override
    public @Nonnull AzurePackComputeService getComputeServices() { return new AzurePackComputeService(this); }

    @Nonnull
    @Override
    public String getProviderName() { return "Private Cloud"; }

    @Override
    public @Nonnull
    ContextRequirements getContextRequirements() {
        return new ContextRequirements(
                new ContextRequirements.Field("apiKey", "The API Keypair", ContextRequirements.FieldType.KEYPAIR, ContextRequirements.Field.X509, true)
        );
    }

    public HttpClientBuilder getAzureClientBuilder() throws CloudException {
        try {

            //boolean disableSSLValidation = isSSLValidationDisabled();
            boolean disableSSLValidation = true;
            HttpClientBuilder builder = HttpClientBuilder.create();
            Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("https", new AzureSSLSocketFactory(new AzureX509(this), disableSSLValidation))
                    .build();

            HttpClientConnectionManager ccm = new BasicHttpClientConnectionManager(registry);
            builder.setConnectionManager(ccm);
            return builder;
        } catch (Exception e) {
            throw new CloudException(e.getMessage());
        }
    }

    public boolean isSSLValidationDisabled() {
        if( this.getContext() == null ) {
            return false;
        }

        if( this.getContext().getCustomProperties() == null ) {
           return false;
        }

        if(this.getContext().getCustomProperties().getProperty("insecure") != null) {
            return this.getContext().getCustomProperties().getProperty("insecure").equalsIgnoreCase("true");
        }

        return (System.getProperty("insecure") != null && System.getProperty("insecure").equalsIgnoreCase("true"));
    }
}
