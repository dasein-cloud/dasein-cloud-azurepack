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

package org.dasein.cloud.azurepack.compute.vm.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by vmunthiu on 4/2/2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WAPNewAdapterModel {
    @JsonProperty("odata.type")
    private String odataType = "VMM.NewVMVirtualNetworkAdapterInput";
    //@JsonProperty("odata.metadata")
    //private String odataMetadata = null;
    @JsonProperty("IPv4AddressType")
    private String ipv4AddressType;
    @JsonProperty("IPv6AddressType")
    private String ipv6AddressType;
    @JsonProperty("MACAddress")
    private String macAddress;
    @JsonProperty("MACAddressType")
    private String macAddressType;
    @JsonProperty("VLanEnabled")
    private String vlanEnabled;
    @JsonProperty("VLanId")
    private String vlanId;
    @JsonProperty("VMNetworkName")
    private String vmNetworkName;

    public String getIpv4AddressType() {
        return ipv4AddressType;
    }

    public void setIpv4AddressType(String ipv4AddressType) {
        this.ipv4AddressType = ipv4AddressType;
    }

    public String getIpv6AddressType() {
        return ipv6AddressType;
    }

    public void setIpv6AddressType(String ipv6AddressType) {
        this.ipv6AddressType = ipv6AddressType;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getMacAddressType() {
        return macAddressType;
    }

    public void setMacAddressType(String macAddressType) {
        this.macAddressType = macAddressType;
    }

    public String getVlanEnabled() {
        return vlanEnabled;
    }

    public void setVlanEnabled(String vlanEnabled) {
        this.vlanEnabled = vlanEnabled;
    }

    public String getVlanId() {
        return vlanId;
    }

    public void setVlanId(String vlanId) {
        this.vlanId = vlanId;
    }

    public String getVmNetworkName() {
        return vmNetworkName;
    }

    public void setVmNetworkName(String vmNetworkName) {
        this.vmNetworkName = vmNetworkName;
    }
}

