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

import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;
import mockit.NonStrictExpectations;
import org.apache.commons.collections.IteratorUtils;
import org.apache.http.Header;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.dasein.cloud.CloudException;
import org.dasein.cloud.CloudProvider;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.azurepack.compute.image.AzurePackImageSupport;
import org.dasein.cloud.azurepack.compute.image.model.WAPTemplateModel;
import org.dasein.cloud.azurepack.compute.image.model.WAPTemplatesModel;
import org.dasein.cloud.azurepack.compute.vm.AzurePackVirtualMachineSupport;
import org.dasein.cloud.azurepack.compute.vm.model.WAPHardwareProfileModel;
import org.dasein.cloud.azurepack.compute.vm.model.WAPHardwareProfilesModel;
import org.dasein.cloud.azurepack.compute.vm.model.WAPVirtualMachineModel;
import org.dasein.cloud.azurepack.compute.vm.model.WAPVirtualMachinesModel;
import org.dasein.cloud.azurepack.compute.vm.model.WAPVirtualNetworkAdapter;
import org.dasein.cloud.azurepack.compute.vm.model.WAPVirtualNetworkAdapters;
import org.dasein.cloud.azurepack.network.AzurePackNetworkSupport;
import org.dasein.cloud.compute.Architecture;
import org.dasein.cloud.compute.ImageClass;
import org.dasein.cloud.compute.MachineImage;
import org.dasein.cloud.compute.MachineImageState;
import org.dasein.cloud.compute.Platform;
import org.dasein.cloud.compute.VirtualMachine;
import org.dasein.cloud.compute.VirtualMachineProduct;
import org.dasein.cloud.compute.VirtualMachineProductFilterOptions;
import org.dasein.cloud.compute.VmState;
import org.dasein.cloud.network.VLAN;
import org.dasein.cloud.util.requester.DaseinRequestExecutor;
import org.dasein.util.uom.storage.Storage;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import static org.dasein.cloud.azurepack.tests.HttpMethodAsserts.assertPut;
import static org.junit.Assert.assertEquals;

/**
 * Created by Jeffrey Yan on 10/27/2015.
 *
 * @author Jeffrey Yan
 * @since 2015.09.1
 */
public class AzurePackVirtualMachineSupportTest extends AzurePackComputeTestsBase {
    private final String VHD_1_ID = UUID.randomUUID().toString();
    private final String VHD_1_OWNER = ACCOUNT_NO;
    private final String VHD_1_NAME = "the first vhd";
    private final String VHD_1_DESCRIPTION = "the first vhd description";

    private final String TPL_1_ID = UUID.randomUUID().toString();
    private final String TPL_1_OWNER = ACCOUNT_NO;
    private final String TPL_1_NAME = "the first template";
    private final String TPL_1_DESCRIPTION = "the first template description";

    private final String HWP_1_ID = UUID.randomUUID().toString();
    private final String HWP_1_NAME = "the first template";
    private final String HWP_1_DESCRIPTION = "the first template description";
    private final String HWP_1_STAMP_ID = "the stample id";
    private final String HWP_1_CPU_COUNT = "8";
    private final String HWP_1_MEMORY = "8192";

    private final String VM_1_ID = UUID.randomUUID().toString();
    private final String VM_1_NAME = "the first VM";
    private final String VM_1_STATUS = "running";
    private final String VM_1_OWNER = ACCOUNT_NO;
    private final String VM_1_CPU_COUNT = "8";
    private final String VM_1_MEMORY = "8192";
    private final String VM_1_NETWORK_ID = UUID.randomUUID().toString();
    private final String VM_1_NETWORK_NAME = "vlan_name";
    private final List<String> VM_1_NETWORK_IP_ADDRESSES = Arrays.asList("192.168.1.15");

    private AzurePackVirtualMachineSupport azurePackVirtualMachineSupport;

    @Mocked protected AzurePackImageSupport imageSupport;
    @Mocked protected AzurePackNetworkSupport networkSupport;

    @Before
    public void setUp() throws CloudException, InternalException {
        super.setUp();

        new NonStrictExpectations() {{
            computeServices.getImageSupport(); result = imageSupport;

            MachineImage vhdImage = MachineImage.getInstance(VHD_1_OWNER, REGION, VHD_1_ID, ImageClass.MACHINE,
                    MachineImageState.ACTIVE, VHD_1_NAME, VHD_1_DESCRIPTION, Architecture.I64, Platform.WINDOWS);
            vhdImage.setTag("type", "vhd");
            imageSupport.getImage(VHD_1_ID); result = vhdImage;

            MachineImage templateImage = MachineImage.getInstance(TPL_1_OWNER, REGION, TPL_1_ID, ImageClass.MACHINE,
                    MachineImageState.ACTIVE, TPL_1_NAME, TPL_1_DESCRIPTION, Architecture.I64, Platform.WINDOWS);
            templateImage.setTag("type", "template");
            imageSupport.getImage(TPL_1_ID); result = templateImage;

            imageSupport.getImage(anyString); result = null;

            VLAN vlan = new VLAN();
            vlan.setProviderVlanId(VM_1_NETWORK_ID);
            vlan.setName(VM_1_NETWORK_NAME);
            networkSupport.getVlan(VM_1_NETWORK_ID); result = vlan;
        }};

        azurePackVirtualMachineSupport = new AzurePackVirtualMachineSupport(azurePackCloudMock);
    }

    @Test(expected=InternalException.class)
    public void listProductsShouldThrowExceptionIfImageIsNotExist() throws CloudException, InternalException {
        new ListProductsRequestExecutorMockUp();
        azurePackVirtualMachineSupport.listProducts("not-exist-image-id", VirtualMachineProductFilterOptions.getInstance());
    }

    @Test(expected=InternalException.class)
    public void listProductsShouldThrowExceptionIfFilterOptionIsNull() throws CloudException, InternalException {
        new ListProductsRequestExecutorMockUp();
        azurePackVirtualMachineSupport.listProducts(TPL_1_ID, null);
    }

    @Test
    public void listProductsShouldReturnDefaultTemplateProduct() throws CloudException, InternalException {
        new ListProductsRequestExecutorMockUp();
        List<VirtualMachineProduct> products = IteratorUtils.toList(azurePackVirtualMachineSupport
                .listProducts(TPL_1_ID, VirtualMachineProductFilterOptions.getInstance()).iterator());
        assertEquals("listProducts doesn't return correct result", 1, products.size());
        VirtualMachineProduct virtualMachineProduct = products.get(0);
        assertEquals("listProducts doesn't return correct result", "Default", virtualMachineProduct.getName());
        assertEquals("listProducts doesn't return correct result", "default", virtualMachineProduct.getProviderProductId());
        assertEquals("listProducts doesn't return correct result", Integer.parseInt(TPL_CPU_COUNT), virtualMachineProduct.getCpuCount());
        assertEquals("listProducts doesn't return correct result", Double.parseDouble(TPL_MEMORY), virtualMachineProduct.getRamSize().getQuantity());
        assertEquals("listProducts doesn't return correct result", Storage.MEGABYTE,
                virtualMachineProduct.getRamSize().getUnitOfMeasure());
    }

    @Test
    public void listProductsShouldReturnCorrectHardwareProfileProducts() throws CloudException, InternalException {
        new ListProductsRequestExecutorMockUp();
        List<VirtualMachineProduct> products = IteratorUtils.toList(
                azurePackVirtualMachineSupport.listProducts(VHD_1_ID, VirtualMachineProductFilterOptions.getInstance())
                        .iterator());
        assertHardwareProfileProducts(products);
    }

    @Test
    public void listAllProductsShouldReturnCorrectHardwareProfileProducts() throws CloudException, InternalException {
        new ListProductsRequestExecutorMockUp();
        List<VirtualMachineProduct> products = IteratorUtils
                .toList(azurePackVirtualMachineSupport.listAllProducts().iterator());
        assertHardwareProfileProducts(products);
    }

    private void assertHardwareProfileProducts(List<VirtualMachineProduct> products) {
        assertEquals("listProducts doesn't return correct result", 1, products.size());
        VirtualMachineProduct virtualMachineProduct = products.get(0);
        assertEquals("listProducts doesn't return correct result", HWP_1_NAME, virtualMachineProduct.getName());
        assertEquals("listProducts doesn't return correct result", HWP_1_ID, virtualMachineProduct.getProviderProductId());
        assertEquals("listProducts doesn't return correct result", Integer.parseInt(HWP_1_CPU_COUNT), virtualMachineProduct.getCpuCount());
        assertEquals("listProducts doesn't return correct result", Double.parseDouble(HWP_1_MEMORY), virtualMachineProduct.getRamSize().getQuantity());
        assertEquals("listProducts doesn't return correct result", Storage.MEGABYTE,
                virtualMachineProduct.getRamSize().getUnitOfMeasure());
    }

    public class ListProductsRequestExecutorMockUp extends MockUp<DaseinRequestExecutor> {
        private int requestResourceType = 0;

        @Mock
        public void $init(CloudProvider provider, HttpClientBuilder clientBuilder, HttpUriRequest request,
                ResponseHandler handler) {
            String requestUri = request.getURI().toString();
            if(request.getMethod().equals("GET") && requestUri.equals(String.format(VMTEMPLATES_RESOURCES, ENDPOINT, ACCOUNT_NO))) {
                requestResourceType = 1;
            }
            if (request.getMethod().equals("GET") && requestUri.equals(String.format(HARDWARE_PROFILES, ENDPOINT, ACCOUNT_NO))) {
                requestResourceType = 2;
            }
        }

        @Mock
        public Object execute() {
            if(requestResourceType == 1) {
                WAPTemplatesModel templatesModel = new WAPTemplatesModel();
                List<WAPTemplateModel> templates = new ArrayList<>();
                templates.add(createTemplate(TPL_1_ID, TPL_1_NAME, TPL_1_OWNER, TPL_1_DESCRIPTION, "true", "windows"));
                templatesModel.setTemplates(templates);
                return templatesModel;
            }
            if(requestResourceType == 2) {
                WAPHardwareProfilesModel hardwareProfilesModel = new WAPHardwareProfilesModel();
                List<WAPHardwareProfileModel> profiles = new ArrayList<>();
                profiles.add(createHardwareProfile(HWP_1_ID, HWP_1_NAME, HWP_1_DESCRIPTION, HWP_1_STAMP_ID, HWP_1_CPU_COUNT, HWP_1_MEMORY));
                hardwareProfilesModel.setHardwareProfiles(profiles);
                return hardwareProfilesModel;
            }
            return null;
        }
    }

    @Test(expected=InternalException.class)
    public void getVirtualMachineShouldThrowExceptionIfVmIdIsNull() throws CloudException, InternalException {
        azurePackVirtualMachineSupport.getVirtualMachine(null);
    }

    @Test
    public void getVirtualMachineShouldReturnCorrectResult() throws CloudException, InternalException {
        new GetOrListVirtualMachinesRequestExecutorMockUp();

        VirtualMachine virtualMachine = azurePackVirtualMachineSupport.getVirtualMachine(VM_1_ID);
        assertVirtualMachine(virtualMachine);
    }

    @Test
    public void listVirtualMachinesShouldReturnCorrectResult() throws CloudException, InternalException {
        new GetOrListVirtualMachinesRequestExecutorMockUp();
        List<VirtualMachine> virtualMachines = IteratorUtils.toList(
                azurePackVirtualMachineSupport.listVirtualMachines().iterator());
        assertEquals("listVirtualMachines doesn't return correct result", 1, virtualMachines.size());
        assertVirtualMachine(virtualMachines.get(0));
    }

    private void assertVirtualMachine(VirtualMachine virtualMachine) {
        assertEquals("getVirtualMachine doesn't return correct result", VM_1_NAME, virtualMachine.getName());
        assertEquals("getVirtualMachine doesn't return correct result", VmState.RUNNING, virtualMachine.getCurrentState());
        assertEquals("getVirtualMachine doesn't return correct result", HWP_1_ID, virtualMachine.getProductId());
        assertEquals("getVirtualMachine doesn't return correct result", REGION, virtualMachine.getProviderRegionId());
        assertEquals("getVirtualMachine doesn't return correct result", DATACENTER_ID, virtualMachine.getProviderDataCenterId());
        assertEquals("getVirtualMachine doesn't return correct result", VM_1_OWNER, virtualMachine.getProviderOwnerId());
        assertEquals("getVirtualMachine doesn't return correct result", VM_1_NETWORK_ID, virtualMachine.getProviderVlanId());
        assertEquals("getVirtualMachine doesn't return correct result", VM_1_NETWORK_IP_ADDRESSES.get(0), virtualMachine.getPublicAddresses()[0].getIpAddress());
    }

    public class GetOrListVirtualMachinesRequestExecutorMockUp extends MockUp<DaseinRequestExecutor> {
        protected ResponseHandler responseHandler;

        protected int requestResourceType = 0;
        @Mock
        public void $init(CloudProvider provider, HttpClientBuilder clientBuilder, HttpUriRequest request,
                ResponseHandler handler) {
            String requestUri = request.getURI().toString();
            if(request.getMethod().equals("GET") && requestUri.equals(String.format(VM_RESOURCES, ENDPOINT, ACCOUNT_NO, DATACENTER_ID, VM_1_ID))) {
                requestResourceType = 1;
            }
            if (request.getMethod().equals("GET") && requestUri.equals(String.format(VM_NETWORK_ADAPTERS, ENDPOINT, ACCOUNT_NO, DATACENTER_ID, VM_1_ID))) {
                requestResourceType = 2;
            }
            if (request.getMethod().equals("GET") && requestUri.equals(String.format(HARDWARE_PROFILES, ENDPOINT, ACCOUNT_NO))) {
                requestResourceType = 3;
            }
            if (request.getMethod().equals("GET") && requestUri.equals(String.format(LIST_VM_RESOURCES, ENDPOINT, ACCOUNT_NO))) {
                requestResourceType = 4;
            }
            responseHandler = handler;
        }

        @Mock
        public Object execute() {
            if(requestResourceType == 1) {
                return mapFromModel(this.responseHandler, createWAPVirtualMachineModel());
            }
            if(requestResourceType == 2) {
                WAPVirtualNetworkAdapters wapVirtualNetworkAdapters = new WAPVirtualNetworkAdapters();
                List<WAPVirtualNetworkAdapter> virtualNetworkAdapters = new ArrayList<>();
                virtualNetworkAdapters.add(createVirtualNetworkAdapter(VM_1_NETWORK_ID, VM_1_NETWORK_IP_ADDRESSES));
                wapVirtualNetworkAdapters.setVirtualNetworkAdapters(virtualNetworkAdapters);
                return wapVirtualNetworkAdapters;
            }
            if(requestResourceType == 3) {
                WAPHardwareProfilesModel hardwareProfilesModel = new WAPHardwareProfilesModel();
                List<WAPHardwareProfileModel> profiles = new ArrayList<>();
                profiles.add(createHardwareProfile(HWP_1_ID, HWP_1_NAME, HWP_1_DESCRIPTION, HWP_1_STAMP_ID,
                        HWP_1_CPU_COUNT, HWP_1_MEMORY));
                hardwareProfilesModel.setHardwareProfiles(profiles);
                return hardwareProfilesModel;
            }
            if(requestResourceType == 4) {
                WAPVirtualMachinesModel wapVirtualMachinesModel = new WAPVirtualMachinesModel();
                List<WAPVirtualMachineModel> virtualMachines = new ArrayList<>();
                virtualMachines.add(createWAPVirtualMachineModel());
                wapVirtualMachinesModel.setVirtualMachines(virtualMachines);
                return wapVirtualMachinesModel;
            }
            return null;
        }

        protected WAPVirtualMachineModel createWAPVirtualMachineModel() {
            return createVirtualMachine(VM_1_ID, VM_1_NAME, VM_1_STATUS, VM_1_OWNER, VM_1_CPU_COUNT, VM_1_MEMORY);
        }
    }

    @Test
    public void startShouldSendCorrectRequest() throws CloudException, InternalException {
        StartOrStopVirtualMachinesRequestExecutorMockUp mockUp = new StartOrStopVirtualMachinesRequestExecutorMockUp("Start");
        azurePackVirtualMachineSupport.start(VM_1_ID);
        assertEquals("stop doesn't send PUT request", 1, mockUp.putCount);
    }

    @Test
    public void stopShouldSendCorrectRequest() throws CloudException, InternalException {
        StartOrStopVirtualMachinesRequestExecutorMockUp mockUp = new StartOrStopVirtualMachinesRequestExecutorMockUp("Shutdown");
        azurePackVirtualMachineSupport.stop(VM_1_ID, false);
        assertEquals("stop doesn't send PUT request", 1, mockUp.putCount);
    }

    public class StartOrStopVirtualMachinesRequestExecutorMockUp extends GetOrListVirtualMachinesRequestExecutorMockUp {
        private int putCount = 0;
        private String operation;
        public StartOrStopVirtualMachinesRequestExecutorMockUp(String operation) {
            this.operation = operation;
        }

        @Mock
        public void $init(CloudProvider provider, HttpClientBuilder clientBuilder, HttpUriRequest request,
                ResponseHandler handler) {
            String requestUri = request.getURI().toString();
            if(request.getMethod().equals("GET") && requestUri.equals(String.format(VM_RESOURCES, ENDPOINT, ACCOUNT_NO, DATACENTER_ID, VM_1_ID))) {
                requestResourceType = 11;
            } else if (request.getMethod().equals("PUT") && requestUri
                    .equals(String.format(VM_RESOURCES, ENDPOINT, ACCOUNT_NO, DATACENTER_ID, VM_1_ID))) {
                requestResourceType = 12;
                WAPVirtualMachineModel wapVirtualMachineModel = createWAPVirtualMachineModel();
                wapVirtualMachineModel.setOperation(operation);
                assertPut(request, String.format(VM_RESOURCES, ENDPOINT, ACCOUNT_NO, DATACENTER_ID, VM_1_ID),
                        new Header[0], wapVirtualMachineModel);
            } else {
                super.$init(provider, clientBuilder, request, handler);
            }
        }
        @Mock
        public Object execute() {
            if(requestResourceType == 11) {
                return createWAPVirtualMachineModel();
            } else if(requestResourceType == 12) {
                putCount++;
                return "";
            } else {
                return super.execute();
            }
        }
    }

    @Test
    public void terminateShouldSendCorrectRequest() throws CloudException, InternalException {
        final AtomicInteger deleteCount = new AtomicInteger(0);
        new GetOrListVirtualMachinesRequestExecutorMockUp() {
            @Mock
            public void $init(CloudProvider provider, HttpClientBuilder clientBuilder, HttpUriRequest request,
                    ResponseHandler handler) {
                String requestUri = request.getURI().toString();
                if(request.getMethod().equals("DELETE") && requestUri.equals(String.format(VM_RESOURCES, ENDPOINT, ACCOUNT_NO, DATACENTER_ID, VM_1_ID))) {
                    requestResourceType = 11;
                } else {
                    super.$init(provider, clientBuilder, request, handler);
                }
            }
            @Mock
            public Object execute() {
                if(requestResourceType == 11) {
                    deleteCount.incrementAndGet();
                    return "";
                } else {
                    return super.execute();
                }
            }
        };
        azurePackVirtualMachineSupport.terminate(VM_1_ID, "no reason");
        assertEquals("terminate doesn't send DELETE request", 1, deleteCount.get());
    }


}
