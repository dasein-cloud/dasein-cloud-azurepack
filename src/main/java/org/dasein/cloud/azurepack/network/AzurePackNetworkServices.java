package org.dasein.cloud.azurepack.network;

import org.dasein.cloud.azurepack.AzurePackCloud;
import org.dasein.cloud.network.AbstractNetworkServices;
import org.dasein.cloud.network.VLANSupport;
import org.dasein.cloud.network.IpAddressSupport;

import javax.annotation.Nullable;

/**
 * Created by vmunthiu on 3/26/2015.
 */
public class AzurePackNetworkServices extends AbstractNetworkServices {
    private AzurePackCloud provider;

    public AzurePackNetworkServices(AzurePackCloud cloud) { this.provider = cloud; }

    public @Nullable IpAddressSupport getIpAddressSupport() {
        return null;
        //return new StaticIp(cloud);
    }

    @Nullable
    @Override
    public VLANSupport getVlanSupport() {
        return new AzurePackNetworkSupport(provider);
    }
}
