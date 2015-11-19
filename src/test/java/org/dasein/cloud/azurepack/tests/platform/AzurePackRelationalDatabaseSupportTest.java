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

package org.dasein.cloud.azurepack.tests.platform;

import static org.dasein.cloud.azurepack.tests.HttpMethodAsserts.*;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import junit.framework.AssertionFailedError;
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
import org.dasein.cloud.azurepack.platform.AzurePackDatabaseCapabilities;
import org.dasein.cloud.azurepack.platform.AzurePackDatabaseSupport;
import org.dasein.cloud.azurepack.platform.model.WAPDatabaseModel;
import org.dasein.cloud.azurepack.tests.AzurePackTestsBaseWithLocation;
import org.dasein.cloud.platform.Database;
import org.dasein.cloud.platform.DatabaseEngine;
import org.dasein.cloud.platform.DatabaseLicenseModel;
import org.dasein.cloud.platform.DatabaseProduct;
import org.dasein.cloud.platform.RelationalDatabaseCapabilities;
import org.dasein.cloud.util.requester.DaseinParallelRequestExecutor;
import org.dasein.cloud.util.requester.DaseinRequestExecutor;
import org.dasein.cloud.util.requester.entities.DaseinObjectToJsonEntity;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Jane Wang on 11/03/2015.
 *
 * @author Jane Wang
 * @since 2016.02.1
 */
public class AzurePackRelationalDatabaseSupportTest extends AzurePackTestsBaseWithLocation {
	
	private final String MYSQL_DBS = "%s/%s/services/mysqlservers/databases";
	private final String MSSQL_DBS = "%s/%s/services/sqlservers/databases";
	private final String MSSQL_DB = "%s/%s/services/sqlservers/databases/%s";
    private final String MYSQL_DB = "%s/%s/services/mysqlservers/databases/%s";
	
	private AzurePackDatabaseSupport support;
	
	private static final String DATA_SOURCE_NAME = "TESTDSNAME";
	
	@Before
	public void setUp() throws CloudException, InternalException {
		super.setUp();
		new NonStrictExpectations(AzurePackDatabaseSupport.class) {
			{ AzurePackDatabaseSupport.hasMSSqlProvider(azurePackCloudMock); result = true; }
			{ AzurePackDatabaseSupport.hasMySqlProvider(azurePackCloudMock); result = true; }
		};
		support = AzurePackDatabaseSupport.getInstance(azurePackCloudMock);
	}
	
	@Test
	public void createMySQLFromScratchShouldPostWithCorrectRequest() throws CloudException, InternalException {
		
		final String MYSQL_SERVER_ID = "lxbn7i";
		final String MYSQL_SERVER_NAME = "MYSQLSERVERNAME";
		final String ADMIN_NAME = "TESTADMIN";
		final String ADMIN_PASSWORD = "TESTPASSWORD";
		
		new MockUp<DaseinRequestExecutor>() {
            
			@Mock(invocations = 1)
            public void $init(CloudProvider provider, HttpClientBuilder httpClientBuilder, HttpUriRequest httpUriRequest, ResponseHandler responseHandler) {
        		
				WAPDatabaseModel expectedDatabaseModel = new WAPDatabaseModel();
        		expectedDatabaseModel.setName(DATA_SOURCE_NAME);
        		expectedDatabaseModel.setSubscriptionId(provider.getContext().getAccountNumber());
        		expectedDatabaseModel.setAdminLogon(ADMIN_NAME);
        		expectedDatabaseModel.setPassword(ADMIN_PASSWORD);
        		expectedDatabaseModel.setCollation("latin1_swedish_ci");
        		expectedDatabaseModel.setMaxSizeMB("1024");
        		
        		HttpEntityEnclosingRequest httpEntityEnclosingRequest = (HttpEntityEnclosingRequest) httpUriRequest;
        		assertReflectionEquals(
						new DaseinObjectToJsonEntity<WAPDatabaseModel>(expectedDatabaseModel),
						httpEntityEnclosingRequest.getEntity());
        		assertPost(httpUriRequest, String.format(MYSQL_DBS, ENDPOINT, ACCOUNT_NO));
            }
			
            @Mock(invocations = 1)
            public Object execute() {
        		WAPDatabaseModel resultDatabaseModel = new WAPDatabaseModel();
        		resultDatabaseModel.setMySqlServerId(MYSQL_SERVER_ID);
        		resultDatabaseModel.setName(MYSQL_SERVER_NAME);
            	return resultDatabaseModel;
            }
        };
        
        DatabaseProduct product = new DatabaseProduct("10G");
		product.setName(DatabaseEngine.MYSQL.name());
		product.setEngine(DatabaseEngine.MYSQL);
		
        String expectedProviderDatabaseId = String.format("%s:%s", MYSQL_SERVER_ID, MYSQL_SERVER_NAME);
		assertEquals(
				expectedProviderDatabaseId,
				support.createFromScratch(DATA_SOURCE_NAME, product, null, ADMIN_NAME, ADMIN_PASSWORD, 0));
	}
	
	@Test
	public void createSQLServerFromScratchShouldPostWithCorrectRequest() throws CloudException, InternalException {
		
		final String SQL_SERVER_ID = "lxbn7i";
		final String SQL_SERVER_NAME = "SQLSERVERNAME";
		final String ADMIN_NAME = "TESTADMIN";
		final String ADMIN_PASSWORD = "TESTPASSWORD";
		
		new MockUp<DaseinRequestExecutor>() {
            
			@Mock(invocations = 1)
            public void $init(CloudProvider provider, HttpClientBuilder httpClientBuilder, HttpUriRequest httpUriRequest, ResponseHandler responseHandler) {
        		
				WAPDatabaseModel expectedDatabaseModel = new WAPDatabaseModel();
        		expectedDatabaseModel.setName(DATA_SOURCE_NAME);
        		expectedDatabaseModel.setSubscriptionId(provider.getContext().getAccountNumber());
        		expectedDatabaseModel.setAdminLogon(ADMIN_NAME);
        		expectedDatabaseModel.setPassword(ADMIN_PASSWORD);
        		expectedDatabaseModel.setCollation("SQL_Latin1_General_CP1_CI_AS");
        		expectedDatabaseModel.setIsContained("false");
        		expectedDatabaseModel.setBaseSizeMB("10");
        		expectedDatabaseModel.setMaxSizeMB("10");
        		
        		HttpEntityEnclosingRequest httpEntityEnclosingRequest = (HttpEntityEnclosingRequest) httpUriRequest;
        		assertReflectionEquals(
						new DaseinObjectToJsonEntity<WAPDatabaseModel>(expectedDatabaseModel),
						httpEntityEnclosingRequest.getEntity());
        		assertPost(httpUriRequest, String.format(MSSQL_DBS, ENDPOINT, ACCOUNT_NO));
            }
			
            @Mock(invocations = 1)
            public Object execute() {
        		WAPDatabaseModel resultDatabaseModel = new WAPDatabaseModel();
        		resultDatabaseModel.setSqlServerId(SQL_SERVER_ID);
        		resultDatabaseModel.setName(SQL_SERVER_NAME);
            	return resultDatabaseModel;
            }
        };
        
        DatabaseProduct product = new DatabaseProduct("10G");
		product.setName(DatabaseEngine.SQLSERVER_EE.name());
		product.setEngine(DatabaseEngine.SQLSERVER_EE);
        String expectedResult = String.format("%s:%s", SQL_SERVER_ID, SQL_SERVER_NAME);
		assertEquals(
				expectedResult,
				support.createFromScratch(DATA_SOURCE_NAME, product, null, ADMIN_NAME, ADMIN_PASSWORD, 0));
	}
	
	@Test(expected = InternalException.class)
	public void createFromScratchShouldThrowExceptionIfProductIsNull() throws CloudException, InternalException {
		support.createFromScratch(DATA_SOURCE_NAME, null, null, "TESTADMIN", "TESTPASSWORD", 0);
	}
	
	@Test
	public void getCapabilitiesShouldReturnCorrectResult() throws InternalException, CloudException {
		RelationalDatabaseCapabilities capabilities = support.getCapabilities();
		assertNotNull(capabilities);
		assertEquals(
				AzurePackDatabaseCapabilities.class, 
				capabilities.getClass());
	}
	
	@Test
	public void getDatabaseShouldReturnCorrectResult() throws CloudException, InternalException {
		
		final String SQL_SERVER_ID = "lxbn8e";
		final String SQL_SERVER_NAME = "SQLSERVERNAME";
		final String MYSQL_SERVER_ID = "lxbn7i";
		final String MYSQL_SERVER_NAME = "MYSQLSERVERNAME";
		
		new MockUp<DaseinParallelRequestExecutor>() {
            
			private ResponseHandler responseHandler;
			
			@Mock(invocations = 1)
            public void $init(CloudProvider provider, HttpClientBuilder httpClientBuilder, ArrayList<HttpUriRequest> httpUriRequests, ResponseHandler responseHandler) {
				assertGet(httpUriRequests.get(0), String.format(MSSQL_DBS, ENDPOINT, ACCOUNT_NO));
        		assertGet(httpUriRequests.get(1), String.format(MYSQL_DBS, ENDPOINT, ACCOUNT_NO));
        		this.responseHandler = responseHandler;
			}
			
            @Mock(invocations = 1)
            public List execute() throws CloudException {
        		WAPDatabaseModel resultMSDatabaseModel = new WAPDatabaseModel();
        		resultMSDatabaseModel.setSqlServerId(SQL_SERVER_ID);
        		resultMSDatabaseModel.setName(SQL_SERVER_NAME);
            	List<Database> msDatabaseModels = mapFromModel(responseHandler, new WAPDatabaseModel[]{resultMSDatabaseModel});
            	WAPDatabaseModel resultMYSQLDatabaseModel = new WAPDatabaseModel();
        		resultMYSQLDatabaseModel.setMySqlServerId(MYSQL_SERVER_ID);
        		resultMYSQLDatabaseModel.setName(MYSQL_SERVER_NAME);
        		List<Database> mysqlDatabaseModels = mapFromModel(responseHandler, new WAPDatabaseModel[]{resultMYSQLDatabaseModel});
        		return Arrays.asList(msDatabaseModels, mysqlDatabaseModels);
            }
        };
		
		assertReflectionEquals (
				generateDatabase(SQL_SERVER_ID, SQL_SERVER_NAME, DatabaseEngine.SQLSERVER_EE),
				support.getDatabase(String.format("%s:%s", SQL_SERVER_ID, SQL_SERVER_NAME)));
	}
	
	@Test
	public void getDatabaseEnginesShouldReturnCorrectResult() throws AssertionFailedError, CloudException, InternalException {
		assertReflectionEquals(
				Arrays.asList(DatabaseEngine.SQLSERVER_EE, DatabaseEngine.MYSQL),
				support.getDatabaseEngines());
	}
	
	@Test
	public void listDatabaseProductsForMYSqlShouldReturnCorrectResult() throws CloudException, InternalException {
		DatabaseProduct product = new DatabaseProduct("Default", "Default");
        product.setLicenseModel(DatabaseLicenseModel.LICENSE_INCLUDED);
        product.setEngine(DatabaseEngine.MYSQL);
        product.setProviderDataCenterId(DATACENTER_ID);
        assertReflectionEquals(
        		Arrays.asList(product),
        		support.listDatabaseProducts(DatabaseEngine.MYSQL));
	}
	
	@Test
	public void listDatabaseProductsForMSSqlShouldReturnCorrectResult() throws CloudException, InternalException {
		DatabaseProduct product = new DatabaseProduct("Default", "Default");
        product.setLicenseModel(DatabaseLicenseModel.LICENSE_INCLUDED);
        product.setEngine(DatabaseEngine.SQLSERVER_EE);
        product.setProviderDataCenterId(DATACENTER_ID);
        assertReflectionEquals(
        		Arrays.asList(product),
        		support.listDatabaseProducts(DatabaseEngine.SQLSERVER_EE));
	}
	
	@Test
	public void listDatabaseProductsForNotSupportedEngineShouldReturnCorrectResult() throws AssertionFailedError, CloudException, InternalException {
		assertReflectionEquals(
				Collections.emptyList(),
				support.listDatabaseProducts(DatabaseEngine.ORACLE_EE));
	}
	
	@Test(expected = InternalException.class)
	public void listDatabaseProductsShouldThrowExceptionIfEngineIsNull() throws CloudException, InternalException {
		support.listDatabaseProducts(null);
	}
	
	@Test
	public void listDatabasesShouldReturnCorrectResult() throws CloudException, InternalException {
		
		final String SQL_SERVER_ID = "lxbn8e";
		final String SQL_SERVER_NAME = "SQLSERVERNAME";
		final String MYSQL_SERVER_ID = "lxbn7i";
		final String MYSQL_SERVER_NAME = "MYSQLSERVERNAME";
		
		new MockUp<DaseinParallelRequestExecutor>() {
            
			private ResponseHandler responseHandler;
			
			@Mock(invocations = 1)
            public void $init(CloudProvider provider, HttpClientBuilder httpClientBuilder, ArrayList<HttpUriRequest> httpUriRequests, ResponseHandler responseHandler) {
				assertGet(httpUriRequests.get(0), String.format(MSSQL_DBS, ENDPOINT, ACCOUNT_NO));
        		assertGet(httpUriRequests.get(1), String.format(MYSQL_DBS, ENDPOINT, ACCOUNT_NO));
        		this.responseHandler = responseHandler;
			}
			
            @Mock(invocations = 1)
            public List execute() throws CloudException {
        		WAPDatabaseModel resultMSDatabaseModel = new WAPDatabaseModel();
        		resultMSDatabaseModel.setSqlServerId(SQL_SERVER_ID);
        		resultMSDatabaseModel.setName(SQL_SERVER_NAME);
            	List<Database> msDatabaseModels = mapFromModel(responseHandler, new WAPDatabaseModel[]{resultMSDatabaseModel});
            	WAPDatabaseModel resultMYSQLDatabaseModel = new WAPDatabaseModel();
        		resultMYSQLDatabaseModel.setMySqlServerId(MYSQL_SERVER_ID);
        		resultMYSQLDatabaseModel.setName(MYSQL_SERVER_NAME);
        		List<Database> mysqlDatabaseModels = mapFromModel(responseHandler, new WAPDatabaseModel[]{resultMYSQLDatabaseModel});
        		return Arrays.asList(msDatabaseModels, mysqlDatabaseModels);
            }
        };
		
        assertReflectionEquals(
        		Arrays.asList(
        				generateDatabase(SQL_SERVER_ID, SQL_SERVER_NAME, DatabaseEngine.SQLSERVER_EE), 
        				generateDatabase(MYSQL_SERVER_ID, MYSQL_SERVER_NAME, DatabaseEngine.MYSQL)),
        		support.listDatabases());
	}
	
	@Test
	public void removeMSSQLDatabaseShouldDeleteWithCorrectRequest() throws CloudException, InternalException {
		
		final String SQL_SERVER_ID = "lxbn8e";
		final String SQL_SERVER_NAME = "SQLSERVERNAME";
		final String MYSQL_SERVER_ID = "lxbn7i";
		final String MYSQL_SERVER_NAME = "MYSQLSERVERNAME";
		
		new MockUp<DaseinParallelRequestExecutor>() {
            
			private ResponseHandler responseHandler;
			
			@Mock(invocations = 1)
            public void $init(CloudProvider provider, HttpClientBuilder httpClientBuilder, ArrayList<HttpUriRequest> httpUriRequests, ResponseHandler responseHandler) {
				assertGet(httpUriRequests.get(0), String.format(MSSQL_DBS, ENDPOINT, ACCOUNT_NO));
        		assertGet(httpUriRequests.get(1), String.format(MYSQL_DBS, ENDPOINT, ACCOUNT_NO));
        		this.responseHandler = responseHandler;
			}
			
            @Mock(invocations = 1)
            public List execute() throws CloudException {
        		WAPDatabaseModel resultMSDatabaseModel = new WAPDatabaseModel();
        		resultMSDatabaseModel.setSqlServerId(SQL_SERVER_ID);
        		resultMSDatabaseModel.setName(SQL_SERVER_NAME);
            	List<Database> msDatabaseModels = mapFromModel(responseHandler, new WAPDatabaseModel[]{resultMSDatabaseModel});
            	WAPDatabaseModel resultMYSQLDatabaseModel = new WAPDatabaseModel();
        		resultMYSQLDatabaseModel.setMySqlServerId(MYSQL_SERVER_ID);
        		resultMYSQLDatabaseModel.setName(MYSQL_SERVER_NAME);
        		List<Database> mysqlDatabaseModels = mapFromModel(responseHandler, new WAPDatabaseModel[]{resultMYSQLDatabaseModel});
        		return Arrays.asList(msDatabaseModels, mysqlDatabaseModels);
            }
        };
        
        new MockUp<DaseinRequestExecutor>() {
        	
        	@Mock(invocations = 1)
        	public void $init(CloudProvider provider, HttpClientBuilder httpClientBuilder, HttpUriRequest httpUriRequest, ResponseHandler responseHandler) {
        		assertDelete(httpUriRequest, String.format(MSSQL_DB, ENDPOINT, ACCOUNT_NO, SQL_SERVER_NAME));
        	}
        	
        	@Mock(invocations = 1)
        	public Object execute() throws CloudException {
        		return null;
        	}
        	
        };
		
		support.removeDatabase(String.format("%s:%s", SQL_SERVER_ID, SQL_SERVER_NAME));
	}
	
	@Test
	public void removeMYSQLDatabaseShouldDeleteWithCorrectRequest() throws CloudException, InternalException {
		
		final String SQL_SERVER_ID = "lxbn8e";
		final String SQL_SERVER_NAME = "SQLSERVERNAME";
		final String MYSQL_SERVER_ID = "lxbn7i";
		final String MYSQL_SERVER_NAME = "MYSQLSERVERNAME";
		
		new MockUp<DaseinParallelRequestExecutor>() {
            
			private ResponseHandler responseHandler;
			
			@Mock(invocations = 1)
            public void $init(CloudProvider provider, HttpClientBuilder httpClientBuilder, ArrayList<HttpUriRequest> httpUriRequests, ResponseHandler responseHandler) {
				assertGet(httpUriRequests.get(0), String.format(MSSQL_DBS, ENDPOINT, ACCOUNT_NO));
        		assertGet(httpUriRequests.get(1), String.format(MYSQL_DBS, ENDPOINT, ACCOUNT_NO));
        		this.responseHandler = responseHandler;
			}
			
            @Mock(invocations = 1)
            public List execute() throws CloudException {
        		WAPDatabaseModel resultMSDatabaseModel = new WAPDatabaseModel();
        		resultMSDatabaseModel.setSqlServerId(SQL_SERVER_ID);
        		resultMSDatabaseModel.setName(SQL_SERVER_NAME);
            	List<Database> msDatabaseModels = mapFromModel(responseHandler, new WAPDatabaseModel[]{resultMSDatabaseModel});
            	WAPDatabaseModel resultMYSQLDatabaseModel = new WAPDatabaseModel();
        		resultMYSQLDatabaseModel.setMySqlServerId(MYSQL_SERVER_ID);
        		resultMYSQLDatabaseModel.setName(MYSQL_SERVER_NAME);
        		List<Database> mysqlDatabaseModels = mapFromModel(responseHandler, new WAPDatabaseModel[]{resultMYSQLDatabaseModel});
        		return Arrays.asList(msDatabaseModels, mysqlDatabaseModels);
            }
        };
        
        new MockUp<DaseinRequestExecutor>() {
        	
        	@Mock(invocations = 1)
        	public void $init(CloudProvider provider, HttpClientBuilder httpClientBuilder, HttpUriRequest httpUriRequest, ResponseHandler responseHandler) {
        		assertDelete(httpUriRequest, String.format(MYSQL_DB, ENDPOINT, ACCOUNT_NO, MYSQL_SERVER_NAME));
        	}
        	
        	@Mock(invocations = 1)
        	public Object execute() throws CloudException {
        		return null;
        	}
        	
        };
		
		support.removeDatabase(String.format("%s:%s", MYSQL_SERVER_ID, MYSQL_SERVER_NAME));
	}
	
	@Test(expected = InternalException.class)
	public void removeDatabaseShouldThrowExceptionIfDatabaseIdIsNull() throws CloudException, InternalException {
		support.removeDatabase(null);
	}
	
	@Test(expected = InternalException.class)
	public void removeDatabaseShouldThrowExceptionIfNoDatabaseFound() throws CloudException, InternalException {
		
		final String SQL_SERVER_ID = "lxbn8e";
		final String SQL_SERVER_NAME = "SQLSERVERNAME";
		
		new MockUp<DaseinParallelRequestExecutor>() {
            
			private ResponseHandler responseHandler;
			
			@Mock(invocations = 1)
            public void $init(CloudProvider provider, HttpClientBuilder httpClientBuilder, ArrayList<HttpUriRequest> httpUriRequests, ResponseHandler responseHandler) {
				assertGet(httpUriRequests.get(0), String.format(MSSQL_DBS, ENDPOINT, ACCOUNT_NO));
        		assertGet(httpUriRequests.get(1), String.format(MYSQL_DBS, ENDPOINT, ACCOUNT_NO));
        		this.responseHandler = responseHandler;
			}
			
            @Mock(invocations = 1)
            public List execute() throws CloudException {
        		return Collections.emptyList();
            }
        };
        
		support.removeDatabase(String.format("%s:%s", SQL_SERVER_ID, SQL_SERVER_NAME));
	}
	
	private Database generateDatabase(String id, String name, DatabaseEngine engine) {
		Database database = new Database();
		database.setName(name);
		database.setProviderDatabaseId(String.format("%s:%s", id, name));
		database.setEngine(engine);
		return database;
	}
	
}
