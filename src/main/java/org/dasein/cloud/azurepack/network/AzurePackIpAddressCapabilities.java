package org.dasein.cloud.azurepack.network;

import org.dasein.cloud.AbstractCapabilities;
import org.dasein.cloud.CloudException;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.Requirement;
import org.dasein.cloud.azurepack.AzurePackCloud;
import org.dasein.cloud.compute.VmState;
import org.dasein.cloud.network.IPAddressCapabilities;
import org.dasein.cloud.network.IPVersion;

import javax.annotation.Nonnull;
import java.util.Locale;

/**
 * Created by vmunthiu on 7/16/2015.
 */
public class AzurePackIpAddressCapabilities extends AbstractCapabilities<AzurePackCloud> implements IPAddressCapabilities {
    public AzurePackIpAddressCapabilities(@Nonnull AzurePackCloud provider) {
        super(provider);
    }

    @Nonnull
    @Override
    public String getProviderTermForIpAddress(Locale locale) {
        return null;
    }

    @Nonnull
    @Override
    public Requirement identifyVlanForVlanIPRequirement() throws CloudException, InternalException {
        return null;
    }

    @Nonnull
    @Override
    public Requirement identifyVlanForIPRequirement() throws CloudException, InternalException {
        return null;
    }

    @Nonnull
    @Override
    public Requirement identifyVMForPortForwarding() throws CloudException, InternalException {
        return Requirement.REQUIRED;
    }

    @Override
    public boolean isAssigned(@Nonnull IPVersion version) throws CloudException, InternalException {
        return false;
    }

    @Override
    public boolean canBeAssigned(@Nonnull VmState vmState) throws CloudException, InternalException {
        return false;
    }

    @Override
    public boolean isAssignablePostLaunch(@Nonnull IPVersion version) throws CloudException, InternalException {
        return false;
    }

    @Override
    public boolean isForwarding(IPVersion version) throws CloudException, InternalException {
        return false;
    }

    @Override
    public boolean isRequestable(@Nonnull IPVersion version) throws CloudException, InternalException {
        return false;
    }

    @Nonnull
    @Override
    public Iterable<IPVersion> listSupportedIPVersions() throws CloudException, InternalException {
        return null;
    }

    @Override
    public boolean supportsVLANAddresses(@Nonnull IPVersion ofVersion) throws InternalException, CloudException {
        return false;
    }
}
