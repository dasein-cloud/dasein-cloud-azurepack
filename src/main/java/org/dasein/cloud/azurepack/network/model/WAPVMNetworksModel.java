package org.dasein.cloud.azurepack.network.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.dasein.cloud.azurepack.compute.vm.model.WAPVirtualMachineModel;

import java.util.List;

/**
 * Created by vmunthiu on 3/26/2015.
 */
public class WAPVMNetworksModel {
    @JsonProperty("odata.metadata")
    private String odataMetadata;
    @JsonProperty("value")
    private List<WAPVMNetworkModel> virtualMachineNetworks;

    public String getOdataMetadata() {
        return odataMetadata;
    }

    public void setOdataMetadata(String odataMetadata) {
        this.odataMetadata = odataMetadata;
    }

    public List<WAPVMNetworkModel> getVirtualMachineNetworks() {
        return virtualMachineNetworks;
    }

    public void setVirtualMachineNetworks(List<WAPVMNetworkModel> virtualMachineNetworks) {
        this.virtualMachineNetworks = virtualMachineNetworks;
    }
}
