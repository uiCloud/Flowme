-- 初始化数据库脚本
-- 该脚本可以直接执行，包含连接处理和错误检查

-- 设置错误处理
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO';
SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0;

-- 开始事务
START TRANSACTION;

-- 创建数据库
CREATE DATABASE IF NOT EXISTS ticket CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 切换到ticket数据库
USE ticket;

-- 用户表
CREATE TABLE IF NOT EXISTS user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role_id BIGINT NOT NULL,
    status INT NOT NULL DEFAULT 1,
    create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 角色表
CREATE TABLE IF NOT EXISTS role (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(200),
    status INT NOT NULL DEFAULT 1,
    create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 权限表
CREATE TABLE IF NOT EXISTS permission (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    code VARCHAR(50) NOT NULL UNIQUE,
    type INT NOT NULL,
    parent_id BIGINT,
    path VARCHAR(200),
    component VARCHAR(200),
    icon VARCHAR(50),
    sort INT NOT NULL DEFAULT 0,
    status INT NOT NULL DEFAULT 1,
    create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 角色权限关系表
CREATE TABLE IF NOT EXISTS role_permission (
    role_id BIGINT NOT NULL,
    permission_id BIGINT NOT NULL,
    PRIMARY KEY (role_id, permission_id),
    FOREIGN KEY (role_id) REFERENCES role(id) ON DELETE CASCADE,
    FOREIGN KEY (permission_id) REFERENCES permission(id) ON DELETE CASCADE
);

-- 工单类型表
CREATE TABLE IF NOT EXISTS ticket_type (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(200),
    status INT NOT NULL DEFAULT 1,
    create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 工单表
CREATE TABLE IF NOT EXISTS ticket (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    content TEXT NOT NULL,
    type_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    status INT NOT NULL DEFAULT 1,
    create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (type_id) REFERENCES ticket_type(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
);

-- 菜单表
CREATE TABLE IF NOT EXISTS menu (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    parent_id BIGINT,
    path VARCHAR(200),
    component VARCHAR(200),
    icon VARCHAR(50),
    sort INT NOT NULL DEFAULT 0,
    type INT NOT NULL,
    status INT NOT NULL DEFAULT 1,
    create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 初始化数据
-- 角色数据
INSERT IGNORE INTO role (name, description, status) VALUES
('admin', '管理员', 1),
('user', '普通用户', 1);

-- 用户数据
INSERT IGNORE INTO user (username, password, role_id, status) VALUES
('admin', 'admin123', 1, 1),
('user', 'user123', 2, 1);

-- 工单类型数据
INSERT IGNORE INTO ticket_type (name, description, status) VALUES
('Bug', '软件缺陷', 1),
('Feature', '新功能', 1),
('Improvement', '改进', 1),
('Other', '其他', 1);

-- 权限数据
INSERT IGNORE INTO permission (name, code, type, parent_id, path, component, icon, sort, status) VALUES
('系统管理', 'system', 1, NULL, '/system', NULL, 'settings', 1, 1),
('用户管理', 'user', 2, 1, '/system/user', 'system/user/index', NULL, 1, 1),
('角色管理', 'role', 2, 1, '/system/role', 'system/role/index', NULL, 2, 1),
('权限管理', 'permission', 2, 1, '/system/permission', 'system/permission/index', NULL, 3, 1),
('工单管理', 'ticket', 1, NULL, '/ticket', NULL, 'ticket', 2, 1),
('工单列表', 'ticket-list', 2, 5, '/ticket/list', 'ticket/list/index', NULL, 1, 1),
('工单类型', 'ticket-type', 2, 5, '/ticket/type', 'ticket/type/index', NULL, 2, 1);

-- 角色权限关系数据
INSERT IGNORE INTO role_permission (role_id, permission_id) VALUES
(1, 1), (1, 2), (1, 3), (1, 4), (1, 5), (1, 6), (1, 7),
(2, 5), (2, 6);

-- 菜单数据
INSERT IGNORE INTO menu (name, parent_id, path, component, icon, sort, type, status) VALUES
('系统管理', NULL, '/system', NULL, 'settings', 1, 1, 1),
('用户管理', 1, '/system/user', 'system/user/index', NULL, 1, 2, 1),
('角色管理', 1, '/system/role', 'system/role/index', NULL, 2, 2, 1),
('权限管理', 1, '/system/permission', 'system/permission/index', NULL, 3, 2, 1),
('工单管理', NULL, '/ticket', NULL, 'ticket', 2, 1, 1),
('工单列表', 5, '/ticket/list', 'ticket/list/index', NULL, 1, 2, 1),
('工单类型', 5, '/ticket/type', 'ticket/type/index', NULL, 2, 2, 1);

-- 提交事务
COMMIT;

-- 恢复原始设置
SET SQL_MODE=@OLD_SQL_MODE;
SET SQL_NOTES=@OLD_SQL_NOTES;

-- 显示执行结果
SELECT '数据库初始化完成' AS result;
SELECT '用户表记录数: ' AS table_name, COUNT(*) AS count FROM user;
SELECT '角色表记录数: ' AS table_name, COUNT(*) AS count FROM role;
SELECT '工单类型表记录数: ' AS table_name, COUNT(*) AS count FROM ticket_type;
SELECT '权限表记录数: ' AS table_name, COUNT(*) AS count FROM permission;
SELECT '菜单表记录数: ' AS table_name, COUNT(*) AS count FROM menu;
