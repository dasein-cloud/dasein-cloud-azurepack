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