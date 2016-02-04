package org.dasein.cloud.azurepack.compute.vm.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by vmunthiu on 2/1/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WAPDiskDriveModel {
    @JsonProperty("odata.type")
    private String odataType = "VMM.VirtualDiskDrive";
    @JsonProperty("odata.metadata")
    private String odataMetadata = null;
    @JsonProperty("StampId")
    private String stampId = null;
    @JsonProperty("ID")
    private String id = "00000000-0000-0000-0000-000000000000";
    @JsonProperty("Bus")
    private String bus = null;
    @JsonProperty("BusType")
    private String busType = null;
    @JsonProperty("IsVHD")
    private String isVHD = null;
    @JsonProperty("LUN")
    private String lun = null;
    @JsonProperty("Name")
    private String name = null;
    @JsonProperty("VMId")
    private String vmId = null;
    @JsonProperty("TemplateId")
    private String templateId = null;
    @JsonProperty("ISOId")
    private String isoId = null;
    @JsonProperty("HostDrive")
    private String hostDriveHostDrive = null;
    @JsonProperty("ISOLinked")
    private String isoLinked = null;
    @JsonProperty("Accessibility")
    private String accessibility = null;
    @JsonProperty("Description")
    private String description = null;
    @JsonProperty("AddedTime")
    private String addedTime = null;
    @JsonProperty("ModifiedTime")
    private String modifiedTime = null;
    @JsonProperty("Enabled")
    private String enabled = null;
    @JsonProperty("VirtualHardDiskId")
    private String virtualHardDiskId = null;
    @JsonProperty("VolumeType")
    private String volumeType = null;
    @JsonProperty("IDE")
    private String ide = null;
    @JsonProperty("SCSI")
    private String scsi = null;
    @JsonProperty("FileName")
    private String fileName = null;
    @JsonProperty("Path")
    private String path = null;
    @JsonProperty("Size")
    private String size = null;

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

    public String getBus() {
        return bus;
    }

    public void setBus(String bus) {
        this.bus = bus;
    }

    public String getBusType() {
        return busType;
    }

    public void setBusType(String busType) {
        this.busType = busType;
    }

    public String getIsVHD() {
        return isVHD;
    }

    public void setIsVHD(String isVHD) {
        this.isVHD = isVHD;
    }

    public String getLun() {
        return lun;
    }

    public void setLun(String lun) {
        this.lun = lun;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getIsoId() {
        return isoId;
    }

    public void setIsoId(String isoId) {
        this.isoId = isoId;
    }

    public String getHostDriveHostDrive() {
        return hostDriveHostDrive;
    }

    public void setHostDriveHostDrive(String hostDriveHostDrive) {
        this.hostDriveHostDrive = hostDriveHostDrive;
    }

    public String getIsoLinked() {
        return isoLinked;
    }

    public void setIsoLinked(String isoLinked) {
        this.isoLinked = isoLinked;
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

    public String getAddedTime() {
        return addedTime;
    }

    public void setAddedTime(String addedTime) {
        this.addedTime = addedTime;
    }

    public String getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(String modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public String getVirtualHardDiskId() {
        return virtualHardDiskId;
    }

    public void setVirtualHardDiskId(String virtualHardDiskId) {
        this.virtualHardDiskId = virtualHardDiskId;
    }

    public String getVolumeType() {
        return volumeType;
    }

    public void setVolumeType(String volumeType) {
        this.volumeType = volumeType;
    }

    public String getIde() {
        return ide;
    }

    public void setIde(String ide) {
        this.ide = ide;
    }

    public String getScsi() {
        return scsi;
    }

    public void setScsi(String scsi) {
        this.scsi = scsi;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
