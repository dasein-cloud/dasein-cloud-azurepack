package org.dasein.cloud.azurepack.compute.vm.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.dasein.cloud.azurepack.model.WAPUserModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vmunthiu on 4/23/2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WAPHardwareProfileModel {
    @JsonProperty("odata.type")
    private String odataType = "VMM.HardwareProfile";
    @JsonProperty("odata.metadata")
    private String odataMetadata = null;
    @JsonProperty("StampId")
    private String stampId = null;
    @JsonProperty("ID")
    private String id = null;
    @JsonProperty("AccessedTime")
    private String accessedTime = null;
    @JsonProperty("AddedTime")
    private String addedTime = null;
    @JsonProperty("CPUCount")
    private String cpuCount = null;
    @JsonProperty("CPUMax")
    private String cpuMax = null;
    @JsonProperty("CPUReserve")
    private String cpuReserve = null;
    @JsonProperty("CreationTime")
    private String creationTime = null;
    @JsonProperty("DiskIO")
    private String diskIO = null;
    @JsonProperty("ExpectedCPUUtilization")
    private String expectedCPUUtilization = null;
    @JsonProperty("Enabled")
    private String enabled = null;
    @JsonProperty("LimitCPUForMigration")
    private String limitCPUForMigration = null;
    @JsonProperty("LimitCPUFunctionality")
    private String limitCPUFunctionality = null;
    @JsonProperty("Memory")
    private String memory = null;
    @JsonProperty("ModifiedTime")
    private String modifiedTime = null;
    @JsonProperty("Name")
    private String name = null;
    @JsonProperty("NetworkUtilization")
    private String networkUtilization = null;
    @JsonProperty("Owner")
    private WAPUserModel owner = new WAPUserModel();
    @JsonProperty("GrantedToList@odata.type")
    private String grantedToListOdataType = "Collection(VMM.UserAndRole)";
    @JsonProperty("GrantedToList")
    private List<WAPUserModel> grantedToList = new ArrayList<WAPUserModel>();
    @JsonProperty("RelativeWeight")
    private String relativeWeight = null;
    @JsonProperty("ShareSCSIBus")
    private String shareSCSIBus = null;
    @JsonProperty("TotalVHDCapacity")
    private String totalVHDCapacity = null;
    @JsonProperty("UndoDisksEnabled")
    private String undoDisksEnabled = null;
    @JsonProperty("Accessibility")
    private String accessibility = null;
    @JsonProperty("Description")
    private String description = null;
    @JsonProperty("NumLockEnabled")
    private String numLockEnabled = null;
    @JsonProperty("Generation")
    private String generation = null;

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

    public String getMemory() {
        return memory;
    }

    public void setMemory(String memory) {
        this.memory = memory;
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

    public WAPUserModel getOwner() {
        return owner;
    }

    public void setOwner(WAPUserModel owner) {
        this.owner = owner;
    }

    public String getGrantedToListOdataType() {
        return grantedToListOdataType;
    }

    public void setGrantedToListOdataType(String grantedToListOdataType) {
        this.grantedToListOdataType = grantedToListOdataType;
    }

    public List<WAPUserModel> getGrantedToList() {
        return grantedToList;
    }

    public void setGrantedToList(List<WAPUserModel> grantedToList) {
        this.grantedToList = grantedToList;
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

    public String getAccessibility() {
        return accessibility;
    }

    public void setAccessibility(String accessibility) {
        this.accessibility = accessibility;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNumLockEnabled() {
        return numLockEnabled;
    }

    public void setNumLockEnabled(String numLockEnabled) {
        this.numLockEnabled = numLockEnabled;
    }

    public String getGeneration() {
        return generation;
    }

    public void setGeneration(String generation) {
        this.generation = generation;
    }
}
