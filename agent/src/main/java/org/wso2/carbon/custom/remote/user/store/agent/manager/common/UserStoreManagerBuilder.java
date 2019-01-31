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

package org.wso2.carbon.custom.remote.user.store.agent.manager.common;

import org.wso2.carbon.custom.remote.user.store.agent.manager.jdbc.OnPremJDBCUserStoreManager;

import java.util.Properties;

/**
 * Creates an instance of the Relevant UserStoreManager.
 */
public class UserStoreManagerBuilder {

    /**
     * Using a builder class for any database specific requirements.
     *
     * @param connectionProperties {@link Properties} file with the connection parameters.
     * @return {@link UserStoreManager} instance.
     */
    public static UserStoreManager getUserStoreManager(Properties connectionProperties) {

        return new OnPremJDBCUserStoreManager(connectionProperties);
    }
}
