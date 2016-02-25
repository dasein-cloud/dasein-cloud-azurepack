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

package org.dasein.cloud.azurepack.compute.vm;

import org.apache.http.client.methods.RequestBuilder;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.azurepack.AzurePackCloud;
import org.dasein.cloud.azurepack.compute.vm.model.WAPVirtualMachineModel;
import org.dasein.cloud.azurepack.compute.vm.model.WAPVirtualNetworkAdapter;
import org.dasein.cloud.util.requester.entities.DaseinObjectToJsonEntity;

import java.net.URI;
import java.net.URL;

/**
 * Created by vmunthiu on 3/4/2015.
 */
public class AzurePackVMRequests {

    private final String LIST_VM_RESOURCES = "%s/%s/services/systemcenter/vmm/VirtualMachines?$expand=VirtualDiskDrives";
    private final String CREATE_VM_RESOURCES = "%s/%s/services/systemcenter/vmm/VirtualMachines";
    private final String VM_RESOURCES = "%s/%s/services/systemcenter/vmm/VirtualMachines(StampId=guid'%s',ID=guid'%s')";
    private final String GET_VM_RESOURCES = "%s/%s/services/systemcenter/vmm/VirtualMachines(StampId=guid'%s',ID=guid'%s')?$expand=VirtualDiskDrives";
    private final String HARDWARE_PROFILES = "%s/%s/services/systemcenter/vmm/HardwareProfiles";
    private final String VIRTUAL_NETWORK_ADAPTERS = "%s/%s/services/systemcenter/vmm/VirtualNetworkAdapters";
    private final String VIRTUAL_NETWORK_ADAPTER = "%s/%s/services/systemcenter/vmm/VirtualNetworkAdapters(StampId=guid'%s',ID=guid'%s')";
    private final String VM_NETWORK_ADAPTERS = "%s/%s/services/systemcenter/vmm/VirtualMachines(StampId=guid'%s',ID=guid'%s')/VirtualNetworkAdapters";

    private AzurePackCloud provider;

    public AzurePackVMRequests(AzurePackCloud provider){
        this.provider = provider;
    }

    public RequestBuilder listVirtualMachines(){
        RequestBuilder requestBuilder = RequestBuilder.get();
        addCommonHeaders(requestBuilder);
        requestBuilder.setUri(String.format(LIST_VM_RESOURCES, this.provider.getContext().getEndpoint(), this.provider.getContext().getAccountNumber()));
        return requestBuilder;
    }

    public RequestBuilder createVirtualMachine(WAPVirtualMachineModel virtualMachineModel) {
        RequestBuilder requestBuilder = RequestBuilder.post();
        addCommonHeaders(requestBuilder);
        requestBuilder.setUri(String.format(CREATE_VM_RESOURCES, this.provider.getContext().getEndpoint(), this.provider.getContext().getAccountNumber()));
        requestBuilder.setEntity(new DaseinObjectToJsonEntity<WAPVirtualMachineModel>(virtualMachineModel));
        return requestBuilder;
    }

    public RequestBuilder createVirtualNetworkAdapter(WAPVirtualNetworkAdapter wapVirtualNetworkAdapter){
        RequestBuilder requestBuilder = RequestBuilder.post();
        addCommonHeaders(requestBuilder);
        requestBuilder.setUri(String.format(VIRTUAL_NETWORK_ADAPTERS, this.provider.getContext().getEndpoint(), this.provider.getContext().getAccountNumber()));
        requestBuilder.setEntity(new DaseinObjectToJsonEntity<WAPVirtualNetworkAdapter>(wapVirtualNetworkAdapter));
        return requestBuilder;
    }

    public RequestBuilder listVirtualMachineNetAdapters(String vmId, String stampId) {
        RequestBuilder requestBuilder = RequestBuilder.get();
        addCommonHeaders(requestBuilder);
        requestBuilder.setUri(String.format(VM_NETWORK_ADAPTERS, this.provider.getContext().getEndpoint(), this.provider.getContext().getAccountNumber(), stampId, vmId));
        return requestBuilder;
    }

    public RequestBuilder updateNetworkAdapter(WAPVirtualNetworkAdapter virtualNetworkAdapter) throws InternalException {
        RequestBuilder requestBuilder = RequestBuilder.put();
        addCommonHeaders(requestBuilder);
        requestBuilder.setUri(getEncodedUri(String.format(VIRTUAL_NETWORK_ADAPTER, this.provider.getContext().getEndpoint(), this.provider.getContext().getAccountNumber(), virtualNetworkAdapter.getStampId(), virtualNetworkAdapter.getId())));
        requestBuilder.setEntity(new DaseinObjectToJsonEntity<WAPVirtualNetworkAdapter>(virtualNetworkAdapter));
        return requestBuilder;
    }

    public RequestBuilder getVirtualMachine(String vmId, String dataCenterId) throws InternalException {
        RequestBuilder requestBuilder = RequestBuilder.get();
        addCommonHeaders(requestBuilder);
        requestBuilder.setUri(getEncodedUri(String.format(GET_VM_RESOURCES, this.provider.getContext().getEndpoint(), this.provider.getContext().getAccountNumber(), dataCenterId, vmId)));
        return requestBuilder;
    }

    public RequestBuilder getVirtualMachineForUpdate(String vmId, String dataCenterId) throws InternalException {
        RequestBuilder requestBuilder = RequestBuilder.get();
        addCommonHeaders(requestBuilder);
        requestBuilder.setUri(getEncodedUri(String.format(VM_RESOURCES, this.provider.getContext().getEndpoint(), this.provider.getContext().getAccountNumber(), dataCenterId, vmId)));
        return requestBuilder;
    }

    public RequestBuilder deleteVirtualMachine(String vmId, String dataCenterId) throws InternalException {
        RequestBuilder requestBuilder = RequestBuilder.delete();
        addCommonHeaders(requestBuilder);
        requestBuilder.setUri(getEncodedUri(String.format(VM_RESOURCES, this.provider.getContext().getEndpoint(), this.provider.getContext().getAccountNumber(), dataCenterId, vmId)));
        return requestBuilder;
    }

    public RequestBuilder updateVirtualMachine(String vmId, String dataCenterId, WAPVirtualMachineModel virtualMachineModel) throws InternalException {
        RequestBuilder requestBuilder = RequestBuilder.put();
        addCommonHeaders(requestBuilder);
        requestBuilder.setUri(getEncodedUri(String.format(VM_RESOURCES, this.provider.getContext().getEndpoint(), this.provider.getContext().getAccountNumber(), dataCenterId, vmId)));
        requestBuilder.setEntity(new DaseinObjectToJsonEntity<WAPVirtualMachineModel>(virtualMachineModel));
        return requestBuilder;
    }

    public RequestBuilder listHardwareProfiles(){
        RequestBuilder requestBuilder = RequestBuilder.get();
        addCommonHeaders(requestBuilder);
        requestBuilder.setUri(String.format(HARDWARE_PROFILES, this.provider.getContext().getEndpoint(), this.provider.getContext().getAccountNumber()));
        return requestBuilder;
    }

    private String getEncodedUri(String urlString) throws InternalException {
        try {
            URL url = new URL(urlString);
            return new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef()).toString();
        } catch (Exception e) {
            throw new InternalException(e.getMessage());
        }
    }

    private void addCommonHeaders(RequestBuilder requestBuilder) {
        requestBuilder.addHeader("x-ms-version", "2014-02-01");
        requestBuilder.addHeader("Accept", "application/json");
    }
}
