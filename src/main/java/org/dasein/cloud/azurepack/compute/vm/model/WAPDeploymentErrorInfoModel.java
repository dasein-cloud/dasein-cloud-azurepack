package org.dasein.cloud.azurepack.compute.vm.model;

/**
 * Created by vmunthiu on 3/4/2015.
 */
public class WAPDeploymentErrorInfoModel {
    private String CloudProblem;
    private String Code;
    private String DetailedCode;
    private String DetailedErrorCode;
    private String DetailedSource;
    private String DisplayableErrorCode;
    private String ErrorCodeString;
    private String ErrorType;
    private String ExceptionDetails;
    private String IsConditionallyTerminating;
    private String IsDeploymentBlocker;
    private String IsMomAlert;
    private String IsSuccess;
    private String IsTerminating;
    private String MessageParameters;
    private String MomAlertSeverity;
    private String Problem;
    private String RecommendedAction;
    private String RecommendedActionCLI;
    private String ShowDetailedError;

    public String getCloudProblem() {
        return CloudProblem;
    }

    public void setCloudProblem(String cloudProblem) {
        CloudProblem = cloudProblem;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getDetailedCode() {
        return DetailedCode;
    }

    public void setDetailedCode(String detailedCode) {
        DetailedCode = detailedCode;
    }

    public String getDetailedErrorCode() {
        return DetailedErrorCode;
    }

    public void setDetailedErrorCode(String detailedErrorCode) {
        DetailedErrorCode = detailedErrorCode;
    }

    public String getDetailedSource() {
        return DetailedSource;
    }

    public void setDetailedSource(String detailedSource) {
        DetailedSource = detailedSource;
    }

    public String getDisplayableErrorCode() {
        return DisplayableErrorCode;
    }

    public void setDisplayableErrorCode(String displayableErrorCode) {
        DisplayableErrorCode = displayableErrorCode;
    }

    public String getErrorCodeString() {
        return ErrorCodeString;
    }

    public void setErrorCodeString(String errorCodeString) {
        ErrorCodeString = errorCodeString;
    }

    public String getErrorType() {
        return ErrorType;
    }

    public void setErrorType(String errorType) {
        ErrorType = errorType;
    }

    public String getExceptionDetails() {
        return ExceptionDetails;
    }

    public void setExceptionDetails(String exceptionDetails) {
        ExceptionDetails = exceptionDetails;
    }

    public String getIsConditionallyTerminating() {
        return IsConditionallyTerminating;
    }

    public void setIsConditionallyTerminating(String isConditionallyTerminating) {
        IsConditionallyTerminating = isConditionallyTerminating;
    }

    public String getIsDeploymentBlocker() {
        return IsDeploymentBlocker;
    }

    public void setIsDeploymentBlocker(String isDeploymentBlocker) {
        IsDeploymentBlocker = isDeploymentBlocker;
    }

    public String getIsMomAlert() {
        return IsMomAlert;
    }

    public void setIsMomAlert(String isMomAlert) {
        IsMomAlert = isMomAlert;
    }

    public String getIsSuccess() {
        return IsSuccess;
    }

    public void setIsSuccess(String isSuccess) {
        IsSuccess = isSuccess;
    }

    public String getIsTerminating() {
        return IsTerminating;
    }

    public void setIsTerminating(String isTerminating) {
        IsTerminating = isTerminating;
    }

    public String getMessageParameters() {
        return MessageParameters;
    }

    public void setMessageParameters(String messageParameters) {
        MessageParameters = messageParameters;
    }

    public String getMomAlertSeverity() {
        return MomAlertSeverity;
    }

    public void setMomAlertSeverity(String momAlertSeverity) {
        MomAlertSeverity = momAlertSeverity;
    }

    public String getProblem() {
        return Problem;
    }

    public void setProblem(String problem) {
        Problem = problem;
    }

    public String getRecommendedAction() {
        return RecommendedAction;
    }

    public void setRecommendedAction(String recommendedAction) {
        RecommendedAction = recommendedAction;
    }

    public String getRecommendedActionCLI() {
        return RecommendedActionCLI;
    }

    public void setRecommendedActionCLI(String recommendedActionCLI) {
        RecommendedActionCLI = recommendedActionCLI;
    }

    public String getShowDetailedError() {
        return ShowDetailedError;
    }

    public void setShowDetailedError(String showDetailedError) {
        ShowDetailedError = showDetailedError;
    }
}
