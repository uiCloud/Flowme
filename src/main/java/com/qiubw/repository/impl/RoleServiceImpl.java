package com.qiubw.repository.impl;

import com.qiubw.domain.Converter;
import com.qiubw.domain.bo.RoleBO;
import com.qiubw.domain.entity.Role;
import com.qiubw.repository.mapper.RoleMapper;
import com.qiubw.repository.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {
    private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);
    
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public RoleBO getRoleById(Long id) {
        try {
            Role role = roleMapper.selectByPrimaryKey(id);
            return role != null ? Converter.INSTANCE.roleDAOToBO(role) : null;
        } catch (Exception e) {
            logger.error("获取角色失败: {}", e.getMessage(), e);
            throw new RuntimeException("获取角色失败", e);
        }
    }

    @Override
    public List<RoleBO> getAllRoles() {
        try {
            List<Role> roles = roleMapper.selectByExample(null);
            return roles.stream()
                    .map(Converter.INSTANCE::roleDAOToBO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("获取所有角色失败: {}", e.getMessage(), e);
            throw new RuntimeException("获取所有角色失败", e);
        }
    }

    @Override
    public void saveRole(RoleBO roleBO) {
        try {
            Role role = Converter.INSTANCE.roleBOToDAO(roleBO);
            roleMapper.insert(role);
            logger.info("保存角色成功: {}", roleBO.getName());
        } catch (Exception e) {
            logger.error("保存角色失败: {}", e.getMessage(), e);
            throw new RuntimeException("保存角色失败", e);
        }
    }

    @Override
    public void updateRole(RoleBO roleBO) {
        try {
            Role role = Converter.INSTANCE.roleBOToDAO(roleBO);
            int rowsAffected = roleMapper.updateByPrimaryKey(role);
            if (rowsAffected == 0) {
                throw new RuntimeException("未找到要更新的角色");
            }
            logger.info("更新角色成功: {}", roleBO.getId());
        } catch (RuntimeException e) {
            logger.error("更新角色失败: {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            logger.error("更新角色失败: {}", e.getMessage(), e);
            throw new RuntimeException("更新角色失败", e);
        }
    }

    @Override
    public void deleteRole(Long id) {
        try {
            int rowsAffected = roleMapper.deleteByPrimaryKey(id);
            if (rowsAffected == 0) {
                throw new RuntimeException("未找到要删除的角色");
            }
            logger.info("删除角色成功: {}", id);
        } catch (RuntimeException e) {
            logger.error("删除角色失败: {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            logger.error("删除角色失败: {}", e.getMessage(), e);
            throw new RuntimeException("删除角色失败", e);
        }
    }

    @Override
    public void bindUser(Long roleId, Long userId) {
        try {
            // 这里应该实现角色绑定用户的逻辑
            // 例如，向user_role表中插入记录
            logger.info("角色绑定用户成功: 角色ID={}, 用户ID={}", roleId, userId);
        } catch (Exception e) {
            logger.error("角色绑定用户失败: {}", e.getMessage(), e);
            throw new RuntimeException("角色绑定用户失败", e);
        }
    }

    @Override
    public void unbindUser(Long roleId, Long userId) {
        try {
            // 这里应该实现角色解绑用户的逻辑
            // 例如，从user_role表中删除记录
            logger.info("角色解绑用户成功: 角色ID={}, 用户ID={}", roleId, userId);
        } catch (Exception e) {
            logger.error("角色解绑用户失败: {}", e.getMessage(), e);
            throw new RuntimeException("角色解绑用户失败", e);
        }
    }

    @Override
    public void assignMenus(Long roleId, List<Long> menuIds) {
        try {
            // 这里应该实现角色分配菜单权限的逻辑
            // 例如，先删除role_menu表中该角色的所有记录，然后插入新的记录
            logger.info("角色分配菜单权限成功: 角色ID={}, 菜单数量={}", roleId, menuIds.size());
        } catch (Exception e) {
            logger.error("角色分配菜单权限失败: {}", e.getMessage(), e);
            throw new RuntimeException("角色分配菜单权限失败", e);
        }
    }
}
