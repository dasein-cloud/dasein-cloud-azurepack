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

import mockit.Mocked;
import mockit.NonStrictExpectations;
import org.apache.http.impl.client.HttpClientBuilder;
import org.dasein.cloud.Cloud;
import org.dasein.cloud.CloudException;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.ProviderContext;
import org.dasein.cloud.azurepack.AzurePackCloud;
import org.junit.Before;

/**
 * Created by Jeffrey Yan on 10/14/2015.
 *
 * @author Jeffrey Yan
 * @since 2015.09.1
 */
public class AzurePackTestsBase {
    @Mocked protected ProviderContext providerContextMock;
    @Mocked protected AzurePackCloud azurePackCloudMock;
    @Mocked protected Cloud cloudMock;
    @Mocked protected HttpClientBuilder httpClientBuilderMock;

    protected final String ENDPOINT = "TEST_END_POINT";
    protected final String ACCOUNT_NO = "TEST_ACCOUNT_NO";
    protected final String REGION = "TEST_REGION";

    @Before
    public void setUp() throws CloudException, InternalException {
        new NonStrictExpectations() {{
            azurePackCloudMock.getContext(); result = providerContextMock;
            azurePackCloudMock.getAzureClientBuilder(); result = httpClientBuilderMock;
            providerContextMock.getAccountNumber(); result = ACCOUNT_NO;
            providerContextMock.getRegionId(); result = REGION;
            providerContextMock.getEndpoint(); result = ENDPOINT;
            providerContextMock.getCloud(); result = cloudMock;
            cloudMock.getEndpoint(); result = ENDPOINT;
        }};
    }
}
