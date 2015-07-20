package org.dasein.cloud.azurepack.network.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vmunthiu on 7/13/2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WAPVMNetworkGatewayModel {
    @JsonProperty("odata.type")
    private String odataType = "VMM.VMNetworkGateway";
    @JsonProperty("ID")
    private String id = "00000000-0000-0000-0000-000000000000";
    @JsonProperty("StampId")
    private String stampId;
    @JsonProperty("AutonomousSystemNumber")
    private String autonomousSystemNumber;
    @JsonProperty("Description")
    private String description;
    @JsonProperty("EnableBGP")
    private String enableBGP;
    @JsonProperty("IPSubnets@odata.type")
    private String ipSubnetsODataType = "Collection(Edm.String)";
    @JsonProperty("IPSubnets")
    private List<String> ipSubnets = new ArrayList<String>();
    @JsonProperty("Name")
    private String name;
    @JsonProperty("RequiresNAT")
    private String requiresNAT;
    @JsonProperty("RequiresVPN")
    private String requiresVPN;
    @JsonProperty("VMNetworkId")
    private String vmNetworkId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStampId() {
        return stampId;
    }

    public void setStampId(String stampId) {
        this.stampId = stampId;
    }

    public String getAutonomousSystemNumber() {
        return autonomousSystemNumber;
    }

    public void setAutonomousSystemNumber(String autonomousSystemNumber) {
        this.autonomousSystemNumber = autonomousSystemNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEnableBGP() {
        return enableBGP;
    }

    public void setEnableBGP(String enableBGP) {
        this.enableBGP = enableBGP;
    }

    public List<String> getIpSubnets() {
        return ipSubnets;
    }

    public void setIpSubnets(List<String> ipSubnets) {
        this.ipSubnets = ipSubnets;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRequiresNAT() {
        return requiresNAT;
    }

    public void setRequiresNAT(String requiresNAT) {
        this.requiresNAT = requiresNAT;
    }

    public String getRequiresVPN() {
        return requiresVPN;
    }

    public void setRequiresVPN(String requiresVPN) {
        this.requiresVPN = requiresVPN;
    }

    public String getVmNetworkId() {
        return vmNetworkId;
    }

    public void setVmNetworkId(String vmNetworkId) {
        this.vmNetworkId = vmNetworkId;
    }
}
