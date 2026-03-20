package com.qiubw.repository.service;

import com.qiubw.domain.bo.MenuBO;

import java.util.List;

public interface MenuService {
    MenuBO getMenuById(Long id);
    List<MenuBO> getAllMenus();
    List<MenuBO> getMenusByUserId(Long userId);
    void saveMenu(MenuBO menuBO);
    void updateMenu(MenuBO menuBO);
    void deleteMenu(Long id);
}
