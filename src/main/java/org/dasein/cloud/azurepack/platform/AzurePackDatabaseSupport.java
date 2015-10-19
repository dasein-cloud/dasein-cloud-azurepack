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

package org.dasein.cloud.azurepack.platform;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.collections.Predicate;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.dasein.cloud.*;
import org.dasein.cloud.azurepack.AzurePackCloud;
import org.dasein.cloud.azurepack.platform.model.WAPDatabaseModel;
import org.dasein.cloud.azurepack.utils.AzurePackRequester;
import org.dasein.cloud.dc.DataCenter;
import org.dasein.cloud.identity.ServiceAction;
import org.dasein.cloud.platform.*;
import org.dasein.cloud.util.requester.DriverToCoreMapper;
import org.dasein.cloud.util.requester.fluent.DaseinParallelRequest;
import org.dasein.cloud.util.requester.fluent.DaseinRequest;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by vmunthiu on 6/16/2015.
 */
public class AzurePackDatabaseSupport implements RelationalDatabaseSupport {
    private AzurePackCloud provider;
    private boolean hasMSSql;
    private boolean hasMySql;

    private static HashMap<String, AzurePackDatabaseSupport> instances = null;

    public static AzurePackDatabaseSupport getInstance(AzurePackCloud provider) {
        if(instances == null)
            synchronized (AzurePackDatabaseSupport.class) {
                if (instances == null) {
                    instances = new HashMap<String, AzurePackDatabaseSupport>();
                }
            }

        if(!instances.containsKey(provider.getContext().getCloud().getEndpoint())) {
            synchronized (AzurePackDatabaseSupport.class) {
                if(!instances.containsKey(provider.getContext().getCloud().getEndpoint())) {
                    boolean hasMSSqlSupport = AzurePackDatabaseSupport.hasMSSqlProvider(provider);
                    boolean hasMySqlSupport = AzurePackDatabaseSupport.hasMySqlProvider(provider);
                    if (hasMSSqlSupport || hasMySqlSupport){
                        instances.put(provider.getContext().getCloud().getEndpoint(), new AzurePackDatabaseSupport(provider, hasMSSqlSupport, hasMySqlSupport));
                    } else {
                        instances.put(provider.getContext().getCloud().getEndpoint(), null);
                    }
                }
            }
        }

        return instances.get(provider.getContext().getCloud().getEndpoint());
    }

    public AzurePackDatabaseSupport(AzurePackCloud provider) {
        this.provider = provider;
        this.hasMSSql = hasMSSqlProvider(provider);
        this.hasMySql = hasMySqlProvider(provider);
    }

    private AzurePackDatabaseSupport(AzurePackCloud provider, boolean hasMSSql, boolean hasMySql) {
        this.provider = provider;
        this.hasMSSql = hasMSSql;
        this.hasMySql = hasMySql;
    }

    public static boolean hasMSSqlProvider(AzurePackCloud provider) {
        return hasResourceProvider(provider, DatabaseEngine.SQLSERVER_EE);
    }

    public static boolean hasMySqlProvider(AzurePackCloud provider) {
        return hasResourceProvider(provider, DatabaseEngine.MYSQL);
    }

    private static boolean hasResourceProvider(AzurePackCloud provider, DatabaseEngine databaseEngine) {
        try {
            HttpUriRequest httpUriRequest = new AzurePackDatabaseRequests(provider).listMSSQLDBS().build();
            if(databaseEngine == DatabaseEngine.MYSQL)
                httpUriRequest =  new AzurePackDatabaseRequests(provider).listMYSQLDBS().build();

            new DaseinRequest(provider, provider.getAzureClientBuilder(), httpUriRequest).execute();
            return true;
        } catch (CloudException ex){
            if(ex.getErrorType() == CloudErrorType.GENERAL && ex.getHttpCode() == HttpStatus.SC_FORBIDDEN){
                return false;
            } else {
                return true;
            }
        } catch (Exception ex) {
            return true;
        }
    }

    @Override
    public void addAccess(String providerDatabaseId, String sourceCidr) throws CloudException, InternalException {

    }

    @Override
    public void alterDatabase(String providerDatabaseId, boolean applyImmediately, String productSize, int storageInGigabytes, String configurationId, String newAdminUser, String newAdminPassword, int newPort, int snapshotRetentionInDays, TimeWindow preferredMaintenanceWindow, TimeWindow preferredBackupWindow) throws CloudException, InternalException {

    }

    @Nonnull
    @Override
    public String createFromScratch(String dataSourceName, DatabaseProduct product, String databaseVersion, String withAdminUser, String withAdminPassword, int hostPort) throws CloudException, InternalException {
        if(product == null && product.getName() == null){
            throw new InternalException("Cannot create database. Database product or database product name cannot be empty");
        }

        WAPDatabaseModel databaseModel = new WAPDatabaseModel();
        databaseModel.setName(dataSourceName);
        databaseModel.setSubscriptionId(provider.getContext().getAccountNumber());
        databaseModel.setAdminLogon(withAdminUser);
        databaseModel.setPassword(withAdminPassword);

        HttpUriRequest requestBuilder = null;
        if(product.getEngine() == DatabaseEngine.MYSQL) {
            databaseModel.setCollation("latin1_swedish_ci");
            databaseModel.setMaxSizeMB("1024");
            requestBuilder = new AzurePackDatabaseRequests(provider).createMYSQLDb(databaseModel).build();
        } else {
            databaseModel.setCollation("SQL_Latin1_General_CP1_CI_AS");
            databaseModel.setIsContained("false");
            databaseModel.setBaseSizeMB("10");
            databaseModel.setMaxSizeMB("10");
            requestBuilder = new AzurePackDatabaseRequests(provider).createMSSQLDb(databaseModel).build();
        }

        WAPDatabaseModel result = new AzurePackRequester(provider, requestBuilder).withJsonProcessor(WAPDatabaseModel.class).execute();

        return databaseFrom(result).getProviderDatabaseId();
    }

    @Nonnull
    @Override
    public String createFromLatest(String dataSourceName, String providerDatabaseId, String productSize, String providerDataCenterId, int hostPort) throws InternalException, CloudException {
        return null;
    }

    @Nonnull
    @Override
    public String createFromSnapshot(String dataSourceName, String providerDatabaseId, String providerDbSnapshotId, String productSize, String providerDataCenterId, int hostPort) throws CloudException, InternalException {
        return null;
    }

    @Nonnull
    @Override
    public String createFromTimestamp(String dataSourceName, String providerDatabaseId, long beforeTimestamp, String productSize, String providerDataCenterId, int hostPort) throws InternalException, CloudException {
        return null;
    }

    @Nonnull
    @Override
    public RelationalDatabaseCapabilities getCapabilities() throws InternalException, CloudException {
        return new AzurePackDatabaseCapabilities();
    }

    @Nullable
    @Override
    public DatabaseConfiguration getConfiguration(String providerConfigurationId) throws CloudException, InternalException {
        return null;
    }

    @Nullable
    @Override
    public Database getDatabase(final String providerDatabaseId) throws CloudException, InternalException {
        ArrayList<Database> allDatabases = getAllDatabases();

        return (Database)CollectionUtils.find(allDatabases, new Predicate() {
            @Override
            public boolean evaluate(Object object) {
                return ((Database)object).getProviderDatabaseId().equalsIgnoreCase(providerDatabaseId);
            }
        });
    }

    @Nonnull
    @Override
    public Iterable<DatabaseEngine> getDatabaseEngines() throws CloudException, InternalException {
        ArrayList<DatabaseEngine> availableEngines = new ArrayList<DatabaseEngine>();
        if(hasMSSql)
            availableEngines.add(DatabaseEngine.SQLSERVER_EE);

        if(hasMySql)
            availableEngines.add(DatabaseEngine.MYSQL);

        return availableEngines;
    }

    @Nullable
    @Override
    public String getDefaultVersion(@Nonnull DatabaseEngine forEngine) throws CloudException, InternalException {
        return null;
    }

    @Nonnull
    @Override
    public Iterable<String> getSupportedVersions(@Nonnull DatabaseEngine forEngine) throws CloudException, InternalException {
        return null;
    }

    @Nonnull
    @Override
    public Iterable<DatabaseProduct> listDatabaseProducts(@Nonnull DatabaseEngine forEngine) throws CloudException, InternalException {
        if(forEngine == null)
            throw new InternalException("Please specify the DatabaseEngine for which you want to retrieve the products.");

        if(forEngine != DatabaseEngine.SQLSERVER_EE && forEngine != DatabaseEngine.MYSQL)
            return Arrays.asList();

        if(forEngine == DatabaseEngine.SQLSERVER_EE && !hasMSSql)
            return Arrays.asList();

        if(forEngine == DatabaseEngine.MYSQL && !hasMySql)
            return Arrays.asList();

        List<DataCenter> dataCenters = new ArrayList(IteratorUtils.toList(this.provider.getDataCenterServices().listDataCenters(this.provider.getContext().getRegionId()).iterator()));
        String dataCenterId = dataCenters.get(0).getProviderDataCenterId();

        DatabaseProduct product = new DatabaseProduct("Default", "Default");
        product.setLicenseModel(DatabaseLicenseModel.LICENSE_INCLUDED);
        product.setEngine(forEngine);
        product.setProviderDataCenterId(dataCenterId);
        return Arrays.asList(product);
    }

    @Nullable
    @Override
    public DatabaseSnapshot getSnapshot(String providerDbSnapshotId) throws CloudException, InternalException {
        return null;
    }

    @Override
    public boolean isSubscribed() throws CloudException, InternalException {
        return true;
    }

    @Nonnull
    @Override
    public Iterable<String> listAccess(String toProviderDatabaseId) throws CloudException, InternalException {
        return null;
    }

    @Nonnull
    @Override
    public Iterable<DatabaseConfiguration> listConfigurations() throws CloudException, InternalException {
        return null;
    }

    @Nonnull
    @Override
    public Iterable<ResourceStatus> listDatabaseStatus() throws CloudException, InternalException {
        return null;
    }

    @Nonnull
    @Override
    public Iterable<Database> listDatabases() throws CloudException, InternalException {
        return getAllDatabases();
    }

    private ArrayList<Database> getAllDatabases() throws InternalException, CloudException {
        ArrayList<HttpUriRequest> requests = new ArrayList<HttpUriRequest>();
        if(hasMSSql)
            requests.add(new AzurePackDatabaseRequests(this.provider).listMSSQLDBS().build());

        if(hasMySql)
            requests.add(new AzurePackDatabaseRequests(this.provider).listMYSQLDBS().build());

        List<List<Database>> databasesList = new DaseinParallelRequest(this.provider, this.provider.getAzureClientBuilder(), requests).withJsonProcessor(new DriverToCoreMapper<WAPDatabaseModel[], List<Database>>() {
            @Override
            public List<Database> mapFrom(WAPDatabaseModel[] entity) {
                ArrayList<Database> dbs = new ArrayList<Database>();
                for (int i =0; i<entity.length; i++){
                    dbs.add(databaseFrom(entity[i]));
                }
                return dbs;
            }
        }, WAPDatabaseModel[].class).execute();

        ArrayList<Database> results = new ArrayList<Database>();
        for (List<Database> listResult : databasesList)
            results.addAll(listResult);

        return results;
    }

    private Database databaseFrom(WAPDatabaseModel wapDatabaseModel) {
        if(wapDatabaseModel == null)
            return null;

        String providerDbId = null;
        DatabaseEngine databaseEngine = null;
        if(wapDatabaseModel.getMySqlServerId() != null) {
            databaseEngine = DatabaseEngine.MYSQL;
            providerDbId = String.format("%s:%s", wapDatabaseModel.getMySqlServerId(), wapDatabaseModel.getName());
        } else if (wapDatabaseModel.getSqlServerId() != null) {
            databaseEngine = DatabaseEngine.SQLSERVER_EE;
            providerDbId = String.format("%s:%s", wapDatabaseModel.getSqlServerId(), wapDatabaseModel.getName());
        }

        Database database = new Database();
        database.setName(wapDatabaseModel.getName());
        database.setProviderDatabaseId(providerDbId);
        database.setEngine(databaseEngine);
        return database;
    }

    @Nonnull
    @Override
    public Iterable<ConfigurationParameter> listParameters(String forProviderConfigurationId) throws CloudException, InternalException {
        return null;
    }

    @Nonnull
    @Override
    public Iterable<DatabaseSnapshot> listSnapshots(String forOptionalProviderDatabaseId) throws CloudException, InternalException {
        return null;
    }

    @Override
    public void removeConfiguration(String providerConfigurationId) throws CloudException, InternalException {

    }

    @Override
    public void removeDatabase(String providerDatabaseId) throws CloudException, InternalException {
        if( providerDatabaseId == null)
            throw new InternalException("Provider database id cannot be null");

        Database databaseToRemove = getDatabase(providerDatabaseId);
        if(databaseToRemove == null)
            throw new InternalException("Invalid provider database id");

        HttpUriRequest deleteHttpRequest = null;
        if(databaseToRemove.getEngine() == DatabaseEngine.MYSQL) {
            deleteHttpRequest = new AzurePackDatabaseRequests(provider).deleteMYSQLDb(databaseToRemove.getName()).build();
        } else {
            deleteHttpRequest = new AzurePackDatabaseRequests(provider).deleteMSSQLDb(databaseToRemove.getName()).build();
        }

        new AzurePackRequester(provider, deleteHttpRequest).execute();
    }

    @Override
    public void removeSnapshot(String providerSnapshotId) throws CloudException, InternalException {

    }

    @Override
    public void resetConfiguration(String providerConfigurationId, String... parameters) throws CloudException, InternalException {

    }

    @Override
    public void restart(String providerDatabaseId, boolean blockUntilDone) throws CloudException, InternalException {

    }

    @Override
    public void revokeAccess(String providerDatabaseId, String sourceCide) throws CloudException, InternalException {

    }

    @Override
    public void updateConfiguration(String providerConfigurationId, ConfigurationParameter... parameters) throws CloudException, InternalException {

    }

    @Nonnull
    @Override
    public DatabaseSnapshot snapshot(String providerDatabaseId, String name) throws CloudException, InternalException {
        return null;
    }

    @Nullable
    @Override
    public DatabaseBackup getUsableBackup(String providerDbId, String beforeTimestamp) throws CloudException, InternalException {
        return null;
    }

    @Nonnull
    @Override
    public Iterable<DatabaseBackup> listBackups(String forOptionalProviderDatabaseId) throws CloudException, InternalException {
        return null;
    }

    @Override
    public void createFromBackup(DatabaseBackup backup, String databaseCloneToName) throws CloudException, InternalException {

    }

    @Override
    public void removeBackup(DatabaseBackup backup) throws CloudException, InternalException {

    }

    @Override
    public void restoreBackup(DatabaseBackup backup) throws CloudException, InternalException {

    }

    @Override
    public void removeTags(@Nonnull String providerDatabaseId, @Nonnull Tag... tags) throws CloudException, InternalException {

    }

    @Override
    public void removeTags(@Nonnull String[] providerDatabaseIds, @Nonnull Tag ... tags) throws CloudException, InternalException {

    }

    @Override
    public void setTags( @Nonnull String providerDatabaseId, @Nonnull Tag... tags ) throws CloudException, InternalException {

    }

    @Override
    public void setTags( @Nonnull String[] providerDatabaseIds, @Nonnull Tag... tags ) throws CloudException, InternalException {

    }

    @Override
    public void updateTags(@Nonnull String providerDatabaseId, @Nonnull Tag... tags) throws CloudException, InternalException {

    }

    @Override
    public void updateTags(@Nonnull String[] providerDatabaseIds, @Nonnull Tag... tags) throws CloudException, InternalException {

    }

    @Nonnull
    @Override
    public String[] mapServiceAction(@Nonnull ServiceAction action) {
        return new String[0];
    }


}
