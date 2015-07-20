package org.dasein.cloud.azurepack.network.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by vmunthiu on 7/20/2015.
 */
public class WAPNatRulesModel {
    @JsonProperty("odata.metadata")
    private String odataMetadata;
    @JsonProperty("value")
    private List<WAPNatRuleModel> rules;

    public String getOdataMetadata() {
        return odataMetadata;
    }

    public void setOdataMetadata(String odataMetadata) {
        this.odataMetadata = odataMetadata;
    }

    public List<WAPNatRuleModel> getRules() {
        return rules;
    }

    public void setRules(List<WAPNatRuleModel> rules) {
        this.rules = rules;
    }
}
