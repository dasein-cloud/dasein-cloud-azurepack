package org.dasein.cloud.azurepack.platform.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by vmunthiu on 12/16/2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WAPDatabaseProducts {
    @JsonProperty("cloud")
    private String cloud = null;
    @JsonProperty("provider")
    private String provider = null;
    @JsonProperty("products")
    private WAPDatabaseProduct[] products = null;

    public String getCloud() {
        return cloud;
    }

    public void setCloud(String cloud) {
        this.cloud = cloud;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public WAPDatabaseProduct[] getProducts() {
        return products;
    }

    public void setProducts(WAPDatabaseProduct[] products) {
        this.products = products;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class WAPDatabaseProduct {
        @JsonProperty("name")
        private String name = null;
        @JsonProperty("highAvailability")
        private String highAvailability = null;
        @JsonProperty("license")
        private String license = null;
        @JsonProperty("engine")
        private String engine = null;
        @JsonProperty("maxStorage")
        private String maxStorage = null;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getHighAvailability() {
            return highAvailability;
        }

        public void setHighAvailability(String highAvailability) {
            this.highAvailability = highAvailability;
        }

        public String getLicense() {
            return license;
        }

        public void setLicense(String license) {
            this.license = license;
        }

        public String getEngine() {
            return engine;
        }

        public void setEngine(String engine) {
            this.engine = engine;
        }

        public String getMaxStorage() {
            return maxStorage;
        }

        public void setMaxStorage(String maxStorage) {
            this.maxStorage = maxStorage;
        }
    }
}
