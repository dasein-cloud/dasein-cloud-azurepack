package org.dasein.cloud.azurepack.compute.image.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.dasein.cloud.azurepack.model.WAPOperatingSystemInstance;
import org.dasein.cloud.azurepack.model.WAPUserModel;

import java.util.List;

/**
 * Created by vmunthiu on 3/3/2015.
 *
 **/

@JsonIgnoreProperties(ignoreUnknown = true)
public class WAPVhdModel {
    @JsonProperty("Accessibility")
    private String accessibility;
    @JsonProperty("AddedTime")
    private String addedTime;
    @JsonProperty("Description")
    private String description;
    @JsonProperty("Directory")
    private String directory;
    @JsonProperty("Enabled")
    private String enabled;
    @JsonProperty("ID")
    private String id;
    @JsonProperty("JobGroupId")
    private String jobGroupId;
    @JsonProperty("MaximumSize")
    private String maximumSize;
    @JsonProperty("ModifiedTime")
    private String modifiedTime;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("Owner")
    private WAPUserModel owner;
    @JsonProperty("OwnerSid")
    private String ownerSid;
    @JsonProperty("ParentDiskId")
    private String parentDiskId;
    @JsonProperty("SharePath")
    private String sharePath;
    @JsonProperty("Size")
    private String size;
    @JsonProperty("State")
    private String state;
    @JsonProperty("VHDType")
    private String vhdType;
    @JsonProperty("VMId")
    private String vmId;
    @JsonProperty("TemplateId")
    private String templateId;
    @JsonProperty("StampId")
    private String stampId;
    @JsonProperty("FamilyName")
    private String familyName;
    @JsonProperty("Release")
    private String release;
    @JsonProperty("CloudId")
    private String cloudId;
    @JsonProperty("HostVolumeId")
    private String hostVolumeId;
    @JsonProperty("IsOrphaned")
    private String isOrphaned;
    @JsonProperty("IsResourceGroup")
    private String isResourceGroup;
    @JsonProperty("LibraryGroup")
    private String libraryGroup;
    @JsonProperty("LibraryShareId")
    private String libraryShareId;
    @JsonProperty("Location")
    private String location;
    @JsonProperty("Namespace")
    private String namespace;
    @JsonProperty("ReleaseTime")
    private String releaseTime;
    @JsonProperty("SANCopyCapable")
    private String sanCopyCapable;
    @JsonProperty("Type")
    private String type;
    @JsonProperty("VirtualizationPlatform")
    private String virtualizationPlatform;
    @JsonProperty("OperatingSystem")
    private String operatingSystem;
    @JsonProperty("OperatingSystemInstance")
    private WAPOperatingSystemInstance operatingSystemInstance;
    @JsonProperty("OperatingSystemId")
    private String operatingSystemId;
    @JsonProperty("Tag")
    private List<String> tag;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJobGroupId() {
        return jobGroupId;
    }

    public void setJobGroupId(String jobGroupId) {
        this.jobGroupId = jobGroupId;
    }

    public String getMaximumSize() {
        return maximumSize;
    }

    public void setMaximumSize(String maximumSize) {
        this.maximumSize = maximumSize;
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

    public WAPUserModel getOwner() {
        return owner;
    }

    public void setOwner(WAPUserModel owner) {
        this.owner = owner;
    }

    public String getOwnerSid() {
        return ownerSid;
    }

    public void setOwnerSid(String ownerSid) {
        this.ownerSid = ownerSid;
    }

    public String getParentDiskId() {
        return parentDiskId;
    }

    public void setParentDiskId(String parentDiskId) {
        this.parentDiskId = parentDiskId;
    }

    public String getSharePath() {
        return sharePath;
    }

    public void setSharePath(String sharePath) {
        this.sharePath = sharePath;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getVhdType() {
        return vhdType;
    }

    public void setVhdType(String vhdType) {
        this.vhdType = vhdType;
    }

    public String getVmId() {
        return vmId;
    }

    public void setVmId(String vmId) {
        this.vmId = vmId;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getStampId() {
        return stampId;
    }

    public void setStampId(String stampId) {
        this.stampId = stampId;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getCloudId() {
        return cloudId;
    }

    public void setCloudId(String cloudId) {
        this.cloudId = cloudId;
    }

    public String getHostVolumeId() {
        return hostVolumeId;
    }

    public void setHostVolumeId(String hostVolumeId) {
        this.hostVolumeId = hostVolumeId;
    }

    public String getIsOrphaned() {
        return isOrphaned;
    }

    public void setIsOrphaned(String isOrphaned) {
        this.isOrphaned = isOrphaned;
    }

    public String getIsResourceGroup() {
        return isResourceGroup;
    }

    public void setIsResourceGroup(String isResourceGroup) {
        this.isResourceGroup = isResourceGroup;
    }

    public String getLibraryGroup() {
        return libraryGroup;
    }

    public void setLibraryGroup(String libraryGroup) {
        this.libraryGroup = libraryGroup;
    }

    public String getLibraryShareId() {
        return libraryShareId;
    }

    public void setLibraryShareId(String libraryShareId) {
        this.libraryShareId = libraryShareId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(String releaseTime) {
        this.releaseTime = releaseTime;
    }

    public String getSanCopyCapable() {
        return sanCopyCapable;
    }

    public void setSanCopyCapable(String sanCopyCapable) {
        this.sanCopyCapable = sanCopyCapable;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVirtualizationPlatform() {
        return virtualizationPlatform;
    }

    public void setVirtualizationPlatform(String virtualizationPlatform) {
        this.virtualizationPlatform = virtualizationPlatform;
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

    public String getOperatingSystemId() {
        return operatingSystemId;
    }

    public void setOperatingSystemId(String operatingSystemId) {
        this.operatingSystemId = operatingSystemId;
    }

    public List<String> getTag() {
        return tag;
    }

    public void setTag(List<String> tag) {
        this.tag = tag;
    }
}
