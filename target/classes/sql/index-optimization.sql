-- 数据库表索引优化脚本
-- 分析并为常用查询字段添加索引

-- 连接到ticket数据库
USE ticket;

-- 1. user表索引优化
-- 分析：username字段常用于登录查询，id字段用于外键关联
CREATE INDEX idx_user_username ON user(username);
CREATE INDEX idx_user_role_id ON user(role_id);
CREATE INDEX idx_user_status ON user(status);

-- 2. ticket表索引优化
-- 分析：user_id用于按创建者查询，type_id用于按类型查询，status用于状态筛选
CREATE INDEX idx_ticket_user_id ON ticket(user_id);
CREATE INDEX idx_ticket_type_id ON ticket(type_id);
CREATE INDEX idx_ticket_status ON ticket(status);
CREATE INDEX idx_ticket_create_time ON ticket(create_time);

-- 3. ticket_type表索引优化
-- 分析：name字段用于查询
CREATE INDEX idx_ticket_type_name ON ticket_type(name);

-- 4. role表索引优化
-- 分析：name字段用于查询
CREATE INDEX idx_role_name ON role(name);

-- 5. permission表索引优化
-- 分析：code字段用于权限检查，type和status用于筛选
CREATE INDEX idx_permission_code ON permission(code);
CREATE INDEX idx_permission_type ON permission(type);
CREATE INDEX idx_permission_status ON permission(status);
CREATE INDEX idx_permission_parent_id ON permission(parent_id);

-- 6. menu表索引优化
-- 分析：path字段用于路由匹配，type和status用于筛选
CREATE INDEX idx_menu_path ON menu(path);
CREATE INDEX idx_menu_type ON menu(type);
CREATE INDEX idx_menu_status ON menu(status);
CREATE INDEX idx_menu_parent_id ON menu(parent_id);

-- 7. role_permission表索引优化
-- 分析：复合索引用于关联查询
CREATE INDEX idx_role_permission_role_id ON role_permission(role_id);
CREATE INDEX idx_role_permission_permission_id ON role_permission(permission_id);

-- 查看所有索引
SHOW INDEX FROM user;
SHOW INDEX FROM ticket;
SHOW INDEX FROM ticket_type;
SHOW INDEX FROM role;
SHOW INDEX FROM permission;
SHOW INDEX FROM menu;
SHOW INDEX FROM role_permission;

-- 创建测试表
CREATE TABLE IF NOT EXISTS test_ticket (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    content TEXT NOT NULL,
    type_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    status INT NOT NULL DEFAULT 1,
    create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 为测试表添加索引
CREATE INDEX idx_test_ticket_user_id ON test_ticket(user_id);
CREATE INDEX idx_test_ticket_type_id ON test_ticket(type_id);
CREATE INDEX idx_test_ticket_status ON test_ticket(status);
CREATE INDEX idx_test_ticket_create_time ON test_ticket(create_time);

-- 插入测试数据
INSERT INTO test_ticket (title, content, type_id, user_id, status) VALUES
('测试工单1', '这是测试工单1的内容', 1, 1, 1),
('测试工单2', '这是测试工单2的内容', 2, 1, 2),
('测试工单3', '这是测试工单3的内容', 1, 2, 1),
('测试工单4', '这是测试工单4的内容', 3, 2, 3),
('测试工单5', '这是测试工单5的内容', 2, 1, 1);

-- 测试查询性能
-- 测试按用户ID查询
EXPLAIN SELECT * FROM test_ticket WHERE user_id = 1;
-- 测试按类型ID查询
EXPLAIN SELECT * FROM test_ticket WHERE type_id = 1;
-- 测试按状态查询
EXPLAIN SELECT * FROM test_ticket WHERE status = 1;
-- 测试按创建时间排序
EXPLAIN SELECT * FROM test_ticket ORDER BY create_time DESC;

-- 清理测试环境
DROP TABLE IF EXISTS test_ticket;

-- 查看优化后的索引状态
SELECT '索引优化完成' AS result;
