package org.dasein.cloud.azurepack.compute.vm.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.dasein.cloud.azurepack.model.WAPOperatingSystemInstance;
import org.dasein.cloud.azurepack.model.WAPUserModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vmunthiu on 3/4/2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WAPVirtualMachineModel {
    @JsonProperty("odata.type")
    private String odataType = "VMM.VirtualMachine";
    @JsonProperty("odata.metadata")
    private String odataMetadata = null;
    @JsonProperty("AddedTime")
    private String addedTime = null;
    @JsonProperty("Agent")
    private String agent = null;
    @JsonProperty("AllocatedGPU")
    private String allocatedGPU = null;
    @JsonProperty("BackupEnabled")
    private String backupEnabled = null;
    @JsonProperty("BlockLiveMigrationIfHostBusy")
    private String blockLiveMigrationIfHostBusy = null;
    @JsonProperty("CanVMConnect")
    private String canVMConnect = null;
    @JsonProperty("CheckpointLocation")
    private String checkpointLocation = null;
    @JsonProperty("CloudId")
    private String cloudId = null;
    @JsonProperty("ComputerName")
    private String computerName = null;
    @JsonProperty("ComputerTierId")
    private String computerTierId = null;
    @JsonProperty("CostCenter")
    private String costCenter = null;
    @JsonProperty("CPUCount")
    private String cpuCount = null;
    @JsonProperty("CPUMax")
    private String cpuMax = null;
    @JsonProperty("CPUReserve")
    private String cpuReserve = null;
    @JsonProperty("CPUType")
    private String cpuType = null;
    @JsonProperty("CPUUtilization")
    private String cpuUtilization = null;
    @JsonProperty("CreationSource")
    private String creationSource = null;
    @JsonProperty("CreationTime")
    private String creationTime = null;
    @JsonProperty("DataExchangeEnabled")
    private String dataExchangeEnabled = null;
    @JsonProperty("DelayStart")
    private String delayStart = null;
    @JsonProperty("DeployPath")
    private String deployPath = null;
    @JsonProperty("Description")
    private String description = null;
    @JsonProperty("DiskIO")
    private String diskIO = null;
    @JsonProperty("Dismiss")
    private String dismiss = null;
    @JsonProperty("DynamicMemoryDemandMB")
    private String dynamicMemoryDemandMB = null;
    @JsonProperty("Enabled")
    private String enabled = null;
    @JsonProperty("ExcludeFromPRO")
    private String excludeFromPRO = null;
    @JsonProperty("ExpectedCPUUtilization")
    private String expectedCPUUtilization = null;
    @JsonProperty("FailedJobID")
    private String failedJobID = null;
    @JsonProperty("HasPassthroughDisk")
    private String hasPassthroughDisk = null;
    @JsonProperty("HasSavedState")
    private String hasSavedState = null;
    @JsonProperty("HasVMAdditions")
    private String hasVMAdditions = null;
    @JsonProperty("HeartbeatEnabled")
    private String heartbeatEnabled = null;
    @JsonProperty("HighlyAvailable")
    private String highlyAvailable = null;
    @JsonProperty("ID")
    private String id = "00000000-0000-0000-0000-000000000000";
    @JsonProperty("IsFaultTolerant")
    private String isFaultTolerant = null;
    @JsonProperty("IsHighlyAvailable")
    private String isHighlyAvailable = null;
    @JsonProperty("IsUndergoingLiveMigration")
    private String isUndergoingLiveMigration = null;
    @JsonProperty("LastRestoredCheckpointId")
    private String lastRestoredCheckpointId = null;
    @JsonProperty("LibraryGroup")
    private String libraryGroup = null;
    @JsonProperty("LimitCPUForMigration")
    private String limitCPUForMigration = null;
    @JsonProperty("LimitCPUFunctionality")
    private String limitCPUFunctionality = null;
    @JsonProperty("VMNetworkAssignments@odata.type")
    private String vmNetworkAssignmentsODataType = "Collection(VMM.VMNetworkAssignment)";
    @JsonProperty("VMNetworkAssignments")
    private List<String> vmNetworkAssignments = new ArrayList<String>();
    @JsonProperty("Location")
    private String location = null;
    @JsonProperty("MarkedAsTemplate")
    private String markedAsTemplate = null;
    @JsonProperty("Memory")
    private String memory = null;
    @JsonProperty("DynamicMemoryEnabled")
    private String dynamicMemoryEnabled = null;
    @JsonProperty("DynamicMemoryMinimumMB")
    private String dynamicMemoryMinimumMB = null;
    @JsonProperty("DynamicMemoryMaximumMB")
    private String dynamicMemoryMaximumMB = null;
    @JsonProperty("MemoryAssignedMB")
    private String memoryAssignedMB = null;
    @JsonProperty("MemoryAvailablePercentage")
    private String memoryAvailablePercentage = null;
    @JsonProperty("ModifiedTime")
    private String modifiedTime = null;
    @JsonProperty("MostRecentTaskId")
    private String mostRecentTaskId = null;
    @JsonProperty("Name")
    private String name = null;
    @JsonProperty("NetworkUtilization")
    private String networkUtilization = null;
    @JsonProperty("NumLock")
    private String numLock = null;
    @JsonProperty("OperatingSystem")
    private String operatingSystem = null;
    @JsonProperty("OperatingSystemInstance")
    private WAPOperatingSystemInstance operatingSystemInstance = new WAPOperatingSystemInstance();
    @JsonProperty("OperatingSystemShutdownEnabled")
    private String operatingSystemShutdownEnabled = null;
    @JsonProperty("Operation")
    private String operation = null;
    @JsonProperty("Owner")
    private WAPUserModel owner = new WAPUserModel();
    @JsonProperty("GrantedToList@odata.type")
    private String grantedToListOdataType = "Collection(VMM.UserAndRole)";
    @JsonProperty("GrantedToList")
    private List<String> grantedToList = new ArrayList<String>();
    @JsonProperty("Path")
    private String path = null;
    @JsonProperty("PerfCPUUtilization")
    private String perfCPUUtilization = null;
    @JsonProperty("PerfDiskBytesRead")
    private String perfDiskBytesRead = null;
    @JsonProperty("PerfDiskBytesWrite")
    private String perfDiskBytesWrite = null;
    @JsonProperty("PerfNetworkBytesRead")
    private String perfNetworkBytesRead = null;
    @JsonProperty("PerfNetworkBytesWrite")
    private String perfNetworkBytesWrite = null;
    @JsonProperty("CPURelativeWeight")
    private String cpuRelativeWeight = null;
    @JsonProperty("Retry")
    private String retry = null;
    @JsonProperty("RunGuestAccount")
    private String runGuestAccount = null;
    @JsonProperty("ServiceDeploymentErrorMessage")
    private String serviceDeploymentErrorMessage = null;
    @JsonProperty("ServiceId")
    private String serviceId = null;
    @JsonProperty("SharePath")
    private String sharePath = null;
    @JsonProperty("SourceObjectType")
    private String sourceObjectType = null;
    @JsonProperty("StartAction")
    private String startAction = null;
    @JsonProperty("StartVM")
    private String startVM = null;
    @JsonProperty("Status")
    private String status = null;
    @JsonProperty("StatusString")
    private String statusString = null;
    @JsonProperty("StopAction")
    private String stopAction = null;
    @JsonProperty("Tag")
    private String tag = null;
    @JsonProperty("TimeSynchronizationEnabled")
    private String timeSynchronizationEnabled = null;
    @JsonProperty("TotalSize")
    private String totalSize = null;
    @JsonProperty("Undo")
    private String undo = null;
    @JsonProperty("UndoDisksEnabled")
    private String undoDisksEnabled = null;
    @JsonProperty("UpgradeDomain")
    private String upgradeDomain = null;
    @JsonProperty("UseCluster")
    private String useCluster = null;
    @JsonProperty("UseLAN")
    private String useLAN = null;
    @JsonProperty("VirtualHardDiskId")
    private String virtualHardDiskId = null;
    @JsonProperty("VirtualizationPlatform")
    private String virtualizationPlatform = null;
    @JsonProperty("CapabilityProfile")
    private String capabilityProfile = null;
    @JsonProperty("VMBaseConfigurationId")
    private String vmBaseConfigurationId = null;
    //VMConnection@odata.mediaContentType: "application/x-rdp"
    @JsonProperty("VMConfigResource")
    private String vmConfigResource = null;
    @JsonProperty("VMCPath")
    private String vmCPath = null;
    @JsonProperty("VMHostName")
    private String vmHostName = null;
    @JsonProperty("VMId")
    private String vmId = null;
    @JsonProperty("StampId")
    private String stampId = null;
    @JsonProperty("VMResource")
    private String vmResource = null;
    @JsonProperty("VMResourceGroup")
    private String vmResourceGroup = null;
    @JsonProperty("VirtualMachineState")
    private String virtualMachineState = null;
    @JsonProperty("VMTemplateId")
    private String vmTemplateId = null;
    @JsonProperty("HardwareProfileId")
    private String hardwareProfileId = null;
    @JsonProperty("BlockDynamicOptimization")
    private String blockDynamicOptimization = null;
    @JsonProperty("CPULimitForMigration")
    private String cpuLimitForMigration = null;
    @JsonProperty("CPULimitFunctionality")
    private String cpuLimitFunctionality = null;
    @JsonProperty("Domain")
    private String domain = null;
    @JsonProperty("DynamicMemoryBufferPercentage")
    private String dynamicMemoryBufferPercentage = null;
    @JsonProperty("FullName")
    private String fullName = null;
    @JsonProperty("MemoryWeight")
    private String memoryWeight = null;
    @JsonProperty("OrganizationName")
    private String organizationName = null;
    @JsonProperty("DelayStartSeconds")
    private String delayStartSeconds = null;
    @JsonProperty("ProductKey")
    private String productKey = null;
    @JsonProperty("WorkGroup")
    private String workGroup = null;
    @JsonProperty("TimeZone")
    private String timeZone = null;
    @JsonProperty("RunAsAccountUserName")
    private String runAsAccountUserName = null;
    @JsonProperty("UserName")
    private String userName = null;
    @JsonProperty("Password")
    private String password = null;
    @JsonProperty("LocalAdminRunAsAccountName")
    private String localAdminRunAsAccountName = null;
    @JsonProperty("LocalAdminUserName")
    private String localAdminUserName = null;
    @JsonProperty("LocalAdminPassword")
    private String localAdminPassword = null;
    @JsonProperty("LinuxDomainName")
    private String linuxDomainName = null;
    @JsonProperty("LinuxAdministratorSSHKey")
    private String linuxAdministratorSSHKey = null;
    @JsonProperty("LinuxAdministratorSSHKeyString")
    private String linuxAdministratorSSHKeyString = null;
    @JsonProperty("CloudVMRoleName")
    private String cloudVMRoleName = null;
    @JsonProperty("Generation")
    private String generation = null;
    @JsonProperty("DeploymentErrorInfo")
    private WAPDeploymentErrorInfoModel deploymentErrorInfo = new WAPDeploymentErrorInfoModel();
    @JsonProperty("NewVirtualNetworkAdapterInput@odata.type")
    private String newVirtualNetworkAdapterInputODataType = "Collection(VMM.NewVMVirtualNetworkAdapterInput)";
    @JsonProperty("NewVirtualNetworkAdapterInput")
    private List<WAPNewAdapterModel> newVirtualNetworkAdapterInput = new ArrayList<WAPNewAdapterModel>();
    @JsonProperty("IsRecoveryVM")
    private String isRecoveryVM = null;

    public String getOdataType() {
        return odataType;
    }

    public void setOdataType(String odataType) {
        this.odataType = odataType;
    }

    public String getOdataMetadata() {
        return odataMetadata;
    }

    public void setOdataMetadata(String odataMetadata) {
        this.odataMetadata = odataMetadata;
    }

    public String getAddedTime() {
        return addedTime;
    }

    public void setAddedTime(String addedTime) {
        this.addedTime = addedTime;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getAllocatedGPU() {
        return allocatedGPU;
    }

    public void setAllocatedGPU(String allocatedGPU) {
        this.allocatedGPU = allocatedGPU;
    }

    public String getBackupEnabled() {
        return backupEnabled;
    }

    public void setBackupEnabled(String backupEnabled) {
        this.backupEnabled = backupEnabled;
    }

    public String getBlockLiveMigrationIfHostBusy() {
        return blockLiveMigrationIfHostBusy;
    }

    public void setBlockLiveMigrationIfHostBusy(String blockLiveMigrationIfHostBusy) {
        this.blockLiveMigrationIfHostBusy = blockLiveMigrationIfHostBusy;
    }

    public String getCanVMConnect() {
        return canVMConnect;
    }

    public void setCanVMConnect(String canVMConnect) {
        this.canVMConnect = canVMConnect;
    }

    public String getCheckpointLocation() {
        return checkpointLocation;
    }

    public void setCheckpointLocation(String checkpointLocation) {
        this.checkpointLocation = checkpointLocation;
    }

    public String getCloudId() {
        return cloudId;
    }

    public void setCloudId(String cloudId) {
        this.cloudId = cloudId;
    }

    public String getComputerName() {
        return computerName;
    }

    public void setComputerName(String computerName) {
        this.computerName = computerName;
    }

    public String getComputerTierId() {
        return computerTierId;
    }

    public void setComputerTierId(String computerTierId) {
        this.computerTierId = computerTierId;
    }

    public String getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(String costCenter) {
        this.costCenter = costCenter;
    }

    public String getCpuCount() {
        return cpuCount;
    }

    public void setCpuCount(String cpuCount) {
        this.cpuCount = cpuCount;
    }

    public String getCpuMax() {
        return cpuMax;
    }

    public void setCpuMax(String cpuMax) {
        this.cpuMax = cpuMax;
    }

    public String getCpuReserve() {
        return cpuReserve;
    }

    public void setCpuReserve(String cpuReserve) {
        this.cpuReserve = cpuReserve;
    }

    public String getCpuType() {
        return cpuType;
    }

    public void setCpuType(String cpuType) {
        this.cpuType = cpuType;
    }

    public String getCpuUtilization() {
        return cpuUtilization;
    }

    public void setCpuUtilization(String cpuUtilization) {
        this.cpuUtilization = cpuUtilization;
    }

    public String getCreationSource() {
        return creationSource;
    }

    public void setCreationSource(String creationSource) {
        this.creationSource = creationSource;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getDataExchangeEnabled() {
        return dataExchangeEnabled;
    }

    public void setDataExchangeEnabled(String dataExchangeEnabled) {
        this.dataExchangeEnabled = dataExchangeEnabled;
    }

    public String getDelayStart() {
        return delayStart;
    }

    public void setDelayStart(String delayStart) {
        this.delayStart = delayStart;
    }

    public String getDeployPath() {
        return deployPath;
    }

    public void setDeployPath(String deployPath) {
        this.deployPath = deployPath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDiskIO() {
        return diskIO;
    }

    public void setDiskIO(String diskIO) {
        this.diskIO = diskIO;
    }

    public String getDismiss() {
        return dismiss;
    }

    public void setDismiss(String dismiss) {
        this.dismiss = dismiss;
    }

    public String getDynamicMemoryDemandMB() {
        return dynamicMemoryDemandMB;
    }

    public void setDynamicMemoryDemandMB(String dynamicMemoryDemandMB) {
        this.dynamicMemoryDemandMB = dynamicMemoryDemandMB;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public String getExcludeFromPRO() {
        return excludeFromPRO;
    }

    public void setExcludeFromPRO(String excludeFromPRO) {
        this.excludeFromPRO = excludeFromPRO;
    }

    public String getExpectedCPUUtilization() {
        return expectedCPUUtilization;
    }

    public void setExpectedCPUUtilization(String expectedCPUUtilization) {
        this.expectedCPUUtilization = expectedCPUUtilization;
    }

    public String getFailedJobID() {
        return failedJobID;
    }

    public void setFailedJobID(String failedJobID) {
        this.failedJobID = failedJobID;
    }

    public String getHasPassthroughDisk() {
        return hasPassthroughDisk;
    }

    public void setHasPassthroughDisk(String hasPassthroughDisk) {
        this.hasPassthroughDisk = hasPassthroughDisk;
    }

    public String getHasSavedState() {
        return hasSavedState;
    }

    public void setHasSavedState(String hasSavedState) {
        this.hasSavedState = hasSavedState;
    }

    public String getHasVMAdditions() {
        return hasVMAdditions;
    }

    public void setHasVMAdditions(String hasVMAdditions) {
        this.hasVMAdditions = hasVMAdditions;
    }

    public String getHeartbeatEnabled() {
        return heartbeatEnabled;
    }

    public void setHeartbeatEnabled(String heartbeatEnabled) {
        this.heartbeatEnabled = heartbeatEnabled;
    }

    public String getHighlyAvailable() {
        return highlyAvailable;
    }

    public void setHighlyAvailable(String highlyAvailable) {
        this.highlyAvailable = highlyAvailable;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsFaultTolerant() {
        return isFaultTolerant;
    }

    public void setIsFaultTolerant(String isFaultTolerant) {
        this.isFaultTolerant = isFaultTolerant;
    }

    public String getIsHighlyAvailable() {
        return isHighlyAvailable;
    }

    public void setIsHighlyAvailable(String isHighlyAvailable) {
        this.isHighlyAvailable = isHighlyAvailable;
    }

    public String getIsUndergoingLiveMigration() {
        return isUndergoingLiveMigration;
    }

    public void setIsUndergoingLiveMigration(String isUndergoingLiveMigration) {
        this.isUndergoingLiveMigration = isUndergoingLiveMigration;
    }

    public String getLastRestoredCheckpointId() {
        return lastRestoredCheckpointId;
    }

    public void setLastRestoredCheckpointId(String lastRestoredCheckpointId) {
        this.lastRestoredCheckpointId = lastRestoredCheckpointId;
    }

    public String getLibraryGroup() {
        return libraryGroup;
    }

    public void setLibraryGroup(String libraryGroup) {
        this.libraryGroup = libraryGroup;
    }

    public String getLimitCPUForMigration() {
        return limitCPUForMigration;
    }

    public void setLimitCPUForMigration(String limitCPUForMigration) {
        this.limitCPUForMigration = limitCPUForMigration;
    }

    public String getLimitCPUFunctionality() {
        return limitCPUFunctionality;
    }

    public void setLimitCPUFunctionality(String limitCPUFunctionality) {
        this.limitCPUFunctionality = limitCPUFunctionality;
    }

    public List<String> getVmNetworkAssignments() {
        return vmNetworkAssignments;
    }

    public void setVmNetworkAssignments(List<String> vmNetworkAssignments) {
        this.vmNetworkAssignments = vmNetworkAssignments;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMarkedAsTemplate() {
        return markedAsTemplate;
    }

    public void setMarkedAsTemplate(String markedAsTemplate) {
        this.markedAsTemplate = markedAsTemplate;
    }

    public String getMemory() {
        return memory;
    }

    public void setMemory(String memory) {
        this.memory = memory;
    }

    public String getDynamicMemoryEnabled() {
        return dynamicMemoryEnabled;
    }

    public void setDynamicMemoryEnabled(String dynamicMemoryEnabled) {
        this.dynamicMemoryEnabled = dynamicMemoryEnabled;
    }

    public String getDynamicMemoryMinimumMB() {
        return dynamicMemoryMinimumMB;
    }

    public void setDynamicMemoryMinimumMB(String dynamicMemoryMinimumMB) {
        this.dynamicMemoryMinimumMB = dynamicMemoryMinimumMB;
    }

    public String getDynamicMemoryMaximumMB() {
        return dynamicMemoryMaximumMB;
    }

    public void setDynamicMemoryMaximumMB(String dynamicMemoryMaximumMB) {
        this.dynamicMemoryMaximumMB = dynamicMemoryMaximumMB;
    }

    public String getMemoryAssignedMB() {
        return memoryAssignedMB;
    }

    public void setMemoryAssignedMB(String memoryAssignedMB) {
        this.memoryAssignedMB = memoryAssignedMB;
    }

    public String getMemoryAvailablePercentage() {
        return memoryAvailablePercentage;
    }

    public void setMemoryAvailablePercentage(String memoryAvailablePercentage) {
        this.memoryAvailablePercentage = memoryAvailablePercentage;
    }

    public String getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(String modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public String getMostRecentTaskId() {
        return mostRecentTaskId;
    }

    public void setMostRecentTaskId(String mostRecentTaskId) {
        this.mostRecentTaskId = mostRecentTaskId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNetworkUtilization() {
        return networkUtilization;
    }

    public void setNetworkUtilization(String networkUtilization) {
        this.networkUtilization = networkUtilization;
    }

    public String getNumLock() {
        return numLock;
    }

    public void setNumLock(String numLock) {
        this.numLock = numLock;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public WAPOperatingSystemInstance getOperatingSystemInstance() {
        return operatingSystemInstance;
    }

    public void setOperatingSystemInstance(WAPOperatingSystemInstance operatingSystemInstance) {
        this.operatingSystemInstance = operatingSystemInstance;
    }

    public String getOperatingSystemShutdownEnabled() {
        return operatingSystemShutdownEnabled;
    }

    public void setOperatingSystemShutdownEnabled(String operatingSystemShutdownEnabled) {
        this.operatingSystemShutdownEnabled = operatingSystemShutdownEnabled;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public WAPUserModel getOwner() {
        return owner;
    }

    public void setOwner(WAPUserModel owner) {
        this.owner = owner;
    }

    public List<String> getGrantedToList() {
        return grantedToList;
    }

    public void setGrantedToList(List<String> grantedToList) {
        this.grantedToList = grantedToList;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPerfCPUUtilization() {
        return perfCPUUtilization;
    }

    public void setPerfCPUUtilization(String perfCPUUtilization) {
        this.perfCPUUtilization = perfCPUUtilization;
    }

    public String getPerfDiskBytesRead() {
        return perfDiskBytesRead;
    }

    public void setPerfDiskBytesRead(String perfDiskBytesRead) {
        this.perfDiskBytesRead = perfDiskBytesRead;
    }

    public String getPerfDiskBytesWrite() {
        return perfDiskBytesWrite;
    }

    public void setPerfDiskBytesWrite(String perfDiskBytesWrite) {
        this.perfDiskBytesWrite = perfDiskBytesWrite;
    }

    public String getPerfNetworkBytesRead() {
        return perfNetworkBytesRead;
    }

    public void setPerfNetworkBytesRead(String perfNetworkBytesRead) {
        this.perfNetworkBytesRead = perfNetworkBytesRead;
    }

    public String getPerfNetworkBytesWrite() {
        return perfNetworkBytesWrite;
    }

    public void setPerfNetworkBytesWrite(String perfNetworkBytesWrite) {
        this.perfNetworkBytesWrite = perfNetworkBytesWrite;
    }

    public String getCpuRelativeWeight() {
        return cpuRelativeWeight;
    }

    public void setCpuRelativeWeight(String cpuRelativeWeight) {
        this.cpuRelativeWeight = cpuRelativeWeight;
    }

    public String getRetry() {
        return retry;
    }

    public void setRetry(String retry) {
        this.retry = retry;
    }

    public String getRunGuestAccount() {
        return runGuestAccount;
    }

    public void setRunGuestAccount(String runGuestAccount) {
        this.runGuestAccount = runGuestAccount;
    }

    public String getServiceDeploymentErrorMessage() {
        return serviceDeploymentErrorMessage;
    }

    public void setServiceDeploymentErrorMessage(String serviceDeploymentErrorMessage) {
        this.serviceDeploymentErrorMessage = serviceDeploymentErrorMessage;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getSharePath() {
        return sharePath;
    }

    public void setSharePath(String sharePath) {
        this.sharePath = sharePath;
    }

    public String getSourceObjectType() {
        return sourceObjectType;
    }

    public void setSourceObjectType(String sourceObjectType) {
        this.sourceObjectType = sourceObjectType;
    }

    public String getStartAction() {
        return startAction;
    }

    public void setStartAction(String startAction) {
        this.startAction = startAction;
    }

    public String getStartVM() {
        return startVM;
    }

    public void setStartVM(String startVM) {
        this.startVM = startVM;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusString() {
        return statusString;
    }

    public void setStatusString(String statusString) {
        this.statusString = statusString;
    }

    public String getStopAction() {
        return stopAction;
    }

    public void setStopAction(String stopAction) {
        this.stopAction = stopAction;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTimeSynchronizationEnabled() {
        return timeSynchronizationEnabled;
    }

    public void setTimeSynchronizationEnabled(String timeSynchronizationEnabled) {
        this.timeSynchronizationEnabled = timeSynchronizationEnabled;
    }

    public String getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(String totalSize) {
        this.totalSize = totalSize;
    }

    public String getUndo() {
        return undo;
    }

    public void setUndo(String undo) {
        this.undo = undo;
    }

    public String getUndoDisksEnabled() {
        return undoDisksEnabled;
    }

    public void setUndoDisksEnabled(String undoDisksEnabled) {
        this.undoDisksEnabled = undoDisksEnabled;
    }

    public String getUpgradeDomain() {
        return upgradeDomain;
    }

    public void setUpgradeDomain(String upgradeDomain) {
        this.upgradeDomain = upgradeDomain;
    }

    public String getUseCluster() {
        return useCluster;
    }

    public void setUseCluster(String useCluster) {
        this.useCluster = useCluster;
    }

    public String getUseLAN() {
        return useLAN;
    }

    public void setUseLAN(String useLAN) {
        this.useLAN = useLAN;
    }

    public String getVirtualHardDiskId() {
        return virtualHardDiskId;
    }

    public void setVirtualHardDiskId(String virtualHardDiskId) {
        this.virtualHardDiskId = virtualHardDiskId;
    }

    public String getVirtualizationPlatform() {
        return virtualizationPlatform;
    }

    public void setVirtualizationPlatform(String virtualizationPlatform) {
        this.virtualizationPlatform = virtualizationPlatform;
    }

    public String getCapabilityProfile() {
        return capabilityProfile;
    }

    public void setCapabilityProfile(String capabilityProfile) {
        this.capabilityProfile = capabilityProfile;
    }

    public String getVmBaseConfigurationId() {
        return vmBaseConfigurationId;
    }

    public void setVmBaseConfigurationId(String vmBaseConfigurationId) {
        this.vmBaseConfigurationId = vmBaseConfigurationId;
    }

    public String getVmConfigResource() {
        return vmConfigResource;
    }

    public void setVmConfigResource(String vmConfigResource) {
        this.vmConfigResource = vmConfigResource;
    }

    public String getVmCPath() {
        return vmCPath;
    }

    public void setVmCPath(String vmCPath) {
        this.vmCPath = vmCPath;
    }

    public String getVmHostName() {
        return vmHostName;
    }

    public void setVmHostName(String vmHostName) {
        this.vmHostName = vmHostName;
    }

    public String getVmId() {
        return vmId;
    }

    public void setVmId(String vmId) {
        this.vmId = vmId;
    }

    public String getStampId() {
        return stampId;
    }

    public void setStampId(String stampId) {
        this.stampId = stampId;
    }

    public String getVmResource() {
        return vmResource;
    }

    public void setVmResource(String vmResource) {
        this.vmResource = vmResource;
    }

    public String getVmResourceGroup() {
        return vmResourceGroup;
    }

    public void setVmResourceGroup(String vmResourceGroup) {
        this.vmResourceGroup = vmResourceGroup;
    }

    public String getVirtualMachineState() {
        return virtualMachineState;
    }

    public void setVirtualMachineState(String virtualMachineState) {
        this.virtualMachineState = virtualMachineState;
    }

    public String getVmTemplateId() {
        return vmTemplateId;
    }

    public void setVmTemplateId(String vmTemplateId) {
        this.vmTemplateId = vmTemplateId;
    }

    public String getHardwareProfileId() {
        return hardwareProfileId;
    }

    public void setHardwareProfileId(String hardwareProfileId) {
        this.hardwareProfileId = hardwareProfileId;
    }

    public String getBlockDynamicOptimization() {
        return blockDynamicOptimization;
    }

    public void setBlockDynamicOptimization(String blockDynamicOptimization) {
        this.blockDynamicOptimization = blockDynamicOptimization;
    }

    public String getCpuLimitForMigration() {
        return cpuLimitForMigration;
    }

    public void setCpuLimitForMigration(String cpuLimitForMigration) {
        this.cpuLimitForMigration = cpuLimitForMigration;
    }

    public String getCpuLimitFunctionality() {
        return cpuLimitFunctionality;
    }

    public void setCpuLimitFunctionality(String cpuLimitFunctionality) {
        this.cpuLimitFunctionality = cpuLimitFunctionality;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getDynamicMemoryBufferPercentage() {
        return dynamicMemoryBufferPercentage;
    }

    public void setDynamicMemoryBufferPercentage(String dynamicMemoryBufferPercentage) {
        this.dynamicMemoryBufferPercentage = dynamicMemoryBufferPercentage;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMemoryWeight() {
        return memoryWeight;
    }

    public void setMemoryWeight(String memoryWeight) {
        this.memoryWeight = memoryWeight;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getDelayStartSeconds() {
        return delayStartSeconds;
    }

    public void setDelayStartSeconds(String delayStartSeconds) {
        this.delayStartSeconds = delayStartSeconds;
    }

    public String getProductKey() {
        return productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey;
    }

    public String getWorkGroup() {
        return workGroup;
    }

    public void setWorkGroup(String workGroup) {
        this.workGroup = workGroup;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getRunAsAccountUserName() {
        return runAsAccountUserName;
    }

    public void setRunAsAccountUserName(String runAsAccountUserName) {
        this.runAsAccountUserName = runAsAccountUserName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLocalAdminRunAsAccountName() {
        return localAdminRunAsAccountName;
    }

    public void setLocalAdminRunAsAccountName(String localAdminRunAsAccountName) {
        this.localAdminRunAsAccountName = localAdminRunAsAccountName;
    }

    public String getLocalAdminUserName() {
        return localAdminUserName;
    }

    public void setLocalAdminUserName(String localAdminUserName) {
        this.localAdminUserName = localAdminUserName;
    }

    public String getLocalAdminPassword() {
        return localAdminPassword;
    }

    public void setLocalAdminPassword(String localAdminPassword) {
        this.localAdminPassword = localAdminPassword;
    }

    public String getLinuxDomainName() {
        return linuxDomainName;
    }

    public void setLinuxDomainName(String linuxDomainName) {
        this.linuxDomainName = linuxDomainName;
    }

    public String getLinuxAdministratorSSHKey() {
        return linuxAdministratorSSHKey;
    }

    public void setLinuxAdministratorSSHKey(String linuxAdministratorSSHKey) {
        this.linuxAdministratorSSHKey = linuxAdministratorSSHKey;
    }

    public String getLinuxAdministratorSSHKeyString() {
        return linuxAdministratorSSHKeyString;
    }

    public void setLinuxAdministratorSSHKeyString(String linuxAdministratorSSHKeyString) {
        this.linuxAdministratorSSHKeyString = linuxAdministratorSSHKeyString;
    }

    public String getCloudVMRoleName() {
        return cloudVMRoleName;
    }

    public void setCloudVMRoleName(String cloudVMRoleName) {
        this.cloudVMRoleName = cloudVMRoleName;
    }

    public String getGeneration() {
        return generation;
    }

    public void setGeneration(String generation) {
        this.generation = generation;
    }

    public WAPDeploymentErrorInfoModel getDeploymentErrorInfo() {
        return deploymentErrorInfo;
    }

    public void setDeploymentErrorInfo(WAPDeploymentErrorInfoModel deploymentErrorInfo) {
        this.deploymentErrorInfo = deploymentErrorInfo;
    }

    public List<WAPNewAdapterModel> getNewVirtualNetworkAdapterInput() {
        return newVirtualNetworkAdapterInput;
    }

    public void setNewVirtualNetworkAdapterInput(List<WAPNewAdapterModel> newVirtualNetworkAdapterInput) {
        this.newVirtualNetworkAdapterInput = newVirtualNetworkAdapterInput;
    }

    public String getIsRecoveryVM() {
        return isRecoveryVM;
    }

    public void setIsRecoveryVM(String isRecoveryVM) {
        this.isRecoveryVM = isRecoveryVM;
    }

    public String getVmNetworkAssignmentsODataType() {
        return vmNetworkAssignmentsODataType;
    }

    public void setVmNetworkAssignmentsODataType(String vmNetworkAssignmentsODataType) {
        this.vmNetworkAssignmentsODataType = vmNetworkAssignmentsODataType;
    }

    public String getNewVirtualNetworkAdapterInputODataType() {
        return newVirtualNetworkAdapterInputODataType;
    }

    public void setNewVirtualNetworkAdapterInputODataType(String newVirtualNetworkAdapterInputODataType) {
        this.newVirtualNetworkAdapterInputODataType = newVirtualNetworkAdapterInputODataType;
    }

    public String getGrantedToListOdataType() {
        return grantedToListOdataType;
    }

    public void setGrantedToListOdataType(String grantedToListOdataType) {
        this.grantedToListOdataType = grantedToListOdataType;
    }
}