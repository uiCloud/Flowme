package com.qiubw.controller;

import com.qiubw.constant.Constants;
import com.qiubw.constant.ErrorMessage;
import com.qiubw.domain.bo.UserBO;
import com.qiubw.domain.dto.UserDTO;
import com.qiubw.domain.WebResult;
import com.qiubw.repository.service.UserService;
import com.qiubw.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class LoginController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public WebResult<Map<String, Object>> login(@RequestBody UserDTO userDTO) {
        try {
            UserBO userBO = userService.getUserByUsername(userDTO.getUsername());
            if (userBO == null) {
                return WebResult.error(Constants.UNAUTHORIZED, ErrorMessage.USERNAME_PASSWORD_ERROR);
            }
            // 这里应该验证密码，暂时跳过
            String token = JWTUtil.generateToken(userBO.getId().toString());
            Map<String, Object> data = new HashMap<>();
            data.put("token", token);
            data.put("user", userBO);
            return WebResult.success(ErrorMessage.LOGIN_SUCCESS, data);
        } catch (Exception e) {
            return WebResult.error(Constants.INTERNAL_SERVER_ERROR, ErrorMessage.LOGIN_FAILED);
        }
    }
}
