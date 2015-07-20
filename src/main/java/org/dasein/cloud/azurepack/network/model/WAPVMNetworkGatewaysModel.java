package org.dasein.cloud.azurepack.network.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by vmunthiu on 7/14/2015.
 */
public class WAPVMNetworkGatewaysModel {
    @JsonProperty("odata.metadata")
    private String odataMetadata;
    @JsonProperty("value")
    private List<WAPVMNetworkGatewayModel> gateways;

    public String getOdataMetadata() {
        return odataMetadata;
    }

    public void setOdataMetadata(String odataMetadata) {
        this.odataMetadata = odataMetadata;
    }

    public List<WAPVMNetworkGatewayModel> getGateways() {
        return gateways;
    }

    public void setGateways(List<WAPVMNetworkGatewayModel> gateways) {
        this.gateways = gateways;
    }
}
