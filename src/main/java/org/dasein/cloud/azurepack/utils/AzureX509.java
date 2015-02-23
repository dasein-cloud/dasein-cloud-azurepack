package org.dasein.cloud.azurepack.utils;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMReader;
import org.dasein.cloud.ContextRequirements;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.ProviderContext;
import org.dasein.cloud.azurepack.AzurePackCloud;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.cert.*;
import java.util.List;

/**
 * X509 certficate management for integration with Azure's outmoded form of authentication.
 * @author George Reese (george.reese@imaginary.com)
 * @author Tim Freeman (timothy.freeman@enstratus.com)
 * @since 2012.04.1
 * @version 2012.04.1
 */
public class AzureX509 {
    static public final String ENTRY_ALIAS = "";
    static public final String PASSWORD    = "memory";

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    private KeyStore keystore;

    public AzureX509(AzurePackCloud provider) throws InternalException {
        ProviderContext ctx = provider.getContext();
        try {
            String apiShared = "";
            String apiSecret = "";
            try {
                List<ContextRequirements.Field> fields = provider.getContextRequirements().getConfigurableValues();
                for(ContextRequirements.Field f : fields ) {
                    if(f.type.equals(ContextRequirements.FieldType.KEYPAIR)){
                        byte[][] keyPair = (byte[][])ctx.getConfigurationValue(f);
                        apiShared = new String(keyPair[0], "utf-8");
                        apiSecret = new String(keyPair[1], "utf-8");
                    }
                }
            }
            catch (UnsupportedEncodingException ignore) {}
            //  System.out.println(apiShared);
            //  System.out.println(apiSecret);

            X509Certificate certificate = certFromString(apiShared);
            PrivateKey privateKey = keyFromString(apiSecret);

            keystore = createJavaKeystore(certificate, privateKey);
        }
        catch( Exception e ) {
            throw new InternalException(e);
        }
    }

    private X509Certificate certFromString(String pem) throws IOException {
        return (X509Certificate)readPemObject(pem);
    }

    private KeyStore createJavaKeystore(X509Certificate cert, PrivateKey key) throws NoSuchProviderException, KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException {
        KeyStore store = KeyStore.getInstance("JKS", "SUN");
        char[] pw = PASSWORD.toCharArray();

        store.load(null, pw);
        store.setKeyEntry(ENTRY_ALIAS, key, pw, new java.security.cert.Certificate[] {cert});
        return store;
    }
    public KeyStore getKeystore() {
        return keystore;
    }

    private PrivateKey keyFromString(String pem) throws IOException {
        KeyPair keypair = (KeyPair)readPemObject(pem);

        if( keypair == null ) {
            throw new IOException("Could not parse key from string");
        }
        return keypair.getPrivate();
    }

    private Object readPemObject(String pemString) throws IOException {
        StringReader strReader = new StringReader(pemString);
        PEMReader pemReader = new PEMReader(strReader, null, BouncyCastleProvider.PROVIDER_NAME);

        try {
            return pemReader.readObject();
        }
        finally {
            strReader.close();
            pemReader.close();
        }
    }
}
