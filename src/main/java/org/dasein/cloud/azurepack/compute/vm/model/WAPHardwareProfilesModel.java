package org.dasein.cloud.azurepack.compute.vm.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by vmunthiu on 4/23/2015.
 */
public class WAPHardwareProfilesModel {
    @JsonProperty("odata.metadata")
    private String odataMetadata;
    @JsonProperty("value")
    private List<WAPHardwareProfileModel> hardwareProfiles;

    public String getOdataMetadata() {
        return odataMetadata;
    }

    public void setOdataMetadata(String odataMetadata) {
        this.odataMetadata = odataMetadata;
    }

    public List<WAPHardwareProfileModel> getHardwareProfiles() {
        return hardwareProfiles;
    }

    public void setHardwareProfiles(List<WAPHardwareProfileModel> hardwareProfiles) {
        this.hardwareProfiles = hardwareProfiles;
    }
}
