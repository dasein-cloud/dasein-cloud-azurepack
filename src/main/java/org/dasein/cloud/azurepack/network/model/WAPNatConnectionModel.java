package org.dasein.cloud.azurepack.network.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by vmunthiu on 7/13/2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WAPNatConnectionModel {
    @JsonProperty("odata.type")
    private String odataType = "VMM.NATConnection";
    @JsonProperty("ID")
    private String id = "00000000-0000-0000-0000-000000000000";
    @JsonProperty("StampId")
    private String stampId;
    @JsonProperty("Description")
    private String description;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("MaximumBandwidthInboundKbps")
    private String maximumBandwidthInboundKbps;
    @JsonProperty("MaximumBandwidthOutboundKbps")
    private String maximumBandwidthOutboundKbps;
    @JsonProperty("Status")
    private String status;
    @JsonProperty("VMNetworkGatewayId")
    private String vmNetworkGatewayId;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMaximumBandwidthInboundKbps() {
        return maximumBandwidthInboundKbps;
    }

    public void setMaximumBandwidthInboundKbps(String maximumBandwidthInboundKbps) {
        this.maximumBandwidthInboundKbps = maximumBandwidthInboundKbps;
    }

    public String getMaximumBandwidthOutboundKbps() {
        return maximumBandwidthOutboundKbps;
    }

    public void setMaximumBandwidthOutboundKbps(String maximumBandwidthOutboundKbps) {
        this.maximumBandwidthOutboundKbps = maximumBandwidthOutboundKbps;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVmNetworkGatewayId() {
        return vmNetworkGatewayId;
    }

    public void setVmNetworkGatewayId(String vmNetworkGatewayId) {
        this.vmNetworkGatewayId = vmNetworkGatewayId;
    }
}
