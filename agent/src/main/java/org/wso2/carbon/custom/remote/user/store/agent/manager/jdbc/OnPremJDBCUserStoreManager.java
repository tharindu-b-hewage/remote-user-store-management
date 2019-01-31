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

package org.wso2.carbon.custom.remote.user.store.agent.manager.jdbc;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.custom.remote.user.store.agent.exception.UserStoreException;
import org.wso2.carbon.custom.remote.user.store.agent.manager.common.UserStoreManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.wso2.carbon.custom.remote.user.store.agent.constant.CommonConstants.CONNECTION_PARAMETER_PASSWORD;
import static org.wso2.carbon.custom.remote.user.store.agent.constant.CommonConstants.CONNECTION_PARAMETER_SERVER_URL;
import static org.wso2.carbon.custom.remote.user.store.agent.constant.CommonConstants.CONNECTION_PARAMETER_USER_NAME;
import static org.wso2.carbon.custom.remote.user.store.agent.constant.CommonConstants.GET_ALL_USERS_SQL;
import static org.wso2.carbon.custom.remote.user.store.agent.constant.CommonConstants.TABLE_COLUMN_USER_NAME;

/**
 * Custom user store manager for the remote JDBC user store.
 */
public class OnPremJDBCUserStoreManager implements UserStoreManager {

    private static Log log = LogFactory.getLog(OnPremJDBCUserStoreManager.class);
    private Properties connectionProperties;

    public OnPremJDBCUserStoreManager(Properties connectionProperties) {

        this.connectionProperties = connectionProperties;
    }

    /**
     * {@inheritDoc}
     */
    public String[] doListExistingUsers() throws UserStoreException {

        try (Connection dbConnection = this.getDBConnection()) {
            String sql = GET_ALL_USERS_SQL;
            dbConnection.setAutoCommit(false);
            if (log.isDebugEnabled()) {
                log.debug(sql);
            }
            try (PreparedStatement prepStmt = dbConnection.prepareStatement(sql)) {
                ResultSet rs = prepStmt.executeQuery();
                dbConnection.commit();
                List<String> users = new ArrayList<>();
                while (rs.next()) {
                    users.add(rs.getString(TABLE_COLUMN_USER_NAME));
                }
                return users.toArray(new String[]{});
            }
        } catch (SQLException exp) {
            log.error("Error occurred while retrieving user info.", exp);
            throw new UserStoreException("User Check Failure");
        }
    }

    private Connection getDBConnection() throws SQLException {

        Properties dbConnectionProperties = new Properties();
        dbConnectionProperties.setProperty("user",
                this.connectionProperties.getProperty(CONNECTION_PARAMETER_USER_NAME));
        dbConnectionProperties.setProperty("password",
                this.connectionProperties.getProperty(CONNECTION_PARAMETER_PASSWORD));
        Connection conn = DriverManager.getConnection(
                this.connectionProperties.getProperty(CONNECTION_PARAMETER_SERVER_URL),
                dbConnectionProperties
        );
        // TODO: 1/31/19 handle with connection pool 
        if (log.isDebugEnabled()) {
            log.debug("Connected to database");
        }
        return conn;
    }
}