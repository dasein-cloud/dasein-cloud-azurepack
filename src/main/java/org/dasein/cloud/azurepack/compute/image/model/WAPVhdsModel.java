package org.dasein.cloud.azurepack.compute.image.model;

import com.fasterxml.jackson.annotation.JsonProperty;


import java.util.List;

/**
 * Created by vmunthiu on 3/3/2015.
 */
public class WAPVhdsModel {
    @JsonProperty("odata.metadata")
    private String odataMetadata;
    @JsonProperty("value")
    private List<WAPVhdModel> vhds;

    public String getOdataMetadata() {
        return odataMetadata;
    }

    public void setOdataMetadata(String odataMetadata) {
        this.odataMetadata = odataMetadata;
    }

    public List<WAPVhdModel> getVhds() {
        return vhds;
    }

    public void setVhds(List<WAPVhdModel> vhds) {
        this.vhds = vhds;
    }
}
