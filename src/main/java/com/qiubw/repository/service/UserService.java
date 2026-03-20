package com.qiubw.repository.service;

import com.qiubw.domain.bo.UserBO;

import java.util.List;

public interface UserService {
    UserBO getUserByUsername(String username);
    UserBO getUserById(Long userId);
    List<UserBO> getAllUsers();
    void saveUser(UserBO userBO);
    void updateUser(UserBO userBO);
    void deleteUser(Long userId);
}
