package com.qiubw.controller;

import com.qiubw.constant.Constants;
import com.qiubw.constant.ErrorMessage;
import com.qiubw.domain.Converter;
import com.qiubw.domain.bo.UserBO;
import com.qiubw.domain.dto.UserDTO;
import com.qiubw.domain.WebResult;
import com.qiubw.repository.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public WebResult<Void> register(@RequestBody UserDTO userDTO) {
        try {
            UserBO userBO = Converter.INSTANCE.userDTOToBO(userDTO);
            userService.saveUser(userBO);
            logger.info("用户注册成功: {}", userDTO.getUsername());
            return WebResult.success(ErrorMessage.USER_REGISTER_SUCCESS);
        } catch (Exception e) {
            logger.error("用户注册失败: {}", e.getMessage(), e);
            return WebResult.error(Constants.INTERNAL_SERVER_ERROR, ErrorMessage.USER_REGISTER_FAILED);
        }
    }

    @GetMapping("/list")
    public WebResult<List<UserDTO>> getAllUsers() {
        try {
            List<UserBO> userBOList = userService.getAllUsers();
            List<UserDTO> userDTOList = Converter.INSTANCE.userBOListToDTOList(userBOList);
            return WebResult.success(userDTOList);
        } catch (Exception e) {
            logger.error("获取用户列表失败: {}", e.getMessage(), e);
            return WebResult.error(Constants.INTERNAL_SERVER_ERROR, ErrorMessage.USER_LIST_FAILED);
        }
    }

    @GetMapping("/detail")
    public WebResult<UserDTO> getUserById(@RequestParam Long userId) {
        try {
            UserBO userBO = userService.getUserById(userId);
            UserDTO userDTO = Converter.INSTANCE.userBOToDTO(userBO);
            return WebResult.success(userDTO);
        } catch (Exception e) {
            logger.error("获取用户详情失败: {}", e.getMessage(), e);
            return WebResult.error(Constants.INTERNAL_SERVER_ERROR, ErrorMessage.USER_DETAIL_FAILED);
        }
    }

    @PostMapping("/create")
    public WebResult<Void> createUser(@RequestBody UserDTO userDTO) {
        try {
            UserBO userBO = Converter.INSTANCE.userDTOToBO(userDTO);
            userService.saveUser(userBO);
            logger.info("新增用户成功: {}", userDTO.getUsername());
            return WebResult.success(ErrorMessage.USER_CREATE_SUCCESS);
        } catch (Exception e) {
            logger.error("新增用户失败: {}", e.getMessage(), e);
            return WebResult.error(Constants.INTERNAL_SERVER_ERROR, ErrorMessage.USER_CREATE_FAILED);
        }
    }

    @PutMapping("/update")
    public WebResult<Void> updateUser(@RequestBody UserDTO userDTO) {
        try {
            UserBO userBO = Converter.INSTANCE.userDTOToBO(userDTO);
            userService.updateUser(userBO);
            logger.info("编辑用户成功: {}", userDTO.getId());
            return WebResult.success(ErrorMessage.USER_UPDATE_SUCCESS);
        } catch (Exception e) {
            logger.error("编辑用户失败: {}", e.getMessage(), e);
            return WebResult.error(Constants.INTERNAL_SERVER_ERROR, ErrorMessage.USER_UPDATE_FAILED);
        }
    }

    @DeleteMapping("/delete")
    public WebResult<Void> deleteUser(@RequestParam Long userId) {
        try {
            userService.deleteUser(userId);
            logger.info("删除用户成功: {}", userId);
            return WebResult.success(ErrorMessage.USER_DELETE_SUCCESS);
        } catch (Exception e) {
            logger.error("删除用户失败: {}", e.getMessage(), e);
            return WebResult.error(Constants.INTERNAL_SERVER_ERROR, ErrorMessage.USER_DELETE_FAILED);
        }
    }

    @PutMapping("/status")
    public WebResult<Void> updateUserStatus(@RequestParam Long userId, @RequestParam Integer status) {
        try {
            UserBO userBO = userService.getUserById(userId);
            if (userBO == null) {
                return WebResult.error(Constants.NOT_FOUND, ErrorMessage.USER_NOT_FOUND);
            }
            userBO.setStatus(status);
            userService.updateUser(userBO);
            logger.info("更新用户状态成功: {}, 状态: {}", userId, status);
            return WebResult.success(ErrorMessage.USER_STATUS_UPDATE_SUCCESS);
        } catch (Exception e) {
            logger.error("更新用户状态失败: {}", e.getMessage(), e);
            return WebResult.error(Constants.INTERNAL_SERVER_ERROR, ErrorMessage.USER_STATUS_UPDATE_FAILED);
        }
    }
}
