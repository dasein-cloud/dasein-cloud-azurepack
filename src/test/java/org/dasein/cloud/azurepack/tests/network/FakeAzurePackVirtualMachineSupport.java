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

package org.dasein.cloud.azurepack.tests.network;

import java.util.Arrays;
import java.util.List;

import org.dasein.cloud.azurepack.AzurePackCloud;
import org.dasein.cloud.azurepack.compute.vm.AzurePackVirtualMachineSupport;

public class FakeAzurePackVirtualMachineSupport extends AzurePackVirtualMachineSupport {

	private String vlanId;
	private List<String> ipAddresses;
	
	public FakeAzurePackVirtualMachineSupport(AzurePackCloud provider) {
		this(provider, null);
	}
	
	public FakeAzurePackVirtualMachineSupport(AzurePackCloud provider, String vlanId, String ... ipAddresses) {
		super(provider);
		this.vlanId = vlanId;
		this.ipAddresses = Arrays.asList(ipAddresses);
	}
	
	@Override
	public VirtualMachineNetworkData tryGetVMNetworkId(String virtualMachineId, String stampId) {
		VirtualMachineNetworkData virtualMachineNetworkData = new VirtualMachineNetworkData();
		virtualMachineNetworkData.setVlanId(this.vlanId);
		virtualMachineNetworkData.setIpAddresses(this.ipAddresses);
		return virtualMachineNetworkData;
	}

}
