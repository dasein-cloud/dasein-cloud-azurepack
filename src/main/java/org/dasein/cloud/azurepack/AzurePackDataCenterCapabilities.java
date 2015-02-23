package org.dasein.cloud.azurepack;

import org.dasein.cloud.AbstractCapabilities;
import org.dasein.cloud.dc.DataCenterCapabilities;

import java.util.Locale;

/**
 * Created by vmunthiu on 2/20/2015.
 */
public class AzurePackDataCenterCapabilities extends AbstractCapabilities<AzurePackCloud> implements DataCenterCapabilities {
    public AzurePackDataCenterCapabilities(AzurePackCloud provider) {
        super(provider);
    }

    @Override
    public String getProviderTermForDataCenter(Locale locale) {
        return "Stamp";
    }

    @Override
    public String getProviderTermForRegion(Locale locale) {
        return "Cloud";
    }

    @Override
    public boolean supportsAffinityGroups() {
        return false;
    }

    @Override
    public boolean supportsResourcePools() {
        return false;
    }

    @Override
    public boolean supportsStoragePools() {
        return false;
    }

    @Override
    public boolean supportsFolders() {
        return false;
    }
}
