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
import org.apache.commons.collections.IteratorUtils;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.dasein.cloud.CloudException;
import org.dasein.cloud.CloudProvider;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.azurepack.AzurePackDataCenterService;
import org.dasein.cloud.azurepack.compute.image.AzurePackImageSupport;
import org.dasein.cloud.azurepack.compute.image.model.WAPTemplateModel;
import org.dasein.cloud.azurepack.compute.image.model.WAPTemplatesModel;
import org.dasein.cloud.azurepack.compute.image.model.WAPVhdModel;
import org.dasein.cloud.azurepack.compute.image.model.WAPVhdsModel;
import org.dasein.cloud.azurepack.model.WAPOperatingSystemInstance;
import org.dasein.cloud.azurepack.model.WAPUserModel;
import org.dasein.cloud.azurepack.tests.AzurePackTestsBase;
import org.dasein.cloud.compute.Architecture;
import org.dasein.cloud.compute.ImageClass;
import org.dasein.cloud.compute.ImageFilterOptions;
import org.dasein.cloud.compute.MachineImage;
import org.dasein.cloud.compute.Platform;
import org.dasein.cloud.dc.DataCenter;
import org.dasein.cloud.util.requester.DaseinRequestExecutor;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.dasein.cloud.azurepack.tests.HttpMethodAsserts.assertGet;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by Jeffrey Yan on 10/22/2015.
 *
 * @author Jeffrey Yan
 * @since 2015.09.1
 */
public class AzurePackImageSupportTest extends AzurePackTestsBase {

    private final String VHD_RESOURCES = "%s/%s/services/systemcenter/vmm/VirtualHardDisks";
    private final String VMTEMPLATES_RESOURCES = "%s/%s/services/systemcenter/vmm/VMTemplates";

    private final String VHD_1_ID = UUID.randomUUID().toString();
    private final String VHD_1_OWNER = ACCOUNT_NO;
    private final String VHD_1_NAME = "the first vhd";
    private final String VHD_1_DESCRIPTION = "the first vhd description";
    private final String VHD_2_ID = UUID.randomUUID().toString();
    private final String VHD_2_OWNER = null;
    private final String VHD_2_NAME = "the second vhd";
    private final String VHD_2_DESCRIPTION = "the second vhd description";

    private final String TPL_1_ID = UUID.randomUUID().toString();
    private final String TPL_1_OWNER = ACCOUNT_NO;
    private final String TPL_1_NAME = "the first template";
    private final String TPL_1_DESCRIPTION = "the first template description";
    private final String TPL_2_ID = UUID.randomUUID().toString();
    private final String TPL_2_OWNER = null;
    private final String TPL_2_NAME = "the second template";
    private final String TPL_2_DESCRIPTION = "the second template description";

    private AzurePackImageSupport azurePackImageSupport;

    @Before
    public void setUp() throws CloudException, InternalException {
        super.setUp();
        azurePackImageSupport = new AzurePackImageSupport(azurePackCloudMock);
    }

    @Test
    public void getImageShouldReturnNull() throws CloudException, InternalException {
        new GetAllImagesRequestExecutorMockUp();
        assertNull("getImage doesn't return null", azurePackImageSupport.getImage("this-is-a-not-exist-image-id"));
    }

    @Test
    public void getImageShouldReturnCorrectVhdResult() throws CloudException, InternalException {
        new GetAllImagesRequestExecutorMockUp();
        MachineImage machineImage = azurePackImageSupport.getImage(VHD_1_ID);
        assertEquals("getImage doesn't return correct result", VHD_1_OWNER, machineImage.getProviderOwnerId());
        assertEquals("getImage doesn't return correct result", VHD_1_NAME, machineImage.getName());
        assertEquals("getImage doesn't return correct result", Platform.WINDOWS, machineImage.getPlatform());
        assertEquals("getImage doesn't return correct result", Architecture.I64, machineImage.getArchitecture());
        assertEquals("getImage doesn't return correct result", REGION, machineImage.getProviderRegionId());
    }

    @Test
    public void getImageShouldReturnCorrectTemplateResult() throws CloudException, InternalException {
        new GetAllImagesRequestExecutorMockUp();
        MachineImage machineImage = azurePackImageSupport.getImage(TPL_2_ID);
        assertEquals("getImage doesn't return correct result", "wap", machineImage.getProviderOwnerId());
        assertEquals("getImage doesn't return correct result", TPL_2_NAME, machineImage.getName());
        assertEquals("getImage doesn't return correct result", Platform.UNIX, machineImage.getPlatform());
        assertEquals("getImage doesn't return correct result", Architecture.I64, machineImage.getArchitecture());
        assertEquals("getImage doesn't return correct result", REGION, machineImage.getProviderRegionId());
    }

    @Test
    public void listImagesShouldReturnEmptyResult() throws CloudException, InternalException {
        new GetAllImagesRequestExecutorMockUp();
        List<MachineImage> images;
        images = IteratorUtils.toList(azurePackImageSupport.listImages(ImageFilterOptions.getInstance(ImageClass.KERNEL)).iterator());
        assertEquals("listImages doesn't return empty result", 0, images.size());

        images = IteratorUtils.toList(azurePackImageSupport.listImages(ImageFilterOptions.getInstance()).iterator());
        assertEquals("listImages doesn't return empty result", 0, images.size());
    }

    @Test
    public void listImagesShouldReturnCorrectResult() throws CloudException, InternalException {
        new GetAllImagesRequestExecutorMockUp();
        List<MachineImage> images = IteratorUtils.toList(azurePackImageSupport.listImages(ImageFilterOptions.getInstance(ImageClass.MACHINE)).iterator());
        assertEquals("listImages doesn't return correct result", 2, images.size());
    }

    @Test
    public void searchPublicImagesShouldReturnCorrectResult() throws CloudException, InternalException {
        new GetAllImagesRequestExecutorMockUp();
        List<MachineImage> images;
        images = IteratorUtils.toList(azurePackImageSupport.searchPublicImages(ImageFilterOptions.getInstance(ImageClass.MACHINE)).iterator());
        assertEquals("searchPublicImages doesn't return correct result", 2, images.size());
        images = IteratorUtils.toList(azurePackImageSupport.searchPublicImages(ImageFilterOptions.getInstance()).iterator());
        assertEquals("searchPublicImages doesn't return correct result", 2, images.size());
    }

    @Test
    public void searchImagesShouldReturnCorrectResult() throws CloudException, InternalException {
        new GetAllImagesRequestExecutorMockUp();
        List<MachineImage> images;
        images = IteratorUtils.toList(azurePackImageSupport.searchImages(null, null, null, null).iterator());
        assertEquals("searchImages doesn't return correct result", 4, images.size());

        images = IteratorUtils.toList(azurePackImageSupport.searchImages(ACCOUNT_NO, null, null, null).iterator());
        assertEquals("searchImages doesn't return correct result", 2, images.size());

        images = IteratorUtils.toList(azurePackImageSupport.searchImages(ACCOUNT_NO, null, Platform.WINDOWS, null).iterator());
        assertEquals("searchImages doesn't return correct result", 2, images.size());

        images = IteratorUtils.toList(azurePackImageSupport.searchImages(ACCOUNT_NO, null, Platform.WINDOWS, null, ImageClass.MACHINE).iterator());
        assertEquals("searchImages doesn't return correct result", 2, images.size());

        images = IteratorUtils.toList(azurePackImageSupport.searchImages(ACCOUNT_NO, null, Platform.WINDOWS, null, ImageClass.KERNEL).iterator());
        assertEquals("searchImages doesn't return correct result", 0, images.size());
    }

    public class GetAllImagesRequestExecutorMockUp extends MockUp<DaseinRequestExecutor> {
        private int requestResourceType = 0;

        @Mock
        public void $init(CloudProvider provider, HttpClientBuilder clientBuilder, HttpUriRequest request,
                ResponseHandler handler) {
            String requestUri = request.getURI().toString();
            if(requestUri.equals(String.format(VHD_RESOURCES, ENDPOINT, ACCOUNT_NO))) {
                requestResourceType = 1;
            }
            if(requestUri.equals(String.format(VMTEMPLATES_RESOURCES, ENDPOINT, ACCOUNT_NO))) {
                requestResourceType = 2;
            }
        }

        @Mock
        public Object execute() {
            if(requestResourceType == 1) {
                WAPVhdsModel vhdsModel = new WAPVhdsModel();
                List<WAPVhdModel> vhds = new ArrayList<>();

                vhds.add(createVhd(VHD_1_ID, VHD_1_NAME, VHD_1_OWNER, VHD_1_DESCRIPTION, "true", "windows"));
                vhds.add(createVhd(VHD_2_ID, VHD_2_NAME, VHD_2_OWNER, VHD_2_DESCRIPTION, "true", "linux"));
                vhds.add(createVhd(null, null, null, null, "false", "windows"));

                vhdsModel.setVhds(vhds);
                return vhdsModel;
            }
            if(requestResourceType == 2) {
                WAPTemplatesModel templatesModel = new WAPTemplatesModel();
                List<WAPTemplateModel> templates = new ArrayList<>();

                templates.add(createTemplate(TPL_1_ID, TPL_1_NAME, TPL_1_OWNER, TPL_1_DESCRIPTION, "true", "windows"));
                templates.add(createTemplate(TPL_2_ID, TPL_2_NAME, TPL_2_OWNER, TPL_2_DESCRIPTION, "true", "linux"));
                templates.add(createTemplate(null, null, null, null, "false", "windows"));

                templatesModel.setTemplates(templates);
                return templatesModel;
            }
            return null;
        }

        private WAPTemplateModel createTemplate(String id, String name, String owner, String description, String enabled, String os) {
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
            return wapTemplateModel;
        }

        private WAPVhdModel createVhd(String id, String name, String owner, String description, String enabled, String os) {
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
    }
}
