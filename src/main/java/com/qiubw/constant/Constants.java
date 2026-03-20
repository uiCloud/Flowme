package com.qiubw.constant;

public class Constants {
    // JWT相关常量
    public static final String JWT_SECRET = "5f8a9b7c6d4e3a2b1f0e9d8c7b6a5f4e3d2c1b0a9f8e7d6c5b4a3f2e1d0c9b8a7f6e5d4c3b2a1f0e9d8c7b6a5f4e3d2c1b";
    public static final long JWT_EXPIRATION = 3600000; // 1小时

    // 错误码
    public static final int SUCCESS = 200;
    public static final int UNAUTHORIZED = 401;
    public static final int NOT_FOUND = 404;
    public static final int INTERNAL_SERVER_ERROR = 500;

    // 状态码
    public static final int STATUS_ENABLED = 1;
    public static final int STATUS_DISABLED = 0;

    // 权限类型
    public static final int PERMISSION_TYPE_MENU = 1;
    public static final int PERMISSION_TYPE_BUTTON = 2;

    // 菜单类型
    public static final int MENU_TYPE_DIR = 1;
    public static final int MENU_TYPE_MENU = 2;
    public static final int MENU_TYPE_BUTTON = 3;

    // 工单状态
    public static final int TICKET_STATUS_PENDING = 1;
    public static final int TICKET_STATUS_PROCESSING = 2;
    public static final int TICKET_STATUS_COMPLETED = 3;
    public static final int TICKET_STATUS_CANCELLED = 4;
}
