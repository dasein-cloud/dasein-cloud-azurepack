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

package org.dasein.cloud.azurepack.compute.vm.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by vmunthiu on 3/4/2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WAPDeploymentErrorInfoModel {
    @JsonProperty("odata.type")
    private String odataType = "VMM.ErrorInfo";
    @JsonProperty("CloudProblem")
    private String cloudProblem;
    @JsonProperty("Code")
    private String code;
    @JsonProperty("DetailedCode")
    private String detailedCode;
    @JsonProperty("DetailedErrorCode")
    private String detailedErrorCode;
    @JsonProperty("DetailedSource")
    private String detailedSource;
    @JsonProperty("DisplayableErrorCode")
    private String displayableErrorCode;
    @JsonProperty("ErrorCodeString")
    private String errorCodeString;
    @JsonProperty("ErrorType")
    private String errorType;
    @JsonProperty("ExceptionDetails")
    private String exceptionDetails;
    @JsonProperty("IsConditionallyTerminating")
    private String isConditionallyTerminating;
    @JsonProperty("IsDeploymentBlocker")
    private String isDeploymentBlocker;
    @JsonProperty("IsMomAlert")
    private String isMomAlert;
    @JsonProperty("IsSuccess")
    private String isSuccess;
    @JsonProperty("IsTerminating")
    private String isTerminating;
    @JsonProperty("MessageParameters")
    private String messageParameters;
    @JsonProperty("MomAlertSeverity")
    private String momAlertSeverity;
    @JsonProperty("Problem")
    private String problem;
    @JsonProperty("RecommendedAction")
    private String recommendedAction;
    @JsonProperty("RecommendedActionCLI")
    private String recommendedActionCLI;
    @JsonProperty("ShowDetailedError")
    private String showDetailedError;

    public String getOdataType() {
        return odataType;
    }

    public void setOdataType(String odataType) {
        this.odataType = odataType;
    }

    public String getCloudProblem() {
        return cloudProblem;
    }

    public void setCloudProblem(String cloudProblem) {
        this.cloudProblem = cloudProblem;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDetailedCode() {
        return detailedCode;
    }

    public void setDetailedCode(String detailedCode) {
        this.detailedCode = detailedCode;
    }

    public String getDetailedErrorCode() {
        return detailedErrorCode;
    }

    public void setDetailedErrorCode(String detailedErrorCode) {
        this.detailedErrorCode = detailedErrorCode;
    }

    public String getDetailedSource() {
        return detailedSource;
    }

    public void setDetailedSource(String detailedSource) {
        this.detailedSource = detailedSource;
    }

    public String getDisplayableErrorCode() {
        return displayableErrorCode;
    }

    public void setDisplayableErrorCode(String displayableErrorCode) {
        this.displayableErrorCode = displayableErrorCode;
    }

    public String getErrorCodeString() {
        return errorCodeString;
    }

    public void setErrorCodeString(String errorCodeString) {
        this.errorCodeString = errorCodeString;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public String getExceptionDetails() {
        return exceptionDetails;
    }

    public void setExceptionDetails(String exceptionDetails) {
        this.exceptionDetails = exceptionDetails;
    }

    public String getIsConditionallyTerminating() {
        return isConditionallyTerminating;
    }

    public void setIsConditionallyTerminating(String isConditionallyTerminating) {
        this.isConditionallyTerminating = isConditionallyTerminating;
    }

    public String getIsDeploymentBlocker() {
        return isDeploymentBlocker;
    }

    public void setIsDeploymentBlocker(String isDeploymentBlocker) {
        this.isDeploymentBlocker = isDeploymentBlocker;
    }

    public String getIsMomAlert() {
        return isMomAlert;
    }

    public void setIsMomAlert(String isMomAlert) {
        this.isMomAlert = isMomAlert;
    }

    public String getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(String isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getIsTerminating() {
        return isTerminating;
    }

    public void setIsTerminating(String isTerminating) {
        this.isTerminating = isTerminating;
    }

    public String getMessageParameters() {
        return messageParameters;
    }

    public void setMessageParameters(String messageParameters) {
        this.messageParameters = messageParameters;
    }

    public String getMomAlertSeverity() {
        return momAlertSeverity;
    }

    public void setMomAlertSeverity(String momAlertSeverity) {
        this.momAlertSeverity = momAlertSeverity;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getRecommendedAction() {
        return recommendedAction;
    }

    public void setRecommendedAction(String recommendedAction) {
        this.recommendedAction = recommendedAction;
    }

    public String getRecommendedActionCLI() {
        return recommendedActionCLI;
    }

    public void setRecommendedActionCLI(String recommendedActionCLI) {
        this.recommendedActionCLI = recommendedActionCLI;
    }

    public String getShowDetailedError() {
        return showDetailedError;
    }

    public void setShowDetailedError(String showDetailedError) {
        this.showDetailedError = showDetailedError;
    }
}
