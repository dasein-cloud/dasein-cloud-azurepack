package org.dasein.cloud.azurepack.network.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by vmunthiu on 7/13/2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WAPNatRuleModel {
    @JsonProperty("odata.type")
    private String odataType = "VMM.NATRule";
    @JsonProperty("ID")
    private String id = "00000000-0000-0000-0000-000000000000";
    @JsonProperty("StampId")
    private String stampId;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("ExternalIPAddress")
    private String externalIPAddress;
    @JsonProperty("ExternalPort")
    private String externalPort;
    @JsonProperty("InternalIPAddress")
    private String internalIPAddress;
    @JsonProperty("InternalPort")
    private String internalPort;
    @JsonProperty("NATConnectionId")
    private String natConnectionId;
    @JsonProperty("Protocol")
    private String protocol;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExternalIPAddress() {
        return externalIPAddress;
    }

    public void setExternalIPAddress(String externalIPAddress) {
        this.externalIPAddress = externalIPAddress;
    }

    public String getExternalPort() {
        return externalPort;
    }

    public void setExternalPort(String externalPort) {
        this.externalPort = externalPort;
    }

    public String getInternalIPAddress() {
        return internalIPAddress;
    }

    public void setInternalIPAddress(String internalIPAddress) {
        this.internalIPAddress = internalIPAddress;
    }

    public String getInternalPort() {
        return internalPort;
    }

    public void setInternalPort(String internalPort) {
        this.internalPort = internalPort;
    }

    public String getNatConnectionId() {
        return natConnectionId;
    }

    public void setNatConnectionId(String natConnectionId) {
        this.natConnectionId = natConnectionId;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }
}
