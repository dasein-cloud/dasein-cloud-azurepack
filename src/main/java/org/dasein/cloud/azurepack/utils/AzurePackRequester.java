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

package org.dasein.cloud.azurepack.utils;

import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.dasein.cloud.CloudException;
import org.dasein.cloud.CloudProvider;
import org.dasein.cloud.azurepack.AzurePackCloud;
import org.dasein.cloud.util.requester.DaseinResponseHandler;
import org.dasein.cloud.util.requester.fluent.DaseinRequest;
import org.dasein.cloud.util.requester.fluent.Requester;
import org.dasein.cloud.util.requester.streamprocessors.JsonStreamToObjectProcessor;

import javax.annotation.Nonnull;

/**
 * Created by vmunthiu on 2/20/2015.
 */
public class AzurePackRequester extends DaseinRequest {
    private CloudProvider provider;
    private HttpClientBuilder httpClientBuilder;
    private HttpUriRequest httpUriRequestBuilder;

    public AzurePackRequester(AzurePackCloud provider, HttpUriRequest httpUriRequest) throws CloudException {
        this(provider, provider.getAzureClientBuilder(), httpUriRequest);
    }
    public AzurePackRequester(CloudProvider provider, HttpClientBuilder httpClientBuilder, HttpUriRequest httpUriRequestBuilder) {
        super(provider, httpClientBuilder, httpUriRequestBuilder);
        this.provider = provider;
        this.httpClientBuilder = httpClientBuilder;
        this.httpUriRequestBuilder = httpUriRequestBuilder;
    }

    @Override
    public <T> Requester<T> withJsonProcessor(@Nonnull Class<T> classType) {
        return new AzurePackRequestExecutor<T>(this.provider, this.httpClientBuilder, this.httpUriRequestBuilder,
                new DaseinResponseHandler<T>(new JsonStreamToObjectProcessor(), classType));
    }
}
