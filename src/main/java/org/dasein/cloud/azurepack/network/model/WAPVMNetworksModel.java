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

package org.dasein.cloud.azurepack.network.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.dasein.cloud.azurepack.compute.vm.model.WAPVirtualMachineModel;

import java.util.List;

/**
 * Created by vmunthiu on 3/26/2015.
 */
public class WAPVMNetworksModel {
    @JsonProperty("odata.metadata")
    private String odataMetadata;
    @JsonProperty("value")
    private List<WAPVMNetworkModel> virtualMachineNetworks;

    public String getOdataMetadata() {
        return odataMetadata;
    }

    public void setOdataMetadata(String odataMetadata) {
        this.odataMetadata = odataMetadata;
    }

    public List<WAPVMNetworkModel> getVirtualMachineNetworks() {
        return virtualMachineNetworks;
    }

    public void setVirtualMachineNetworks(List<WAPVMNetworkModel> virtualMachineNetworks) {
        this.virtualMachineNetworks = virtualMachineNetworks;
    }
}
