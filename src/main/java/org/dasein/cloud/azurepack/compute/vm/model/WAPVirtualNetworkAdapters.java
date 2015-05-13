package org.dasein.cloud.azurepack.compute.vm.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by vmunthiu on 5/11/2015.
 */
public class WAPVirtualNetworkAdapters {
    @JsonProperty("odata.metadata")
    private String odataMetadata;
    @JsonProperty("value")
    private List<WAPVirtualNetworkAdapter> virtualNetworkAdapters;

    public String getOdataMetadata() {
        return odataMetadata;
    }

    public void setOdataMetadata(String odataMetadata) {
        this.odataMetadata = odataMetadata;
    }

    public List<WAPVirtualNetworkAdapter> getVirtualNetworkAdapters() {
        return virtualNetworkAdapters;
    }

    public void setVirtualNetworkAdapters(List<WAPVirtualNetworkAdapter> virtualNetworkAdapters) {
        this.virtualNetworkAdapters = virtualNetworkAdapters;
    }
}
