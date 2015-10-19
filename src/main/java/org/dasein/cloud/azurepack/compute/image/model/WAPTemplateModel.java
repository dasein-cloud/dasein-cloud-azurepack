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

package org.dasein.cloud.azurepack.compute.image.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.dasein.cloud.azurepack.model.WAPOperatingSystemInstance;
import org.dasein.cloud.azurepack.model.WAPUserModel;

import java.util.List;

/**
 * Created by vmunthiu on 3/3/2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WAPTemplateModel {
    @JsonProperty("StampId")
    private String stampId;
    @JsonProperty("ID")
    private String id;
    @JsonProperty("AccessedTime")
    private String accessedTime;
    @JsonProperty("AddedTime")
    private String addedTime;
    @JsonProperty("Admin")
    private String admin;
    @JsonProperty("AdminPasswordHasValue")
    private String adminPasswordHasValue;
    @JsonProperty("ComputerName")
    private String computerName;
    @JsonProperty("CPUCount")
    private String cpuCount;
    @JsonProperty("CPUMax")
    private String cpuMax;
    @JsonProperty("CPUReserve")
    private String cpuReserve;
    @JsonProperty("CPUType")
    private String cpuType;
    @JsonProperty("CreationTime")
    private String creationTime;
    @JsonProperty("DiskIO")
    private String diskIO;
    @JsonProperty("DomainAdmin")
    private String domainAdmin;
    @JsonProperty("DomainAdminPasswordHasValue")
    private String domainAdminPasswordHasValue;
    @JsonProperty("ExpectedCPUUtilization")
    private String expectedCPUUtilization;
    @JsonProperty("Enabled")
    private String enabled;
    @JsonProperty("FullName")
    private String fullName;
    @JsonProperty("HasVMAdditions")
    private String hasVMAdditions;
    @JsonProperty("IsHighlyAvailable")
    private String isHighlyAvailable;
    @JsonProperty("JoinDomain")
    private String joinDomain;
    @JsonProperty("JoinWorkgroup")
    private String joinWorkgroup;
    @JsonProperty("LibraryGroup")
    private String libraryGroup;
    @JsonProperty("LimitCPUForMigration")
    private String limitCPUForMigration;
    @JsonProperty("LimitCPUFunctionality")
    private String limitCPUFunctionality;
    @JsonProperty("Location")
    private String location;
    @JsonProperty("Memory")
    private String memory;
    @JsonProperty("MergeAnswerFile")
    private String mergeAnswerFile;
    @JsonProperty("ModifiedTime")
    private String modifiedTime;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("NetworkUtilization")
    private String networkUtilization;
    @JsonProperty("OperatingSystem")
    private String operatingSystem;
    @JsonProperty("OperatingSystemInstance")
    private WAPOperatingSystemInstance operatingSystemInstance;
    @JsonProperty("OSType")
    private String osType;
    @JsonProperty("OrgName")
    private String orgName;
    @JsonProperty("Owner")
    private WAPUserModel owner;
    @JsonProperty("GrantedToList")
    private List<WAPUserModel> grantedToList;
    @JsonProperty("QuotaPoint")
    private String quotaPoint;
    @JsonProperty("ProductKeyHasValue")
    private String productKeyHasValue;
    @JsonProperty("RelativeWeight")
    private String relativeWeight;
    @JsonProperty("ShareSCSIBus")
    private String shareSCSIBus;
    @JsonProperty("Tag")
    private String tag;
    @JsonProperty("TimeZone")
    private String timeZone;
    @JsonProperty("TotalVHDCapacity")
    private String totalVHDCapacity;
    @JsonProperty("UndoDisksEnabled")
    private String undoDisksEnabled;
    @JsonProperty("UseHardwareAssistedVirtualization")
    private String useHardwareAssistedVirtualization;
    @JsonProperty("Accessibility")
    private String accessibility;
    @JsonProperty("CostCenter")
    private String costCenter;
    @JsonProperty("Description")
    private String description;
    @JsonProperty("IsTagEmpty")
    private String isTagEmpty;
    @JsonProperty("NicCount")
    private String nicCount;
    @JsonProperty("NumLockEnabled")
    private String numLockEnabled;
    @JsonProperty("VMAddition")
    private String vmAddition;
    @JsonProperty("IsCustomizable")
    private String isCustomizable;
    @JsonProperty("DomainAdminPasswordIsServiceSetting")
    private String domainAdminPasswordIsServiceSetting;
    @JsonProperty("SANCopyCapable")
    private String sanCopyCapable;
    @JsonProperty("IsTemporaryTemplate")
    private String isTemporaryTemplate;
    @JsonProperty("VMTemplateId")
    private String vmTemplateId;
    @JsonProperty("VirtualHardDiskId")
    private String virtualHardDiskId;
    @JsonProperty("VMId")
    private String vmId;
    @JsonProperty("SharePath")
    private String sharePath;
    @JsonProperty("ApplicationProfileId")
    private String applicationProfileId;
    @JsonProperty("CloudID")
    private String cloudID;
    @JsonProperty("DynamicMemoryBufferPercentage")
    private String dynamicMemoryBufferPercentage;
    @JsonProperty("DynamicMemoryEnabled")
    private String dynamicMemoryEnabled;
    @JsonProperty("DynamicMemoryMaximumMB")
    private String dynamicMemoryMaximumMB;
    @JsonProperty("MemoryWeight")
    private String memoryWeight;
    @JsonProperty("DynamicMemoryPreferredBufferPercentage")
    private String dynamicMemoryPreferredBufferPercentage;
    @JsonProperty("SQLProfileId")
    private String sqlProfileId;
    @JsonProperty("VirtualFloppyDriveId")
    private String virtualFloppyDriveId;
    @JsonProperty("BootOrder")
    private List<String> bootOrder;
    @JsonProperty("CustomProperties")
    private List<String> customProperties;
    @JsonProperty("GuiRunOnceCommands")
    private List<String> guiRunOnceCommands;
    @JsonProperty("ServerFeatures")
    private List<String> serverFeatures;
    @JsonProperty("Status")
    private String status;
    @JsonProperty("VirtualizationPlatform")
    private String virtualizationPlatform;
    @JsonProperty("CapabilityProfile")
    private String capabilityProfile;
    @JsonProperty("AutoLogonCount")
    private String autoLogonCount;
    @JsonProperty("DomainJoinOrganizationalUnit")
    private String domainJoinOrganizationalUnit;
    @JsonProperty("SANStatus")
    private List<String> sanStatus;
    @JsonProperty("Generation")
    private String generation;

    public String getStampId() {
        return stampId;
    }

    public void setStampId(String stampId) {
        this.stampId = stampId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccessedTime() {
        return accessedTime;
    }

    public void setAccessedTime(String accessedTime) {
        this.accessedTime = accessedTime;
    }

    public String getAddedTime() {
        return addedTime;
    }

    public void setAddedTime(String addedTime) {
        this.addedTime = addedTime;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getAdminPasswordHasValue() {
        return adminPasswordHasValue;
    }

    public void setAdminPasswordHasValue(String adminPasswordHasValue) {
        this.adminPasswordHasValue = adminPasswordHasValue;
    }

    public String getComputerName() {
        return computerName;
    }

    public void setComputerName(String computerName) {
        this.computerName = computerName;
    }

    public String getCpuCount() {
        return cpuCount;
    }

    public void setCpuCount(String cpuCount) {
        this.cpuCount = cpuCount;
    }

    public String getCpuMax() {
        return cpuMax;
    }

    public void setCpuMax(String cpuMax) {
        this.cpuMax = cpuMax;
    }

    public String getCpuReserve() {
        return cpuReserve;
    }

    public void setCpuReserve(String cpuReserve) {
        this.cpuReserve = cpuReserve;
    }

    public String getCpuType() {
        return cpuType;
    }

    public void setCpuType(String cpuType) {
        this.cpuType = cpuType;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getDiskIO() {
        return diskIO;
    }

    public void setDiskIO(String diskIO) {
        this.diskIO = diskIO;
    }

    public String getDomainAdmin() {
        return domainAdmin;
    }

    public void setDomainAdmin(String domainAdmin) {
        this.domainAdmin = domainAdmin;
    }

    public String getDomainAdminPasswordHasValue() {
        return domainAdminPasswordHasValue;
    }

    public void setDomainAdminPasswordHasValue(String domainAdminPasswordHasValue) {
        this.domainAdminPasswordHasValue = domainAdminPasswordHasValue;
    }

    public String getExpectedCPUUtilization() {
        return expectedCPUUtilization;
    }

    public void setExpectedCPUUtilization(String expectedCPUUtilization) {
        this.expectedCPUUtilization = expectedCPUUtilization;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getHasVMAdditions() {
        return hasVMAdditions;
    }

    public void setHasVMAdditions(String hasVMAdditions) {
        this.hasVMAdditions = hasVMAdditions;
    }

    public String getIsHighlyAvailable() {
        return isHighlyAvailable;
    }

    public void setIsHighlyAvailable(String isHighlyAvailable) {
        this.isHighlyAvailable = isHighlyAvailable;
    }

    public String getJoinDomain() {
        return joinDomain;
    }

    public void setJoinDomain(String joinDomain) {
        this.joinDomain = joinDomain;
    }

    public String getJoinWorkgroup() {
        return joinWorkgroup;
    }

    public void setJoinWorkgroup(String joinWorkgroup) {
        this.joinWorkgroup = joinWorkgroup;
    }

    public String getLibraryGroup() {
        return libraryGroup;
    }

    public void setLibraryGroup(String libraryGroup) {
        this.libraryGroup = libraryGroup;
    }

    public String getLimitCPUForMigration() {
        return limitCPUForMigration;
    }

    public void setLimitCPUForMigration(String limitCPUForMigration) {
        this.limitCPUForMigration = limitCPUForMigration;
    }

    public String getLimitCPUFunctionality() {
        return limitCPUFunctionality;
    }

    public void setLimitCPUFunctionality(String limitCPUFunctionality) {
        this.limitCPUFunctionality = limitCPUFunctionality;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMemory() {
        return memory;
    }

    public void setMemory(String memory) {
        this.memory = memory;
    }

    public String getMergeAnswerFile() {
        return mergeAnswerFile;
    }

    public void setMergeAnswerFile(String mergeAnswerFile) {
        this.mergeAnswerFile = mergeAnswerFile;
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

    public String getNetworkUtilization() {
        return networkUtilization;
    }

    public void setNetworkUtilization(String networkUtilization) {
        this.networkUtilization = networkUtilization;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public WAPOperatingSystemInstance getOperatingSystemInstance() {
        return operatingSystemInstance;
    }

    public void setOperatingSystemInstance(WAPOperatingSystemInstance operatingSystemInstance) {
        this.operatingSystemInstance = operatingSystemInstance;
    }

    public String getOsType() {
        return osType;
    }

    public void setOsType(String osType) {
        this.osType = osType;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public WAPUserModel getOwner() {
        return owner;
    }

    public void setOwner(WAPUserModel owner) {
        this.owner = owner;
    }

    public List<WAPUserModel> getGrantedToList() {
        return grantedToList;
    }

    public void setGrantedToList(List<WAPUserModel> grantedToList) {
        this.grantedToList = grantedToList;
    }

    public String getQuotaPoint() {
        return quotaPoint;
    }

    public void setQuotaPoint(String quotaPoint) {
        this.quotaPoint = quotaPoint;
    }

    public String getProductKeyHasValue() {
        return productKeyHasValue;
    }

    public void setProductKeyHasValue(String productKeyHasValue) {
        this.productKeyHasValue = productKeyHasValue;
    }

    public String getRelativeWeight() {
        return relativeWeight;
    }

    public void setRelativeWeight(String relativeWeight) {
        this.relativeWeight = relativeWeight;
    }

    public String getShareSCSIBus() {
        return shareSCSIBus;
    }

    public void setShareSCSIBus(String shareSCSIBus) {
        this.shareSCSIBus = shareSCSIBus;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getTotalVHDCapacity() {
        return totalVHDCapacity;
    }

    public void setTotalVHDCapacity(String totalVHDCapacity) {
        this.totalVHDCapacity = totalVHDCapacity;
    }

    public String getUndoDisksEnabled() {
        return undoDisksEnabled;
    }

    public void setUndoDisksEnabled(String undoDisksEnabled) {
        this.undoDisksEnabled = undoDisksEnabled;
    }

    public String getUseHardwareAssistedVirtualization() {
        return useHardwareAssistedVirtualization;
    }

    public void setUseHardwareAssistedVirtualization(String useHardwareAssistedVirtualization) {
        this.useHardwareAssistedVirtualization = useHardwareAssistedVirtualization;
    }

    public String getAccessibility() {
        return accessibility;
    }

    public void setAccessibility(String accessibility) {
        this.accessibility = accessibility;
    }

    public String getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(String costCenter) {
        this.costCenter = costCenter;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIsTagEmpty() {
        return isTagEmpty;
    }

    public void setIsTagEmpty(String isTagEmpty) {
        this.isTagEmpty = isTagEmpty;
    }

    public String getNicCount() {
        return nicCount;
    }

    public void setNicCount(String nicCount) {
        this.nicCount = nicCount;
    }

    public String getNumLockEnabled() {
        return numLockEnabled;
    }

    public void setNumLockEnabled(String numLockEnabled) {
        this.numLockEnabled = numLockEnabled;
    }

    public String getVmAddition() {
        return vmAddition;
    }

    public void setVmAddition(String vmAddition) {
        this.vmAddition = vmAddition;
    }

    public String getIsCustomizable() {
        return isCustomizable;
    }

    public void setIsCustomizable(String isCustomizable) {
        this.isCustomizable = isCustomizable;
    }

    public String getDomainAdminPasswordIsServiceSetting() {
        return domainAdminPasswordIsServiceSetting;
    }

    public void setDomainAdminPasswordIsServiceSetting(String domainAdminPasswordIsServiceSetting) {
        this.domainAdminPasswordIsServiceSetting = domainAdminPasswordIsServiceSetting;
    }

    public String getSanCopyCapable() {
        return sanCopyCapable;
    }

    public void setSanCopyCapable(String sanCopyCapable) {
        this.sanCopyCapable = sanCopyCapable;
    }

    public String getIsTemporaryTemplate() {
        return isTemporaryTemplate;
    }

    public void setIsTemporaryTemplate(String isTemporaryTemplate) {
        this.isTemporaryTemplate = isTemporaryTemplate;
    }

    public String getVmTemplateId() {
        return vmTemplateId;
    }

    public void setVmTemplateId(String vmTemplateId) {
        this.vmTemplateId = vmTemplateId;
    }

    public String getVirtualHardDiskId() {
        return virtualHardDiskId;
    }

    public void setVirtualHardDiskId(String virtualHardDiskId) {
        this.virtualHardDiskId = virtualHardDiskId;
    }

    public String getVmId() {
        return vmId;
    }

    public void setVmId(String vmId) {
        this.vmId = vmId;
    }

    public String getSharePath() {
        return sharePath;
    }

    public void setSharePath(String sharePath) {
        this.sharePath = sharePath;
    }

    public String getApplicationProfileId() {
        return applicationProfileId;
    }

    public void setApplicationProfileId(String applicationProfileId) {
        this.applicationProfileId = applicationProfileId;
    }

    public String getCloudID() {
        return cloudID;
    }

    public void setCloudID(String cloudID) {
        this.cloudID = cloudID;
    }

    public String getDynamicMemoryBufferPercentage() {
        return dynamicMemoryBufferPercentage;
    }

    public void setDynamicMemoryBufferPercentage(String dynamicMemoryBufferPercentage) {
        this.dynamicMemoryBufferPercentage = dynamicMemoryBufferPercentage;
    }

    public String getDynamicMemoryEnabled() {
        return dynamicMemoryEnabled;
    }

    public void setDynamicMemoryEnabled(String dynamicMemoryEnabled) {
        this.dynamicMemoryEnabled = dynamicMemoryEnabled;
    }

    public String getDynamicMemoryMaximumMB() {
        return dynamicMemoryMaximumMB;
    }

    public void setDynamicMemoryMaximumMB(String dynamicMemoryMaximumMB) {
        this.dynamicMemoryMaximumMB = dynamicMemoryMaximumMB;
    }

    public String getMemoryWeight() {
        return memoryWeight;
    }

    public void setMemoryWeight(String memoryWeight) {
        this.memoryWeight = memoryWeight;
    }

    public String getDynamicMemoryPreferredBufferPercentage() {
        return dynamicMemoryPreferredBufferPercentage;
    }

    public void setDynamicMemoryPreferredBufferPercentage(String dynamicMemoryPreferredBufferPercentage) {
        this.dynamicMemoryPreferredBufferPercentage = dynamicMemoryPreferredBufferPercentage;
    }

    public String getSqlProfileId() {
        return sqlProfileId;
    }

    public void setSqlProfileId(String sqlProfileId) {
        this.sqlProfileId = sqlProfileId;
    }

    public String getVirtualFloppyDriveId() {
        return virtualFloppyDriveId;
    }

    public void setVirtualFloppyDriveId(String virtualFloppyDriveId) {
        this.virtualFloppyDriveId = virtualFloppyDriveId;
    }

    public List<String> getBootOrder() {
        return bootOrder;
    }

    public void setBootOrder(List<String> bootOrder) {
        this.bootOrder = bootOrder;
    }

    public List<String> getCustomProperties() {
        return customProperties;
    }

    public void setCustomProperties(List<String> customProperties) {
        this.customProperties = customProperties;
    }

    public List<String> getGuiRunOnceCommands() {
        return guiRunOnceCommands;
    }

    public void setGuiRunOnceCommands(List<String> guiRunOnceCommands) {
        this.guiRunOnceCommands = guiRunOnceCommands;
    }

    public List<String> getServerFeatures() {
        return serverFeatures;
    }

    public void setServerFeatures(List<String> serverFeatures) {
        this.serverFeatures = serverFeatures;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVirtualizationPlatform() {
        return virtualizationPlatform;
    }

    public void setVirtualizationPlatform(String virtualizationPlatform) {
        this.virtualizationPlatform = virtualizationPlatform;
    }

    public String getCapabilityProfile() {
        return capabilityProfile;
    }

    public void setCapabilityProfile(String capabilityProfile) {
        this.capabilityProfile = capabilityProfile;
    }

    public String getAutoLogonCount() {
        return autoLogonCount;
    }

    public void setAutoLogonCount(String autoLogonCount) {
        this.autoLogonCount = autoLogonCount;
    }

    public String getDomainJoinOrganizationalUnit() {
        return domainJoinOrganizationalUnit;
    }

    public void setDomainJoinOrganizationalUnit(String domainJoinOrganizationalUnit) {
        this.domainJoinOrganizationalUnit = domainJoinOrganizationalUnit;
    }

    public List<String> getSanStatus() {
        return sanStatus;
    }

    public void setSanStatus(List<String> sanStatus) {
        this.sanStatus = sanStatus;
    }

    public String getGeneration() {
        return generation;
    }

    public void setGeneration(String generation) {
        this.generation = generation;
    }
}
