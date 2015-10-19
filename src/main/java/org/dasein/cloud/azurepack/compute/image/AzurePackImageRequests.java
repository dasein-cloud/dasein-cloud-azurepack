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

import org.apache.http.client.methods.RequestBuilder;
import org.dasein.cloud.azurepack.AzurePackCloud;

/**
 * Created by vmunthiu on 3/3/2015.
 */
public class AzurePackImageRequests {
    private final String VHD_RESOURCES = "%s/%s/services/systemcenter/vmm/VirtualHardDisks";
    private final String VMTEMPLATES_RESOURCES = "%s/%s/services/systemcenter/vmm/VMTemplates";

    private AzurePackCloud provider;

    public AzurePackImageRequests(AzurePackCloud provider){
        this.provider = provider;
    }

    public RequestBuilder listVirtualDisks(){
        RequestBuilder requestBuilder = RequestBuilder.get();
        addCommonHeaders(requestBuilder);
        requestBuilder.setUri(String.format(VHD_RESOURCES, this.provider.getContext().getEndpoint(), this.provider.getContext().getAccountNumber()));
        return requestBuilder;
    }

    public RequestBuilder listTemplates(){
        RequestBuilder requestBuilder = RequestBuilder.get();
        addCommonHeaders(requestBuilder);
        requestBuilder.setUri(String.format(VMTEMPLATES_RESOURCES, this.provider.getContext().getEndpoint(), this.provider.getContext().getAccountNumber()));
        return requestBuilder;
    }

    private void addCommonHeaders(RequestBuilder requestBuilder) {
        requestBuilder.addHeader("x-ms-version", "2014-02-01");
        requestBuilder.addHeader("Accept", "application/json");
    }
}
