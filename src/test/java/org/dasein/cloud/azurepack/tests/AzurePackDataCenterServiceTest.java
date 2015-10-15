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
import org.dasein.cloud.util.requester.DaseinRequestExecutor;
import org.junit.Before;
import org.junit.Test;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.dasein.cloud.azurepack.tests.HttpMethodAsserts.assertGet;
import static org.junit.Assert.assertEquals;
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
    private final String DATA_CENTER_NAME = "TEST_DATA_CENTER";


    private AzurePackDataCenterService azurePackDataCenterService;

    @Before
    public void setUp() throws CloudException, InternalException {
        super.setUp();
        azurePackDataCenterService = new AzurePackDataCenterService(azurePackCloudMock);
    }

    @Test
    public void listDataCentersShouldReturnCorrectResult() throws CloudException, InternalException, LoginException {
        new MockUp<DaseinRequestExecutor>() {
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
                validWapCloudModel.setName(DATA_CENTER_NAME);
                validWapCloudModel.setStampId(DATA_CENTER_ID);
                wapCloudModels.add(validWapCloudModel);

                wapCloudsModel.setClouds(wapCloudModels);
                return wapCloudsModel;
            }
        };

        List<DataCenter> dataCenters = IteratorUtils.toList(
                azurePackDataCenterService.listDataCenters(REGION).iterator());

        assertEquals("listDataCenters doesn't return correct data center size", 1, dataCenters.size());
        DataCenter dataCenter = dataCenters.get(0);
        assertEquals("listDataCenters doesn't return correct data center", REGION, dataCenter.getRegionId());
        assertEquals("listDataCenters doesn't return correct data center", DATA_CENTER_ID, dataCenter.getProviderDataCenterId());
        assertEquals("listDataCenters doesn't return correct data center", DATA_CENTER_NAME, dataCenter.getName());
        assertTrue("listDataCenters doesn't return correct data center", dataCenter.isActive());
        assertTrue("listDataCenters doesn't return correct data center", dataCenter.isAvailable());
    }
}




