package org.dasein.cloud.azurepack.network;

import org.dasein.cloud.azurepack.AzurePackCloud;
import org.dasein.cloud.network.AbstractNetworkServices;
import org.dasein.cloud.network.VLANSupport;
import org.dasein.cloud.network.IpAddressSupport;

import javax.annotation.Nullable;

/**
 * Created by vmunthiu on 3/26/2015.
 */
public class AzurePackNetworkServices extends AbstractNetworkServices<AzurePackCloud> {
    private AzurePackCloud provider;

    public AzurePackNetworkServices(AzurePackCloud cloud) {
        super(cloud);
        this.provider = cloud;
    }

    public @Nullable IpAddressSupport getIpAddressSupport() {
        return new AzurePackIpAddressSupport(this.provider);
    }

    @Nullable
    @Override
    public VLANSupport getVlanSupport() {
        return new AzurePackNetworkSupport(provider);
    }
}
