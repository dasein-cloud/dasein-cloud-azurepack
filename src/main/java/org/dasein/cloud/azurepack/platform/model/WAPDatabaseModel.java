/**
 * Copyright (C) 2009-2015 Dell, Inc
 * See annotations for authorship information
 *
 * ====================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ====================================================================
 */

package org.dasein.cloud.azurepack.platform.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by vmunthiu on 6/16/2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WAPDatabaseModel {
    @JsonProperty("Name")
    private String name = null;
    @JsonProperty("SqlServerName")
    private String sqlServerName = null;
    @JsonProperty("SqlServerId")
    private String sqlServerId = null;
    @JsonProperty("MySqlServerName")
    private String mySqlServerName = null;
    @JsonProperty("MySqlServerId")
    private String mySqlServerId = null;
    @JsonProperty("SubscriptionId")
    private String subscriptionId = null;
    @JsonProperty("ConnectionString")
    private String connectionString = null;
    @JsonProperty("Edition")
    private String edition = "Default";
    @JsonProperty("BaseSizeMB")
    private String baseSizeMB = null;
    @JsonProperty("MaxSizeMB")
    private String maxSizeMB = null;
    @JsonProperty("Collation")
    private String collation = null;
    @JsonProperty("IsContained")
    private String isContained = null;
    @JsonProperty("CreationDate")
    private String creationDate = null;
    @JsonProperty("Status")
    private String status = null;
    @JsonProperty("SelfLink")
    private String selfLink = null;
    @JsonProperty("Quota")
    private String quota = null;
    @JsonProperty("AdminLogon")
    private String adminLogon = null;
    @JsonProperty("Password")
    private String password = null;
    @JsonProperty("AccountAdminId")
    private String accountAdminId = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSqlServerName() {
        return sqlServerName;
    }

    public void setSqlServerName(String sqlServerName) {
        this.sqlServerName = sqlServerName;
    }

    public String getSqlServerId() {
        return sqlServerId;
    }

    public void setSqlServerId(String sqlServerId) {
        this.sqlServerId = sqlServerId;
    }

    public String getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public String getConnectionString() {
        return connectionString;
    }

    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getBaseSizeMB() {
        return baseSizeMB;
    }

    public void setBaseSizeMB(String baseSizeMB) {
        this.baseSizeMB = baseSizeMB;
    }

    public String getMaxSizeMB() {
        return maxSizeMB;
    }

    public void setMaxSizeMB(String maxSizeMB) {
        this.maxSizeMB = maxSizeMB;
    }

    public String getCollation() {
        return collation;
    }

    public void setCollation(String collation) {
        this.collation = collation;
    }

    public String getIsContained() {
        return isContained;
    }

    public void setIsContained(String isContained) {
        this.isContained = isContained;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSelfLink() {
        return selfLink;
    }

    public void setSelfLink(String selfLink) {
        this.selfLink = selfLink;
    }

    public String getQuota() {
        return quota;
    }

    public void setQuota(String quota) {
        this.quota = quota;
    }

    public String getAdminLogon() {
        return adminLogon;
    }

    public void setAdminLogon(String adminLogon) {
        this.adminLogon = adminLogon;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccountAdminId() {
        return accountAdminId;
    }

    public void setAccountAdminId(String accountAdminId) {
        this.accountAdminId = accountAdminId;
    }

    public String getMySqlServerName() {
        return mySqlServerName;
    }

    public void setMySqlServerName(String mySqlServerName) {
        this.mySqlServerName = mySqlServerName;
    }

    public String getMySqlServerId() {
        return mySqlServerId;
    }

    public void setMySqlServerId(String mySqlServerId) {
        this.mySqlServerId = mySqlServerId;
    }
}
