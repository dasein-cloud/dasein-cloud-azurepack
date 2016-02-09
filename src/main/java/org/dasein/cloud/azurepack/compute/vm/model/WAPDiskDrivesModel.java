package org.dasein.cloud.azurepack.compute.vm.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by vmunthiu on 2/1/2016.
 */
public class WAPDiskDrivesModel {
    @JsonProperty("odata.metadata")
    private String odataMetadata;
    @JsonProperty("value")
    private List<WAPDiskDriveModel> diskDriveModels;

    public String getOdataMetadata() {
        return odataMetadata;
    }

    public void setOdataMetadata(String odataMetadata) {
        this.odataMetadata = odataMetadata;
    }

    public List<WAPDiskDriveModel> getDiskDriveModels() {
        return diskDriveModels;
    }

    public void setDiskDriveModels(List<WAPDiskDriveModel> diskDriveModels) {
        this.diskDriveModels = diskDriveModels;
    }
}
