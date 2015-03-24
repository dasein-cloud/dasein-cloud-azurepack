package org.dasein.cloud.azurepack.utils;

import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.dasein.cloud.InternalException;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * Custom socket factory for handling X509 authentication with Azure using an in-memory key store.
 * @author George Reese (george.reese@imaginary.com)
 * @author Tim Freeman (tim.freeman@enstratus.com)
 * @since 2012.04.1
 * @version 2012.04.1
 */
public class AzureSSLSocketFactory extends SSLSocketFactory {

    public AzureSSLSocketFactory(AzureX509 creds, boolean disableSSLValidation) throws InternalException, NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
        super("TLS", creds.getKeystore(), AzureX509.PASSWORD, null, null, disableSSLValidation ? trustStrategy : null, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
    }

    private static final TrustStrategy trustStrategy = new TrustStrategy() {
        @Override
        public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            return true;
        }
    };
}
