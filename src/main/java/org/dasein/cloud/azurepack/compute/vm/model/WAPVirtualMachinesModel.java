package org.dasein.cloud.azurepack.compute.vm.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.dasein.cloud.azurepack.compute.image.model.WAPVhdModel;

import java.util.List;

/**
 * Created by vmunthiu on 3/4/2015.
 */
public class WAPVirtualMachinesModel {
    @JsonProperty("odata.metadata")
    private String odataMetadata;
    @JsonProperty("value")
    private List<WAPVirtualMachineModel> virtualMachines;

    public String getOdataMetadata() {
        return odataMetadata;
    }

    public void setOdataMetadata(String odataMetadata) {
        this.odataMetadata = odataMetadata;
    }

    public List<WAPVirtualMachineModel> getVirtualMachines() {
        return virtualMachines;
    }

    public void setVirtualMachines(List<WAPVirtualMachineModel> virtualMachines) {
        this.virtualMachines = virtualMachines;
    }
}
