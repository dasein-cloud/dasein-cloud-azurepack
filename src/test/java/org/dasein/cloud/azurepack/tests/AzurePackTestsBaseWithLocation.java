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

import java.util.Arrays;

import mockit.Mocked;
import mockit.NonStrictExpectations;

import org.dasein.cloud.CloudException;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.azurepack.AzurePackDataCenterService;
import org.dasein.cloud.dc.DataCenter;
import org.dasein.cloud.dc.Region;
import org.junit.Before;

public class AzurePackTestsBaseWithLocation extends AzurePackTestsBase {

	@Mocked
	private AzurePackDataCenterService azurePackDataCenterService;
	
	protected final String REGION_NAME = "test_region_name";
	protected final String DATACENTER_ID = "test_datacenter_id";
	protected final String DATACENTER_NAME = "test_datacenter_name";
	
	@Before
	public void setUp() throws CloudException, InternalException {
		super.setUp();
		
		final Region region = new Region(REGION, REGION_NAME, true, true);
        final DataCenter dataCenter = new DataCenter(DATACENTER_ID, DATACENTER_NAME, REGION, true, true);
        new NonStrictExpectations() {{
        	azurePackCloudMock.getDataCenterServices(); result = azurePackDataCenterService;
        	azurePackDataCenterService.getRegion(REGION); result= region;
        	azurePackDataCenterService.listDataCenters(REGION); result = Arrays.asList(dataCenter);
        	azurePackDataCenterService.getDataCenter(DATACENTER_ID); result = dataCenter;
        }};
		
	}
	
}
