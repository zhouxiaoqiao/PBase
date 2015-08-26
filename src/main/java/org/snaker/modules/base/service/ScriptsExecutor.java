/*
 *  Copyright 2013-2014 the original author or authors.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *     http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package org.snaker.modules.base.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snaker.engine.access.ScriptRunner;
import org.snaker.engine.access.jdbc.JdbcHelper;

import com.ibatis.common.resources.Resources;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 脚本执行类
 * @author yuqs
 * @since 1.0
 */
public class ScriptsExecutor {
    private static final Logger log = LoggerFactory.getLogger(ScriptsExecutor.class);
    //数据源
    private DataSource dataSource;
    
    public void setDataSource(DataSource dataSource) {
    	log.info("dataSource dataSource......"+dataSource.toString());
        this.dataSource = dataSource;
    }

    public void run() {
        log.info("scripts running......>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
//        	conn = getConnection();
            if(JdbcHelper.isExec(conn)) {
                log.info("script has completed execution.skip this step");
                return;
            }
            String databaseType = JdbcHelper.getDatabaseType(conn);
            String[] schemas = new String[]{"db/core/schema-" + databaseType + ".sql",
                    "sql/flow/web/schema-" + databaseType + ".sql", "sql/flow/web/init-data.sql","sql/hr/create_tab.sql"};
            ScriptRunner runner = new ScriptRunner(conn, true);
            for(String schema : schemas) {
                runner.runScript(schema);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                JdbcHelper.close(conn);
            } catch (SQLException e) {
                //ignore
            }
            log.info("scripts has runned......<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        }
    }
    public Connection getConnection() throws Exception{
    	Properties props = Resources.getResourceAsProperties("jdbc.properties");
		String url = props.getProperty("jdbc.url");
		String driver = props.getProperty("jdbc.driverClassName");
		String username = props.getProperty("jdbc.username");
		String password = props.getProperty("jdbc.password");
		Class.forName(driver).newInstance();
		Connection conn = (Connection) DriverManager.getConnection(url, username, password);
		return conn;
    }
}
