package org.dasein.cloud.azurepack.platform;

import org.dasein.cloud.CloudProvider;
import org.dasein.cloud.azurepack.AzurePackCloud;
import org.dasein.cloud.platform.AbstractPlatformServices;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by vmunthiu on 6/16/2015.
 */
public class AzurePackPlatformServices extends AbstractPlatformServices {
    private AzurePackCloud provider;

    public AzurePackPlatformServices(AzurePackCloud provider) {
        super(provider);
        this.provider = provider;
    }

    @Override
    public @Nullable AzurePackDatabaseSupport getRelationalDatabaseSupport() {
        return AzurePackDatabaseSupport.getInstance(provider);
    }
}
