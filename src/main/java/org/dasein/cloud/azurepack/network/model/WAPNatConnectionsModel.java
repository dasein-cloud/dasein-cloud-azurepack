package org.dasein.cloud.azurepack.network.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by vmunthiu on 7/14/2015.
 */
public class WAPNatConnectionsModel {
    @JsonProperty("odata.metadata")
    private String odataMetadata;
    @JsonProperty("value")
    private List<WAPNatConnectionModel> connections;

    public String getOdataMetadata() {
        return odataMetadata;
    }

    public void setOdataMetadata(String odataMetadata) {
        this.odataMetadata = odataMetadata;
    }

    public List<WAPNatConnectionModel> getConnections() {
        return connections;
    }

    public void setConnections(List<WAPNatConnectionModel> connections) {
        this.connections = connections;
    }
}
