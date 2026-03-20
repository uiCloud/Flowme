package com.qiubw.repository.impl;

import com.qiubw.domain.Converter;
import com.qiubw.domain.bo.PermissionBO;
import com.qiubw.domain.entity.Permission;
import com.qiubw.repository.mapper.PermissionMapper;
import com.qiubw.repository.service.PermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PermissionServiceImpl implements PermissionService {
    private static final Logger logger = LoggerFactory.getLogger(PermissionServiceImpl.class);
    
    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public PermissionBO getPermissionById(Long permissionId) {
        try {
            Permission permission = permissionMapper.selectByPrimaryKey(permissionId);
            return permission != null ? Converter.INSTANCE.permissionDAOToBO(permission) : null;
        } catch (Exception e) {
            logger.error("获取权限失败: {}", e.getMessage(), e);
            throw new RuntimeException("获取权限失败", e);
        }
    }

    @Override
    public List<PermissionBO> getAllPermissions() {
        try {
            List<Permission> permissions = permissionMapper.selectByExample(null);
            return permissions.stream()
                    .map(Converter.INSTANCE::permissionDAOToBO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("获取所有权限失败: {}", e.getMessage(), e);
            throw new RuntimeException("获取所有权限失败", e);
        }
    }

    @Override
    public void savePermission(PermissionBO permissionBO) {
        try {
            Permission permission = Converter.INSTANCE.permissionBOToDAO(permissionBO);
            permissionMapper.insert(permission);
            logger.info("保存权限成功: {}", permissionBO.getName());
        } catch (Exception e) {
            logger.error("保存权限失败: {}", e.getMessage(), e);
            throw new RuntimeException("保存权限失败", e);
        }
    }

    @Override
    public void updatePermission(PermissionBO permissionBO) {
        try {
            Permission permission = Converter.INSTANCE.permissionBOToDAO(permissionBO);
            int rowsAffected = permissionMapper.updateByPrimaryKey(permission);
            if (rowsAffected == 0) {
                throw new RuntimeException("未找到要更新的权限");
            }
            logger.info("更新权限成功: {}", permissionBO.getId());
        } catch (RuntimeException e) {
            logger.error("更新权限失败: {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            logger.error("更新权限失败: {}", e.getMessage(), e);
            throw new RuntimeException("更新权限失败", e);
        }
    }

    @Override
    public void deletePermission(Long permissionId) {
        try {
            int rowsAffected = permissionMapper.deleteByPrimaryKey(permissionId);
            if (rowsAffected == 0) {
                throw new RuntimeException("未找到要删除的权限");
            }
            logger.info("删除权限成功: {}", permissionId);
        } catch (RuntimeException e) {
            logger.error("删除权限失败: {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            logger.error("删除权限失败: {}", e.getMessage(), e);
            throw new RuntimeException("删除权限失败", e);
        }
    }
}
