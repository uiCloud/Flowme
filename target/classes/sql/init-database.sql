-- 数据库初始化脚本
-- 创建ticket数据库
CREATE DATABASE IF NOT EXISTS ticket CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 连接到ticket数据库
USE ticket;

-- 创建user表
CREATE TABLE IF NOT EXISTS user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role_id BIGINT NOT NULL,
    status INT NOT NULL DEFAULT 1,
    create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 创建role表
CREATE TABLE IF NOT EXISTS role (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL UNIQUE,
    create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 创建ticket_type表
CREATE TABLE IF NOT EXISTS ticket_type (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL UNIQUE,
    create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 创建ticket表
CREATE TABLE IF NOT EXISTS ticket (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    content TEXT NOT NULL,
    type_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    status INT NOT NULL DEFAULT 1,
    create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (type_id) REFERENCES ticket_type(id),
    FOREIGN KEY (user_id) REFERENCES user(id)
);

-- 创建permission表
CREATE TABLE IF NOT EXISTS permission (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    code VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(50) NOT NULL,
    type INT NOT NULL DEFAULT 1,
    status INT NOT NULL DEFAULT 1,
    parent_id BIGINT DEFAULT NULL,
    create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 创建menu表
CREATE TABLE IF NOT EXISTS menu (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    path VARCHAR(100) NOT NULL,
    type INT NOT NULL DEFAULT 1,
    status INT NOT NULL DEFAULT 1,
    parent_id BIGINT DEFAULT NULL,
    create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 创建role_permission表
CREATE TABLE IF NOT EXISTS role_permission (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    role_id BIGINT NOT NULL,
    permission_id BIGINT NOT NULL,
    create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (role_id) REFERENCES role(id),
    FOREIGN KEY (permission_id) REFERENCES permission(id),
    UNIQUE KEY uk_role_permission (role_id, permission_id)
);

-- 插入初始数据
-- 插入角色数据
INSERT INTO role (name) VALUES
('admin'),
('user');

-- 插入用户数据
INSERT INTO user (username, password, role_id) VALUES
('admin', '123456', 1),
('user', '123456', 2);

-- 插入工单类型数据
INSERT INTO ticket_type (name) VALUES
('Bug'),
('Feature'),
('Task');

-- 插入权限数据
INSERT INTO permission (code, name, type, parent_id) VALUES
('system:user:manage', '用户管理', 1, NULL),
('system:role:manage', '角色管理', 1, NULL),
('system:ticket:manage', '工单管理', 1, NULL);

-- 插入菜单数据
INSERT INTO menu (name, path, type, parent_id) VALUES
('系统管理', '/system', 1, NULL),
('用户管理', '/system/user', 2, 1),
('角色管理', '/system/role', 2, 1),
('工单管理', '/ticket', 1, NULL),
('工单列表', '/ticket/list', 2, 4),
('工单创建', '/ticket/create', 2, 4);

-- 插入角色权限数据
INSERT INTO role_permission (role_id, permission_id) VALUES
(1, 1),
(1, 2),
(1, 3),
(2, 3);

-- 查看初始化结果
SELECT '数据库初始化完成' AS result;