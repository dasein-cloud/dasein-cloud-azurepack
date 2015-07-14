package org.dasein.cloud.azurepack.compute.image;

import org.apache.commons.collections.Closure;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.http.client.methods.HttpUriRequest;
import org.dasein.cloud.CloudException;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.OperationNotSupportedException;
import org.dasein.cloud.azurepack.AzurePackCloud;
import org.dasein.cloud.azurepack.compute.image.model.WAPTemplateModel;
import org.dasein.cloud.azurepack.compute.image.model.WAPTemplatesModel;
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
        if (options != null && !ImageClass.MACHINE.equals(options.getImageClass())) {
            return Collections.emptyList();
        }

        if(options != null)
            options.withAccountNumber(provider.getContext().getAccountNumber());

        return getImages(new Predicate() {
            @Override
            public boolean evaluate(Object object) {
                if(options != null)
                    return options.matches((MachineImage)object);
                else
                    return provider.getContext().getAccountNumber().equals(((MachineImage)object).getProviderOwnerId());
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
        List<MachineImage> images = new ArrayList<MachineImage>();
        images.addAll(getAllVhds());
        images.addAll(getAllTemplates());
        return images;
    }

    private List<MachineImage> getAllTemplates() throws CloudException {
        HttpUriRequest listTemplatesRequest = new AzurePackImageRequests(this.provider).listTemplates().build();
        WAPTemplatesModel templatesModel = new AzurePackRequester(this.provider, listTemplatesRequest).withJsonProcessor(WAPTemplatesModel.class).execute();

        final List<MachineImage> images = new ArrayList<MachineImage>();
        final String regionId = this.provider.getContext().getRegionId();

        CollectionUtils.forAllDo(templatesModel.getTemplates(), new Closure() {
            @Override
            public void execute(Object input) {
                WAPTemplateModel templateModel = (WAPTemplateModel)input;
                if(templateModel.getEnabled().equalsIgnoreCase("true")) {
                    MachineImage image = MachineImage.getInstance(templateModel.getOwner().getRoleID() != null ? templateModel.getOwner().getRoleID() : "wap",
                            regionId,
                            templateModel.getId(),
                            ImageClass.MACHINE,
                            MachineImageState.ACTIVE,
                            templateModel.getName(),
                            templateModel.getDescription(),
                            Architecture.I64,
                            templateModel.getOperatingSystemInstance().getOsType().toLowerCase().contains("windows") ? Platform.WINDOWS : Platform.UNIX);
                    image.setTag("type", "template");
                    image.setTag("ProductKeyHasValue", templateModel.getProductKeyHasValue());
                    images.add(image);
                }
            }
        });

        return images;
    }


    private List<MachineImage> getAllVhds() throws CloudException {
        HttpUriRequest listVhdsRequest = new AzurePackImageRequests(this.provider).listVirtualDisks().build();
        WAPVhdsModel vhds = new AzurePackRequester(this.provider, listVhdsRequest).withJsonProcessor(WAPVhdsModel.class).execute();

        final List<MachineImage> images = new ArrayList<MachineImage>();

        final String regionId = this.provider.getContext().getRegionId();
        CollectionUtils.forAllDo(vhds.getVhds(), new Closure() {
            @Override
            public void execute(Object input) {
                WAPVhdModel vhd = (WAPVhdModel) input;
                if(vhd.getEnabled().equalsIgnoreCase("true")) {
                    MachineImage image = MachineImage.getInstance((vhd.getOwner() != null && vhd.getOwner().getRoleID() != null) ? vhd.getOwner().getRoleID() : "wap",
                            regionId,
                            vhd.getId(),
                            ImageClass.MACHINE,
                            MachineImageState.ACTIVE,
                            vhd.getName(),
                            vhd.getDescription(),
                            Architecture.I64,
                            vhd.getOperatingSystemInstance().getOsType().toLowerCase().contains("windows") ? Platform.WINDOWS : Platform.UNIX);
                    image.setTag("type", "vhd");
                    images.add(image);
                }
            }
        });

        return images;
    }

    @Override
    public void remove(@Nonnull String providerImageId, boolean checkState) throws CloudException, InternalException {
        throw new OperationNotSupportedException("Support for removing image is not currently supported");
    }
}
