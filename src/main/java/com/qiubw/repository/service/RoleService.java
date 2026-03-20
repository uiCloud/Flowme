package com.qiubw.repository.service;

import com.qiubw.domain.bo.RoleBO;

import java.util.List;

public interface RoleService {
    RoleBO getRoleById(Long id);
    List<RoleBO> getAllRoles();
    void saveRole(RoleBO roleBO);
    void updateRole(RoleBO roleBO);
    void deleteRole(Long id);
    void bindUser(Long roleId, Long userId);
    void unbindUser(Long roleId, Long userId);
    void assignMenus(Long roleId, List<Long> menuIds);
}
