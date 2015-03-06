package org.dasein.cloud.azurepack.model;

/**
 * Created by vmunthiu on 3/4/2015.
 */
public class WAPUserModel {
        private String UserName;
        private String RoleName;
        private String RoleID;

        public String getUserName() {
            return UserName;
        }

        public void setUserName(String userName) {
            UserName = userName;
        }

        public String getRoleName() {
            return RoleName;
        }

        public void setRoleName(String roleName) {
            RoleName = roleName;
        }

        public String getRoleID() {
            return RoleID;
        }

        public void setRoleID(String roleID) {
            RoleID = roleID;
        }
}