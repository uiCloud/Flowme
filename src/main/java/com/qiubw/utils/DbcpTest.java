package com.qiubw.utils;

import java.sql.Connection;
import java.sql.SQLException;

public class DbcpTest {
    public static void main(String[] args) {
        try {
            System.out.println("连接池初始化中...");
            
            // 测试获取连接
            Connection conn1 = DbcpUtil.getConnection();
            System.out.println("获取连接1成功: " + conn1);
            
            Connection conn2 = DbcpUtil.getConnection();
            System.out.println("获取连接2成功: " + conn2);
            
            System.out.println("活跃连接数: " + DbcpUtil.getActiveCount());
            System.out.println("连接池大小: " + DbcpUtil.getPoolSize());
            
            // 测试释放连接
            DbcpUtil.releaseConnection(conn1);
            System.out.println("释放连接1后，活跃连接数: " + DbcpUtil.getActiveCount());
            System.out.println("释放连接1后，连接池大小: " + DbcpUtil.getPoolSize());
            
            // 测试验证连接
            Connection conn3 = DbcpUtil.getConnection();
            System.out.println("获取连接3成功: " + conn3);
            
            // 测试关闭连接池
            DbcpUtil.shutdown();
            System.out.println("连接池已关闭");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}