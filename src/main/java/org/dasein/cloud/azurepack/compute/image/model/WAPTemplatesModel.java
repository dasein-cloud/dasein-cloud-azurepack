package org.dasein.cloud.azurepack.compute.image.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by vmunthiu on 3/3/2015.
 */
public class WAPTemplatesModel {
    @JsonProperty("odata.metadata")
    private String odataMetadata;
    @JsonProperty("value")
    private List<WAPTemplateModel> templates;

    public String getOdataMetadata() {
        return odataMetadata;
    }

    public void setOdataMetadata(String odataMetadata) {
        this.odataMetadata = odataMetadata;
    }

    public List<WAPTemplateModel> getTemplates() {
        return templates;
    }

    public void setTemplates(List<WAPTemplateModel> templates) {
        this.templates = templates;
    }
}
