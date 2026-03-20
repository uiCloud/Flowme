package com.qiubw.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Statement;

public class DatabaseInitializer {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseInitializer.class);

    public static void initialize() {
        logger.info("开始初始化数据库...");
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DbcpUtil.getConnection();
            statement = connection.createStatement();
            
            // 读取初始化脚本
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(DatabaseInitializer.class.getResourceAsStream("/init-database.sql"))
            );
            
            StringBuilder sqlBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                // 跳过注释和空行
                if (!line.trim().startsWith("--") && !line.trim().isEmpty()) {
                    sqlBuilder.append(line);
                    // 执行完整的SQL语句
                    if (line.trim().endsWith(";")) {
                        String sql = sqlBuilder.toString();
                        statement.execute(sql);
                        sqlBuilder.setLength(0);
                    }
                }
            }
            
            logger.info("数据库初始化完成");
        } catch (Exception e) {
            logger.error("数据库初始化失败", e);
        } finally {
            DbcpUtil.releaseConnection(connection);
            if (statement != null) {
                try {
                    statement.close();
                } catch (Exception e) {
                    logger.error("关闭Statement失败", e);
                }
            }
        }
    }
}