/*
 *  *
 *  Copyright (C) 2009-2015 Dell, Inc.
 *  See annotations for authorship information
 *
 *  ====================================================================
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  ====================================================================
 *
 */

package org.dasein.cloud.azurepack.tests;

import mockit.Mock;
import mockit.MockUp;
import org.apache.commons.collections.IteratorUtils;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.dasein.cloud.CloudException;
import org.dasein.cloud.CloudProvider;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.azurepack.AzurePackDataCenterService;
import org.dasein.cloud.azurepack.model.WAPCloudModel;
import org.dasein.cloud.azurepack.model.WAPCloudsModel;
import org.dasein.cloud.dc.DataCenter;
import org.dasein.cloud.dc.Region;
import org.dasein.cloud.util.requester.DaseinRequestExecutor;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import static org.dasein.cloud.azurepack.tests.HttpMethodAsserts.assertGet;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Jeffrey Yan on 10/14/2015.
 *
 * @author Jeffrey Yan
 * @since 2015.09.1
 */
public class AzurePackDataCenterServiceTest extends AzurePackTestsBase {

    private final String CLOUD_RESOURCES = "%s/%s/services/systemcenter/vmm/Clouds";
    private final String DATA_CENTER_ID = UUID.randomUUID().toString();
    private final String REGION_NAME = "TEST_REGION_NAME";


    private AzurePackDataCenterService azurePackDataCenterService;

    @Before
    public void setUp() throws CloudException, InternalException {
        super.setUp();
        azurePackDataCenterService = new AzurePackDataCenterService(azurePackCloudMock);
    }

    @Test
    public void getDataCenterShouldReturnCorrectResult() throws CloudException, InternalException {
        new ListCloudsDaseinRequestExecutorMockUp();

        DataCenter dataCenter = azurePackDataCenterService.getDataCenter(DATA_CENTER_ID);
        assertDataCenter(dataCenter);
    }


    @Test
    public void listDataCentersShouldReturnCorrectResult() throws CloudException, InternalException {
        new ListCloudsDaseinRequestExecutorMockUp();

        List<DataCenter> dataCenters = IteratorUtils.toList(
                azurePackDataCenterService.listDataCenters(REGION).iterator());

        assertEquals("listDataCenters doesn't return correct data center size", 1, dataCenters.size());
        assertDataCenter(dataCenters.get(0));
    }

    @Test
    public void getRegionShouldReturnCorrectResult() throws CloudException, InternalException {
        new ListCloudsDaseinRequestExecutorMockUp();

        Region region = azurePackDataCenterService.getRegion(REGION);
        assertRegion(region);
    }

    @Test
    public void listRegionsShouldReturnCorrectResult() throws CloudException, InternalException {
        new ListCloudsDaseinRequestExecutorMockUp();

        List<Region> regions = IteratorUtils.toList(azurePackDataCenterService.listRegions().iterator());
        assertEquals("listRegions doesn't return correct region size", 2, regions.size());
        assertRegion(regions.get(1));
    }

    private void assertDataCenter(DataCenter dataCenter) {
        assertEquals("doesn't return correct data center", REGION, dataCenter.getRegionId());
        assertEquals("doesn't return correct data center", DATA_CENTER_ID, dataCenter.getProviderDataCenterId());
        assertEquals("doesn't return correct data center", REGION_NAME, dataCenter.getName());
        assertTrue("doesn't return correct data center", dataCenter.isActive());
        assertTrue("doesn't return correct data center", dataCenter.isAvailable());
    }

    private void assertRegion(Region region) {
        assertEquals("doesn't return correct region", REGION, region.getProviderRegionId());
        assertEquals("doesn't return correct region", "US", region.getJurisdiction());
        assertEquals("doesn't return correct region", REGION_NAME, region.getName());
        assertTrue("doesn't return correct region", region.isActive());
        assertTrue("doesn't return correct region", region.isAvailable());
    }

    public class ListCloudsDaseinRequestExecutorMockUp extends MockUp<DaseinRequestExecutor> {
        @Mock
        public void $init(CloudProvider provider, HttpClientBuilder clientBuilder, HttpUriRequest request, ResponseHandler handler) {
            assertGet(request, String.format(CLOUD_RESOURCES, ENDPOINT, ACCOUNT_NO));
        }

        @Mock
        public Object execute() {
            WAPCloudsModel wapCloudsModel = new WAPCloudsModel();
            wapCloudsModel.setOdataMetadata("https://smapi-server:30006/2222aa22-22a2-2a22-2a22-2aaaaa2aaaaa/services/systemcenter/vmm/$metadata#Clouds");

            List<WAPCloudModel> wapCloudModels = new ArrayList<WAPCloudModel>();
            WAPCloudModel invalidWapCloudModel = new WAPCloudModel();
            invalidWapCloudModel.setId("3333b333-b3bb-333b-333b-3bbb33333bbb");
            invalidWapCloudModel.setName("KatalCloud");
            invalidWapCloudModel.setStampId("444c4444-c44c-4444-c4c4-44ccc4444c4c");
            wapCloudModels.add(invalidWapCloudModel);

            WAPCloudModel validWapCloudModel = new WAPCloudModel();
            validWapCloudModel.setId(REGION);
            validWapCloudModel.setName(REGION_NAME);
            validWapCloudModel.setStampId(DATA_CENTER_ID);
            wapCloudModels.add(validWapCloudModel);

            wapCloudsModel.setClouds(wapCloudModels);
            return wapCloudsModel;
        }
    };

    @Test
    public void getProviderTermForDataCenterShouldReturnCorrectResult() throws CloudException, InternalException {
        assertEquals("getProviderTermForDataCenter doesn't return correct result", "Stamp",
                azurePackDataCenterService.getProviderTermForDataCenter(Locale.US));
    }

    @Test
    public void getProviderTermForRegionShouldReturnCorrectResult() throws CloudException, InternalException {
        assertEquals("getProviderTermForRegion doesn't return correct result", "Cloud",
                azurePackDataCenterService.getProviderTermForRegion(Locale.US));
    }

    @Test
    public void listResourcePoolsShouldReturnNull() throws CloudException, InternalException {
        assertNull("listResourcePools doesn't return null",
                azurePackDataCenterService.listResourcePools(DATA_CENTER_ID));
    }

    @Test
    public void getResourcePoolShouldReturnNull() throws CloudException, InternalException {
        assertNull("getResourcePool doesn't return null",
                azurePackDataCenterService.getResourcePool("resource_pool_id"));
    }

    @Test
    public void listStoragePoolsShouldReturnNull() throws CloudException, InternalException {
        assertNull("listStoragePools doesn't return null", azurePackDataCenterService.listStoragePools());
    }

    @Test
    public void getStoragePoolShouldReturnNull() throws CloudException, InternalException {
        assertNull("getStoragePool doesn't return null", azurePackDataCenterService.getStoragePool("storage_pool_id"));
    }

    @Test
    public void listVMFoldersShouldReturnNull() throws CloudException, InternalException {
        assertNull("listVMFolders doesn't return null", azurePackDataCenterService.listVMFolders());
    }

    @Test
    public void getVMFolderShouldReturnNull() throws CloudException, InternalException {
        assertNull("getVMFolder doesn't return null", azurePackDataCenterService.getVMFolder("vm_folder_id"));
    }
}




