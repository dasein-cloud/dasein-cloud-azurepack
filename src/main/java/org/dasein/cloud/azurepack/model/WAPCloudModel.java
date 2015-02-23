package org.dasein.cloud.azurepack.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by vmunthiu on 2/23/2015.
 */
public class WAPCloudModel {
    @JsonProperty("ID")
    private String id;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("Description")
    private String description;
    @JsonProperty("LastModifiedDate")
    private String lastModifiedDate;
    @JsonProperty("WritableLibraryPath")
    private String writableLibraryPath;
    @JsonProperty("UserRoleID")
    private String userRoleId;
    @JsonProperty("StampId")
    private String stampId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getWritableLibraryPath() {
        return writableLibraryPath;
    }

    public void setWritableLibraryPath(String writableLibraryPath) {
        this.writableLibraryPath = writableLibraryPath;
    }

    public String getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(String userRoleId) {
        this.userRoleId = userRoleId;
    }

    public String getStampId() {
        return stampId;
    }

    public void setStampId(String stampId) {
        this.stampId = stampId;
    }
}
