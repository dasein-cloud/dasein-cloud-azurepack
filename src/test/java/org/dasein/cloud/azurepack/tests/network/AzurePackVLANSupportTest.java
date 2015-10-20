package org.dasein.cloud.azurepack.tests.network;

import static org.dasein.cloud.azurepack.tests.HttpMethodAsserts.*;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;
import java.util.Arrays;
import java.util.UUID;
import mockit.Invocation;
import mockit.Mock;
import mockit.MockUp;
import mockit.NonStrictExpectations;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.dasein.cloud.CloudException;
import org.dasein.cloud.CloudProvider;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.azurepack.model.WAPUserModel;
import org.dasein.cloud.azurepack.network.AzurePackNetworkServices;
import org.dasein.cloud.azurepack.network.model.WAPSubnetModel;
import org.dasein.cloud.azurepack.network.model.WAPSubnetsModel;
import org.dasein.cloud.azurepack.network.model.WAPVMNetworkModel;
import org.dasein.cloud.azurepack.network.model.WAPVMNetworksModel;
import org.dasein.cloud.azurepack.tests.AzurePackTestsBaseWithLocation;
import org.dasein.cloud.network.Subnet;
import org.dasein.cloud.network.SubnetCreateOptions;
import org.dasein.cloud.network.SubnetState;
import org.dasein.cloud.network.VLANSupport;
import org.dasein.cloud.util.requester.DaseinRequestExecutor;
import org.dasein.cloud.util.requester.entities.DaseinObjectToJsonEntity;
import org.junit.Before;
import org.junit.Test;

public class AzurePackVLANSupportTest extends AzurePackTestsBaseWithLocation {

	private final String VM_NETWORKS = "%s/%s/services/systemcenter/vmm/VMNetworks";
	private final String VM_SUBNETS = "%s/%s/services/systemcenter/vmm/VMSubnets";
	private final String LIST_VM_SUBNETS = "%s/%s/services/systemcenter/vmm/VMSubnets()?$filter=StampId eq guid'%s'";
	private final String VM_SUBNET = "%s/%s/services/systemcenter/vmm/VMSubnets(ID=Guid'%s',StampId=Guid'%s')";
	
	private VLANSupport azurePackNetworkSupport;
	
	private final String VLAN_ENDPOINT = "https://endpoint.com:443";
	private final String TEST_VLAN_ID = UUID.randomUUID().toString();
	private final String TEST_VLAN_NAME = "TESTVLAN";
	private final String TEST_SUBNET_ID = UUID.randomUUID().toString();
	private final String TEST_SUBNET_NAME = "TESTSUBNET";
	
	@Before
	public void setUp() throws CloudException, InternalException {
		
		super.setUp();
		
		new NonStrictExpectations() {
			{ 
				providerContextMock.getEndpoint(); result = VLAN_ENDPOINT; 
				cloudMock.getEndpoint(); result = VLAN_ENDPOINT;
			}
		};
		
		final AzurePackNetworkServices azurePackNetworkServices = new AzurePackNetworkServices(azurePackCloudMock);
		azurePackNetworkSupport = azurePackNetworkServices.getVlanSupport();
	}
	
	@Test
	public void createSubnetShouldPostWithCorrectRequest() throws CloudException, InternalException {
		
		final SubnetCreateOptions options = SubnetCreateOptions.getInstance(TEST_VLAN_ID, REGION, "10.30.16.1/16", TEST_SUBNET_NAME, TEST_SUBNET_NAME);
		
		new MockUp<DaseinRequestExecutor>() {
            
			private ResponseHandler responseHandler;
			
			@Mock(invocations = 2)
            public void $init(Invocation inv, CloudProvider provider, HttpClientBuilder httpClientBuilder, HttpUriRequest httpUriRequest, ResponseHandler responseHandler) {
				if (inv.getInvocationCount() == 1) {
					assertGet(httpUriRequest, String.format(VM_NETWORKS, VLAN_ENDPOINT, ACCOUNT_NO));
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
            		assertPost(httpUriRequest, String.format(VM_SUBNETS, VLAN_ENDPOINT, ACCOUNT_NO));
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
            		wapVMNetworkModel.setStampId(REGION);
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
		SubnetCreateOptions options = SubnetCreateOptions.getInstance(TEST_VLAN_ID, "10.30.16.1/16", TEST_VLAN_NAME, TEST_VLAN_NAME);
		azurePackNetworkSupport.createSubnet(options);
	}
	
	@Test
	public void listSubnetsShouldReturnCorrectResult() throws CloudException, InternalException {

		new MockUp<DaseinRequestExecutor>() {
            
			private ResponseHandler responseHandler;
			
			@Mock(invocations = 1)
            public void $init(CloudProvider provider, HttpClientBuilder httpClientBuilder, HttpUriRequest httpUriRequest, ResponseHandler responseHandler) {
				assertGet(httpUriRequest, String.format(LIST_VM_SUBNETS, VLAN_ENDPOINT, ACCOUNT_NO, REGION).replace(" ", "%20"));
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
		
		new MockUp<DaseinRequestExecutor>() {
            
			@Mock(invocations = 2)
            public void $init(Invocation inv, CloudProvider provider, HttpClientBuilder httpClientBuilder, HttpUriRequest httpUriRequest, ResponseHandler responseHandler) {
				if (inv.getInvocationCount() == 1) {
					assertGet(httpUriRequest, String.format(LIST_VM_SUBNETS, VLAN_ENDPOINT, ACCOUNT_NO, REGION).replace(" ", "%20"));
				} else if (inv.getInvocationCount() == 2) {
					assertDelete(httpUriRequest, String.format(VM_SUBNET, VLAN_ENDPOINT, ACCOUNT_NO, TEST_SUBNET_ID, REGION));
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
	            	wapSubnetModel.setStampId(REGION);
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
		
		new MockUp<DaseinRequestExecutor>() {
			@Mock(invocations = 1)
            public void $init(Invocation inv, CloudProvider provider, HttpClientBuilder httpClientBuilder, HttpUriRequest httpUriRequest, ResponseHandler responseHandler) {
				assertGet(httpUriRequest, String.format(LIST_VM_SUBNETS, VLAN_ENDPOINT, ACCOUNT_NO, REGION).replace(" ", "%20"));
            }
            @Mock(invocations = 1)
            public Object execute() {
	            return new WAPSubnetsModel();
            }
        };
		
		azurePackNetworkSupport.removeSubnet(TEST_SUBNET_ID);
	}
	
}
