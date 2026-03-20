package com.qiubw.repository.impl;

import com.qiubw.domain.Converter;
import com.qiubw.domain.bo.UserBO;
import com.qiubw.domain.dao.UserDAO;
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
    
    private RowMapper<UserDAO> userDAORowMapper = new RowMapper<UserDAO>() {
        @Override
        public UserDAO mapRow(ResultSet rs, int rowNum) throws SQLException {
            UserDAO userDAO = new UserDAO();
            userDAO.setId(rs.getLong("id"));
            userDAO.setUsername(rs.getString("username"));
            userDAO.setPassword(rs.getString("password"));
            userDAO.setRoleId(rs.getLong("role_id"));
            userDAO.setStatus(rs.getInt("status"));
            userDAO.setCreateTime(rs.getTimestamp("create_time"));
            userDAO.setUpdateTime(rs.getTimestamp("update_time"));
            return userDAO;
        }
    };

    @Override
    public UserBO getUserByUsername(String username) {
        try {
            String sql = "SELECT * FROM user WHERE username = ?";
            UserDAO userDAO = jdbcTemplate.queryForObject(sql, userDAORowMapper, username);
            return Converter.INSTANCE.userDAOToBO(userDAO);
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
            UserDAO userDAO = jdbcTemplate.queryForObject(sql, userDAORowMapper, userId);
            return Converter.INSTANCE.userDAOToBO(userDAO);
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
            UserDAO userDAO = Converter.INSTANCE.userBOToDAO(userBO);
            String sql = "INSERT INTO user (username, password, role_id, status, create_time, update_time) VALUES (?, ?, ?, ?, NOW(), NOW())";
            jdbcTemplate.update(sql, userDAO.getUsername(), userDAO.getPassword(), userDAO.getRoleId(), userDAO.getStatus());
            logger.info("保存用户成功: {}", userBO.getUsername());
        } catch (Exception e) {
            logger.error("保存用户失败: {}", e.getMessage(), e);
            throw new RuntimeException("保存用户失败", e);
        }
    }

    @Override
    public void updateUser(UserBO userBO) {
        try {
            UserDAO userDAO = Converter.INSTANCE.userBOToDAO(userBO);
            String sql = "UPDATE user SET username = ?, password = ?, role_id = ?, status = ?, update_time = NOW() WHERE id = ?";
            int rowsAffected = jdbcTemplate.update(sql, userDAO.getUsername(), userDAO.getPassword(), userDAO.getRoleId(), userDAO.getStatus(), userDAO.getId());
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
            List<UserDAO> userDAOs = jdbcTemplate.query(sql, userDAORowMapper);
            return userDAOs.stream()
                    .map(Converter.INSTANCE::userDAOToBO)
                    .collect(java.util.stream.Collectors.toList());
        } catch (Exception e) {
            logger.error("获取所有用户失败: {}", e.getMessage(), e);
            throw new RuntimeException("获取所有用户失败", e);
        }
    }
}
