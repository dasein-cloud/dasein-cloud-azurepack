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
import java.util.Collections;
import java.util.UUID;
import mockit.Invocation;
import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;
import mockit.NonStrictExpectations;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.dasein.cloud.CloudException;
import org.dasein.cloud.CloudProvider;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.azurepack.compute.AzurePackComputeService;
import org.dasein.cloud.azurepack.network.AzurePackIpAddressCapabilities;
import org.dasein.cloud.azurepack.network.AzurePackNetworkServices;
import org.dasein.cloud.azurepack.network.model.WAPNatConnectionModel;
import org.dasein.cloud.azurepack.network.model.WAPNatConnectionsModel;
import org.dasein.cloud.azurepack.network.model.WAPNatRuleModel;
import org.dasein.cloud.azurepack.network.model.WAPNatRulesModel;
import org.dasein.cloud.azurepack.tests.AzurePackTestsBaseWithLocation;
import org.dasein.cloud.network.IpAddressSupport;
import org.dasein.cloud.network.IpForwardingRule;
import org.dasein.cloud.network.Protocol;
import org.dasein.cloud.util.requester.DaseinRequestExecutor;
import org.dasein.cloud.util.requester.entities.DaseinObjectToJsonEntity;
import org.junit.Before;
import org.junit.Test;
import static org.dasein.cloud.azurepack.tests.HttpMethodAsserts.*;
import static org.junit.Assert.*;
import static org.unitils.reflectionassert.ReflectionAssert.*;

/**
 * Created by Jane Wang on 10/20/2015.
 *
 * @author Jane Wang
 * @since 2015.09.1
 */
public class AzurePackIpAddressSupportTest extends AzurePackTestsBaseWithLocation {

	private final String NAT_RULES = "%s/%s/services/systemcenter/vmm/NATRules";
	private final String GATEWAY_NAT_CONNECTIONS = "%s/%s/services/systemcenter/vmm/NATConnections()?$filter=StampId eq guid'%s' and VMNetworkGatewayId eq guid'%s'";
	private final String NAT_RULE = "%s/%s/services/systemcenter/vmm/NATRules(ID=guid'%s',StampId=guid'%s')";
	private final String NAT_RULES_FOR_CONNECTION = "%s/%s/services/systemcenter/vmm/NATRules()?$filter=StampId eq guid'%s' and NATConnectionId eq guid'%s'";
	
	private final String ADDRESS_ID = UUID.randomUUID().toString();
	private final String SERVER_ID = UUID.randomUUID().toString();
	private final String VLAN_ID = UUID.randomUUID().toString();
	private final String ADDRESS = "10.30.45.66";
	private final String INTERNET_GATEWAY_ID = UUID.randomUUID().toString();
	
	private IpAddressSupport azurePackIpAddressSupport;
	
	@Before
	public void setUp() throws CloudException, InternalException {
		super.setUp();
		final AzurePackNetworkServices azurePackNetworkServices = new AzurePackNetworkServices(azurePackCloudMock);
		azurePackIpAddressSupport = azurePackNetworkServices.getIpAddressSupport();
	}
	
	@Test
	public void forwardShouldPostWithCorrectRequest(
			@Mocked final AzurePackComputeService azurePackComputeService, 
			@Mocked final AzurePackNetworkServices azurePackNetworkServices) throws InternalException, CloudException {
		
		new NonStrictExpectations() {
			{ azurePackComputeService.getVirtualMachineSupport(); result = new FakeAzurePackVirtualMachineSupport(azurePackCloudMock, VLAN_ID, ADDRESS); }
			{ azurePackNetworkServices.getVlanSupport(); result = new FakeAzurePackVlanSupport(azurePackCloudMock, INTERNET_GATEWAY_ID); }
		};
		
		final String wapNatConnectionId = UUID.randomUUID().toString();
		final String testRuleId = UUID.randomUUID().toString();
		
		new MockUp<DaseinRequestExecutor>() {
            
			@Mock(invocations = 2)
            public void $init(Invocation inv, CloudProvider provider, HttpClientBuilder httpClientBuilder, HttpUriRequest httpUriRequest, ResponseHandler responseHandler) {
            	if (inv.getInvocationCount() == 1) {
					assertGet(httpUriRequest, String.format(GATEWAY_NAT_CONNECTIONS, ENDPOINT, ACCOUNT_NO, DATACENTER_ID, INTERNET_GATEWAY_ID).replace(" ", "%20"));
				} else if (inv.getInvocationCount() == 2) {
					
					WAPNatRuleModel expectedNatRuleModel = new WAPNatRuleModel();
					expectedNatRuleModel.setName(wapNatConnectionId);
					expectedNatRuleModel.setInternalPort("908");
					expectedNatRuleModel.setInternalIPAddress(ADDRESS_ID);
					expectedNatRuleModel.setExternalPort("80");
					expectedNatRuleModel.setStampId(DATACENTER_ID);
					expectedNatRuleModel.setNatConnectionId(wapNatConnectionId);
					expectedNatRuleModel.setProtocol(Protocol.UDP.name());
					
					HttpEntityEnclosingRequest httpEntityEnclosingRequest = (HttpEntityEnclosingRequest) httpUriRequest;
					assertReflectionEquals(
							new DaseinObjectToJsonEntity<WAPNatRuleModel>(expectedNatRuleModel),
							httpEntityEnclosingRequest.getEntity());
            		assertPost(httpUriRequest, String.format(NAT_RULES, ENDPOINT, ACCOUNT_NO));
            	}
            }
			
            @Mock(invocations = 2)
            public Object execute(Invocation inv) {
            	if (inv.getInvocationCount() == 1) {
                	WAPNatConnectionsModel wapNatConnectionsModel = new WAPNatConnectionsModel();
                	WAPNatConnectionModel wapNatConnectionModel = new WAPNatConnectionModel();
                	wapNatConnectionModel.setId(wapNatConnectionId);
                	wapNatConnectionModel.setName(wapNatConnectionModel.getId());
                	wapNatConnectionModel.setStampId(ADDRESS_ID);
                	wapNatConnectionModel.setStatus("Active");
                	wapNatConnectionModel.setVmNetworkGatewayId(INTERNET_GATEWAY_ID);
                	wapNatConnectionsModel.setConnections(Arrays.asList(wapNatConnectionModel));
                	return wapNatConnectionsModel;
            	} else if (inv.getInvocationCount() == 2) {	 
                	WAPNatRuleModel wapNatRuleModel = new WAPNatRuleModel();
                	wapNatRuleModel.setId(testRuleId);
                	return wapNatRuleModel;
                } else {
                	throw new RuntimeException("Invalid invocation count!");
                }
            }
        };
        
        String actualRuleId = azurePackIpAddressSupport.forward(ADDRESS_ID, 80, Protocol.UDP, 908, SERVER_ID);
        assertEquals(testRuleId, actualRuleId);
	}
	
	@Test(expected = InternalException.class)
	public void forwardShouldThrowExceptionIfNoInternetGatewayFound(
			@Mocked final AzurePackComputeService azurePackComputeService, 
			@Mocked final AzurePackNetworkServices azurePackNetworkServices) throws InternalException, CloudException {
		
		new NonStrictExpectations() {
			{ azurePackComputeService.getVirtualMachineSupport(); result = new FakeAzurePackVirtualMachineSupport(azurePackCloudMock, VLAN_ID, ADDRESS); }
			{ azurePackNetworkServices.getVlanSupport(); result = new FakeAzurePackVlanSupport(azurePackCloudMock, null); }
		};
		
		azurePackIpAddressSupport.forward(ADDRESS_ID, 80, Protocol.UDP, 908, SERVER_ID);
	}
	
	@Test(expected = InternalException.class)
	public void forwardShouldThrowExceptionIfNoNatConnectionFound(
			@Mocked final AzurePackComputeService azurePackComputeService, 
			@Mocked final AzurePackNetworkServices azurePackNetworkServices) throws InternalException, CloudException {
		
		new NonStrictExpectations() {
			{ azurePackComputeService.getVirtualMachineSupport(); result = new FakeAzurePackVirtualMachineSupport(azurePackCloudMock, VLAN_ID, ADDRESS); }
			{ azurePackNetworkServices.getVlanSupport(); result = new FakeAzurePackVlanSupport(azurePackCloudMock, INTERNET_GATEWAY_ID); }
		};
		
		new MockUp<DaseinRequestExecutor>() {
			@Mock(invocations = 1)
            public void $init(CloudProvider provider, HttpClientBuilder httpClientBuilder, HttpUriRequest httpUriRequest, ResponseHandler responseHandler) {
				assertGet(httpUriRequest, String.format(GATEWAY_NAT_CONNECTIONS, ENDPOINT, ACCOUNT_NO, DATACENTER_ID, INTERNET_GATEWAY_ID).replace(" ", "%20"));
            }
            @Mock(invocations = 1)
            public Object execute() {
            	return null;
            }
        };
		
        azurePackIpAddressSupport.forward(ADDRESS_ID, 80, Protocol.UDP, 908, SERVER_ID);
	}
	
	@Test
	public void stopForwardShouldDeleteWithCorrectRequest() throws InternalException, CloudException {
		
		final String testRuleId = UUID.randomUUID().toString();
		
		new MockUp<DaseinRequestExecutor>() {
			@Mock(invocations = 1)
            public void $init(CloudProvider provider, HttpClientBuilder httpClientBuilder, HttpUriRequest httpUriRequest, ResponseHandler responseHandler) {
				assertDelete(httpUriRequest, String.format(NAT_RULE, ENDPOINT, ACCOUNT_NO, testRuleId, DATACENTER_ID));
            }
            @Mock(invocations = 1)
            public Object execute() {
            	return null;
            }
        };
		
		azurePackIpAddressSupport.stopForward(testRuleId);
	}
	
	@Test(expected = InternalException.class)
	public void stopForwardShouldThrowExceptionIfRuleIdIsNull() throws InternalException, CloudException {
		azurePackIpAddressSupport.stopForward(null);
	}
	
	@Test
	public void listRulesForServerShouldReturnCorrectResult(
			@Mocked final AzurePackComputeService azurePackComputeService, 
			@Mocked final AzurePackNetworkServices azurePackNetworkServices) throws InternalException, CloudException {
		
		new NonStrictExpectations() {
			{ azurePackComputeService.getVirtualMachineSupport(); result = new FakeAzurePackVirtualMachineSupport(azurePackCloudMock, VLAN_ID, ADDRESS); }
			{ azurePackNetworkServices.getVlanSupport(); result = new FakeAzurePackVlanSupport(azurePackCloudMock, INTERNET_GATEWAY_ID); }
		};
		
		final String natConnectionId = UUID.randomUUID().toString();
		final String ruleId = UUID.randomUUID().toString();
		final String serverId = UUID.randomUUID().toString();
		
		new MockUp<DaseinRequestExecutor>() {
            
			@Mock(invocations = 2)
            public void $init(Invocation inv, CloudProvider provider, HttpClientBuilder httpClientBuilder, HttpUriRequest httpUriRequest, ResponseHandler responseHandler) {
            	if (inv.getInvocationCount() == 1) {
					assertGet(httpUriRequest, String.format(GATEWAY_NAT_CONNECTIONS, ENDPOINT, ACCOUNT_NO, DATACENTER_ID, INTERNET_GATEWAY_ID).replace(" ", "%20"));
				} else if (inv.getInvocationCount() == 2) {
					assertGet(httpUriRequest, String.format(NAT_RULES_FOR_CONNECTION, ENDPOINT, ACCOUNT_NO, DATACENTER_ID, natConnectionId).replace(" ", "%20"));
            	}
            }
			
            @Mock(invocations = 2)
            public Object execute(Invocation inv) {
            	if (inv.getInvocationCount() == 1) {
                	WAPNatConnectionsModel wapNatConnectionsModel = new WAPNatConnectionsModel();
                	WAPNatConnectionModel wapNatConnectionModel = new WAPNatConnectionModel();
                	wapNatConnectionModel.setId(natConnectionId);
                	wapNatConnectionModel.setName(wapNatConnectionModel.getId());
                	wapNatConnectionModel.setStampId(ADDRESS_ID);
                	wapNatConnectionModel.setStatus("Active");
                	wapNatConnectionModel.setVmNetworkGatewayId(INTERNET_GATEWAY_ID);
                	wapNatConnectionsModel.setConnections(Arrays.asList(wapNatConnectionModel));
                	return wapNatConnectionsModel;
            	} else if (inv.getInvocationCount() == 2) {	
            		WAPNatRulesModel wapNatRulesModel = new WAPNatRulesModel();
            		WAPNatRuleModel wapNatRuleModel = new WAPNatRuleModel();
                	wapNatRuleModel.setId(ruleId);
                	wapNatRuleModel.setInternalPort("446");
                	wapNatRuleModel.setExternalPort("80");
                	wapNatRuleModel.setProtocol(Protocol.TCP.name());
                	wapNatRulesModel.setRules(Arrays.asList(wapNatRuleModel));
                	return wapNatRulesModel;
                } else {
                	throw new RuntimeException("Invalid invocation count!");
                }
            }
        };
		
        IpForwardingRule expectedRule = new IpForwardingRule();
        expectedRule.setServerId(serverId);
        expectedRule.setProviderRuleId(ruleId);
        expectedRule.setPrivatePort(446);
        expectedRule.setPublicPort(80);
        expectedRule.setProtocol(Protocol.TCP);
        
        assertReflectionEquals(
        		Arrays.asList(expectedRule), 
        		azurePackIpAddressSupport.listRulesForServer(serverId));
	}
	
	@Test(expected = InternalException.class)
	public void listRulesForServerShouldThrowExceptionIfServerIdIsNull() throws InternalException, CloudException {
		azurePackIpAddressSupport.listRulesForServer(null);
	}
	
	@Test
	public void listRulesForServerShouldReturnCorrectResultIfNatConnectionNotFound(
			@Mocked final AzurePackComputeService azurePackComputeService, 
			@Mocked final AzurePackNetworkServices azurePackNetworkServices) throws InternalException, CloudException {
		
		new NonStrictExpectations() {
			{ azurePackComputeService.getVirtualMachineSupport(); result = new FakeAzurePackVirtualMachineSupport(azurePackCloudMock, VLAN_ID, ADDRESS); }
			{ azurePackNetworkServices.getVlanSupport(); result = new FakeAzurePackVlanSupport(azurePackCloudMock, INTERNET_GATEWAY_ID); }
		};
		
		new MockUp<DaseinRequestExecutor>() {
			@Mock(invocations = 1)
            public void $init(CloudProvider provider, HttpClientBuilder httpClientBuilder, HttpUriRequest httpUriRequest, ResponseHandler responseHandler) {
				assertGet(httpUriRequest, String.format(GATEWAY_NAT_CONNECTIONS, ENDPOINT, ACCOUNT_NO, DATACENTER_ID, INTERNET_GATEWAY_ID).replace(" ", "%20"));
            }
            @Mock(invocations = 1)
            public Object execute() {
            	return null;
            }
        };
        assertEquals(Collections.emptyList(), azurePackIpAddressSupport.listRulesForServer(UUID.randomUUID().toString()));
	}
	
	@Test
	public void getCapabilitiesShouldReturnCorrectResult() throws CloudException, InternalException {
		assertEquals(
				AzurePackIpAddressCapabilities.class, 
				azurePackIpAddressSupport.getCapabilities().getClass());
	}
}
