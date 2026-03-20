package com.qiubw.repository.impl;

import com.qiubw.domain.Converter;
import com.qiubw.domain.bo.UserBO;
import com.qiubw.domain.entity.User;
import com.qiubw.repository.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    private RowMapper<User> userRowMapper = new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setRoleId(rs.getLong("role_id"));
            user.setStatus(rs.getInt("status"));
            user.setCreateTime(rs.getTimestamp("create_time"));
            user.setUpdateTime(rs.getTimestamp("update_time"));
            return user;
        }
    };

    @Override
    public UserBO getUserByUsername(String username) {
        try {
            String sql = "SELECT * FROM user WHERE username = ?";
            User user = jdbcTemplate.queryForObject(sql, userRowMapper, username);
            return Converter.INSTANCE.userDAOToBO(user);
        } catch (EmptyResultDataAccessException e) {
            logger.warn("未找到用户名为{}的用户", username);
            return null;
        } catch (Exception e) {
            logger.error("查询用户失败: {}", e.getMessage(), e);
            throw new RuntimeException("查询用户失败", e);
        }
    }

    @Override
    public UserBO getUserById(Long userId) {
        try {
            String sql = "SELECT * FROM user WHERE id = ?";
            User user = jdbcTemplate.queryForObject(sql, userRowMapper, userId);
            return Converter.INSTANCE.userDAOToBO(user);
        } catch (EmptyResultDataAccessException e) {
            logger.warn("未找到ID为{}的用户", userId);
            return null;
        } catch (Exception e) {
            logger.error("查询用户失败: {}", e.getMessage(), e);
            throw new RuntimeException("查询用户失败", e);
        }
    }

    @Override
    public void saveUser(UserBO userBO) {
        try {
            User user = Converter.INSTANCE.userBOToDAO(userBO);
            String sql = "INSERT INTO user (username, password, role_id, status, create_time, update_time) VALUES (?, ?, ?, ?, NOW(), NOW())";
            jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), user.getRoleId(), user.getStatus());
            logger.info("保存用户成功: {}", userBO.getUsername());
        } catch (Exception e) {
            logger.error("保存用户失败: {}", e.getMessage(), e);
            throw new RuntimeException("保存用户失败", e);
        }
    }

    @Override
    public void updateUser(UserBO userBO) {
        try {
            User user = Converter.INSTANCE.userBOToDAO(userBO);
            String sql = "UPDATE user SET username = ?, password = ?, role_id = ?, status = ?, update_time = NOW() WHERE id = ?";
            int rowsAffected = jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), user.getRoleId(), user.getStatus(), user.getId());
            if (rowsAffected == 0) {
                throw new RuntimeException("未找到要更新的用户");
            }
            logger.info("更新用户成功: {}", userBO.getId());
        } catch (RuntimeException e) {
            logger.error("更新用户失败: {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            logger.error("更新用户失败: {}", e.getMessage(), e);
            throw new RuntimeException("更新用户失败", e);
        }
    }

    @Override
    public void deleteUser(Long userId) {
        try {
            String sql = "DELETE FROM user WHERE id = ?";
            int rowsAffected = jdbcTemplate.update(sql, userId);
            if (rowsAffected == 0) {
                throw new RuntimeException("未找到要删除的用户");
            }
            logger.info("删除用户成功: {}", userId);
        } catch (RuntimeException e) {
            logger.error("删除用户失败: {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            logger.error("删除用户失败: {}", e.getMessage(), e);
            throw new RuntimeException("删除用户失败", e);
        }
    }

    @Override
    public List<UserBO> getAllUsers() {
        try {
            String sql = "SELECT * FROM user";
            List<User> users = jdbcTemplate.query(sql, userRowMapper);
            return users.stream()
                    .map(Converter.INSTANCE::userDAOToBO)
                    .collect(java.util.stream.Collectors.toList());
        } catch (Exception e) {
            logger.error("获取所有用户失败: {}", e.getMessage(), e);
            throw new RuntimeException("获取所有用户失败", e);
        }
    }
}
