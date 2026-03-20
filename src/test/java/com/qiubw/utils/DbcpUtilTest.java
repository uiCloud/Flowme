package com.qiubw.utils;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class DbcpUtilTest {
    
    @BeforeAll
    public static void setUp() {
        // 初始化连接池
        System.out.println("连接池初始化中...");
    }
    
    @Test
    public void testGetConnection() throws SQLException {
        Connection conn = DbcpUtil.getConnection();
        assertNotNull(conn, "获取连接失败");
        assertFalse(conn.isClosed(), "连接已关闭");
        System.out.println("获取连接成功: " + conn);
        DbcpUtil.releaseConnection(conn);
    }
    
    @Test
    public void testConnectionPoolSize() throws SQLException {
        int initialPoolSize = DbcpUtil.getPoolSize();
        System.out.println("初始连接池大小: " + initialPoolSize);
        
        // 获取多个连接
        Connection[] conns = new Connection[10];
        for (int i = 0; i < 10; i++) {
            conns[i] = DbcpUtil.getConnection();
        }
        
        System.out.println("活跃连接数: " + DbcpUtil.getActiveCount());
        System.out.println("连接池大小: " + DbcpUtil.getPoolSize());
        
        // 释放连接
        for (Connection conn : conns) {
            DbcpUtil.releaseConnection(conn);
        }
        
        System.out.println("释放后活跃连接数: " + DbcpUtil.getActiveCount());
        System.out.println("释放后连接池大小: " + DbcpUtil.getPoolSize());
    }
    
    @Test
    public void testConnectionValidation() throws SQLException {
        Connection conn = DbcpUtil.getConnection();
        assertNotNull(conn, "获取连接失败");
        
        // 验证连接是否有效
        boolean isValid = false;
        try {
            conn.createStatement().execute("SELECT 1");
            isValid = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        assertTrue(isValid, "连接验证失败");
        DbcpUtil.releaseConnection(conn);
    }
    
    @AfterAll
    public static void tearDown() {
        // 关闭连接池
        DbcpUtil.shutdown();
        System.out.println("连接池已关闭");
    }
}