package org.dasein.cloud.azurepack;

import org.apache.commons.collections.Closure;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.log4j.Logger;
import org.dasein.cloud.CloudException;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.azurepack.model.WAPCloudModel;
import org.dasein.cloud.azurepack.model.WAPCloudsModel;
import org.dasein.cloud.azurepack.utils.AzurePackRequester;
import org.dasein.cloud.azurepack.utils.LoggerUtils;
import org.dasein.cloud.dc.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

/**
 * Created by vmunthiu on 2/20/2015.
 */
public class AzurePackDataCenterService implements DataCenterServices {
    static private final Logger logger = LoggerUtils.getLogger(AzurePackDataCenterService.class);

    private AzurePackCloud provider;

    public AzurePackDataCenterService(AzurePackCloud provider){
        this.provider = provider;
    }

    @Nonnull
    @Override
    public DataCenterCapabilities getCapabilities() throws InternalException, CloudException {
        return null;
    }

    @Override
    public DataCenter getDataCenter(final String providerDataCenterId) throws InternalException, CloudException {
        return (DataCenter)CollectionUtils.find(this.listDataCenters(provider.getContext().getRegionId()), new Predicate() {
            @Override
            public boolean evaluate(Object object) {
                return ((DataCenter) object).getProviderDataCenterId().equalsIgnoreCase(providerDataCenterId);
            }
        });
    }

    @Override
    public String getProviderTermForDataCenter(Locale locale) {
        return null;
    }

    @Override
    public String getProviderTermForRegion(Locale locale) {
        return null;
    }

    @Override
    public Region getRegion(final String providerRegionId) throws InternalException, CloudException {
        return (Region) CollectionUtils.find(this.listRegions(), new Predicate() {
            @Override
            public boolean evaluate(Object object) {
                return ((Region)object).getProviderRegionId().equalsIgnoreCase(providerRegionId);
            }
        });
    }

    @Override
    public Collection<DataCenter> listDataCenters(final String providerRegionId) throws InternalException, CloudException {
        HttpUriRequest httpUriRequest = new AzurePackDataCenterRequests(provider).listClouds().build();

        WAPCloudsModel result = new AzurePackRequester(provider, httpUriRequest).withJsonProcessor(WAPCloudsModel.class).execute();

        List<WAPCloudModel> wapClouds = result.getClouds();
        CollectionUtils.filter(wapClouds, new Predicate() {
            @Override
            public boolean evaluate(Object object) {
                return ((WAPCloudModel) object).getId().equalsIgnoreCase(providerRegionId);
            }
        });

        final List<DataCenter> dataCenters = new ArrayList<DataCenter>();

        CollectionUtils.forAllDo(wapClouds, new Closure() {
            @Override
            public void execute(Object input) {
                WAPCloudModel cloudModel = (WAPCloudModel) input;

                DataCenter dataCenter = new DataCenter();
                dataCenter.setName(cloudModel.getName());
                dataCenter.setActive(true);
                dataCenter.setAvailable(true);
                dataCenter.setRegionId(providerRegionId);
                dataCenter.setProviderDataCenterId(cloudModel.getStampId());

                dataCenters.add(dataCenter);
            }
        });

        return dataCenters;
    }

    @Override
    public Collection<Region> listRegions() throws InternalException, CloudException {
        HttpUriRequest httpUriRequest = new AzurePackDataCenterRequests(provider).listClouds().build();

        WAPCloudsModel result = new AzurePackRequester(provider, httpUriRequest).withJsonProcessor(WAPCloudsModel.class).execute();

        final List<Region> regions = new ArrayList<Region>();

        CollectionUtils.forAllDo(result.getClouds(), new Closure() {
            @Override
            public void execute(Object input) {
                WAPCloudModel cloudModel = (WAPCloudModel) input;
                Region region = new Region();
                region.setName(cloudModel.getName());
                region.setProviderRegionId(cloudModel.getId());
                region.setActive(true);
                region.setAvailable(true);
                region.setJurisdiction("US");

                regions.add(region);
            }
        });

        return regions;
    }

    @Nonnull
    @Override
    public Collection<ResourcePool> listResourcePools(String providerDataCenterId) throws InternalException, CloudException {
        return null;
    }

    @Nullable
    @Override
    public ResourcePool getResourcePool(String providerResourcePoolId) throws InternalException, CloudException {
        return null;
    }

    @Nonnull
    @Override
    public Collection<StoragePool> listStoragePools() throws InternalException, CloudException {
        return null;
    }

    @Nonnull
    @Override
    public StoragePool getStoragePool(String providerStoragePoolId) throws InternalException, CloudException {
        return null;
    }

    @Nonnull
    @Override
    public Collection<Folder> listVMFolders() throws InternalException, CloudException {
        return null;
    }

    @Nonnull
    @Override
    public Folder getVMFolder(String providerVMFolderId) throws InternalException, CloudException {
        return null;
    }
}
