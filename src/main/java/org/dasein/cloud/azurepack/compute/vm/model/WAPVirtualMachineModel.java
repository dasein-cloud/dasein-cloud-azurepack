package org.dasein.cloud.azurepack.compute.vm.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.dasein.cloud.azurepack.model.WAPOperatingSystemInstance;
import org.dasein.cloud.azurepack.model.WAPUserModel;

import java.util.List;

/**
 * Created by vmunthiu on 3/4/2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WAPVirtualMachineModel {
    @JsonProperty("odata.type")
    private String OdataType = "VMM.VirtualMachine";
    @JsonProperty("odata.metadata")
    private String OdataMetadata;
    @JsonProperty("AddedTime")
    private String AddedTime;
    @JsonProperty("Agent")
    private String Agent;
    private String AllocatedGPU;
    private String BackupEnabled;
    private String BlockLiveMigrationIfHostBusy;
    private String CanVMConnect;
    private String CheckpointLocation;
    @JsonProperty("CloudId")
    private String CloudId;
    private String ComputerName;
    private String ComputerTierId;
    private String CostCenter;
    private String CPUCount;
    private String CPUMax;
    private String CPUReserve;
    private String CPUType;
    private String CPUUtilization;
    private String CreationSource;
    private String CreationTime;
    private String DataExchangeEnabled;
    private String DelayStart;
    private String DeployPath;
    private String Description;
    private String DiskIO;
    private String Dismiss;
    private String DynamicMemoryDemandMB;
    private String Enabled;
    private String ExcludeFromPRO;
    private String ExpectedCPUUtilization;
    private String FailedJobID;
    private String HasPassthroughDisk;
    private String HasSavedState;
    private String HasVMAdditions;
    private String HeartbeatEnabled;
    private String HighlyAvailable;
    @JsonProperty("ID")
    private String ID;
    private String IsFaultTolerant;
    private String IsHighlyAvailable;
    private String IsUndergoingLiveMigration;
    private String LastRestoredCheckpointId;
    private String LibraryGroup;
    private String LimitCPUForMigration;
    private String LimitCPUFunctionality;
    private String VMNetworkAssignments;
    private String Location;
    private String MarkedAsTemplate;
    private String Memory;
    private String DynamicMemoryEnabled;
    private String DynamicMemoryMinimumMB;
    private String DynamicMemoryMaximumMB;
    private String MemoryAssignedMB;
    private String MemoryAvailablePercentage;
    private String ModifiedTime;
    private String MostRecentTaskId;
    @JsonProperty("Name")
    private String Name;
    private String NetworkUtilization;
    private String NumLock;
    private String OperatingSystem;
    private WAPOperatingSystemInstance OperatingSystemInstance;
    private String OperatingSystemShutdownEnabled;
    private String Operation;
    private WAPUserModel Owner;
    private List<String> GrantedToList;;
    private String Path;
    private String PerfCPUUtilization;
    private String PerfDiskBytesRead;
    private String PerfDiskBytesWrite;
    private String PerfNetworkBytesRead;
    private String PerfNetworkBytesWrite;
    private String CPURelativeWeight;
    private String Retry;
    private String RunGuestAccount;
    private String ServiceDeploymentErrorMessage;
    private String ServiceId;
    private String SharePath;
    private String SourceObjectType;
    private String StartAction;
    private String StartVM;
    private String Status;
    @JsonProperty("StatusString")
    private String StatusString;
    private String StopAction;
    private String Tag;
    private String TimeSynchronizationEnabled;
    private String TotalSize;
    private String Undo;
    private String UndoDisksEnabled;
    private String UpgradeDomain;
    private String UseCluster;
    private String UseLAN;
    private String VirtualHardDiskId;
    private String VirtualizationPlatform;
    private String CapabilityProfile;
    private String VMBaseConfigurationId;
    //VMConnection@odata.mediaContentType: "application/x-rdp"
    private String VMConfigResource;
    private String VMCPath;
    private String VMHostName;
    private String VMId;
    @JsonProperty("StampId")
    private String StampId;
    private String VMResource;
    private String VMResourceGroup;
    private String VirtualMachineState;
    private String VMTemplateId;
    private String HardwareProfileId;
    private String BlockDynamicOptimization;
    private String CPULimitForMigration;
    private String CPULimitFunctionality;
    private String Domain;
    private String DynamicMemoryBufferPercentage;
    private String FullName;
    private String MemoryWeight;
    private String OrganizationName;
    private String DelayStartSeconds;
    private String ProductKey;
    private String WorkGroup;
    private String TimeZone;
    private String RunAsAccountUserName;
    private String UserName;
    private String Password;
    private String LocalAdminRunAsAccountName;
    private String LocalAdminUserName;
    private String LocalAdminPassword;
    private String LinuxDomainName;
    private String LinuxAdministratorSSHKey;
    private String LinuxAdministratorSSHKeyString;
    private String CloudVMRoleName;
    private String Generation;
    private WAPDeploymentErrorInfoModel DeploymentErrorInfo;
    private List<String> NewVirtualNetworkAdapterInput;
    private String IsRecoveryVM;

    public String getAddedTime() {
        return AddedTime;
    }

    public void setAddedTime(String addedTime) {
        AddedTime = addedTime;
    }

    public String getAgent() {
        return Agent;
    }

    public void setAgent(String agent) {
        Agent = agent;
    }

    public String getAllocatedGPU() {
        return AllocatedGPU;
    }

    public void setAllocatedGPU(String allocatedGPU) {
        AllocatedGPU = allocatedGPU;
    }

    public String getBackupEnabled() {
        return BackupEnabled;
    }

    public void setBackupEnabled(String backupEnabled) {
        BackupEnabled = backupEnabled;
    }

    public String getBlockLiveMigrationIfHostBusy() {
        return BlockLiveMigrationIfHostBusy;
    }

    public void setBlockLiveMigrationIfHostBusy(String blockLiveMigrationIfHostBusy) {
        BlockLiveMigrationIfHostBusy = blockLiveMigrationIfHostBusy;
    }

    public String getCanVMConnect() {
        return CanVMConnect;
    }

    public void setCanVMConnect(String canVMConnect) {
        CanVMConnect = canVMConnect;
    }

    public String getCheckpointLocation() {
        return CheckpointLocation;
    }

    public void setCheckpointLocation(String checkpointLocation) {
        CheckpointLocation = checkpointLocation;
    }

    public String getCloudId() {
        return CloudId;
    }

    public void setCloudId(String cloudId) {
        CloudId = cloudId;
    }

    public String getComputerName() {
        return ComputerName;
    }

    public void setComputerName(String computerName) {
        ComputerName = computerName;
    }

    public String getComputerTierId() {
        return ComputerTierId;
    }

    public void setComputerTierId(String computerTierId) {
        ComputerTierId = computerTierId;
    }

    public String getCostCenter() {
        return CostCenter;
    }

    public void setCostCenter(String costCenter) {
        CostCenter = costCenter;
    }

    public String getCPUCount() {
        return CPUCount;
    }

    public void setCPUCount(String CPUCount) {
        this.CPUCount = CPUCount;
    }

    public String getCPUMax() {
        return CPUMax;
    }

    public void setCPUMax(String CPUMax) {
        this.CPUMax = CPUMax;
    }

    public String getCPUReserve() {
        return CPUReserve;
    }

    public void setCPUReserve(String CPUReserve) {
        this.CPUReserve = CPUReserve;
    }

    public String getCPUType() {
        return CPUType;
    }

    public void setCPUType(String CPUType) {
        this.CPUType = CPUType;
    }

    public String getCPUUtilization() {
        return CPUUtilization;
    }

    public void setCPUUtilization(String CPUUtilization) {
        this.CPUUtilization = CPUUtilization;
    }

    public String getCreationSource() {
        return CreationSource;
    }

    public void setCreationSource(String creationSource) {
        CreationSource = creationSource;
    }

    public String getCreationTime() {
        return CreationTime;
    }

    public void setCreationTime(String creationTime) {
        CreationTime = creationTime;
    }

    public String getDataExchangeEnabled() {
        return DataExchangeEnabled;
    }

    public void setDataExchangeEnabled(String dataExchangeEnabled) {
        DataExchangeEnabled = dataExchangeEnabled;
    }

    public String getDelayStart() {
        return DelayStart;
    }

    public void setDelayStart(String delayStart) {
        DelayStart = delayStart;
    }

    public String getDeployPath() {
        return DeployPath;
    }

    public void setDeployPath(String deployPath) {
        DeployPath = deployPath;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getDiskIO() {
        return DiskIO;
    }

    public void setDiskIO(String diskIO) {
        DiskIO = diskIO;
    }

    public String getDismiss() {
        return Dismiss;
    }

    public void setDismiss(String dismiss) {
        Dismiss = dismiss;
    }

    public String getDynamicMemoryDemandMB() {
        return DynamicMemoryDemandMB;
    }

    public void setDynamicMemoryDemandMB(String dynamicMemoryDemandMB) {
        DynamicMemoryDemandMB = dynamicMemoryDemandMB;
    }

    public String getEnabled() {
        return Enabled;
    }

    public void setEnabled(String enabled) {
        Enabled = enabled;
    }

    public String getExcludeFromPRO() {
        return ExcludeFromPRO;
    }

    public void setExcludeFromPRO(String excludeFromPRO) {
        ExcludeFromPRO = excludeFromPRO;
    }

    public String getExpectedCPUUtilization() {
        return ExpectedCPUUtilization;
    }

    public void setExpectedCPUUtilization(String expectedCPUUtilization) {
        ExpectedCPUUtilization = expectedCPUUtilization;
    }

    public String getFailedJobID() {
        return FailedJobID;
    }

    public void setFailedJobID(String failedJobID) {
        FailedJobID = failedJobID;
    }

    public String getHasPassthroughDisk() {
        return HasPassthroughDisk;
    }

    public void setHasPassthroughDisk(String hasPassthroughDisk) {
        HasPassthroughDisk = hasPassthroughDisk;
    }

    public String getHasSavedState() {
        return HasSavedState;
    }

    public void setHasSavedState(String hasSavedState) {
        HasSavedState = hasSavedState;
    }

    public String getHasVMAdditions() {
        return HasVMAdditions;
    }

    public void setHasVMAdditions(String hasVMAdditions) {
        HasVMAdditions = hasVMAdditions;
    }

    public String getHeartbeatEnabled() {
        return HeartbeatEnabled;
    }

    public void setHeartbeatEnabled(String heartbeatEnabled) {
        HeartbeatEnabled = heartbeatEnabled;
    }

    public String getHighlyAvailable() {
        return HighlyAvailable;
    }

    public void setHighlyAvailable(String highlyAvailable) {
        HighlyAvailable = highlyAvailable;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getIsFaultTolerant() {
        return IsFaultTolerant;
    }

    public void setIsFaultTolerant(String isFaultTolerant) {
        IsFaultTolerant = isFaultTolerant;
    }

    public String getIsHighlyAvailable() {
        return IsHighlyAvailable;
    }

    public void setIsHighlyAvailable(String isHighlyAvailable) {
        IsHighlyAvailable = isHighlyAvailable;
    }

    public String getIsUndergoingLiveMigration() {
        return IsUndergoingLiveMigration;
    }

    public void setIsUndergoingLiveMigration(String isUndergoingLiveMigration) {
        IsUndergoingLiveMigration = isUndergoingLiveMigration;
    }

    public String getLastRestoredCheckpointId() {
        return LastRestoredCheckpointId;
    }

    public void setLastRestoredCheckpointId(String lastRestoredCheckpointId) {
        LastRestoredCheckpointId = lastRestoredCheckpointId;
    }

    public String getLibraryGroup() {
        return LibraryGroup;
    }

    public void setLibraryGroup(String libraryGroup) {
        LibraryGroup = libraryGroup;
    }

    public String getLimitCPUForMigration() {
        return LimitCPUForMigration;
    }

    public void setLimitCPUForMigration(String limitCPUForMigration) {
        LimitCPUForMigration = limitCPUForMigration;
    }

    public String getLimitCPUFunctionality() {
        return LimitCPUFunctionality;
    }

    public void setLimitCPUFunctionality(String limitCPUFunctionality) {
        LimitCPUFunctionality = limitCPUFunctionality;
    }

    public String getVMNetworkAssignments() {
        return VMNetworkAssignments;
    }

    public void setVMNetworkAssignments(String VMNetworkAssignments) {
        this.VMNetworkAssignments = VMNetworkAssignments;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getMarkedAsTemplate() {
        return MarkedAsTemplate;
    }

    public void setMarkedAsTemplate(String markedAsTemplate) {
        MarkedAsTemplate = markedAsTemplate;
    }

    public String getMemory() {
        return Memory;
    }

    public void setMemory(String memory) {
        Memory = memory;
    }

    public String getDynamicMemoryEnabled() {
        return DynamicMemoryEnabled;
    }

    public void setDynamicMemoryEnabled(String dynamicMemoryEnabled) {
        DynamicMemoryEnabled = dynamicMemoryEnabled;
    }

    public String getDynamicMemoryMinimumMB() {
        return DynamicMemoryMinimumMB;
    }

    public void setDynamicMemoryMinimumMB(String dynamicMemoryMinimumMB) {
        DynamicMemoryMinimumMB = dynamicMemoryMinimumMB;
    }

    public String getDynamicMemoryMaximumMB() {
        return DynamicMemoryMaximumMB;
    }

    public void setDynamicMemoryMaximumMB(String dynamicMemoryMaximumMB) {
        DynamicMemoryMaximumMB = dynamicMemoryMaximumMB;
    }

    public String getMemoryAssignedMB() {
        return MemoryAssignedMB;
    }

    public void setMemoryAssignedMB(String memoryAssignedMB) {
        MemoryAssignedMB = memoryAssignedMB;
    }

    public String getMemoryAvailablePercentage() {
        return MemoryAvailablePercentage;
    }

    public void setMemoryAvailablePercentage(String memoryAvailablePercentage) {
        MemoryAvailablePercentage = memoryAvailablePercentage;
    }

    public String getModifiedTime() {
        return ModifiedTime;
    }

    public void setModifiedTime(String modifiedTime) {
        ModifiedTime = modifiedTime;
    }

    public String getMostRecentTaskId() {
        return MostRecentTaskId;
    }

    public void setMostRecentTaskId(String mostRecentTaskId) {
        MostRecentTaskId = mostRecentTaskId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getNetworkUtilization() {
        return NetworkUtilization;
    }

    public void setNetworkUtilization(String networkUtilization) {
        NetworkUtilization = networkUtilization;
    }

    public String getNumLock() {
        return NumLock;
    }

    public void setNumLock(String numLock) {
        NumLock = numLock;
    }

    public String getOperatingSystem() {
        return OperatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        OperatingSystem = operatingSystem;
    }

    public WAPOperatingSystemInstance getOperatingSystemInstance() {
        return OperatingSystemInstance;
    }

    public void setOperatingSystemInstance(WAPOperatingSystemInstance operatingSystemInstance) {
        OperatingSystemInstance = operatingSystemInstance;
    }

    public String getOperatingSystemShutdownEnabled() {
        return OperatingSystemShutdownEnabled;
    }

    public void setOperatingSystemShutdownEnabled(String operatingSystemShutdownEnabled) {
        OperatingSystemShutdownEnabled = operatingSystemShutdownEnabled;
    }

    public String getOperation() {
        return Operation;
    }

    public void setOperation(String operation) {
        Operation = operation;
    }

    public WAPUserModel getOwner() {
        return Owner;
    }

    public void setOwner(WAPUserModel owner) {
        Owner = owner;
    }

    public List<String> getGrantedToList() {
        return GrantedToList;
    }

    public void setGrantedToList(List<String> grantedToList) {
        GrantedToList = grantedToList;
    }

    public String getPath() {
        return Path;
    }

    public void setPath(String path) {
        Path = path;
    }

    public String getPerfCPUUtilization() {
        return PerfCPUUtilization;
    }

    public void setPerfCPUUtilization(String perfCPUUtilization) {
        PerfCPUUtilization = perfCPUUtilization;
    }

    public String getPerfDiskBytesRead() {
        return PerfDiskBytesRead;
    }

    public void setPerfDiskBytesRead(String perfDiskBytesRead) {
        PerfDiskBytesRead = perfDiskBytesRead;
    }

    public String getPerfDiskBytesWrite() {
        return PerfDiskBytesWrite;
    }

    public void setPerfDiskBytesWrite(String perfDiskBytesWrite) {
        PerfDiskBytesWrite = perfDiskBytesWrite;
    }

    public String getPerfNetworkBytesRead() {
        return PerfNetworkBytesRead;
    }

    public void setPerfNetworkBytesRead(String perfNetworkBytesRead) {
        PerfNetworkBytesRead = perfNetworkBytesRead;
    }

    public String getPerfNetworkBytesWrite() {
        return PerfNetworkBytesWrite;
    }

    public void setPerfNetworkBytesWrite(String perfNetworkBytesWrite) {
        PerfNetworkBytesWrite = perfNetworkBytesWrite;
    }

    public String getCPURelativeWeight() {
        return CPURelativeWeight;
    }

    public void setCPURelativeWeight(String CPURelativeWeight) {
        this.CPURelativeWeight = CPURelativeWeight;
    }

    public String getRetry() {
        return Retry;
    }

    public void setRetry(String retry) {
        Retry = retry;
    }

    public String getRunGuestAccount() {
        return RunGuestAccount;
    }

    public void setRunGuestAccount(String runGuestAccount) {
        RunGuestAccount = runGuestAccount;
    }

    public String getServiceDeploymentErrorMessage() {
        return ServiceDeploymentErrorMessage;
    }

    public void setServiceDeploymentErrorMessage(String serviceDeploymentErrorMessage) {
        ServiceDeploymentErrorMessage = serviceDeploymentErrorMessage;
    }

    public String getServiceId() {
        return ServiceId;
    }

    public void setServiceId(String serviceId) {
        ServiceId = serviceId;
    }

    public String getSharePath() {
        return SharePath;
    }

    public void setSharePath(String sharePath) {
        SharePath = sharePath;
    }

    public String getSourceObjectType() {
        return SourceObjectType;
    }

    public void setSourceObjectType(String sourceObjectType) {
        SourceObjectType = sourceObjectType;
    }

    public String getStartAction() {
        return StartAction;
    }

    public void setStartAction(String startAction) {
        StartAction = startAction;
    }

    public String getStartVM() {
        return StartVM;
    }

    public void setStartVM(String startVM) {
        StartVM = startVM;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getStatusString() {
        return StatusString;
    }

    public void setStatusString(String statusString) {
        StatusString = statusString;
    }

    public String getStopAction() {
        return StopAction;
    }

    public void setStopAction(String stopAction) {
        StopAction = stopAction;
    }

    public String getTag() {
        return Tag;
    }

    public void setTag(String tag) {
        Tag = tag;
    }

    public String getTimeSynchronizationEnabled() {
        return TimeSynchronizationEnabled;
    }

    public void setTimeSynchronizationEnabled(String timeSynchronizationEnabled) {
        TimeSynchronizationEnabled = timeSynchronizationEnabled;
    }

    public String getTotalSize() {
        return TotalSize;
    }

    public void setTotalSize(String totalSize) {
        TotalSize = totalSize;
    }

    public String getUndo() {
        return Undo;
    }

    public void setUndo(String undo) {
        Undo = undo;
    }

    public String getUndoDisksEnabled() {
        return UndoDisksEnabled;
    }

    public void setUndoDisksEnabled(String undoDisksEnabled) {
        UndoDisksEnabled = undoDisksEnabled;
    }

    public String getUpgradeDomain() {
        return UpgradeDomain;
    }

    public void setUpgradeDomain(String upgradeDomain) {
        UpgradeDomain = upgradeDomain;
    }

    public String getUseCluster() {
        return UseCluster;
    }

    public void setUseCluster(String useCluster) {
        UseCluster = useCluster;
    }

    public String getUseLAN() {
        return UseLAN;
    }

    public void setUseLAN(String useLAN) {
        UseLAN = useLAN;
    }

    public String getVirtualHardDiskId() {
        return VirtualHardDiskId;
    }

    public void setVirtualHardDiskId(String virtualHardDiskId) {
        VirtualHardDiskId = virtualHardDiskId;
    }

    public String getVirtualizationPlatform() {
        return VirtualizationPlatform;
    }

    public void setVirtualizationPlatform(String virtualizationPlatform) {
        VirtualizationPlatform = virtualizationPlatform;
    }

    public String getCapabilityProfile() {
        return CapabilityProfile;
    }

    public void setCapabilityProfile(String capabilityProfile) {
        CapabilityProfile = capabilityProfile;
    }

    public String getVMBaseConfigurationId() {
        return VMBaseConfigurationId;
    }

    public void setVMBaseConfigurationId(String VMBaseConfigurationId) {
        this.VMBaseConfigurationId = VMBaseConfigurationId;
    }

    public String getVMConfigResource() {
        return VMConfigResource;
    }

    public void setVMConfigResource(String VMConfigResource) {
        this.VMConfigResource = VMConfigResource;
    }

    public String getVMCPath() {
        return VMCPath;
    }

    public void setVMCPath(String VMCPath) {
        this.VMCPath = VMCPath;
    }

    public String getVMHostName() {
        return VMHostName;
    }

    public void setVMHostName(String VMHostName) {
        this.VMHostName = VMHostName;
    }

    public String getVMId() {
        return VMId;
    }

    public void setVMId(String VMId) {
        this.VMId = VMId;
    }

    public String getStampId() {
        return StampId;
    }

    public void setStampId(String stampId) {
        StampId = stampId;
    }

    public String getVMResource() {
        return VMResource;
    }

    public void setVMResource(String VMResource) {
        this.VMResource = VMResource;
    }

    public String getVMResourceGroup() {
        return VMResourceGroup;
    }

    public void setVMResourceGroup(String VMResourceGroup) {
        this.VMResourceGroup = VMResourceGroup;
    }

    public String getVirtualMachineState() {
        return VirtualMachineState;
    }

    public void setVirtualMachineState(String virtualMachineState) {
        VirtualMachineState = virtualMachineState;
    }

    public String getVMTemplateId() {
        return VMTemplateId;
    }

    public void setVMTemplateId(String VMTemplateId) {
        this.VMTemplateId = VMTemplateId;
    }

    public String getHardwareProfileId() {
        return HardwareProfileId;
    }

    public void setHardwareProfileId(String hardwareProfileId) {
        HardwareProfileId = hardwareProfileId;
    }

    public String getBlockDynamicOptimization() {
        return BlockDynamicOptimization;
    }

    public void setBlockDynamicOptimization(String blockDynamicOptimization) {
        BlockDynamicOptimization = blockDynamicOptimization;
    }

    public String getCPULimitForMigration() {
        return CPULimitForMigration;
    }

    public void setCPULimitForMigration(String CPULimitForMigration) {
        this.CPULimitForMigration = CPULimitForMigration;
    }

    public String getCPULimitFunctionality() {
        return CPULimitFunctionality;
    }

    public void setCPULimitFunctionality(String CPULimitFunctionality) {
        this.CPULimitFunctionality = CPULimitFunctionality;
    }

    public String getDomain() {
        return Domain;
    }

    public void setDomain(String domain) {
        Domain = domain;
    }

    public String getDynamicMemoryBufferPercentage() {
        return DynamicMemoryBufferPercentage;
    }

    public void setDynamicMemoryBufferPercentage(String dynamicMemoryBufferPercentage) {
        DynamicMemoryBufferPercentage = dynamicMemoryBufferPercentage;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getMemoryWeight() {
        return MemoryWeight;
    }

    public void setMemoryWeight(String memoryWeight) {
        MemoryWeight = memoryWeight;
    }

    public String getOrganizationName() {
        return OrganizationName;
    }

    public void setOrganizationName(String organizationName) {
        OrganizationName = organizationName;
    }

    public String getDelayStartSeconds() {
        return DelayStartSeconds;
    }

    public void setDelayStartSeconds(String delayStartSeconds) {
        DelayStartSeconds = delayStartSeconds;
    }

    public String getProductKey() {
        return ProductKey;
    }

    public void setProductKey(String productKey) {
        ProductKey = productKey;
    }

    public String getWorkGroup() {
        return WorkGroup;
    }

    public void setWorkGroup(String workGroup) {
        WorkGroup = workGroup;
    }

    public String getTimeZone() {
        return TimeZone;
    }

    public void setTimeZone(String timeZone) {
        TimeZone = timeZone;
    }

    public String getRunAsAccountUserName() {
        return RunAsAccountUserName;
    }

    public void setRunAsAccountUserName(String runAsAccountUserName) {
        RunAsAccountUserName = runAsAccountUserName;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getLocalAdminRunAsAccountName() {
        return LocalAdminRunAsAccountName;
    }

    public void setLocalAdminRunAsAccountName(String localAdminRunAsAccountName) {
        LocalAdminRunAsAccountName = localAdminRunAsAccountName;
    }

    public String getLocalAdminUserName() {
        return LocalAdminUserName;
    }

    public void setLocalAdminUserName(String localAdminUserName) {
        LocalAdminUserName = localAdminUserName;
    }

    public String getLocalAdminPassword() {
        return LocalAdminPassword;
    }

    public void setLocalAdminPassword(String localAdminPassword) {
        LocalAdminPassword = localAdminPassword;
    }

    public String getLinuxDomainName() {
        return LinuxDomainName;
    }

    public void setLinuxDomainName(String linuxDomainName) {
        LinuxDomainName = linuxDomainName;
    }

    public String getLinuxAdministratorSSHKey() {
        return LinuxAdministratorSSHKey;
    }

    public void setLinuxAdministratorSSHKey(String linuxAdministratorSSHKey) {
        LinuxAdministratorSSHKey = linuxAdministratorSSHKey;
    }

    public String getLinuxAdministratorSSHKeyString() {
        return LinuxAdministratorSSHKeyString;
    }

    public void setLinuxAdministratorSSHKeyString(String linuxAdministratorSSHKeyString) {
        LinuxAdministratorSSHKeyString = linuxAdministratorSSHKeyString;
    }

    public String getCloudVMRoleName() {
        return CloudVMRoleName;
    }

    public void setCloudVMRoleName(String cloudVMRoleName) {
        CloudVMRoleName = cloudVMRoleName;
    }

    public String getGeneration() {
        return Generation;
    }

    public void setGeneration(String generation) {
        Generation = generation;
    }

    public WAPDeploymentErrorInfoModel getDeploymentErrorInfo() {
        return DeploymentErrorInfo;
    }

    public void setDeploymentErrorInfo(WAPDeploymentErrorInfoModel deploymentErrorInfo) {
        DeploymentErrorInfo = deploymentErrorInfo;
    }

    public List<String> getNewVirtualNetworkAdapterInput() {
        return NewVirtualNetworkAdapterInput;
    }

    public void setNewVirtualNetworkAdapterInput(List<String> newVirtualNetworkAdapterInput) {
        NewVirtualNetworkAdapterInput = newVirtualNetworkAdapterInput;
    }

    public String getIsRecoveryVM() {
        return IsRecoveryVM;
    }

    public void setIsRecoveryVM(String isRecoveryVM) {
        IsRecoveryVM = isRecoveryVM;
    }

    public String getOdataType() {
        return OdataType;
    }

    public void setOdataType(String odataType) {
        OdataType = odataType;
    }

    public String getOdataMetadata() {
        return OdataMetadata;
    }

    public void setOdataMetadata(String odataMetadata) {
        OdataMetadata = odataMetadata;
    }
}
