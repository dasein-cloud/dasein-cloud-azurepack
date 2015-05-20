package org.dasein.cloud.azurepack.compute.image;

import org.apache.commons.collections.Closure;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.http.client.methods.HttpUriRequest;
import org.dasein.cloud.CloudException;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.OperationNotSupportedException;
import org.dasein.cloud.azurepack.AzurePackCloud;
import org.dasein.cloud.azurepack.compute.image.model.WAPVhdModel;
import org.dasein.cloud.azurepack.compute.image.model.WAPVhdsModel;
import org.dasein.cloud.azurepack.utils.AzurePackRequester;
import org.dasein.cloud.compute.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by vmunthiu on 2/27/2015.
 */
public class AzurePackImageSupport extends AbstractImageSupport<AzurePackCloud> {
    private AzurePackCloud provider;

    public AzurePackImageSupport(@Nonnull AzurePackCloud provider) {
        super(provider);
        this.provider = provider;
    }

    @Override
    public ImageCapabilities getCapabilities() throws CloudException, InternalException {
        return new AzurePackImageCapabilities(provider);
    }

    @Nullable
    @Override
    public MachineImage getImage(final String providerImageId) throws CloudException, InternalException {
        return (MachineImage)CollectionUtils.find(getAllImages(), new Predicate() {
            @Override
            public boolean evaluate(Object object) {
                MachineImage image = (MachineImage) object;
                return image.getProviderMachineImageId().equalsIgnoreCase(providerImageId);
            }
        });
    }

    @Override
    public boolean isSubscribed() throws CloudException, InternalException {
        return true;
    }

    @Nonnull
    @Override
    public Iterable<MachineImage> listImages(@Nullable final ImageFilterOptions options) throws CloudException, InternalException {
        if (!options.getImageClass().equals(ImageClass.MACHINE)) {
            return Collections.emptyList();
        }

        options.withAccountNumber(provider.getContext().getAccountNumber());
        return getImages(new Predicate() {
            @Override
            public boolean evaluate(Object object) {
                return options.matches((MachineImage)object);
            }
        });
    }

    @Override
    public @Nonnull Iterable<MachineImage> searchPublicImages(@Nonnull final ImageFilterOptions options) throws CloudException, InternalException {
        return getImages(new Predicate() {
            @Override
            public boolean evaluate(Object object) {
                MachineImage image = (MachineImage) object;
                return !image.getProviderOwnerId().equalsIgnoreCase(provider.getContext().getAccountNumber()) && options.matches(image) ;
            }
        });
    }

    @Override
    public @Nonnull Iterable<MachineImage> searchImages(@Nullable String accountNumber, @Nullable String keyword, @Nullable Platform platform, @Nullable Architecture architecture, @Nullable ImageClass ... imageClasses) throws CloudException, InternalException {
        final ImageFilterOptions options = ImageFilterOptions.getInstance();

        if (accountNumber != null) {
            options.withAccountNumber(accountNumber);
        }
        if( keyword != null ) {
            options.matchingRegex(keyword);
        }
        if( architecture != null ) {
            options.withArchitecture(architecture);
        }
        if( platform != null ) {
            options.onPlatform(platform);
        }
        if( imageClasses == null || imageClasses.length < 1 ) {
            return getImages(new Predicate() {
                @Override
                public boolean evaluate(Object object) {
                    return options.matches((MachineImage) object);
                }
            });
        }
        else if( imageClasses.length == 1 ) {
            options.withImageClass(imageClasses[0]);
            return getImages(new Predicate() {
                @Override
                public boolean evaluate(Object object) {
                    return options.matches((MachineImage) object);
                }
            });
        }
        else {
            ArrayList<MachineImage> images = new ArrayList<MachineImage>();

            for( MachineImage img : getAllImages() ) {
                boolean matches = false;

                for( ImageClass cls : imageClasses ) {
                    if( img.getImageClass().equals(cls) ) {
                        matches = true;
                        break;
                    }
                }
                if( matches && options.matches(img) ) {
                    images.add(img);
                }
            }
            return images;
        }
    }

    private List<MachineImage> getImages(Predicate predicate) throws CloudException {
        List<MachineImage> images = getAllImages();
        CollectionUtils.filter(images, predicate);
        return images;
    }

    private List<MachineImage> getAllImages() throws CloudException {
        HttpUriRequest listVhdsRequest = new AzurePackImageRequests(this.provider).listVirtualDisks().build();
        WAPVhdsModel vhds = new AzurePackRequester(this.provider, listVhdsRequest).withJsonProcessor(WAPVhdsModel.class).execute();

        final List<MachineImage> images = new ArrayList<MachineImage>();

        final String regionId = this.provider.getContext().getRegionId();
        CollectionUtils.forAllDo(vhds.getVhds(), new Closure() {
            @Override
            public void execute(Object input) {
                WAPVhdModel vhd = (WAPVhdModel) input;
                MachineImage image = MachineImage.getInstance(vhd.getOwner().getRoleID() != null ? vhd.getOwner().getRoleID() : "wap", regionId, vhd.getId(),ImageClass.MACHINE,MachineImageState.ACTIVE,vhd.getName(),vhd.getDescription(),Architecture.I64, vhd.getOperatingSystemInstance().getOsType().toLowerCase().contains("windows") ? Platform.WINDOWS : Platform.UNIX);
                images.add(image);
            }
        });

        return images;
    }

    @Override
    public void remove(@Nonnull String providerImageId, boolean checkState) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Support for removing image is not currently supported");
    }
}
