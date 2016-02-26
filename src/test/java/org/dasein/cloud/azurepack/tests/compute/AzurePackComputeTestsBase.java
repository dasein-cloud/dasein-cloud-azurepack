/*
 *  *
 *  Copyright (C) 2009-2015 Dell, Inc.
 *  See annotations for authorship information
 *
 *  ====================================================================
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  ====================================================================
 *
 */

package org.dasein.cloud.azurepack.tests.compute;

import mockit.Mocked;
import mockit.NonStrictExpectations;
import org.dasein.cloud.CloudException;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.azurepack.compute.AzurePackComputeService;
import org.dasein.cloud.azurepack.compute.image.model.WAPTemplateModel;
import org.dasein.cloud.azurepack.compute.image.model.WAPVhdModel;
import org.dasein.cloud.azurepack.compute.vm.model.WAPHardwareProfileModel;
import org.dasein.cloud.azurepack.compute.vm.model.WAPVirtualMachineModel;
import org.dasein.cloud.azurepack.compute.vm.model.WAPVirtualNetworkAdapter;
import org.dasein.cloud.azurepack.model.WAPOperatingSystemInstance;
import org.dasein.cloud.azurepack.model.WAPUserModel;
import org.dasein.cloud.azurepack.tests.AzurePackTestsBaseWithLocation;
import org.junit.Before;

import java.util.List;

/**
 * Created by Jeffrey Yan on 10/27/2015.
 *
 * @author Jeffrey Yan
 * @since 2015.09.1
 */
public class AzurePackComputeTestsBase extends AzurePackTestsBaseWithLocation {

    protected final String LIST_VM_RESOURCES = "%s/%s/services/systemcenter/vmm/VirtualMachines?$expand=VirtualDiskDrives";
    protected final String CREATE_VM_RESOURCES = "%s/%s/services/systemcenter/vmm/VirtualMachines";
    protected final String VM_RESOURCES = "%s/%s/services/systemcenter/vmm/VirtualMachines(StampId=guid'%s',ID=guid'%s')";
    protected final String GET_VM_RESOURCES = "%s/%s/services/systemcenter/vmm/VirtualMachines(StampId=guid'%s',ID=guid'%s')?$expand=VirtualDiskDrives";
    protected final String VHD_RESOURCES = "%s/%s/services/systemcenter/vmm/VirtualHardDisks";
    protected final String VMTEMPLATES_RESOURCES = "%s/%s/services/systemcenter/vmm/VMTemplates";
    protected final String HARDWARE_PROFILES = "%s/%s/services/systemcenter/vmm/HardwareProfiles";
    protected final String VM_NETWORK_ADAPTERS = "%s/%s/services/systemcenter/vmm/VirtualMachines(StampId=guid'%s',ID=guid'%s')/VirtualNetworkAdapters";

    protected final String TPL_CPU_COUNT = "4";
    protected final String TPL_MEMORY = "4096";

    @Mocked protected AzurePackComputeService computeServices;

    @Before
    public void setUp() throws CloudException, InternalException {
        super.setUp();
        NonStrictExpectations nse = new NonStrictExpectations() {
            { }
        };
        new NonStrictExpectations() {{
            azurePackCloudMock.getComputeServices(); result = computeServices;
        }};
    }

    protected WAPTemplateModel createTemplate(String id, String name, String owner, String description, String enabled, String os) {
        WAPTemplateModel wapTemplateModel = new WAPTemplateModel();
        wapTemplateModel.setId(id);
        wapTemplateModel.setName(name);
        WAPUserModel userModel = new WAPUserModel();
        userModel.setRoleID(owner);
        wapTemplateModel.setOwner(userModel);
        wapTemplateModel.setDescription(description);
        wapTemplateModel.setEnabled(enabled);
        WAPOperatingSystemInstance wapOperatingSystemInstance = new WAPOperatingSystemInstance();
        wapOperatingSystemInstance.setOsType(os);
        wapTemplateModel.setOperatingSystemInstance(wapOperatingSystemInstance);
        wapTemplateModel.setCpuCount(TPL_CPU_COUNT);
        wapTemplateModel.setMemory(TPL_MEMORY);
        return wapTemplateModel;
    }

    protected WAPVhdModel createVhd(String id, String name, String owner, String description, String enabled, String os) {
        WAPVhdModel vhdModel = new WAPVhdModel();
        vhdModel.setId(id);
        vhdModel.setName(name);
        WAPUserModel userModel = new WAPUserModel();
        userModel.setRoleID(owner);
        vhdModel.setOwner(userModel);
        vhdModel.setDescription(description);
        vhdModel.setEnabled(enabled);
        WAPOperatingSystemInstance wapOperatingSystemInstance = new WAPOperatingSystemInstance();
        wapOperatingSystemInstance.setOsType(os);
        vhdModel.setOperatingSystemInstance(wapOperatingSystemInstance);
        return vhdModel;
    }

    protected WAPHardwareProfileModel createHardwareProfile(String id, String name, String description, String stampId, String cpuCount, String memory) {
        WAPHardwareProfileModel hardwareProfileModel = new WAPHardwareProfileModel();
        hardwareProfileModel.setId(id);
        hardwareProfileModel.setName(name);
        hardwareProfileModel.setDescription(description);
        hardwareProfileModel.setStampId(stampId);
        hardwareProfileModel.setCpuCount(cpuCount);
        hardwareProfileModel.setMemory(memory);
        return hardwareProfileModel;
    }

    protected WAPVirtualMachineModel createVirtualMachine(String id, String name, String status, String owner, String cpuCount, String memory) {
        WAPVirtualMachineModel virtualMachineModel = new WAPVirtualMachineModel();
        virtualMachineModel.setId(id);
        virtualMachineModel.setName(name);
        virtualMachineModel.setCloudId(REGION);
        virtualMachineModel.setStampId(DATACENTER_ID);
        virtualMachineModel.setStatusString(status);
        WAPUserModel userModel = new WAPUserModel();
        userModel.setRoleID(owner);
        virtualMachineModel.setOwner(userModel);
        virtualMachineModel.setCpuCount(cpuCount);
        virtualMachineModel.setMemory(memory);
        return virtualMachineModel;
    }

    protected WAPVirtualNetworkAdapter createVirtualNetworkAdapter(String vmNetworkId, List<String> ipAddresses) {
        WAPVirtualNetworkAdapter virtualNetworkAdapter = new WAPVirtualNetworkAdapter();
        virtualNetworkAdapter.setVmNetworkId(vmNetworkId);
        virtualNetworkAdapter.setIpv4Addresses(ipAddresses);
        return virtualNetworkAdapter;
    }
}
