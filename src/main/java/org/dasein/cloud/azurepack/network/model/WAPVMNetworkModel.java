package org.dasein.cloud.azurepack.network.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.dasein.cloud.azurepack.model.WAPUserModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vmunthiu on 3/26/2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WAPVMNetworkModel {
    @JsonProperty("odata.type")
    private String odataType = "VMM.VMNetwork";
    @JsonProperty("odata.metadata")
    private String odataMetadata = null;
    @JsonProperty("ID")
    private String id = "00000000-0000-0000-0000-000000000000";
    @JsonProperty("StampId")
    private String stampId;
    @JsonProperty("AddedTime")
    private String addedTime;
    @JsonProperty("AutoCreateSubnet")
    private String autoCreateSubnet;
    @JsonProperty("CAIPAddressPoolType")
    private String caipAddressPoolType;
    @JsonProperty("Description")
    private String description;
    @JsonProperty("Enabled")
    private String enabled;
    @JsonProperty("ExternalName")
    private String externalName;
    @JsonProperty("GrantedToList@odata.type")
    private String grantedToListODataType = "Collection(VMM.UserAndRole)";
    @JsonProperty("GrantedToList")
    private List<WAPUserModel> grantedToList = new ArrayList<WAPUserModel>();
    @JsonProperty("IsAssigned")
    private String isAssigned;
    @JsonProperty("IsolationType")
    private String isolationType;
    @JsonProperty("LogicalNetworkId")
    private String logicalNetworkId;
    @JsonProperty("LogicalNetworkName")
    private String logicalNetworkName;
    @JsonProperty("ModifiedTime")
    private String modifiedTime;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("Owner")
    private WAPUserModel owner = new WAPUserModel();
    @JsonProperty("PAIPAddressPoolType")
    private String paipAddressPoolType;
    @JsonProperty("PROTipID")
    private String proTipId;
    @JsonProperty("RoutingDomainId")
    private String routingDomainId;

    public String getOdataType() {
        return odataType;
    }

    public void setOdataType(String odataType) {
        this.odataType = odataType;
    }

    public String getOdataMetadata() {
        return odataMetadata;
    }

    public void setOdataMetadata(String odataMetadata) {
        this.odataMetadata = odataMetadata;
    }

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

    public String getAddedTime() {
        return addedTime;
    }

    public void setAddedTime(String addedTime) {
        this.addedTime = addedTime;
    }

    public String getAutoCreateSubnet() {
        return autoCreateSubnet;
    }

    public void setAutoCreateSubnet(String autoCreateSubnet) {
        this.autoCreateSubnet = autoCreateSubnet;
    }

    public String getCaipAddressPoolType() {
        return caipAddressPoolType;
    }

    public void setCaipAddressPoolType(String caipAddressPoolType) {
        this.caipAddressPoolType = caipAddressPoolType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public String getExternalName() {
        return externalName;
    }

    public void setExternalName(String externalName) {
        this.externalName = externalName;
    }

    public String getGrantedToListODataType() {
        return grantedToListODataType;
    }

    public void setGrantedToListODataType(String grantedToListODataType) {
        this.grantedToListODataType = grantedToListODataType;
    }

    public List<WAPUserModel> getGrantedToList() {
        return grantedToList;
    }

    public void setGrantedToList(List<WAPUserModel> grantedToList) {
        this.grantedToList = grantedToList;
    }

    public String getIsAssigned() {
        return isAssigned;
    }

    public void setIsAssigned(String isAssigned) {
        this.isAssigned = isAssigned;
    }

    public String getIsolationType() {
        return isolationType;
    }

    public void setIsolationType(String isolationType) {
        this.isolationType = isolationType;
    }

    public String getLogicalNetworkId() {
        return logicalNetworkId;
    }

    public void setLogicalNetworkId(String logicalNetworkId) {
        this.logicalNetworkId = logicalNetworkId;
    }

    public String getLogicalNetworkName() {
        return logicalNetworkName;
    }

    public void setLogicalNetworkName(String logicalNetworkName) {
        this.logicalNetworkName = logicalNetworkName;
    }

    public String getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(String modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public WAPUserModel getOwner() {
        return owner;
    }

    public void setOwner(WAPUserModel owner) {
        this.owner = owner;
    }

    public String getPaipAddressPoolType() {
        return paipAddressPoolType;
    }

    public void setPaipAddressPoolType(String paipAddressPoolType) {
        this.paipAddressPoolType = paipAddressPoolType;
    }

    public String getProTipId() {
        return proTipId;
    }

    public void setProTipId(String proTipId) {
        this.proTipId = proTipId;
    }

    public String getRoutingDomainId() {
        return routingDomainId;
    }

    public void setRoutingDomainId(String routingDomainId) {
        this.routingDomainId = routingDomainId;
    }
}
