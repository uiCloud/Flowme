package com.qiubw.repository.service;

import com.qiubw.domain.bo.PermissionBO;

import java.util.List;

public interface PermissionService {
    PermissionBO getPermissionById(Long id);
    List<PermissionBO> getAllPermissions();
    void savePermission(PermissionBO permissionBO);
    void updatePermission(PermissionBO permissionBO);
    void deletePermission(Long id);
}
