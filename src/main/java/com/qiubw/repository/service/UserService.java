package com.qiubw.repository.service;

import com.qiubw.domain.bo.UserBO;

public interface UserService {
    UserBO getUserByUsername(String username);
    UserBO getUserById(Long userId);
    void saveUser(UserBO userBO);
    void updateUser(UserBO userBO);
    void deleteUser(Long userId);
}
