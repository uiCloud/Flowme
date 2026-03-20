package com.qiubw.repository.impl;

import com.qiubw.domain.bo.MenuBO;
import com.qiubw.domain.bo.UserBO;
import com.qiubw.domain.entity.Menu;
import com.qiubw.domain.entity.RolePermission;
import com.qiubw.repository.mapper.MenuMapper;
import com.qiubw.repository.mapper.RolePermissionMapper;
import com.qiubw.repository.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MenuServiceImplTest {

    @Mock
    private MenuMapper menuMapper;

    @Mock
    private UserService userService;

    @Mock
    private RolePermissionMapper rolePermissionMapper;

    @InjectMocks
    private MenuServiceImpl menuService;

    private MenuBO testMenu;
    private Menu testMenuEntity;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        testMenu = new MenuBO();
        testMenu.setId(1L);
        testMenu.setName("测试菜单");
        testMenu.setUrl("/test");
        testMenu.setType(1);

        testMenuEntity = new Menu();
        testMenuEntity.setId(1L);
        testMenuEntity.setName("测试菜单");
        testMenuEntity.setPath("/test");
        testMenuEntity.setType(1);
    }

    @Test
    public void testGetMenuById() {
        when(menuMapper.selectByPrimaryKey(1L)).thenReturn(testMenuEntity);

        MenuBO menu = menuService.getMenuById(1L);
        assertNotNull(menu);
        assertEquals(1L, menu.getId());
        assertEquals("测试菜单", menu.getName());

        when(menuMapper.selectByPrimaryKey(999L)).thenReturn(null);
        MenuBO nonExistentMenu = menuService.getMenuById(999L);
        assertNull(nonExistentMenu);
    }

    @Test
    public void testGetAllMenus() {
        List<Menu> menuEntities = new ArrayList<>();
        menuEntities.add(testMenuEntity);

        when(menuMapper.selectByExample(null)).thenReturn(menuEntities);

        List<MenuBO> menus = menuService.getAllMenus();
        assertNotNull(menus);
        assertEquals(1, menus.size());
        assertEquals("测试菜单", menus.get(0).getName());
    }

    @Test
    public void testGetMenusByUserId() {
        UserBO user = new UserBO();
        user.setId(1L);
        user.setRoleId(1L);

        List<Menu> menuEntities = new ArrayList<>();
        menuEntities.add(testMenuEntity);

        List<RolePermission> rolePermissions = new ArrayList<>();
        RolePermission rolePermission = new RolePermission();
        rolePermission.setRoleId(1L);
        rolePermission.setPermissionId(1L);
        rolePermissions.add(rolePermission);

        when(userService.getUserById(1L)).thenReturn(user);
        when(rolePermissionMapper.selectByExample(any())).thenReturn(rolePermissions);
        when(menuMapper.selectByExample(null)).thenReturn(menuEntities);

        List<MenuBO> menus = menuService.getMenusByUserId(1L);
        assertNotNull(menus);
        assertEquals(1, menus.size());

        when(userService.getUserById(999L)).thenReturn(null);
        List<MenuBO> emptyMenus = menuService.getMenusByUserId(999L);
        assertNotNull(emptyMenus);
        assertTrue(emptyMenus.isEmpty());
    }

    @Test
    public void testSaveMenu() {
        menuService.saveMenu(testMenu);
        verify(menuMapper, times(1)).insert(any(Menu.class));
    }

    @Test
    public void testUpdateMenu() {
        when(menuMapper.updateByPrimaryKey(any(Menu.class))).thenReturn(1);

        menuService.updateMenu(testMenu);
        verify(menuMapper, times(1)).updateByPrimaryKey(any(Menu.class));
    }

    @Test
    public void testDeleteMenu() {
        when(menuMapper.deleteByPrimaryKey(1L)).thenReturn(1);

        menuService.deleteMenu(1L);
        verify(menuMapper, times(1)).deleteByPrimaryKey(1L);
    }
}
