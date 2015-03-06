package org.dasein.cloud.azurepack.model;

/**
 * Created by vmunthiu on 3/4/2015.
 */
public class WAPOperatingSystemInstance {
        private String Name;
        private String Description;
        private String Version;
        private String Architecture;
        private String Edition;
        private String OSType;
        private String ProductType;

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public String getDescription() {
            return Description;
        }

        public void setDescription(String description) {
            Description = description;
        }

        public String getVersion() {
            return Version;
        }

        public void setVersion(String version) {
            Version = version;
        }

        public String getArchitecture() {
            return Architecture;
        }

        public void setArchitecture(String architecture) {
            Architecture = architecture;
        }

        public String getEdition() {
            return Edition;
        }

        public void setEdition(String edition) {
            Edition = edition;
        }

        public String getOSType() {
            return OSType;
        }

        public void setOSType(String OSType) {
            this.OSType = OSType;
        }

        public String getProductType() {
            return ProductType;
        }

        public void setProductType(String productType) {
            ProductType = productType;
        }
}

