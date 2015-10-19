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
import org.dasein.cloud.azurepack.model.WAPUserModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vmunthiu on 4/24/2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WAPVirtualNetworkAdapter {
    @JsonProperty("odata.type")
    private String odataType = "VMM.VirtualNetworkAdapter";
    @JsonProperty("odata.metadata")
    private String odataMetadata = null;
    @JsonProperty("Accessibility")
    private String accessibility = null;
    @JsonProperty("AddedTime")
    private String addedTime = null;
    @JsonProperty("ChildObjectIDs@odata.type")
    private String childObjectIDsODataType = "Collection(Edm.Guid)";
    @JsonProperty("ChildObjectIDs")
    private List<String> grantedToList = new ArrayList<String>();
    @JsonProperty("Description")
    private String description = null;
    @JsonProperty("Enabled")
    private String enabled = null;
    @JsonProperty("EnableMACAddressSpoofing")
    private String enableMACAddressSpoofing = null;
    @JsonProperty("EthernetAddress")
    private String ethernetAddress = null;
    @JsonProperty("EthernetAddressType")
    private String ethernetAddressType = null;
    @JsonProperty("ID")
    private String id = "00000000-0000-0000-0000-000000000000";
    @JsonProperty("IPv4Addresses@odata.type")
    private String ipv4AddressesODataType = "Collection(Edm.String)";
    @JsonProperty("IPv4Addresses")
    private List<String> ipv4Addresses = new ArrayList<String>();
    @JsonProperty("IPv4AddressType")
    private String ipv4AddressType = null;
    @JsonProperty("IPv6Addresses@odata.type")
    private String ipv6AddressesODataType = "Collection(Edm.String)";
    @JsonProperty("IPv6Addresses")
    private List<String> ipv6Addresses = new ArrayList<String>();
    @JsonProperty("IPv6AddressType")
    private String ipv6AddressType = null;
    @JsonProperty("IsSynthetic")
    private String isSynthetic = null;
    @JsonProperty("Location")
    private String location = null;
    @JsonProperty("MACAddress")
    private String macAddress = null;
    @JsonProperty("MACAddressesSpoofingEnabled")
    private String macAddressesSpoofingEnabled = null;
    @JsonProperty("MACAddressesSpoolingEnabled")
    private String macAddressesSpoolingEnabled = null;
    @JsonProperty("MACAddressSpoofingEnabled")
    private String macAddressSpoofingEnabled = null;
    @JsonProperty("MACAddressType")
    private String macAddressType = null;
    @JsonProperty("ModifiedTime")
    private String modifiedTime = null;
    @JsonProperty("Name")
    private String name = null;
    @JsonProperty("ParentId")
    private String parentId = null;
    @JsonProperty("PhysicalAddress")
    private String physicalAddress = null;
    @JsonProperty("PhysicalAddressType")
    private String physicalAddressType = null;
    @JsonProperty("RequiredBandwidth")
    private String requiredBandwidth = null;
    @JsonProperty("SlotId")
    private String slotId = null;
    @JsonProperty("StampId")
    private String stampId = null;
    @JsonProperty("Tag")
    private String tag = null;
    @JsonProperty("TemplateId")
    private String templateId = null;
    @JsonProperty("VirtualNetworkAdapterType")
    private String virtualNetworkAdapterType = null;
    @JsonProperty("VLanEnabled")
    private String vLanEnabled = null;
    @JsonProperty("VLanId")
    private String vLanId = null;
    @JsonProperty("VMId")
    private String vmId = null;
    @JsonProperty("VMNetworkId")
    private String vmNetworkId = null;
    @JsonProperty("VMNetworkName")
    private String vmNetworkName = null;
    @JsonProperty("VMNetworkOptimizationEnabled")
    private String vmNetworkOptimizationEnabled = null;
    @JsonProperty("VMSubnetId")
    private String vmSubnetId = null;
    @JsonProperty("VmwAdapterIndex")
    private String vmwAdapterIndex = null;
    @JsonProperty("VMwarePortGroup")
    private String vmwarePortGroup = null;

    public String getOdataType() {
        return odataType;
    }

    public void setOdataType(String odataType) {
        this.odataType = odataType;
    }

    public String getOdataMetadata() {
        return odataMetadata;
    }

    public void setOdataMetadata(String odataMetadata) {
        this.odataMetadata = odataMetadata;
    }

    public String getAccessibility() {
        return accessibility;
    }

    public void setAccessibility(String accessibility) {
        this.accessibility = accessibility;
    }

    public String getAddedTime() {
        return addedTime;
    }

    public void setAddedTime(String addedTime) {
        this.addedTime = addedTime;
    }

    public String getChildObjectIDsODataType() {
        return childObjectIDsODataType;
    }

    public void setChildObjectIDsODataType(String childObjectIDsODataType) {
        this.childObjectIDsODataType = childObjectIDsODataType;
    }

    public List<String> getGrantedToList() {
        return grantedToList;
    }

    public void setGrantedToList(List<String> grantedToList) {
        this.grantedToList = grantedToList;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public String getEnableMACAddressSpoofing() {
        return enableMACAddressSpoofing;
    }

    public void setEnableMACAddressSpoofing(String enableMACAddressSpoofing) {
        this.enableMACAddressSpoofing = enableMACAddressSpoofing;
    }

    public String getEthernetAddress() {
        return ethernetAddress;
    }

    public void setEthernetAddress(String ethernetAddress) {
        this.ethernetAddress = ethernetAddress;
    }

    public String getEthernetAddressType() {
        return ethernetAddressType;
    }

    public void setEthernetAddressType(String ethernetAddressType) {
        this.ethernetAddressType = ethernetAddressType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIpv4AddressesODataType() {
        return ipv4AddressesODataType;
    }

    public void setIpv4AddressesODataType(String ipv4AddressesODataType) {
        this.ipv4AddressesODataType = ipv4AddressesODataType;
    }

    public List<String> getIpv4Addresses() {
        return ipv4Addresses;
    }

    public void setIpv4Addresses(List<String> ipv4Addresses) {
        this.ipv4Addresses = ipv4Addresses;
    }

    public String getIpv4AddressType() {
        return ipv4AddressType;
    }

    public void setIpv4AddressType(String ipv4AddressType) {
        this.ipv4AddressType = ipv4AddressType;
    }

    public String getIpv6AddressesODataType() {
        return ipv6AddressesODataType;
    }

    public void setIpv6AddressesODataType(String ipv6AddressesODataType) {
        this.ipv6AddressesODataType = ipv6AddressesODataType;
    }

    public List<String> getIpv6Addresses() {
        return ipv6Addresses;
    }

    public void setIpv6Addresses(List<String> ipv6Addresses) {
        this.ipv6Addresses = ipv6Addresses;
    }

    public String getIPv6AddressType() {
        return ipv6AddressType;
    }

    public void setIPv6AddressType(String ipv6AddressesType) {
        this.ipv6AddressType = ipv6AddressesType;
    }

    public String getIsSynthetic() {
        return isSynthetic;
    }

    public void setIsSynthetic(String isSynthetic) {
        this.isSynthetic = isSynthetic;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getMacAddressesSpoofingEnabled() {
        return macAddressesSpoofingEnabled;
    }

    public void setMacAddressesSpoofingEnabled(String macAddressesSpoofingEnabled) {
        this.macAddressesSpoofingEnabled = macAddressesSpoofingEnabled;
    }

    public String getMacAddressesSpoolingEnabled() {
        return macAddressesSpoolingEnabled;
    }

    public void setMacAddressesSpoolingEnabled(String macAddressesSpoolingEnabled) {
        this.macAddressesSpoolingEnabled = macAddressesSpoolingEnabled;
    }

    public String getMacAddressSpoofingEnabled() {
        return macAddressSpoofingEnabled;
    }

    public void setMacAddressSpoofingEnabled(String macAddressSpoofingEnabled) {
        this.macAddressSpoofingEnabled = macAddressSpoofingEnabled;
    }

    public String getMacAddressType() {
        return macAddressType;
    }

    public void setMacAddressType(String macAddressType) {
        this.macAddressType = macAddressType;
    }

    public String getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(String modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getPhysicalAddress() {
        return physicalAddress;
    }

    public void setPhysicalAddress(String physicalAddress) {
        this.physicalAddress = physicalAddress;
    }

    public String getPhysicalAddressType() {
        return physicalAddressType;
    }

    public void setPhysicalAddressType(String physicalAddressType) {
        this.physicalAddressType = physicalAddressType;
    }

    public String getRequiredBandwidth() {
        return requiredBandwidth;
    }

    public void setRequiredBandwidth(String requiredBandwidth) {
        this.requiredBandwidth = requiredBandwidth;
    }

    public String getSlotId() {
        return slotId;
    }

    public void setSlotId(String slotId) {
        this.slotId = slotId;
    }

    public String getStampId() {
        return stampId;
    }

    public void setStampId(String stampId) {
        this.stampId = stampId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getVirtualNetworkAdapterType() {
        return virtualNetworkAdapterType;
    }

    public void setVirtualNetworkAdapterType(String virtualNetworkAdapterType) {
        this.virtualNetworkAdapterType = virtualNetworkAdapterType;
    }

    public String getvLanEnabled() {
        return vLanEnabled;
    }

    public void setvLanEnabled(String vLanEnabled) {
        this.vLanEnabled = vLanEnabled;
    }

    public String getvLanId() {
        return vLanId;
    }

    public void setvLanId(String vLanId) {
        this.vLanId = vLanId;
    }

    public String getVmId() {
        return vmId;
    }

    public void setVmId(String vmId) {
        this.vmId = vmId;
    }

    public String getVmNetworkId() {
        return vmNetworkId;
    }

    public void setVmNetworkId(String vmNetworkId) {
        this.vmNetworkId = vmNetworkId;
    }

    public String getVmNetworkName() {
        return vmNetworkName;
    }

    public void setVmNetworkName(String vmNetworkName) {
        this.vmNetworkName = vmNetworkName;
    }

    public String getVmNetworkOptimizationEnabled() {
        return vmNetworkOptimizationEnabled;
    }

    public void setVmNetworkOptimizationEnabled(String vmNetworkOptimizationEnabled) {
        this.vmNetworkOptimizationEnabled = vmNetworkOptimizationEnabled;
    }

    public String getVmSubnetId() {
        return vmSubnetId;
    }

    public void setVmSubnetId(String vmSubnetId) {
        this.vmSubnetId = vmSubnetId;
    }

    public String getVmwAdapterIndex() {
        return vmwAdapterIndex;
    }

    public void setVmwAdapterIndex(String vmwAdapterIndex) {
        this.vmwAdapterIndex = vmwAdapterIndex;
    }

    public String getVmwarePortGroup() {
        return vmwarePortGroup;
    }

    public void setVmwarePortGroup(String vmwarePortGroup) {
        this.vmwarePortGroup = vmwarePortGroup;
    }
}