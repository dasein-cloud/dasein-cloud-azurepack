package org.dasein.cloud.azurepack.network.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by vmunthiu on 4/1/2015.
 */
public class WAPLogicalNetsMode {
    @JsonProperty("odata.metadata")
    private String odataMetadata;
    @JsonProperty("value")
    private List<WAPLogicalNetModel> logicalNets;

    public String getOdataMetadata() {
        return odataMetadata;
    }

    public void setOdataMetadata(String odataMetadata) {
        this.odataMetadata = odataMetadata;
    }

    public List<WAPLogicalNetModel> getLogicalNets() {
        return logicalNets;
    }

    public void setLogicalNets(List<WAPLogicalNetModel> logicalNets) {
        this.logicalNets = logicalNets;
    }
}
