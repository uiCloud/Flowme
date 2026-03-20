package com.qiubw.controller;

import com.qiubw.constant.Constants;
import com.qiubw.constant.ErrorMessage;
import com.qiubw.domain.Converter;
import com.qiubw.domain.bo.RoleBO;
import com.qiubw.domain.dto.RoleDTO;
import com.qiubw.domain.WebResult;
import com.qiubw.repository.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/role")
public class RoleController {
    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);
    
    @Autowired
    private RoleService roleService;

    @GetMapping("/list")
    public WebResult<List<RoleDTO>> getAllRoles() {
        try {
            List<RoleBO> roleBOList = roleService.getAllRoles();
            List<RoleDTO> roleDTOList = Converter.INSTANCE.roleBOListToDTOList(roleBOList);
            return WebResult.success(roleDTOList);
        } catch (Exception e) {
            logger.error("获取角色列表失败: {}", e.getMessage(), e);
            return WebResult.error(Constants.INTERNAL_SERVER_ERROR, ErrorMessage.ROLE_LIST_FAILED);
        }
    }

    @GetMapping("/detail")
    public WebResult<RoleDTO> getRoleById(@RequestParam Long roleId) {
        try {
            RoleBO roleBO = roleService.getRoleById(roleId);
            RoleDTO roleDTO = Converter.INSTANCE.roleBOToDTO(roleBO);
            return WebResult.success(roleDTO);
        } catch (Exception e) {
            logger.error("获取角色详情失败: {}", e.getMessage(), e);
            return WebResult.error(Constants.INTERNAL_SERVER_ERROR, ErrorMessage.ROLE_DETAIL_FAILED);
        }
    }

    @PostMapping("/create")
    public WebResult<Void> createRole(@RequestBody RoleDTO roleDTO) {
        try {
            RoleBO roleBO = Converter.INSTANCE.roleDTOToBO(roleDTO);
            roleService.saveRole(roleBO);
            logger.info("创建角色成功: {}", roleDTO.getName());
            return WebResult.success(ErrorMessage.ROLE_CREATE_SUCCESS);
        } catch (Exception e) {
            logger.error("创建角色失败: {}", e.getMessage(), e);
            return WebResult.error(Constants.INTERNAL_SERVER_ERROR, ErrorMessage.ROLE_CREATE_FAILED);
        }
    }

    @PutMapping("/update")
    public WebResult<Void> updateRole(@RequestBody RoleDTO roleDTO) {
        try {
            RoleBO roleBO = Converter.INSTANCE.roleDTOToBO(roleDTO);
            roleService.updateRole(roleBO);
            logger.info("更新角色成功: {}", roleDTO.getId());
            return WebResult.success(ErrorMessage.ROLE_UPDATE_SUCCESS);
        } catch (Exception e) {
            logger.error("更新角色失败: {}", e.getMessage(), e);
            return WebResult.error(Constants.INTERNAL_SERVER_ERROR, ErrorMessage.ROLE_UPDATE_FAILED);
        }
    }

    @DeleteMapping("/delete")
    public WebResult<Void> deleteRole(@RequestParam Long roleId) {
        try {
            roleService.deleteRole(roleId);
            logger.info("删除角色成功: {}", roleId);
            return WebResult.success(ErrorMessage.ROLE_DELETE_SUCCESS);
        } catch (Exception e) {
            logger.error("删除角色失败: {}", e.getMessage(), e);
            return WebResult.error(Constants.INTERNAL_SERVER_ERROR, ErrorMessage.ROLE_DELETE_FAILED);
        }
    }

    @PostMapping("/bind-user")
    public WebResult<Void> bindUser(@RequestParam Long roleId, @RequestParam Long userId) {
        try {
            roleService.bindUser(roleId, userId);
            logger.info("角色绑定用户成功: 角色ID={}, 用户ID={}", roleId, userId);
            return WebResult.success(ErrorMessage.ROLE_BIND_USER_SUCCESS);
        } catch (Exception e) {
            logger.error("角色绑定用户失败: {}", e.getMessage(), e);
            return WebResult.error(Constants.INTERNAL_SERVER_ERROR, ErrorMessage.ROLE_BIND_USER_FAILED);
        }
    }

    @PostMapping("/unbind-user")
    public WebResult<Void> unbindUser(@RequestParam Long roleId, @RequestParam Long userId) {
        try {
            roleService.unbindUser(roleId, userId);
            logger.info("角色解绑用户成功: 角色ID={}, 用户ID={}", roleId, userId);
            return WebResult.success(ErrorMessage.ROLE_UNBIND_USER_SUCCESS);
        } catch (Exception e) {
            logger.error("角色解绑用户失败: {}", e.getMessage(), e);
            return WebResult.error(Constants.INTERNAL_SERVER_ERROR, ErrorMessage.ROLE_UNBIND_USER_FAILED);
        }
    }

    @PostMapping("/assign-menu")
    public WebResult<Void> assignMenu(@RequestParam Long roleId, @RequestParam List<Long> menuIds) {
        try {
            roleService.assignMenus(roleId, menuIds);
            logger.info("角色分配菜单权限成功: 角色ID={}, 菜单数量={}", roleId, menuIds.size());
            return WebResult.success(ErrorMessage.ROLE_ASSIGN_MENU_SUCCESS);
        } catch (Exception e) {
            logger.error("角色分配菜单权限失败: {}", e.getMessage(), e);
            return WebResult.error(Constants.INTERNAL_SERVER_ERROR, ErrorMessage.ROLE_ASSIGN_MENU_FAILED);
        }
    }
}
