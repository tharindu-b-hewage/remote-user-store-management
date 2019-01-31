/*
 * Copyright (c) 2019, WSO2 Inc. (http://wso2.com) All Rights Reserved.
 *
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
 */

package org.wso2.carbon.custom.remote.user.store.manager;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.wso2.carbon.custom.remote.user.store.manager.util.EndpointUtil;
import org.wso2.carbon.user.api.Properties;
import org.wso2.carbon.user.api.RealmConfiguration;
import org.wso2.carbon.user.core.UserStoreException;
import org.wso2.carbon.user.core.common.AbstractUserStoreManager;
import org.wso2.carbon.user.core.common.RoleContext;
import org.wso2.carbon.user.core.tenant.Tenant;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * User store manager for the web service user manager.
 */
public class WSUserStoreManager extends AbstractUserStoreManager {

    private static final String URL = "url";
    private static Log log = LogFactory.getLog(WSUserStoreManager.class);

    public WSUserStoreManager() {

    }

    @Override
    public String[] doListUsers(String filter, int maxItemLimit) throws UserStoreException {

        String url = EndpointUtil.getUserListEndpoint(getHostName());
        try (CloseableHttpClient httpclient = HttpClientBuilder.create().useSystemProperties().build()) {

            HttpGet httpGet = new HttpGet(url);

            try (CloseableHttpResponse response = httpclient.execute(httpGet)) {

                if (log.isDebugEnabled()) {
                    log.debug("HTTP status " + response.getStatusLine().getStatusCode());
                }
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                    String inputLine;
                    StringBuilder responseString = new StringBuilder();

                    while ((inputLine = reader.readLine()) != null) {
                        responseString.append(inputLine);
                    }
                    JSONObject resultObj = new JSONObject(responseString.toString());
                    JSONArray jsonArray = resultObj.getJSONArray("users");
                    List<String> userList = new ArrayList<String>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        userList.add(jsonArray.getString(i));
                    }
                    return userList.toArray(new String[]{});
                } else {
                    throw new UserStoreException("Error while retrieving data from " + url + ". Found http " +
                            "status " + response.getStatusLine());
                }
            } finally {
                httpGet.releaseConnection();
            }
        } catch (IOException e) {
            throw new UserStoreException("Error while creating a connection with " + url);
        }
    }

    protected Map<String, String> getUserPropertyValues(String s, String[] strings, String s1) throws UserStoreException {

        return null;
    }

    protected boolean doCheckExistingRole(String s) throws UserStoreException {

        return false;
    }

    protected RoleContext createRoleContext(String s) throws UserStoreException {

        return null;
    }

    protected boolean doCheckExistingUser(String s) throws UserStoreException {

        return false;
    }

    protected String[] getUserListFromProperties(String s, String s1, String s2) throws UserStoreException {

        return new String[0];
    }

    protected boolean doAuthenticate(String s, Object o) throws UserStoreException {

        return false;
    }

    protected void doAddUser(String s, Object o, String[] strings, Map<String, String> map, String s1, boolean b) throws UserStoreException {

    }

    protected void doUpdateCredential(String s, Object o, Object o1) throws UserStoreException {

    }

    protected void doUpdateCredentialByAdmin(String s, Object o) throws UserStoreException {

    }

    protected void doDeleteUser(String s) throws UserStoreException {

    }

    protected void doSetUserClaimValue(String s, String s1, String s2, String s3) throws UserStoreException {

    }

    protected void doSetUserClaimValues(String s, Map<String, String> map, String s1) throws UserStoreException {

    }

    protected void doDeleteUserClaimValue(String s, String s1, String s2) throws UserStoreException {

    }

    protected void doDeleteUserClaimValues(String s, String[] strings, String s1) throws UserStoreException {

    }

    protected void doUpdateUserListOfRole(String s, String[] strings, String[] strings1) throws UserStoreException {

    }

    protected void doUpdateRoleListOfUser(String s, String[] strings, String[] strings1) throws UserStoreException {

    }

    protected String[] doGetExternalRoleListOfUser(String s, String s1) throws UserStoreException {

        return new String[0];
    }

    protected String[] doGetSharedRoleListOfUser(String s, String s1, String s2) throws UserStoreException {

        return new String[0];
    }

    protected void doAddRole(String s, String[] strings, boolean b) throws UserStoreException {

    }

    protected void doDeleteRole(String s) throws UserStoreException {

    }

    protected void doUpdateRoleName(String s, String s1) throws UserStoreException {

    }

    protected String[] doGetRoleNames(String s, int i) throws UserStoreException {

        return new String[0];
    }

    protected String[] doGetDisplayNamesForInternalRole(String[] strings) throws UserStoreException {

        return new String[0];
    }

    public boolean doCheckIsUserInRole(String s, String s1) throws UserStoreException {

        return false;
    }

    protected String[] doGetSharedRoleNames(String s, String s1, int i) throws UserStoreException {

        return new String[0];
    }

    protected String[] doGetUserListOfRole(String s, String s1) throws UserStoreException {

        return new String[0];
    }

    public String[] getProfileNames(String s) throws UserStoreException {

        return new String[0];
    }

    public String[] getAllProfileNames() throws UserStoreException {

        return new String[0];
    }

    public boolean isReadOnly() throws UserStoreException {

        return false;
    }

    public int getUserId(String s) throws UserStoreException {

        return 0;
    }

    public int getTenantId(String s) throws UserStoreException {

        return 0;
    }

    public int getTenantId() throws UserStoreException {

        return 0;
    }

    public Map<String, String> getProperties(Tenant tenant) throws UserStoreException {

        return null;
    }

    public boolean isBulkImportSupported() throws UserStoreException {

        return false;
    }

    public RealmConfiguration getRealmConfiguration() {

        return null;
    }

    public Map<String, String> getProperties(org.wso2.carbon.user.api.Tenant tenant) throws org.wso2.carbon.user.api.UserStoreException {

        return null;
    }

    public boolean isMultipleProfilesAllowed() {

        return false;
    }

    public void addRememberMe(String s, String s1) throws org.wso2.carbon.user.api.UserStoreException {

    }

    public boolean isValidRememberMeToken(String s, String s1) throws org.wso2.carbon.user.api.UserStoreException {

        return false;
    }

    public Properties getDefaultUserStoreProperties() {

        return null;
    }

    private String getHostName() {

        return this.realmConfig.getUserStoreProperty(URL);
    }

    private HttpClient getHttpClient() {

        HttpClient httpClient = new HttpClient(new MultiThreadedHttpConnectionManager());
        httpClient.getParams().setParameter("http.connection.stalecheck", new Boolean(true));
        return httpClient;
    }
}
