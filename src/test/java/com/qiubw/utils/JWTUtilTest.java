package com.qiubw.utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JWTUtilTest {

    @Test
    public void testGenerateToken() {
        String userId = "123";
        String token = JWTUtil.generateToken(userId);
        assertNotNull(token);
        assertTrue(token.length() > 0);
    }

    @Test
    public void testGetUserIdFromToken() {
        String userId = "123";
        String token = JWTUtil.generateToken(userId);
        String extractedUserId = JWTUtil.getUserIdFromToken(token);
        assertEquals(userId, extractedUserId);
    }

    @Test
    public void testValidateToken() {
        String userId = "123";
        String token = JWTUtil.generateToken(userId);
        boolean isValid = JWTUtil.validateToken(token);
        assertTrue(isValid);
    }

    @Test
    public void testValidateInvalidToken() {
        String invalidToken = "invalid-token";
        boolean isValid = JWTUtil.validateToken(invalidToken);
        assertFalse(isValid);
    }
}
