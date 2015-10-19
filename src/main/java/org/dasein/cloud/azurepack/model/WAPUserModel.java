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

package org.dasein.cloud.azurepack.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by vmunthiu on 3/4/2015.
 */
public class WAPUserModel {
        @JsonProperty("odata.type")
        private String odataType = "VMM.UserAndRole";
        @JsonProperty("UserName")
        private String userName = null;
        @JsonProperty("RoleName")
        private String roleName = null;
        @JsonProperty("RoleID")
        private String roleID = null;

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getRoleName() {
            return roleName;
        }

        public void setRoleName(String roleName) {
            this.roleName = roleName;
        }

        public String getRoleID() {
            return roleID;
        }

        public void setRoleID(String roleID) {
            this.roleID = roleID;
        }

        public String getOdataType() { return odataType;  }

        public void setOdataType(String odataType) {  this.odataType = odataType; }
}