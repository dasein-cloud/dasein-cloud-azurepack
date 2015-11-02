package org.dasein.cloud.azurepack.tests.network;

import static org.dasein.cloud.azurepack.tests.HttpMethodAsserts.*;
import static org.junit.Assert.*;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;
import java.util.Arrays;
import java.util.UUID;
import mockit.Invocation;
import mockit.Mock;
import mockit.MockUp;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.dasein.cloud.CloudErrorType;
import org.dasein.cloud.CloudException;
import org.dasein.cloud.CloudProvider;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.azurepack.model.WAPUserModel;
import org.dasein.cloud.azurepack.network.AzurePackNetworkCapabilities;
import org.dasein.cloud.azurepack.network.AzurePackNetworkServices;
import org.dasein.cloud.azurepack.network.model.WAPLogicalNetModel;
import org.dasein.cloud.azurepack.network.model.WAPLogicalNetsModel;
import org.dasein.cloud.azurepack.network.model.WAPNatConnectionModel;
import org.dasein.cloud.azurepack.network.model.WAPNatConnectionsModel;
import org.dasein.cloud.azurepack.network.model.WAPSubnetModel;
import org.dasein.cloud.azurepack.network.model.WAPSubnetsModel;
import org.dasein.cloud.azurepack.network.model.WAPVMNetworkGatewayModel;
import org.dasein.cloud.azurepack.network.model.WAPVMNetworkGatewaysModel;
import org.dasein.cloud.azurepack.network.model.WAPVMNetworkModel;
import org.dasein.cloud.azurepack.network.model.WAPVMNetworksModel;
import org.dasein.cloud.azurepack.tests.AzurePackTestsBaseWithLocation;
import org.dasein.cloud.network.InternetGateway;
import org.dasein.cloud.network.Subnet;
import org.dasein.cloud.network.SubnetCreateOptions;
import org.dasein.cloud.network.SubnetState;
import org.dasein.cloud.network.VLAN;
import org.dasein.cloud.network.VLANState;
import org.dasein.cloud.network.VLANSupport;
import org.dasein.cloud.network.VlanCreateOptions;
import org.dasein.cloud.util.requester.DaseinRequestExecutor;
import org.dasein.cloud.util.requester.entities.DaseinObjectToJsonEntity;
import org.junit.Before;
import org.junit.Test;

public class AzurePackVLANSupportTest extends AzurePackTestsBaseWithLocation {

	private final String VM_NETWORKS = "%s/%s/services/systemcenter/vmm/VMNetworks";
	private final String VM_SUBNETS = "%s/%s/services/systemcenter/vmm/VMSubnets";
	private final String LIST_VM_SUBNETS = "%s/%s/services/systemcenter/vmm/VMSubnets()?$filter=StampId eq guid'%s'";
	private final String VM_SUBNET = "%s/%s/services/systemcenter/vmm/VMSubnets(ID=Guid'%s',StampId=Guid'%s')";
	private final String LOGICAL_NETS = "%s/%s/services/systemcenter/vmm/LogicalNetworks";
	private final String VM_NETWORK = "%s/%s/services/systemcenter/vmm/VMNetworks(ID=Guid'%s',StampId=Guid'%s')";
	private final String VM_NET_GATEWAYS = "%s/%s/services/systemcenter/vmm/VMNetworkGateways";
	private final String NET_NAT_CONNECTIONS = "%s/%s/services/systemcenter/vmm/NATConnections";
	private final String VM_NET_GATEWAY = "%s/%s/services/systemcenter/vmm/VMNetworkGateways(ID=Guid'%s',StampId=Guid'%s')";
	private final String LIST_NET_GATEWAYS = "%s/%s/services/systemcenter/vmm/VMNetworkGateways()?$filter=VMNetworkId eq guid'%s' and StampId eq guid'%s'";
	private final String GATEWAY_NAT_CONNECTIONS = "%s/%s/services/systemcenter/vmm/NATConnections()?$filter=StampId eq guid'%s' and VMNetworkGatewayId eq guid'%s'";
	private final String NET_NAT_CONNECTION = "%s/%s/services/systemcenter/vmm/NATConnections(ID=Guid'%s',StampId=Guid'%s')";
	
	private VLANSupport azurePackNetworkSupport;
	
	private final String TEST_VLAN_ID = UUID.randomUUID().toString();
	private final String TEST_VLAN_NAME = "TESTVLAN";
	
	@Before
	public void setUp() throws CloudException, InternalException {
		super.setUp();
		final AzurePackNetworkServices azurePackNetworkServices = new AzurePackNetworkServices(azurePackCloudMock);
		azurePackNetworkSupport = azurePackNetworkServices.getVlanSupport();
	}
	
	@Test
	public void createSubnetShouldPostWithCorrectRequest() throws CloudException, InternalException {
		
		final String TEST_SUBNET_ID = UUID.randomUUID().toString();
		final String TEST_SUBNET_NAME = "TESTSUBNET";
		
		final SubnetCreateOptions options = SubnetCreateOptions.getInstance(TEST_VLAN_ID, DATACENTER_ID, "10.30.16.1/16", TEST_SUBNET_NAME, TEST_SUBNET_NAME);
		
		new MockUp<DaseinRequestExecutor>() {
            
			private ResponseHandler responseHandler;
			
			@Mock(invocations = 2)
            public void $init(Invocation inv, CloudProvider provider, HttpClientBuilder httpClientBuilder, HttpUriRequest httpUriRequest, ResponseHandler responseHandler) {
				if (inv.getInvocationCount() == 1) {
					assertGet(httpUriRequest, String.format(VM_NETWORKS, ENDPOINT, ACCOUNT_NO));
				} else if (inv.getInvocationCount() == 2) {
					
					WAPSubnetModel expectedSubnetModel = new WAPSubnetModel();
					expectedSubnetModel.setName(options.getName());
					expectedSubnetModel.setStampId(options.getProviderDataCenterId());
					expectedSubnetModel.setSubnet(options.getCidr());
					expectedSubnetModel.setVmNetworkId(options.getProviderVlanId());
					
					HttpEntityEnclosingRequest httpEntityEnclosingRequest = (HttpEntityEnclosingRequest) httpUriRequest;
					assertReflectionEquals(
							new DaseinObjectToJsonEntity<WAPSubnetModel>(expectedSubnetModel),
							httpEntityEnclosingRequest.getEntity());
            		assertPost(httpUriRequest, String.format(VM_SUBNETS, ENDPOINT, ACCOUNT_NO));
            	}
				this.responseHandler = responseHandler;
            }
			
            @Mock(invocations = 2)
            public Object execute(Invocation inv) {
            	if (inv.getInvocationCount() == 1) {
            		WAPVMNetworksModel wapVMNetworksModel = new WAPVMNetworksModel();
            		WAPUserModel wapUserModel = new WAPUserModel();
            		wapUserModel.setRoleID(UUID.randomUUID().toString());
            		WAPVMNetworkModel wapVMNetworkModel = new WAPVMNetworkModel();
            		wapVMNetworkModel.setId(TEST_VLAN_ID);
            		wapVMNetworkModel.setName(TEST_VLAN_NAME);
            		wapVMNetworkModel.setDescription(wapVMNetworkModel.getName());
            		wapVMNetworkModel.setStampId(DATACENTER_ID);
            		wapVMNetworkModel.setOwner(wapUserModel);
            		wapVMNetworkModel.setEnabled("true");
            		wapVMNetworksModel.setVirtualMachineNetworks(Arrays.asList(wapVMNetworkModel));
            		return mapFromModel(this.responseHandler, wapVMNetworksModel);            		
            	} else if (inv.getInvocationCount() == 2) {	 
            		WAPSubnetModel wapSubnetModel = new WAPSubnetModel();
            		wapSubnetModel.setId(TEST_SUBNET_ID);
            		wapSubnetModel.setName(TEST_SUBNET_NAME);
            		wapSubnetModel.setVmNetworkId(TEST_VLAN_ID);
            		wapSubnetModel.setSubnet(options.getCidr());
            		return mapFromModel(this.responseHandler, wapSubnetModel);
                } else {
                	throw new RuntimeException("Invalid invocation count!");
                }
            }
        };
		
        Subnet expectedSubnet = Subnet.getInstance(ACCOUNT_NO, REGION, TEST_VLAN_ID, TEST_SUBNET_ID, SubnetState.AVAILABLE, TEST_SUBNET_NAME, TEST_SUBNET_NAME, options.getCidr());
        assertReflectionEquals(
        		expectedSubnet,
        		azurePackNetworkSupport.createSubnet(options));
	}
	
	@Test(expected = InternalException.class)
	public void createSubnetShouldThrowExceptionIfOptionIsNull() throws CloudException, InternalException {
		azurePackNetworkSupport.createSubnet(null);
	}
	
	@Test(expected = InternalException.class)
	public void createSubnetShouldThrowExceptionIfNoVlanFound() throws CloudException, InternalException {
		
		new MockUp<DaseinRequestExecutor>() {
			@Mock(invocations = 1)
            public void $init(CloudProvider provider, HttpClientBuilder httpClientBuilder, HttpUriRequest httpUriRequest, ResponseHandler responseHandler) {
					assertGet(httpUriRequest, String.format(VM_NETWORKS, ENDPOINT, ACCOUNT_NO));
            }
            @Mock(invocations = 1)
            public Object execute() {
        		return null;            		
            }
        };
		
        SubnetCreateOptions options = SubnetCreateOptions.getInstance(TEST_VLAN_ID, "10.30.16.1/16", TEST_VLAN_NAME, TEST_VLAN_NAME);
		azurePackNetworkSupport.createSubnet(options);
	}
	
	@Test
	public void listSubnetsShouldReturnCorrectResult() throws CloudException, InternalException {

		final String TEST_SUBNET_ID = UUID.randomUUID().toString();
		final String TEST_SUBNET_NAME = "TESTSUBNET";
		
		new MockUp<DaseinRequestExecutor>() {
            
			private ResponseHandler responseHandler;
			
			@Mock(invocations = 1)
            public void $init(CloudProvider provider, HttpClientBuilder httpClientBuilder, HttpUriRequest httpUriRequest, ResponseHandler responseHandler) {
				assertGet(httpUriRequest, String.format(LIST_VM_SUBNETS, ENDPOINT, ACCOUNT_NO, DATACENTER_ID).replace(" ", "%20"));
				this.responseHandler = responseHandler;
            }
			
            @Mock(invocations = 1)
            public Object execute() {
            	WAPSubnetsModel wapSubnetsModel = new WAPSubnetsModel();
            	WAPSubnetModel wapSubnetModel = new WAPSubnetModel();
            	wapSubnetModel.setId(TEST_SUBNET_ID);
            	wapSubnetModel.setName(TEST_SUBNET_NAME);
            	wapSubnetModel.setSubnet("10.30.16.1/16");
            	wapSubnetModel.setVmNetworkId(TEST_VLAN_ID);
            	wapSubnetsModel.setSubnets(Arrays.asList(wapSubnetModel));
        		return mapFromModel(this.responseHandler, wapSubnetsModel);            		
            }
        };
		
        Subnet expectedSubnet = Subnet.getInstance(ACCOUNT_NO, REGION, TEST_VLAN_ID, TEST_SUBNET_ID, SubnetState.AVAILABLE, TEST_SUBNET_NAME, TEST_SUBNET_NAME, "10.30.16.1/16");
        assertReflectionEquals(
        		Arrays.asList(expectedSubnet),
        		azurePackNetworkSupport.listSubnets(TEST_VLAN_ID));
	}
	
	@Test
	public void removeSubnetShouldDeleteWithCorrectRequest() throws CloudException, InternalException {
		
		final String TEST_SUBNET_ID = UUID.randomUUID().toString();
		final String TEST_SUBNET_NAME = "TESTSUBNET";
		
		new MockUp<DaseinRequestExecutor>() {
            
			@Mock(invocations = 2)
            public void $init(Invocation inv, CloudProvider provider, HttpClientBuilder httpClientBuilder, HttpUriRequest httpUriRequest, ResponseHandler responseHandler) {
				if (inv.getInvocationCount() == 1) {
					assertGet(httpUriRequest, String.format(LIST_VM_SUBNETS, ENDPOINT, ACCOUNT_NO, DATACENTER_ID).replace(" ", "%20"));
				} else if (inv.getInvocationCount() == 2) {
					assertDelete(httpUriRequest, String.format(VM_SUBNET, ENDPOINT, ACCOUNT_NO, TEST_SUBNET_ID, DATACENTER_ID));
				}
            }
			
            @Mock(invocations = 2)
            public Object execute(Invocation inv) {
            	if (inv.getInvocationCount() == 1) {
	            	WAPSubnetsModel wapSubnetsModel = new WAPSubnetsModel();
	            	WAPSubnetModel wapSubnetModel = new WAPSubnetModel();
	            	wapSubnetModel.setId(TEST_SUBNET_ID);
	            	wapSubnetModel.setName(TEST_SUBNET_NAME);
	            	wapSubnetModel.setSubnet("10.30.16.1/16");
	            	wapSubnetModel.setStampId(DATACENTER_ID);
	            	wapSubnetModel.setVmNetworkId(TEST_VLAN_ID);
	            	wapSubnetsModel.setSubnets(Arrays.asList(wapSubnetModel));
	        		return wapSubnetsModel;
            	} else if (inv.getInvocationCount() == 2) {
            		return null;
            	} else {
            		throw new RuntimeException("Invalid invocation count!");
            	}
            }
        };
		
		azurePackNetworkSupport.removeSubnet(TEST_SUBNET_ID);
	}
	
	@Test(expected = InternalException.class)
	public void removeSubnetShouldThrowExceptionIfNoSubnetFound() throws CloudException, InternalException {
		
		final String TEST_SUBNET_ID = UUID.randomUUID().toString();
		
		new MockUp<DaseinRequestExecutor>() {
			@Mock(invocations = 1)
            public void $init(CloudProvider provider, HttpClientBuilder httpClientBuilder, HttpUriRequest httpUriRequest, ResponseHandler responseHandler) {
				assertGet(httpUriRequest, String.format(LIST_VM_SUBNETS, ENDPOINT, ACCOUNT_NO, DATACENTER_ID).replace(" ", "%20"));
            }
            @Mock(invocations = 1)
            public Object execute() {
	            return new WAPSubnetsModel();
            }
        };
		
		azurePackNetworkSupport.removeSubnet(TEST_SUBNET_ID);
	}
	
	@Test
	public void createVlanShouldPostWithCorrectRequest() throws InternalException, CloudException {
		
		final String LOGICAL_NETWORK_ID = UUID.randomUUID().toString();
		final String USER_ROLE_ID = UUID.randomUUID().toString();
		final String TEST_SUBNET_ID = UUID.randomUUID().toString();
		
		final VlanCreateOptions vlanCreateOptions = VlanCreateOptions.getInstance(TEST_VLAN_NAME, TEST_VLAN_NAME, "10.30.16.1/16", REGION, null, null);
		
		new MockUp<DaseinRequestExecutor>() {
            
			private ResponseHandler responseHandler;
			
			@Mock(invocations = 3)
            public void $init(Invocation inv, CloudProvider provider, HttpClientBuilder httpClientBuilder, HttpUriRequest httpUriRequest, ResponseHandler responseHandler) {
				if (inv.getInvocationCount() == 1) {
					assertGet(httpUriRequest, String.format(LOGICAL_NETS, ENDPOINT, ACCOUNT_NO));
				} else if (inv.getInvocationCount() == 2) {
					
					WAPVMNetworkModel networkModel = new WAPVMNetworkModel();
			        networkModel.setName(vlanCreateOptions.getName());
			        networkModel.setStampId(DATACENTER_ID);
			        networkModel.setLogicalNetworkId(LOGICAL_NETWORK_ID);
			        networkModel.setDescription(vlanCreateOptions.getDescription());
					
			        HttpEntityEnclosingRequest httpEntityEnclosingRequest = (HttpEntityEnclosingRequest) httpUriRequest;
					assertReflectionEquals(
							new DaseinObjectToJsonEntity<WAPVMNetworkModel>(networkModel),
							httpEntityEnclosingRequest.getEntity());
					assertPost(httpUriRequest, String.format(VM_NETWORKS, ENDPOINT, ACCOUNT_NO));
				} else if (inv.getInvocationCount() == 3) {
					
					WAPSubnetModel subnetModel = new WAPSubnetModel();
			        subnetModel.setName(vlanCreateOptions.getName());
			        subnetModel.setStampId(DATACENTER_ID);
			        subnetModel.setSubnet(vlanCreateOptions.getCidr());
			        subnetModel.setVmNetworkId(TEST_VLAN_ID);
			        
			        HttpEntityEnclosingRequest httpEntityEnclosingRequest = (HttpEntityEnclosingRequest) httpUriRequest;
					assertReflectionEquals(
							new DaseinObjectToJsonEntity<WAPSubnetModel>(subnetModel),
							httpEntityEnclosingRequest.getEntity());
					assertPost(httpUriRequest, String.format(VM_SUBNETS, ENDPOINT, ACCOUNT_NO));
					this.responseHandler = responseHandler;
				}
			}
			
            @Mock(invocations = 3)
            public Object execute(Invocation inv) {
            	if (inv.getInvocationCount() == 1) {
	            	WAPLogicalNetsModel wapLogicalNetsModel = new WAPLogicalNetsModel();
	            	WAPLogicalNetModel wapLogicalNetModel = new WAPLogicalNetModel();
	            	wapLogicalNetModel.setNetworkVirtualizationEnabled("true");
	            	wapLogicalNetModel.setId(LOGICAL_NETWORK_ID);
	            	wapLogicalNetModel.setStampId(DATACENTER_ID);
	            	wapLogicalNetsModel.setLogicalNets(Arrays.asList(wapLogicalNetModel));
	            	return wapLogicalNetsModel;
            	} else if (inv.getInvocationCount() == 2) {
            		WAPVMNetworkModel wapVMNetworkModel = new WAPVMNetworkModel();
            		WAPUserModel wapUserModel = new WAPUserModel();
            		wapUserModel.setRoleID(USER_ROLE_ID);
            		wapVMNetworkModel.setId(TEST_VLAN_ID);
            		wapVMNetworkModel.setName(TEST_VLAN_NAME);
            		wapVMNetworkModel.setDescription(wapVMNetworkModel.getName());
            		wapVMNetworkModel.setStampId(DATACENTER_ID);
            		wapVMNetworkModel.setEnabled("true");
            		wapVMNetworkModel.setOwner(wapUserModel);
            		wapVMNetworkModel.setLogicalNetworkId(LOGICAL_NETWORK_ID);
            		return wapVMNetworkModel;
            	} else if (inv.getInvocationCount() == 3) {
            		WAPSubnetModel subnetModel = new WAPSubnetModel();
            		subnetModel.setId(TEST_SUBNET_ID);
                    subnetModel.setName(vlanCreateOptions.getName());
                    subnetModel.setStampId(DATACENTER_ID);
                    subnetModel.setSubnet(vlanCreateOptions.getCidr());
                    subnetModel.setVmNetworkId(TEST_VLAN_ID);
            		return mapFromModel(this.responseHandler, subnetModel);
            	} else {
            		throw new RuntimeException("Invalid invocation count!");
            	}
            }
        };
		
        VLAN expectedResult = new VLAN();
        expectedResult.setName(TEST_VLAN_NAME);
        expectedResult.setDescription(TEST_VLAN_NAME);
        expectedResult.setProviderVlanId(TEST_VLAN_ID);
        expectedResult.setProviderDataCenterId(DATACENTER_ID);
        expectedResult.setProviderRegionId(REGION);
        expectedResult.setProviderOwnerId(USER_ROLE_ID);
        expectedResult.setCurrentState(VLANState.AVAILABLE);
        assertReflectionEquals(
        		expectedResult,
        		azurePackNetworkSupport.createVlan(vlanCreateOptions));
	}
	
	@Test(expected = InternalException.class)
	public void createVlanShouldThrowExceptionIfOptionIsNull() throws InternalException, CloudException {
		azurePackNetworkSupport.createVlan(null);
	}
	
	@Test
	public void removeVlanShouldDeleteWithCorrectRequest() throws CloudException, InternalException {
		
		new MockUp<DaseinRequestExecutor>() {
            
			@Mock(invocations = 2)
            public void $init(Invocation inv, CloudProvider provider, HttpClientBuilder httpClientBuilder, HttpUriRequest httpUriRequest, ResponseHandler responseHandler) {
				if (inv.getInvocationCount() == 1) {
					assertGet(httpUriRequest, String.format(VM_NETWORKS, ENDPOINT, ACCOUNT_NO));
				} else if (inv.getInvocationCount() == 2) {
					assertDelete(httpUriRequest, String.format(VM_NETWORK, ENDPOINT, ACCOUNT_NO, TEST_VLAN_ID, DATACENTER_ID));
				}
			}
			
            @Mock(invocations = 2)
            public Object execute(Invocation inv) {
            	if (inv.getInvocationCount() == 1) {
            		WAPVMNetworksModel wapVMNetworksModel = new WAPVMNetworksModel();
            		WAPVMNetworkModel wapVMNetworkModel = new WAPVMNetworkModel();
            		wapVMNetworkModel.setId(TEST_VLAN_ID);
            		wapVMNetworkModel.setStampId(DATACENTER_ID);
            		wapVMNetworksModel.setVirtualMachineNetworks(Arrays.asList(wapVMNetworkModel));
            		return wapVMNetworksModel;
            	} else if (inv.getInvocationCount() == 2) {
            		return null;
            	} else {
            		throw new RuntimeException("Invalid invocation count!");
            	}
            }
        };
		
		azurePackNetworkSupport.removeVlan(TEST_VLAN_ID);
	}
	
	@Test(expected = InternalException.class)
	public void removeVlanShouldThrowExceptionIfNoVlanFound() throws CloudException, InternalException {
		
		new MockUp<DaseinRequestExecutor>() {
			@Mock(invocations = 1)
            public void $init(CloudProvider provider, HttpClientBuilder httpClientBuilder, HttpUriRequest httpUriRequest, ResponseHandler responseHandler) {
				assertGet(httpUriRequest, String.format(VM_NETWORKS, ENDPOINT, ACCOUNT_NO));
			}
            @Mock(invocations = 1)
            public Object execute() {
            	return new WAPVMNetworksModel();
            }
        };
		azurePackNetworkSupport.removeVlan(TEST_VLAN_ID);
	}
	
	@Test
	public void listVlansShouldReturnCorrectResult() throws CloudException, InternalException {
		
		final String USER_ROLE_ID = UUID.randomUUID().toString();
		
		new MockUp<DaseinRequestExecutor>() {
            
			private ResponseHandler responseHandler;
			
			@Mock(invocations = 1)
            public void $init(CloudProvider provider, HttpClientBuilder httpClientBuilder, HttpUriRequest httpUriRequest, ResponseHandler responseHandler) {
				assertGet(httpUriRequest, String.format(VM_NETWORKS, ENDPOINT, ACCOUNT_NO));
				this.responseHandler = responseHandler;
			}
			
            @Mock(invocations = 1)
            public Object execute() {
        		WAPVMNetworksModel wapVMNetworksModel = new WAPVMNetworksModel();
        		WAPVMNetworkModel wapVMNetworkModel = new WAPVMNetworkModel();
        		WAPUserModel wapUserModel = new WAPUserModel();
        		wapUserModel.setRoleID(USER_ROLE_ID);
        		wapVMNetworkModel.setId(TEST_VLAN_ID);
        		wapVMNetworkModel.setName(TEST_VLAN_NAME);
        		wapVMNetworkModel.setDescription(wapVMNetworkModel.getName());
        		wapVMNetworkModel.setStampId(DATACENTER_ID);
        		wapVMNetworkModel.setEnabled("true");
        		wapVMNetworkModel.setOwner(wapUserModel);
        		wapVMNetworksModel.setVirtualMachineNetworks(Arrays.asList(wapVMNetworkModel));
        		return mapFromModel(this.responseHandler, wapVMNetworksModel);
            }
        };
		
        VLAN expectedResult = new VLAN();
        expectedResult.setName(TEST_VLAN_NAME);
        expectedResult.setDescription(TEST_VLAN_NAME);
        expectedResult.setProviderVlanId(TEST_VLAN_ID);
        expectedResult.setProviderDataCenterId(DATACENTER_ID);
        expectedResult.setProviderRegionId(REGION);
        expectedResult.setProviderOwnerId(USER_ROLE_ID);
        expectedResult.setCurrentState(VLANState.AVAILABLE);
        assertReflectionEquals( 
        		Arrays.asList(expectedResult),
        		azurePackNetworkSupport.listVlans());
	}
	
	@Test
	public void getCapabilitiesShouldReturnCorrectResult() throws CloudException, InternalException {
		assertEquals(
				AzurePackNetworkCapabilities.class, 
				azurePackNetworkSupport.getCapabilities().getClass());
	}
	
	@Test
	public void getVlanShouldReturnCorrectResult() throws CloudException, InternalException {
		
		final String USER_ROLE_ID = UUID.randomUUID().toString();
		
		new MockUp<DaseinRequestExecutor>() {
            
			private ResponseHandler responseHandler;
			
			@Mock(invocations = 1)
            public void $init(CloudProvider provider, HttpClientBuilder httpClientBuilder, HttpUriRequest httpUriRequest, ResponseHandler responseHandler) {
				assertGet(httpUriRequest, String.format(VM_NETWORKS, ENDPOINT, ACCOUNT_NO));
				this.responseHandler = responseHandler;
			}
			
            @Mock(invocations = 1)
            public Object execute() {
        		WAPVMNetworksModel wapVMNetworksModel = new WAPVMNetworksModel();
        		WAPVMNetworkModel wapVMNetworkModel = new WAPVMNetworkModel();
        		WAPUserModel wapUserModel = new WAPUserModel();
        		wapUserModel.setRoleID(USER_ROLE_ID);
        		wapVMNetworkModel.setId(TEST_VLAN_ID);
        		wapVMNetworkModel.setName(TEST_VLAN_NAME);
        		wapVMNetworkModel.setDescription(wapVMNetworkModel.getName());
        		wapVMNetworkModel.setStampId(DATACENTER_ID);
        		wapVMNetworkModel.setEnabled("true");
        		wapVMNetworkModel.setOwner(wapUserModel);
        		wapVMNetworksModel.setVirtualMachineNetworks(Arrays.asList(wapVMNetworkModel));
        		return mapFromModel(this.responseHandler, wapVMNetworksModel);
            }
        };
		
        VLAN expectedResult = new VLAN();
        expectedResult.setName(TEST_VLAN_NAME);
        expectedResult.setDescription(TEST_VLAN_NAME);
        expectedResult.setProviderVlanId(TEST_VLAN_ID);
        expectedResult.setProviderDataCenterId(DATACENTER_ID);
        expectedResult.setProviderRegionId(REGION);
        expectedResult.setProviderOwnerId(USER_ROLE_ID);
        expectedResult.setCurrentState(VLANState.AVAILABLE);
        assertReflectionEquals( 
        		expectedResult,
        		azurePackNetworkSupport.getVlan(TEST_VLAN_ID));
	}
	
	@Test(expected = InternalException.class)
	public void getVlanShouldThrowExceptionIfVlanIdIsNull() throws CloudException, InternalException {
		azurePackNetworkSupport.getVlan(null);
	}
	
	@Test
	public void createInternetGatewayShouldPostWithCorrectRequest() throws CloudException, InternalException {
		
		final String USER_ROLE_ID = UUID.randomUUID().toString();
		final String INTERNET_GATEWAY_ID = UUID.randomUUID().toString();
		
		new MockUp<DaseinRequestExecutor>() {
            
			private ResponseHandler responseHandler;
			
			@Mock(invocations = 3)
            public void $init(Invocation inv, CloudProvider provider, HttpClientBuilder httpClientBuilder, HttpUriRequest httpUriRequest, ResponseHandler responseHandler) {
				if (inv.getInvocationCount() == 1) {
					assertGet(httpUriRequest, String.format(VM_NETWORKS, ENDPOINT, ACCOUNT_NO));
					this.responseHandler = responseHandler;
				} else if (inv.getInvocationCount() == 2) {
					
					WAPVMNetworkGatewayModel wapvmNetworkGatewayModel = new WAPVMNetworkGatewayModel();
			        wapvmNetworkGatewayModel.setName(TEST_VLAN_NAME + "Gateway");
			        wapvmNetworkGatewayModel.setDescription(TEST_VLAN_NAME + "Gateway");
			        wapvmNetworkGatewayModel.setStampId(DATACENTER_ID);
			        wapvmNetworkGatewayModel.setVmNetworkId(TEST_VLAN_ID);
			        wapvmNetworkGatewayModel.setRequiresNAT("true");
			        
			        HttpEntityEnclosingRequest httpEntityEnclosingRequest = (HttpEntityEnclosingRequest) httpUriRequest;
					assertReflectionEquals(
							new DaseinObjectToJsonEntity<WAPVMNetworkGatewayModel>(wapvmNetworkGatewayModel),
							httpEntityEnclosingRequest.getEntity());
					assertPost(httpUriRequest, String.format(VM_NET_GATEWAYS, ENDPOINT, ACCOUNT_NO));
				} else if (inv.getInvocationCount() == 3) {
					
					WAPNatConnectionModel wapNatConnectionModel = new WAPNatConnectionModel();
			        wapNatConnectionModel.setName(TEST_VLAN_NAME + "NAT Connection");
			        wapNatConnectionModel.setStampId(DATACENTER_ID);
			        wapNatConnectionModel.setVmNetworkGatewayId(INTERNET_GATEWAY_ID);
			        
			        HttpEntityEnclosingRequest httpEntityEnclosingRequest = (HttpEntityEnclosingRequest) httpUriRequest;
					assertReflectionEquals(
							new DaseinObjectToJsonEntity<WAPNatConnectionModel>(wapNatConnectionModel),
							httpEntityEnclosingRequest.getEntity());
					assertPost(httpUriRequest, String.format(NET_NAT_CONNECTIONS, ENDPOINT, ACCOUNT_NO));
				}
			}
			
            @Mock(invocations = 3)
            public Object execute(Invocation inv) {
            	if (inv.getInvocationCount() == 1) {
	        		WAPVMNetworksModel wapVMNetworksModel = new WAPVMNetworksModel();
	        		WAPVMNetworkModel wapVMNetworkModel = new WAPVMNetworkModel();
	        		WAPUserModel wapUserModel = new WAPUserModel();
	        		wapUserModel.setRoleID(USER_ROLE_ID);
	        		wapVMNetworkModel.setId(TEST_VLAN_ID);
	        		wapVMNetworkModel.setName(TEST_VLAN_NAME);
	        		wapVMNetworkModel.setDescription(wapVMNetworkModel.getName());
	        		wapVMNetworkModel.setStampId(DATACENTER_ID);
	        		wapVMNetworkModel.setEnabled("true");
	        		wapVMNetworkModel.setOwner(wapUserModel);
	        		wapVMNetworksModel.setVirtualMachineNetworks(Arrays.asList(wapVMNetworkModel));
	        		return mapFromModel(this.responseHandler, wapVMNetworksModel);
            	} else if (inv.getInvocationCount() == 2) {
            		WAPVMNetworkGatewayModel wapVMNetworkGatewayModel = new WAPVMNetworkGatewayModel();
            		wapVMNetworkGatewayModel.setId(INTERNET_GATEWAY_ID);
            		return wapVMNetworkGatewayModel;
            	} else if (inv.getInvocationCount() == 3) {
            		return null;
            	} else {
            		throw new RuntimeException("Invalid invocation count!");
            	}
            }
        };
		
		assertEquals(
				INTERNET_GATEWAY_ID, 
				azurePackNetworkSupport.createInternetGateway(TEST_VLAN_ID));
	}
	
	@Test(expected = InternalException.class)
	public void createInternetGatewayShouldThrowExceptionIfNoVlanFound() throws CloudException, InternalException {
		new MockUp<DaseinRequestExecutor>() {
			@Mock(invocations = 1)
            public void $init(CloudProvider provider, HttpClientBuilder httpClientBuilder, HttpUriRequest httpUriRequest, ResponseHandler responseHandler) {
				assertGet(httpUriRequest, String.format(VM_NETWORKS, ENDPOINT, ACCOUNT_NO));
			}
            @Mock(invocations = 1)
            public Object execute() {
	        	return null;
            }
        };
        azurePackNetworkSupport.createInternetGateway(TEST_VLAN_ID);
	}
	
	@Test(expected = CloudException.class)
	public void createInternetGatewayShouldThrowExceptionIfCreateNatConnectionFailed() throws CloudException, InternalException {
		
		final String USER_ROLE_ID = UUID.randomUUID().toString();
		final String INTERNET_GATEWAY_ID = UUID.randomUUID().toString();
		
		new MockUp<DaseinRequestExecutor>() {
            
			private ResponseHandler responseHandler;
			
			@Mock(invocations = 4)
            public void $init(Invocation inv, CloudProvider provider, HttpClientBuilder httpClientBuilder, HttpUriRequest httpUriRequest, ResponseHandler responseHandler) {
				if (inv.getInvocationCount() == 1) {
					assertGet(httpUriRequest, String.format(VM_NETWORKS, ENDPOINT, ACCOUNT_NO));
					this.responseHandler = responseHandler;
				} else if (inv.getInvocationCount() == 2) {
					
					WAPVMNetworkGatewayModel wapvmNetworkGatewayModel = new WAPVMNetworkGatewayModel();
			        wapvmNetworkGatewayModel.setName(TEST_VLAN_NAME + "Gateway");
			        wapvmNetworkGatewayModel.setDescription(TEST_VLAN_NAME + "Gateway");
			        wapvmNetworkGatewayModel.setStampId(DATACENTER_ID);
			        wapvmNetworkGatewayModel.setVmNetworkId(TEST_VLAN_ID);
			        wapvmNetworkGatewayModel.setRequiresNAT("true");
			        
			        HttpEntityEnclosingRequest httpEntityEnclosingRequest = (HttpEntityEnclosingRequest) httpUriRequest;
					assertReflectionEquals(
							new DaseinObjectToJsonEntity<WAPVMNetworkGatewayModel>(wapvmNetworkGatewayModel),
							httpEntityEnclosingRequest.getEntity());
					assertPost(httpUriRequest, String.format(VM_NET_GATEWAYS, ENDPOINT, ACCOUNT_NO));
				} else if (inv.getInvocationCount() == 3) {
					
					WAPNatConnectionModel wapNatConnectionModel = new WAPNatConnectionModel();
			        wapNatConnectionModel.setName(TEST_VLAN_NAME + "NAT Connection");
			        wapNatConnectionModel.setStampId(DATACENTER_ID);
			        wapNatConnectionModel.setVmNetworkGatewayId(INTERNET_GATEWAY_ID);
			        
			        HttpEntityEnclosingRequest httpEntityEnclosingRequest = (HttpEntityEnclosingRequest) httpUriRequest;
					assertReflectionEquals(
							new DaseinObjectToJsonEntity<WAPNatConnectionModel>(wapNatConnectionModel),
							httpEntityEnclosingRequest.getEntity());
					assertPost(httpUriRequest, String.format(NET_NAT_CONNECTIONS, ENDPOINT, ACCOUNT_NO));
				} else if (inv.getInvocationCount() == 4) {
					assertDelete(httpUriRequest, String.format(VM_NET_GATEWAY, ENDPOINT, ACCOUNT_NO, INTERNET_GATEWAY_ID, DATACENTER_ID));
				}
			}
			
            @Mock(invocations = 4)
            public Object execute(Invocation inv) {
            	if (inv.getInvocationCount() == 1) {
	        		WAPVMNetworksModel wapVMNetworksModel = new WAPVMNetworksModel();
	        		WAPVMNetworkModel wapVMNetworkModel = new WAPVMNetworkModel();
	        		WAPUserModel wapUserModel = new WAPUserModel();
	        		wapUserModel.setRoleID(USER_ROLE_ID);
	        		wapVMNetworkModel.setId(TEST_VLAN_ID);
	        		wapVMNetworkModel.setName(TEST_VLAN_NAME);
	        		wapVMNetworkModel.setDescription(wapVMNetworkModel.getName());
	        		wapVMNetworkModel.setStampId(DATACENTER_ID);
	        		wapVMNetworkModel.setEnabled("true");
	        		wapVMNetworkModel.setOwner(wapUserModel);
	        		wapVMNetworksModel.setVirtualMachineNetworks(Arrays.asList(wapVMNetworkModel));
	        		return mapFromModel(this.responseHandler, wapVMNetworksModel);
            	} else if (inv.getInvocationCount() == 2) {
            		WAPVMNetworkGatewayModel wapVMNetworkGatewayModel = new WAPVMNetworkGatewayModel();
            		wapVMNetworkGatewayModel.setId(INTERNET_GATEWAY_ID);
            		wapVMNetworkGatewayModel.setStampId(DATACENTER_ID);
            		return wapVMNetworkGatewayModel;
            	} else if (inv.getInvocationCount() == 3) {
            		throw new RuntimeException("Create Nat Connection Failed!");
            	} else if (inv.getInvocationCount() == 4) {
            		return null;
            	} else {
            		throw new RuntimeException("Invalid invocation count!");
            	}
            }
        };
		
		azurePackNetworkSupport.createInternetGateway(TEST_VLAN_ID);
	}
	
	@Test
	public void removeInternetGatewayShouldDeleteWithCorrectRequest() throws CloudException, InternalException {
		
		final String INTERNET_GATEWAY_ID = UUID.randomUUID().toString();
		final String USER_ROLE_ID = UUID.randomUUID().toString();
		final String NAT_CONNECTION_ID = UUID.randomUUID().toString();
		
		new MockUp<DaseinRequestExecutor>() {
            
			private ResponseHandler responseHandler;
			
			@Mock(invocations = 5)
            public void $init(Invocation inv, CloudProvider provider, HttpClientBuilder httpClientBuilder, HttpUriRequest httpUriRequest, ResponseHandler responseHandler) {
				if (inv.getInvocationCount() == 1) {
					assertGet(httpUriRequest, String.format(VM_NETWORKS, ENDPOINT, ACCOUNT_NO));
					this.responseHandler = responseHandler;
				} else if (inv.getInvocationCount() == 2) {
					assertGet(httpUriRequest, String.format(LIST_NET_GATEWAYS, ENDPOINT, ACCOUNT_NO, TEST_VLAN_ID, DATACENTER_ID).replace(" ", "%20"));
				} else if (inv.getInvocationCount() == 3) {
					assertGet(httpUriRequest, String.format(GATEWAY_NAT_CONNECTIONS, ENDPOINT, ACCOUNT_NO, DATACENTER_ID, INTERNET_GATEWAY_ID).replace(" ", "%20"));
				} else if (inv.getInvocationCount() == 4) {
					assertDelete(httpUriRequest, String.format(NET_NAT_CONNECTION, ENDPOINT, ACCOUNT_NO, NAT_CONNECTION_ID, DATACENTER_ID));
				} else if (inv.getInvocationCount() == 5) {
					assertDelete(httpUriRequest, String.format(VM_NET_GATEWAY, ENDPOINT, ACCOUNT_NO, INTERNET_GATEWAY_ID, DATACENTER_ID));
				}
			}
			
            @Mock(invocations = 5)
            public Object execute(Invocation inv) {
            	if (inv.getInvocationCount() == 1) {
            		WAPVMNetworksModel wapVMNetworksModel = new WAPVMNetworksModel();
            		WAPVMNetworkModel wapVMNetworkModel = new WAPVMNetworkModel();
            		WAPUserModel wapUserModel = new WAPUserModel();
            		wapUserModel.setRoleID(USER_ROLE_ID);
            		wapVMNetworkModel.setId(TEST_VLAN_ID);
            		wapVMNetworkModel.setName(TEST_VLAN_NAME);
            		wapVMNetworkModel.setDescription(wapVMNetworkModel.getName());
            		wapVMNetworkModel.setStampId(DATACENTER_ID);
            		wapVMNetworkModel.setEnabled("true");
            		wapVMNetworkModel.setOwner(wapUserModel);
            		wapVMNetworksModel.setVirtualMachineNetworks(Arrays.asList(wapVMNetworkModel));
            		return mapFromModel(this.responseHandler, wapVMNetworksModel);
            	} else if (inv.getInvocationCount() == 2) {
	            	WAPVMNetworkGatewaysModel wapVMNetworkGatewaysModel = new WAPVMNetworkGatewaysModel();
	            	WAPVMNetworkGatewayModel wapVMNetworkGatewayModel = new WAPVMNetworkGatewayModel();
	            	wapVMNetworkGatewayModel.setId(INTERNET_GATEWAY_ID);
	            	wapVMNetworkGatewayModel.setStampId(DATACENTER_ID);
	            	wapVMNetworkGatewaysModel.setGateways(Arrays.asList(wapVMNetworkGatewayModel));
	        		return wapVMNetworkGatewaysModel;
            	} else if (inv.getInvocationCount() == 3) {
            		WAPNatConnectionsModel wapNatConnectionsModel = new WAPNatConnectionsModel();
            		WAPNatConnectionModel wapNatConnectionModel = new WAPNatConnectionModel();
            		wapNatConnectionModel.setId(NAT_CONNECTION_ID);
            		wapNatConnectionModel.setStampId(DATACENTER_ID);
            		wapNatConnectionsModel.setConnections(Arrays.asList(wapNatConnectionModel));
            		return wapNatConnectionsModel;
            	} else if (inv.getInvocationCount() == 4) {
            		return null;
            	} else if (inv.getInvocationCount() == 5) {
            		return null;
            	} else {
            		throw new RuntimeException("Invalid invocation count!");
            	}
            }
        };
		
		azurePackNetworkSupport.removeInternetGateway(TEST_VLAN_ID);
	}
	
	@Test(expected = InternalException.class)
	public void removeInternetGatewayShouldThrowExceptionIfNoVlanFound() throws CloudException, InternalException {
		new MockUp<DaseinRequestExecutor>() {
			@Mock(invocations = 1)
            public void $init(CloudProvider provider, HttpClientBuilder httpClientBuilder, HttpUriRequest httpUriRequest, ResponseHandler responseHandler) {
					assertGet(httpUriRequest, String.format(VM_NETWORKS, ENDPOINT, ACCOUNT_NO));
			}
            @Mock(invocations = 1)
            public Object execute() {
        		return null;
            }
        };
		azurePackNetworkSupport.removeInternetGateway(TEST_VLAN_ID);
	}
	
	@Test(expected = InternalException.class)
	public void removeInternetGatewayShouldThrowExceptionIfNoInternetGatewayFound() throws CloudException, InternalException {
		
		final String USER_ROLE_ID = UUID.randomUUID().toString();
		
		new MockUp<DaseinRequestExecutor>() {
			
			private ResponseHandler responseHandler;
			
			@Mock(invocations = 2)
            public void $init(Invocation inv, CloudProvider provider, HttpClientBuilder httpClientBuilder, HttpUriRequest httpUriRequest, ResponseHandler responseHandler) {
				if (inv.getInvocationCount() == 1) {
					assertGet(httpUriRequest, String.format(VM_NETWORKS, ENDPOINT, ACCOUNT_NO));
					this.responseHandler = responseHandler;
				} else if (inv.getInvocationCount() == 2) {
					assertGet(httpUriRequest, String.format(LIST_NET_GATEWAYS, ENDPOINT, ACCOUNT_NO, TEST_VLAN_ID, DATACENTER_ID).replace(" ", "%20"));
				}
			}
			
        	@Mock(invocations = 2)
            public Object execute(Invocation inv) {
            	if (inv.getInvocationCount() == 1) {
            		WAPVMNetworksModel wapVMNetworksModel = new WAPVMNetworksModel();
            		WAPVMNetworkModel wapVMNetworkModel = new WAPVMNetworkModel();
            		WAPUserModel wapUserModel = new WAPUserModel();
            		wapUserModel.setRoleID(USER_ROLE_ID);
            		wapVMNetworkModel.setId(TEST_VLAN_ID);
            		wapVMNetworkModel.setName(TEST_VLAN_NAME);
            		wapVMNetworkModel.setDescription(wapVMNetworkModel.getName());
            		wapVMNetworkModel.setStampId(DATACENTER_ID);
            		wapVMNetworkModel.setEnabled("true");
            		wapVMNetworkModel.setOwner(wapUserModel);
            		wapVMNetworksModel.setVirtualMachineNetworks(Arrays.asList(wapVMNetworkModel));
            		return mapFromModel(this.responseHandler, wapVMNetworksModel);
            	} else if (inv.getInvocationCount() == 2) {
	        		return new WAPVMNetworkGatewaysModel();
            	} else {
            		throw new RuntimeException("Invalid invocation count!");
            	}
            }
        };
		azurePackNetworkSupport.removeInternetGateway(TEST_VLAN_ID);
	}
	
	@Test
	public void isConnectedViaInternetGatewayShouldReturnCorrectResultIfConnect() throws CloudException, InternalException {
		
		final String USER_ROLE_ID = UUID.randomUUID().toString();
		final String INTERNET_GATEWAY_ID = UUID.randomUUID().toString();
		
		new MockUp<DaseinRequestExecutor>() {
			
			private ResponseHandler responseHandler;
			
			@Mock(invocations = 2)
            public void $init(Invocation inv, CloudProvider provider, HttpClientBuilder httpClientBuilder, HttpUriRequest httpUriRequest, ResponseHandler responseHandler) {
				if (inv.getInvocationCount() == 1) {
					assertGet(httpUriRequest, String.format(VM_NETWORKS, ENDPOINT, ACCOUNT_NO));
					this.responseHandler = responseHandler;
				} else if (inv.getInvocationCount() == 2) {
					assertGet(httpUriRequest, String.format(LIST_NET_GATEWAYS, ENDPOINT, ACCOUNT_NO, TEST_VLAN_ID, DATACENTER_ID).replace(" ", "%20"));
				}
			}
			
        	@Mock(invocations = 2)
            public Object execute(Invocation inv) {
            	if (inv.getInvocationCount() == 1) {
            		WAPVMNetworksModel wapVMNetworksModel = new WAPVMNetworksModel();
            		WAPVMNetworkModel wapVMNetworkModel = new WAPVMNetworkModel();
            		WAPUserModel wapUserModel = new WAPUserModel();
            		wapUserModel.setRoleID(USER_ROLE_ID);
            		wapVMNetworkModel.setId(TEST_VLAN_ID);
            		wapVMNetworkModel.setName(TEST_VLAN_NAME);
            		wapVMNetworkModel.setDescription(wapVMNetworkModel.getName());
            		wapVMNetworkModel.setStampId(DATACENTER_ID);
            		wapVMNetworkModel.setEnabled("true");
            		wapVMNetworkModel.setOwner(wapUserModel);
            		wapVMNetworksModel.setVirtualMachineNetworks(Arrays.asList(wapVMNetworkModel));
            		return mapFromModel(this.responseHandler, wapVMNetworksModel);
            	} else if (inv.getInvocationCount() == 2) {
            		WAPVMNetworkGatewaysModel wapVMNetworkGatewaysModel = new WAPVMNetworkGatewaysModel();
            		WAPVMNetworkGatewayModel wapVMNetworkGatewayModel = new WAPVMNetworkGatewayModel();
            		wapVMNetworkGatewaysModel.setGateways(Arrays.asList(wapVMNetworkGatewayModel));
            		return wapVMNetworkGatewaysModel;
            	} else {
            		throw new RuntimeException("Invalid invocation count!");
            	}
            }
        };
		assertTrue(azurePackNetworkSupport.isConnectedViaInternetGateway(TEST_VLAN_ID));
	}
	
	@Test
	public void isConnectedViaInternetGatewayShouldReturnCorrectResultIfNotConnect() throws CloudException, InternalException {
		
		final String USER_ROLE_ID = UUID.randomUUID().toString();
		
		new MockUp<DaseinRequestExecutor>() {
			
			private ResponseHandler responseHandler;
			
			@Mock(invocations = 2)
            public void $init(Invocation inv, CloudProvider provider, HttpClientBuilder httpClientBuilder, HttpUriRequest httpUriRequest, ResponseHandler responseHandler) {
				if (inv.getInvocationCount() == 1) {
					assertGet(httpUriRequest, String.format(VM_NETWORKS, ENDPOINT, ACCOUNT_NO));
					this.responseHandler = responseHandler;
				} else if (inv.getInvocationCount() == 2) {
					assertGet(httpUriRequest, String.format(LIST_NET_GATEWAYS, ENDPOINT, ACCOUNT_NO, TEST_VLAN_ID, DATACENTER_ID).replace(" ", "%20"));
				}
			}
			
        	@Mock(invocations = 2)
            public Object execute(Invocation inv) {
            	if (inv.getInvocationCount() == 1) {
            		WAPVMNetworksModel wapVMNetworksModel = new WAPVMNetworksModel();
            		WAPVMNetworkModel wapVMNetworkModel = new WAPVMNetworkModel();
            		WAPUserModel wapUserModel = new WAPUserModel();
            		wapUserModel.setRoleID(USER_ROLE_ID);
            		wapVMNetworkModel.setId(TEST_VLAN_ID);
            		wapVMNetworkModel.setName(TEST_VLAN_NAME);
            		wapVMNetworkModel.setDescription(wapVMNetworkModel.getName());
            		wapVMNetworkModel.setStampId(DATACENTER_ID);
            		wapVMNetworkModel.setEnabled("true");
            		wapVMNetworkModel.setOwner(wapUserModel);
            		wapVMNetworksModel.setVirtualMachineNetworks(Arrays.asList(wapVMNetworkModel));
            		return mapFromModel(this.responseHandler, wapVMNetworksModel);
            	} else if (inv.getInvocationCount() == 2) {
            		return new WAPVMNetworkGatewaysModel();
            	} else {
            		throw new RuntimeException("Invalid invocation count!");
            	}
            }
        };
		assertFalse(azurePackNetworkSupport.isConnectedViaInternetGateway(TEST_VLAN_ID));
	}
	
	@Test(expected = InternalException.class)
	public void isConnectedViaInternetGatewayShouldThrowExceptionIfNoVlanFound() throws CloudException, InternalException {
		new MockUp<DaseinRequestExecutor>() {
			@Mock(invocations = 1)
            public void $init(CloudProvider provider, HttpClientBuilder httpClientBuilder, HttpUriRequest httpUriRequest, ResponseHandler responseHandler) {
					assertGet(httpUriRequest, String.format(VM_NETWORKS, ENDPOINT, ACCOUNT_NO));
			}
        	@Mock(invocations = 1)
            public Object execute() {
            		return null;
            }
        };
		azurePackNetworkSupport.isConnectedViaInternetGateway(TEST_VLAN_ID);
	}
	
	@Test
	public void getAttachedInternetGatewayIdShouldReturnCorrectResult() throws CloudException, InternalException {
		
		final String USER_ROLE_ID = UUID.randomUUID().toString();
		final String INTERNET_GATEWAY_ID = UUID.randomUUID().toString();
		
		new MockUp<DaseinRequestExecutor>() {
			
			private ResponseHandler responseHandler;
			
			@Mock(invocations = 2)
            public void $init(Invocation inv, CloudProvider provider, HttpClientBuilder httpClientBuilder, HttpUriRequest httpUriRequest, ResponseHandler responseHandler) {
				if (inv.getInvocationCount() == 1) {
					assertGet(httpUriRequest, String.format(VM_NETWORKS, ENDPOINT, ACCOUNT_NO));
					this.responseHandler = responseHandler;
				} else if (inv.getInvocationCount() == 2) {
					assertGet(httpUriRequest, String.format(LIST_NET_GATEWAYS, ENDPOINT, ACCOUNT_NO, TEST_VLAN_ID, DATACENTER_ID).replace(" ", "%20"));
				}
			}
			
        	@Mock(invocations = 2)
            public Object execute(Invocation inv) {
            	if (inv.getInvocationCount() == 1) {
            		WAPVMNetworksModel wapVMNetworksModel = new WAPVMNetworksModel();
            		WAPVMNetworkModel wapVMNetworkModel = new WAPVMNetworkModel();
            		WAPUserModel wapUserModel = new WAPUserModel();
            		wapUserModel.setRoleID(USER_ROLE_ID);
            		wapVMNetworkModel.setId(TEST_VLAN_ID);
            		wapVMNetworkModel.setName(TEST_VLAN_NAME);
            		wapVMNetworkModel.setDescription(wapVMNetworkModel.getName());
            		wapVMNetworkModel.setStampId(DATACENTER_ID);
            		wapVMNetworkModel.setEnabled("true");
            		wapVMNetworkModel.setOwner(wapUserModel);
            		wapVMNetworksModel.setVirtualMachineNetworks(Arrays.asList(wapVMNetworkModel));
            		return mapFromModel(this.responseHandler, wapVMNetworksModel);
            	} else if (inv.getInvocationCount() == 2) {
            		WAPVMNetworkGatewaysModel wapVMNetworkGatewaysModel = new WAPVMNetworkGatewaysModel();
            		WAPVMNetworkGatewayModel wapVMNetworkGatewayModel = new WAPVMNetworkGatewayModel();
            		wapVMNetworkGatewayModel.setId(INTERNET_GATEWAY_ID);
            		wapVMNetworkGatewaysModel.setGateways(Arrays.asList(wapVMNetworkGatewayModel));
            		return wapVMNetworkGatewaysModel;
            	} else {
            		throw new RuntimeException("Invalid invocation count!");
            	}
            }
        };
		
		assertEquals(
				INTERNET_GATEWAY_ID,
				azurePackNetworkSupport.getAttachedInternetGatewayId(TEST_VLAN_ID));
	}
	
	@Test(expected = InternalException.class)
	public void getAttachedInternetGatewayIdShouldThrowExceptionIfNoVlanFound() throws CloudException, InternalException {
		
		new MockUp<DaseinRequestExecutor>() {
			@Mock(invocations = 1)
            public void $init(CloudProvider provider, HttpClientBuilder httpClientBuilder, HttpUriRequest httpUriRequest, ResponseHandler responseHandler) {
				assertGet(httpUriRequest, String.format(VM_NETWORKS, ENDPOINT, ACCOUNT_NO));
			}
        	@Mock(invocations = 1)
            public Object execute() {
        		return null;
            }
        };
		azurePackNetworkSupport.getAttachedInternetGatewayId(TEST_VLAN_ID);
	}
	
	@Test
	public void getAttachedInternetGatewayIdShouldReturnCorrectResultIfNoGatewayFound() throws CloudException, InternalException {
		
		final String USER_ROLE_ID = UUID.randomUUID().toString();
		
		new MockUp<DaseinRequestExecutor>() {
			
			private ResponseHandler responseHandler;
			
			@Mock(invocations = 2)
            public void $init(Invocation inv, CloudProvider provider, HttpClientBuilder httpClientBuilder, HttpUriRequest httpUriRequest, ResponseHandler responseHandler) {
				if (inv.getInvocationCount() == 1) {
					assertGet(httpUriRequest, String.format(VM_NETWORKS, ENDPOINT, ACCOUNT_NO));
					this.responseHandler = responseHandler;
				} else if (inv.getInvocationCount() == 2) {
					assertGet(httpUriRequest, String.format(LIST_NET_GATEWAYS, ENDPOINT, ACCOUNT_NO, TEST_VLAN_ID, DATACENTER_ID).replace(" ", "%20"));
				}
			}
			
        	@Mock(invocations = 2)
            public Object execute(Invocation inv) {
            	if (inv.getInvocationCount() == 1) {
            		WAPVMNetworksModel wapVMNetworksModel = new WAPVMNetworksModel();
            		WAPVMNetworkModel wapVMNetworkModel = new WAPVMNetworkModel();
            		WAPUserModel wapUserModel = new WAPUserModel();
            		wapUserModel.setRoleID(USER_ROLE_ID);
            		wapVMNetworkModel.setId(TEST_VLAN_ID);
            		wapVMNetworkModel.setName(TEST_VLAN_NAME);
            		wapVMNetworkModel.setDescription(wapVMNetworkModel.getName());
            		wapVMNetworkModel.setStampId(DATACENTER_ID);
            		wapVMNetworkModel.setEnabled("true");
            		wapVMNetworkModel.setOwner(wapUserModel);
            		wapVMNetworksModel.setVirtualMachineNetworks(Arrays.asList(wapVMNetworkModel));
            		return mapFromModel(this.responseHandler, wapVMNetworksModel);
            	} else if (inv.getInvocationCount() == 2) {
            		return new WAPVMNetworkGatewaysModel();
            	} else {
            		throw new RuntimeException("Invalid invocation count!");
            	}
            }
        };
		
		assertNull(azurePackNetworkSupport.getAttachedInternetGatewayId(TEST_VLAN_ID));
	}
	
	@Test
	public void getInternetGatewayByIdShouldReturnCorrectResult() throws CloudException, InternalException {
		
		final String INTERNET_GATEWAY_ID = UUID.randomUUID().toString();
		
		new MockUp<DaseinRequestExecutor>() {
			@Mock(invocations = 1)
            public void $init(CloudProvider provider, HttpClientBuilder httpClientBuilder, HttpUriRequest httpUriRequest, ResponseHandler responseHandler) {
				assertGet(httpUriRequest, String.format(VM_NET_GATEWAY, ENDPOINT, ACCOUNT_NO, INTERNET_GATEWAY_ID, DATACENTER_ID));
			}
        	@Mock(invocations = 1)
            public Object execute() {
        		WAPVMNetworkGatewayModel wapVMNetworkGatewayModel = new WAPVMNetworkGatewayModel();
        		wapVMNetworkGatewayModel.setId(INTERNET_GATEWAY_ID);
        		wapVMNetworkGatewayModel.setVmNetworkId(TEST_VLAN_ID);
        		return wapVMNetworkGatewayModel;
            }
        };
		
        InternetGateway expectedResult = new InternetGateway();
        expectedResult.setProviderInternetGatewayId(INTERNET_GATEWAY_ID);
        expectedResult.setProviderVlanId(TEST_VLAN_ID);
        expectedResult.setProviderRegionId(REGION);
        expectedResult.setProviderOwnerId(ACCOUNT_NO);
        assertReflectionEquals(
        		expectedResult,
        		azurePackNetworkSupport.getInternetGatewayById(INTERNET_GATEWAY_ID));
	}
	
	@Test(expected = InternalException.class)
	public void getInternetGatewayByIdShouldThrowExceptionIfGatewayIdIsNull() throws CloudException, InternalException {
		azurePackNetworkSupport.getInternetGatewayById(null);
	}
	
	@Test
	public void getInternetGatewayByIdShouldReturnCorrectResultIfNoGatewayFound() throws CloudException, InternalException {
		
		final String INTERNET_GATEWAY_ID = UUID.randomUUID().toString();
		
		new MockUp<DaseinRequestExecutor>() {
			@Mock(invocations = 1)
            public void $init(CloudProvider provider, HttpClientBuilder httpClientBuilder, HttpUriRequest httpUriRequest, ResponseHandler responseHandler) {
				assertGet(httpUriRequest, String.format(VM_NET_GATEWAY, ENDPOINT, ACCOUNT_NO, INTERNET_GATEWAY_ID, DATACENTER_ID));
			}
        	@Mock(invocations = 1)
            public Object execute() {
        		return null;
            }
        };
        assertNull(azurePackNetworkSupport.getInternetGatewayById(INTERNET_GATEWAY_ID));
	}
	
	@Test
	public void getInternetGatewayByIdShouldReturnCorrectResultIfNoGatewayFoundOnServer() throws CloudException, InternalException {
		
		final String INTERNET_GATEWAY_ID = UUID.randomUUID().toString();
		
		new MockUp<DaseinRequestExecutor>() {
			@Mock(invocations = 1)
            public void $init(CloudProvider provider, HttpClientBuilder httpClientBuilder, HttpUriRequest httpUriRequest, ResponseHandler responseHandler) {
				assertGet(httpUriRequest, String.format(VM_NET_GATEWAY, ENDPOINT, ACCOUNT_NO, INTERNET_GATEWAY_ID, DATACENTER_ID));
			}
        	@Mock(invocations = 1)
            public Object execute() throws CloudException {
        		throw new CloudException(CloudErrorType.GENERAL, 500, "Not found", "Gateway Not Found");
            }
        };
        assertNull(azurePackNetworkSupport.getInternetGatewayById(INTERNET_GATEWAY_ID));
	}
	
	@Test
	public void getProviderTermForNetworkInterfaceShouldReturnCorrectResult() {
		assertEquals("Netwrok Adapter", azurePackNetworkSupport.getProviderTermForNetworkInterface(null));
	}
	
	@Test
	public void getProviderTermForSubnetShouldReturnCorrectResult() {
		assertEquals("Subnet", azurePackNetworkSupport.getProviderTermForSubnet(null));
	}
	
	@Test
	public void getProviderTermForVlanShouldReturnCorrectResult() {
		assertEquals("Virtual Machine Network", azurePackNetworkSupport.getProviderTermForVlan(null));
	}
	
	@Test
	public void isSubscribedShouldReturnCorrectResult() throws CloudException, InternalException {
		assertTrue(azurePackNetworkSupport.isSubscribed());
	}
}
