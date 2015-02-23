package org.dasein.cloud.azurepack.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by vmunthiu on 2/23/2015.
 */
public class WAPCloudsModel {
    @JsonProperty("odata.metadata")
    private String odataMetadata;
    @JsonProperty("value")
    private List<WAPCloudModel> clouds;

    public String getOdataMetadata() {
        return odataMetadata;
    }

    public void setOdataMetadata(String odataMetadata) {
        this.odataMetadata = odataMetadata;
    }

    public List<WAPCloudModel> getClouds() {
        return clouds;
    }

    public void setClouds(List<WAPCloudModel> clouds) {
        this.clouds = clouds;
    }
}
