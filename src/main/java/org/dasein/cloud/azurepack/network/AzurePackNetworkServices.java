/**
 * Copyright (C) 2009-2015 Dell, Inc
 * See annotations for authorship information
 *
 * ====================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ====================================================================
 */

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
