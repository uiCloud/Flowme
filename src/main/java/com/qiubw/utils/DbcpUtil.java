package com.qiubw.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Properties;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class DbcpUtil {
    
    private static String URL;
    private static String USERNAME;
    private static String PASSWORD;
    private static int MIN_POOL_SIZE;
    private static int MAX_POOL_SIZE;
    private static long CONN_TIMEOUT;
    private static long IDLE_TIMEOUT;
    
    private static LinkedList<Connection> pool = new LinkedList<>();
    private static AtomicInteger activeCount = new AtomicInteger(0);
    private static ScheduledExecutorService scheduler;
    
    static {
        try {
            loadConfig();
            Class.forName("com.mysql.cj.jdbc.Driver");
            initPool();
            startCleanupTask();
        } catch (ClassNotFoundException | IOException e) {
            System.err.println("初始化失败");
            e.printStackTrace();
            throw new RuntimeException("初始化失败", e);
        }
    }
    
    private static void loadConfig() throws IOException {
        Properties properties = new Properties();
        try (InputStream input = DbcpUtil.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                throw new IOException("无法找到配置文件");
            }
            properties.load(input);
            
            URL = properties.getProperty("spring.datasource.url", "jdbc:mysql://localhost:3306/ticket?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai");
            USERNAME = properties.getProperty("spring.datasource.username", "root");
            PASSWORD = properties.getProperty("spring.datasource.password", "root");
            MIN_POOL_SIZE = Integer.parseInt(properties.getProperty("dbcp.minPoolSize", "5"));
            MAX_POOL_SIZE = Integer.parseInt(properties.getProperty("dbcp.maxPoolSize", "20"));
            CONN_TIMEOUT = Long.parseLong(properties.getProperty("dbcp.connTimeout", "30000"));
            IDLE_TIMEOUT = Long.parseLong(properties.getProperty("dbcp.idleTimeout", "60000"));
        }
        System.out.println("配置加载完成");
    }
    
    private static void initPool() {
        for (int i = 0; i < MIN_POOL_SIZE; i++) {
            try {
                Connection conn = createConnection();
                pool.add(conn);
            } catch (SQLException e) {
                System.err.println("初始化连接池失败");
                e.printStackTrace();
            }
        }
        System.out.println("连接池初始化完成，初始连接数: " + pool.size());
    }
    
    private static Connection createConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        System.out.println("创建新数据库连接: " + conn);
        return conn;
    }
    
    public static Connection getConnection() throws SQLException {
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < CONN_TIMEOUT) {
            synchronized (pool) {
                if (!pool.isEmpty()) {
                    Connection conn = pool.removeFirst();
                    if (isValid(conn)) {
                        activeCount.incrementAndGet();
                        System.out.println("获取数据库连接: " + conn + ", 当前活跃连接数: " + activeCount.get());
                        return conn;
                    } else {
                        System.out.println("连接已失效，创建新连接");
                    }
                } else if (activeCount.get() < MAX_POOL_SIZE) {
                    try {
                        Connection conn = createConnection();
                        activeCount.incrementAndGet();
                        System.out.println("创建新连接: " + conn + ", 当前活跃连接数: " + activeCount.get());
                        return conn;
                    } catch (SQLException e) {
                        System.err.println("创建连接失败");
                        e.printStackTrace();
                        throw e;
                    }
                }
                
                try {
                    pool.wait(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new SQLException("获取连接被中断", e);
                }
            }
        }
        throw new SQLException("获取数据库连接超时");
    }
    
    public static void releaseConnection(Connection conn) {
        if (conn == null) return;
        
        synchronized (pool) {
            pool.addLast(conn);
            activeCount.decrementAndGet();
            System.out.println("释放数据库连接: " + conn + ", 当前活跃连接数: " + activeCount.get());
            pool.notifyAll();
        }
    }
    
    public static void closeConnection(Connection conn) {
        if (conn == null) return;
        
        try {
            conn.close();
            activeCount.decrementAndGet();
            System.out.println("关闭数据库连接: " + conn + ", 当前活跃连接数: " + activeCount.get());
        } catch (SQLException e) {
            System.err.println("关闭连接失败");
            e.printStackTrace();
        }
    }
    
    private static boolean isValid(Connection conn) {
        try {
            if (conn.isClosed()) return false;
            conn.createStatement().execute("SELECT 1");
            return true;
        } catch (SQLException e) {
            System.out.println("连接验证失败");
            e.printStackTrace();
            return false;
        }
    }
    
    private static void startCleanupTask() {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleWithFixedDelay(() -> {
            synchronized (pool) {
                LinkedList<Connection> validConnections = new LinkedList<>();
                while (!pool.isEmpty()) {
                    Connection conn = pool.removeFirst();
                    if (isValid(conn)) {
                        validConnections.addLast(conn);
                    } else {
                        closeConnection(conn);
                    }
                }
                pool.addAll(validConnections);
                
                while (pool.size() > MIN_POOL_SIZE) {
                    Connection conn = pool.removeLast();
                    closeConnection(conn);
                }
                System.out.println("连接池清理完成，当前连接数: " + pool.size());
            }
        }, IDLE_TIMEOUT, IDLE_TIMEOUT, TimeUnit.MILLISECONDS);
    }
    
    public static void shutdown() {
        if (scheduler != null) {
            scheduler.shutdown();
        }
        synchronized (pool) {
            while (!pool.isEmpty()) {
                Connection conn = pool.removeFirst();
                closeConnection(conn);
            }
        }
        System.out.println("连接池已关闭");
    }
    
    public static int getPoolSize() {
        synchronized (pool) {
            return pool.size();
        }
    }
    
    public static int getActiveCount() {
        return activeCount.get();
    }
}