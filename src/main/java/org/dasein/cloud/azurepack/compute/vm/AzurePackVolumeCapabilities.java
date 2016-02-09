package org.dasein.cloud.azurepack.compute.vm;

import org.dasein.cloud.CloudException;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.Requirement;
import org.dasein.cloud.compute.Platform;
import org.dasein.cloud.compute.VmState;
import org.dasein.cloud.compute.VolumeCapabilities;
import org.dasein.cloud.compute.VolumeFormat;
import org.dasein.cloud.util.NamingConstraints;
import org.dasein.util.uom.storage.Gigabyte;
import org.dasein.util.uom.storage.Storage;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Locale;

/**
 * Created by vmunthiu on 2/1/2016.
 */
public class AzurePackVolumeCapabilities implements VolumeCapabilities {
    @Override
    public boolean canAttach(VmState vmState) throws InternalException, CloudException {
        return vmState == VmState.STOPPED;
    }

    @Override
    public boolean canDetach(VmState vmState) throws InternalException, CloudException {
        return vmState == VmState.STOPPED;
    }

    @Override
    public int getMaximumVolumeCount() throws InternalException, CloudException {
        return 0;
    }

    @Override
    public int getMaximumVolumeSizeIOPS() throws InternalException, CloudException {
        return 0;
    }

    @Override
    public int getMinimumVolumeSizeIOPS() throws InternalException, CloudException {
        return 0;
    }

    @Nonnull
    @Override
    public Storage<Gigabyte> getMaximumVolumeSize() throws InternalException, CloudException {
        return null;
    }

    @Nonnull
    @Override
    public Storage<Gigabyte> getMinimumVolumeSize() throws InternalException, CloudException {
        return null;
    }

    @Nonnull
    @Override
    public NamingConstraints getVolumeNamingConstraints() throws CloudException, InternalException {
        return null;
    }

    @Nonnull
    @Override
    public String getProviderTermForVolume(@Nonnull Locale locale) {
        return "Virtual Disk Drive";
    }

    @Nonnull
    @Override
    public Requirement getVolumeProductRequirement() throws InternalException, CloudException {
        return Requirement.NONE;
    }

    @Nonnull
    @Override
    public Requirement getDeviceIdOnAttachRequirement() throws InternalException, CloudException {
        return Requirement.NONE;
    }

    @Override
    public boolean supportsIOPSVolumes() throws InternalException, CloudException {
        return false;
    }

    @Override
    public boolean isVolumeSizeDeterminedByProduct() throws InternalException, CloudException {
        return false;
    }

    @Nonnull
    @Override
    public Iterable<String> listPossibleDeviceIds(@Nonnull Platform platform) throws InternalException, CloudException {
        return null;
    }

    @Nonnull
    @Override
    public Iterable<VolumeFormat> listSupportedFormats() throws InternalException, CloudException {
        return Arrays.asList(VolumeFormat.BLOCK);
    }

    @Nonnull
    @Override
    public Requirement requiresVMOnCreate() throws InternalException, CloudException {
        return null;
    }

    @Override
    public boolean supportsAttach() {
        return true;
    }

    @Override
    public boolean supportsDetach() {
        return true;
    }

    @Nonnull
    @Override
    public String getAccountNumber() {
        return null;
    }

    @Nonnull
    @Override
    public String getRegionId() {
        return null;
    }
}
