package org.dasein.cloud.azurepack.compute;

import org.dasein.cloud.azurepack.AzurePackCloud;
import org.dasein.cloud.azurepack.compute.image.AzurePackImageSupport;
import org.dasein.cloud.azurepack.compute.vm.AzurePackVirtualMachineSupport;
import org.dasein.cloud.compute.AbstractComputeServices;
import org.dasein.cloud.compute.MachineImageSupport;
import org.dasein.cloud.compute.VirtualMachineSupport;

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
}
