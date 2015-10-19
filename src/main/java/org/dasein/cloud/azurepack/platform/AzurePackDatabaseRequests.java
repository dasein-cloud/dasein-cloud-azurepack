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

import org.apache.http.client.methods.RequestBuilder;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.azurepack.AzurePackCloud;
import org.dasein.cloud.azurepack.platform.model.WAPDatabaseModel;
import org.dasein.cloud.util.requester.entities.DaseinObjectToJsonEntity;
import org.dasein.cloud.util.requester.entities.DaseinObjectToXmlEntity;

import java.net.URI;
import java.net.URL;

/**
 * Created by vmunthiu on 6/17/2015.
 */
public class AzurePackDatabaseRequests {
    private final String MSSQL_DBS = "%s/%s/services/sqlservers/databases";
    private final String MYSQL_DBS = "%s/%s/services/mysqlservers/databases";
    private final String MSSQL_DB = "%s/%s/services/sqlservers/databases/%s";
    private final String MYSQL_DB = "%s/%s/services/mysqlservers/databases/%s";

    private AzurePackCloud provider;

    public AzurePackDatabaseRequests(AzurePackCloud provider){
        this.provider = provider;
    }

    public RequestBuilder listMSSQLDBS() throws InternalException {
        return getListRequestBuilder(MSSQL_DBS);
    }

    public RequestBuilder listMYSQLDBS() throws InternalException {
        return getListRequestBuilder(MYSQL_DBS);
    }

    public RequestBuilder createMSSQLDb(WAPDatabaseModel databaseModel) {
        return getCreateDBRequestBuilder(MSSQL_DBS, databaseModel);
    }

    public RequestBuilder createMYSQLDb(WAPDatabaseModel databaseModel) {
        return getCreateDBRequestBuilder(MYSQL_DBS, databaseModel);
    }

    public RequestBuilder deleteMSSQLDb(String databaseName) {
        return getDeleteDBRequestBuilder(MSSQL_DB, databaseName);
    }

    public RequestBuilder deleteMYSQLDb(String databaseName) {
        return getDeleteDBRequestBuilder(MYSQL_DB, databaseName);
    }

    private RequestBuilder getListRequestBuilder(String uri) {
        RequestBuilder requestBuilder = RequestBuilder.get();
        addCommonHeaders(requestBuilder);
        requestBuilder.setUri(String.format(uri, this.provider.getContext().getCloud().getEndpoint(), this.provider.getContext().getAccountNumber()));
        return requestBuilder;
    }

    private RequestBuilder getCreateDBRequestBuilder(String uri, WAPDatabaseModel databaseModel){
        RequestBuilder requestBuilder = RequestBuilder.post();
        addCommonHeaders(requestBuilder);
        requestBuilder.setUri(String.format(uri, this.provider.getContext().getCloud().getEndpoint(), this.provider.getContext().getAccountNumber()));
        requestBuilder.setEntity(new DaseinObjectToJsonEntity<WAPDatabaseModel>(databaseModel));
        return requestBuilder;
    }

    private RequestBuilder getDeleteDBRequestBuilder(String uri, String databaseName) {
        RequestBuilder requestBuilder = RequestBuilder.delete();
        addCommonHeaders(requestBuilder);
        requestBuilder.setUri(String.format(uri, this.provider.getContext().getCloud().getEndpoint(), this.provider.getContext().getAccountNumber(), databaseName));
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
