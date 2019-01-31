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

package org.wso2.carbon.custom.remote.user.store.agent.constant;

/**
 * Constants common to all user stores.
 */
public class CommonConstants {

    public static final String APPLICATION_CONTEXT_PATH = "/wso2agent";
    public static final String TABLE_COLUMN_USER_NAME = "UM_USER_NAME";
    public static final String CONNECTION_PARAMETER_USER_NAME = "connection.parameter.user-name";
    public static final String CONNECTION_PARAMETER_PASSWORD = "connection.parameter.password";
    public static final String CONNECTION_PARAMETER_SERVER_URL = "connection.parameter.server-url";
    public static final String SERVER_PORT = "service.port";
    public static final String GET_ALL_USERS_SQL = "SELECT * FROM UM_USER";
}
