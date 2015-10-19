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

package org.dasein.cloud.azurepack.compute.image;

import org.dasein.cloud.*;
import org.dasein.cloud.azurepack.AzurePackCloud;
import org.dasein.cloud.compute.*;
import org.dasein.cloud.util.NamingConstraints;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Locale;

/**
 * Created by vmunthiu on 5/13/2015.
 */
public class AzurePackImageCapabilities extends AbstractCapabilities<AzurePackCloud> implements ImageCapabilities {
    public AzurePackImageCapabilities(AzurePackCloud provider) {
        super(provider);
    }

    @Override
    public boolean canBundle(VmState fromState) throws CloudException, InternalException {
        return false;
    }

    @Override
    public boolean canImage(VmState fromState) throws CloudException, InternalException {
        return false;
    }

    @Nonnull
    @Override
    public String getProviderTermForImage(Locale locale, ImageClass cls) {
        if (cls.equals(ImageClass.MACHINE)) {
            return "Virtual Hard Disk";
        }
        return "image";
    }

    @Nonnull
    @Override
    public String getProviderTermForCustomImage(@Nonnull Locale locale, @Nonnull ImageClass cls) {
        return "image";
    }

    @Nullable
    @Override
    public VisibleScope getImageVisibleScope() {
        return null;
    }

    @Nonnull
    @Override
    public Requirement identifyLocalBundlingRequirement() throws CloudException, InternalException {
        return Requirement.NONE;
    }

    @Nonnull
    @Override
    public Iterable<MachineImageFormat> listSupportedFormats() throws CloudException, InternalException {
        return Collections.singletonList(MachineImageFormat.VHD);
    }

    @Nonnull
    @Override
    public Iterable<MachineImageFormat> listSupportedFormatsForBundling() throws CloudException, InternalException {
        return Collections.emptyList();
    }

    @Nonnull
    @Override
    public Iterable<ImageClass> listSupportedImageClasses() throws CloudException, InternalException {
        return Collections.singletonList(ImageClass.MACHINE);
    }

    @Nonnull
    @Override
    public Iterable<MachineImageType> listSupportedImageTypes() throws CloudException, InternalException {
        return Collections.singletonList(MachineImageType.VOLUME);
    }

    @Override
    public boolean supportsDirectImageUpload() throws CloudException, InternalException {
        return false;
    }

    @Override
    public boolean supportsImageCapture(@Nonnull MachineImageType type) throws CloudException, InternalException {
        return false;
    }

    @Override
    public boolean supportsImageCopy() throws CloudException, InternalException {
        return false;
    }

    @Override
    public boolean supportsImageRemoval() throws CloudException, InternalException {
        return false;
    }

    @Override
    public boolean supportsImageSharing() throws CloudException, InternalException {
        return false;
    }

    @Override
    public boolean supportsImageSharingWithPublic() throws CloudException, InternalException {
        return false;
    }

    @Override
    public boolean supportsListingAllRegions() throws CloudException, InternalException {
        return false;
    }

    @Override
    public boolean supportsPublicLibrary(@Nonnull ImageClass cls) throws CloudException, InternalException {
        return true;
    }

    @Override
    public boolean imageCaptureDestroysVM() throws CloudException, InternalException {
        return false;
    }

    @Override
    public NamingConstraints getImageNamingConstraints(){
        return NamingConstraints.getStrictInstance(3, 15).constrainedBy(new char[]{'-', '.'});
    }
}
