package com.qiubw.repository.impl;

import com.qiubw.domain.bo.UserBO;
import com.qiubw.repository.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceImplTest {
    
    @Autowired
    private UserService userService;
    
    private UserBO testUser;
    
    @BeforeEach
    void setUp() {
        testUser = new UserBO();
        testUser.setUsername("testuser");
        testUser.setPassword("test123");
        testUser.setRoleId(2L);
        testUser.setStatus(1);
    }
    
    @Test
    void testGetUserByUsername() {
        // 测试获取现有用户
        UserBO adminUser = userService.getUserByUsername("admin");
        assertNotNull(adminUser);
        assertEquals("admin", adminUser.getUsername());
        
        // 测试获取不存在的用户
        UserBO nonExistentUser = userService.getUserByUsername("nonexistent");
        assertNull(nonExistentUser);
    }
    
    @Test
    void testGetUserById() {
        // 测试获取现有用户
        UserBO adminUser = userService.getUserByUsername("admin");
        assertNotNull(adminUser);
        
        UserBO userById = userService.getUserById(adminUser.getId());
        assertNotNull(userById);
        assertEquals(adminUser.getId(), userById.getId());
        
        // 测试获取不存在的用户
        UserBO nonExistentUser = userService.getUserById(999L);
        assertNull(nonExistentUser);
    }
    
    @Test
    void testSaveUser() {
        // 保存测试用户
        userService.saveUser(testUser);
        
        // 验证用户是否保存成功
        UserBO savedUser = userService.getUserByUsername("testuser");
        assertNotNull(savedUser);
        assertEquals("testuser", savedUser.getUsername());
        assertEquals(2L, savedUser.getRoleId());
        assertEquals(1, savedUser.getStatus());
    }
    
    @Test
    void testUpdateUser() {
        // 先保存用户
        userService.saveUser(testUser);
        UserBO savedUser = userService.getUserByUsername("testuser");
        assertNotNull(savedUser);
        
        // 更新用户信息
        savedUser.setPassword("updated123");
        savedUser.setStatus(0);
        userService.updateUser(savedUser);
        
        // 验证更新是否成功
        UserBO updatedUser = userService.getUserByUsername("testuser");
        assertNotNull(updatedUser);
        assertEquals("updated123", updatedUser.getPassword());
        assertEquals(0, updatedUser.getStatus());
    }
    
    @Test
    void testDeleteUser() {
        // 先保存用户
        userService.saveUser(testUser);
        UserBO savedUser = userService.getUserByUsername("testuser");
        assertNotNull(savedUser);
        
        // 删除用户
        userService.deleteUser(savedUser.getId());
        
        // 验证用户是否删除成功
        UserBO deletedUser = userService.getUserByUsername("testuser");
        assertNull(deletedUser);
    }
}
