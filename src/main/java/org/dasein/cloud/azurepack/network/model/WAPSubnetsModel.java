package org.dasein.cloud.azurepack.network.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by vmunthiu on 3/27/2015.
 */
public class WAPSubnetsModel {
    @JsonProperty("odata.metadata")
    private String odataMetadata;
    @JsonProperty("value")
    private List<WAPSubnetModel> subnets;

    public String getOdataMetadata() {
        return odataMetadata;
    }

    public void setOdataMetadata(String odataMetadata) {
        this.odataMetadata = odataMetadata;
    }

    public List<WAPSubnetModel> getSubnets() {
        return subnets;
    }

    public void setSubnets(List<WAPSubnetModel> subnets) {
        this.subnets = subnets;
    }
}
