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

package org.dasein.cloud.azurepack.compute;

import org.dasein.cloud.azurepack.AzurePackCloud;
import org.dasein.cloud.azurepack.compute.image.AzurePackImageSupport;
import org.dasein.cloud.azurepack.compute.vm.AzurePackVirtualMachineSupport;
import org.dasein.cloud.azurepack.compute.vm.AzurePackVolumeSupport;
import org.dasein.cloud.compute.AbstractComputeServices;
import org.dasein.cloud.compute.MachineImageSupport;
import org.dasein.cloud.compute.VirtualMachineSupport;
import org.dasein.cloud.compute.VolumeSupport;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by vmunthiu on 2/27/2015.
 */
public class AzurePackComputeService extends AbstractComputeServices<AzurePackCloud> {
    private AzurePackCloud provider;

    public AzurePackComputeService(@Nonnull AzurePackCloud provider) {
        super(provider);
        this.provider = provider;
    }


    @Override
    public MachineImageSupport getImageSupport() {
        return new AzurePackImageSupport(provider);
    }

    @Override
    public VirtualMachineSupport getVirtualMachineSupport() {
        return new AzurePackVirtualMachineSupport(provider);
    }

    @Override
    public @Nullable
    VolumeSupport getVolumeSupport() {
        return new AzurePackVolumeSupport(provider);
    }
}
