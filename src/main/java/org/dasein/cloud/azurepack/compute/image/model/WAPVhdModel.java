package org.dasein.cloud.azurepack.compute.image.model;

import org.dasein.cloud.azurepack.model.WAPOperatingSystemInstance;
import org.dasein.cloud.azurepack.model.WAPUserModel;

import java.util.List;

/**
 * Created by vmunthiu on 3/3/2015.
 *
 **/

public class WAPVhdModel {
    private String Accessibility;
    private String AddedTime;
    private String Description;
    private String Directory;
    private String Enabled;
    private String ID;
    private String JobGroupId;
    private String MaximumSize;
    private String ModifiedTime;
    private String Name;
    private WAPUserModel Owner;
    private String OwnerSid;
    private String ParentDiskId;
    private String SharePath;
    private String Size;
    private String State;
    private String VHDType;
    private String VMId;
    private String TemplateId;
    private String StampId;
    private String FamilyName;
    private String Release;
    private String CloudId;
    private String HostVolumeId;
    private String IsOrphaned;
    private String IsResourceGroup;
    private String LibraryGroup;
    private String LibraryShareId;
    private String Location;
    private String Namespace;
    private String ReleaseTime;
    private String SANCopyCapable;
    private String Type;
    private String VirtualizationPlatform;
    private String OperatingSystem;
    private WAPOperatingSystemInstance OperatingSystemInstance;
    private String OperatingSystemId;
    private List<String> Tag;

    public String getAccessibility() {
        return Accessibility;
    }

    public void setAccessibility(String accessibility) {
        Accessibility = accessibility;
    }

    public String getAddedTime() {
        return AddedTime;
    }

    public void setAddedTime(String addedTime) {
        AddedTime = addedTime;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getDirectory() {
        return Directory;
    }

    public void setDirectory(String directory) {
        Directory = directory;
    }

    public String getEnabled() {
        return Enabled;
    }

    public void setEnabled(String enabled) {
        Enabled = enabled;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getJobGroupId() {
        return JobGroupId;
    }

    public void setJobGroupId(String jobGroupId) {
        JobGroupId = jobGroupId;
    }

    public String getMaximumSize() {
        return MaximumSize;
    }

    public void setMaximumSize(String maximumSize) {
        MaximumSize = maximumSize;
    }

    public String getModifiedTime() {
        return ModifiedTime;
    }

    public void setModifiedTime(String modifiedTime) {
        ModifiedTime = modifiedTime;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public WAPUserModel getOwner() {
        return Owner;
    }

    public void setOwner(WAPUserModel owner) {
        Owner = owner;
    }

    public String getOwnerSid() {
        return OwnerSid;
    }

    public void setOwnerSid(String ownerSid) {
        OwnerSid = ownerSid;
    }

    public String getParentDiskId() {
        return ParentDiskId;
    }

    public void setParentDiskId(String parentDiskId) {
        ParentDiskId = parentDiskId;
    }

    public String getSharePath() {
        return SharePath;
    }

    public void setSharePath(String sharePath) {
        SharePath = sharePath;
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String size) {
        Size = size;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getVHDType() {
        return VHDType;
    }

    public void setVHDType(String VHDType) {
        this.VHDType = VHDType;
    }

    public String getVMId() {
        return VMId;
    }

    public void setVMId(String VMId) {
        this.VMId = VMId;
    }

    public String getTemplateId() {
        return TemplateId;
    }

    public void setTemplateId(String templateId) {
        TemplateId = templateId;
    }

    public String getStampId() {
        return StampId;
    }

    public void setStampId(String stampId) {
        StampId = stampId;
    }

    public String getFamilyName() {
        return FamilyName;
    }

    public void setFamilyName(String familyName) {
        FamilyName = familyName;
    }

    public String getRelease() {
        return Release;
    }

    public void setRelease(String release) {
        Release = release;
    }

    public String getCloudId() {
        return CloudId;
    }

    public void setCloudId(String cloudId) {
        CloudId = cloudId;
    }

    public String getHostVolumeId() {
        return HostVolumeId;
    }

    public void setHostVolumeId(String hostVolumeId) {
        HostVolumeId = hostVolumeId;
    }

    public String getIsOrphaned() {
        return IsOrphaned;
    }

    public void setIsOrphaned(String isOrphaned) {
        IsOrphaned = isOrphaned;
    }

    public String getIsResourceGroup() {
        return IsResourceGroup;
    }

    public void setIsResourceGroup(String isResourceGroup) {
        IsResourceGroup = isResourceGroup;
    }

    public String getLibraryGroup() {
        return LibraryGroup;
    }

    public void setLibraryGroup(String libraryGroup) {
        LibraryGroup = libraryGroup;
    }

    public String getLibraryShareId() {
        return LibraryShareId;
    }

    public void setLibraryShareId(String libraryShareId) {
        LibraryShareId = libraryShareId;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getNamespace() {
        return Namespace;
    }

    public void setNamespace(String namespace) {
        Namespace = namespace;
    }

    public String getReleaseTime() {
        return ReleaseTime;
    }

    public void setReleaseTime(String releaseTime) {
        ReleaseTime = releaseTime;
    }

    public String getSANCopyCapable() {
        return SANCopyCapable;
    }

    public void setSANCopyCapable(String SANCopyCapable) {
        this.SANCopyCapable = SANCopyCapable;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getVirtualizationPlatform() {
        return VirtualizationPlatform;
    }

    public void setVirtualizationPlatform(String virtualizationPlatform) {
        VirtualizationPlatform = virtualizationPlatform;
    }

    public String getOperatingSystem() {
        return OperatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        OperatingSystem = operatingSystem;
    }

    public WAPOperatingSystemInstance getOperatingSystemInstance() {
        return OperatingSystemInstance;
    }

    public void setOperatingSystemInstance(WAPOperatingSystemInstance operatingSystemInstance) {
        OperatingSystemInstance = operatingSystemInstance;
    }

    public String getOperatingSystemId() {
        return OperatingSystemId;
    }

    public void setOperatingSystemId(String operatingSystemId) {
        OperatingSystemId = operatingSystemId;
    }

    public List<String> getTag() {
        return Tag;
    }

    public void setTag(List<String> tag) {
        Tag = tag;
    }
}
