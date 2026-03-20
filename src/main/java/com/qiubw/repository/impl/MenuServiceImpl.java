package com.qiubw.repository.impl;

import com.qiubw.domain.Converter;
import com.qiubw.domain.bo.MenuBO;
import com.qiubw.domain.entity.Menu;
import com.qiubw.repository.mapper.MenuMapper;
import com.qiubw.repository.service.MenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl implements MenuService {
    private static final Logger logger = LoggerFactory.getLogger(MenuServiceImpl.class);
    
    @Autowired
    private MenuMapper menuMapper;

    @Override
    public MenuBO getMenuById(Long id) {
        try {
            Menu menu = menuMapper.selectByPrimaryKey(id);
            return menu != null ? Converter.INSTANCE.menuDAOToBO(menu) : null;
        } catch (Exception e) {
            logger.error("获取菜单失败: {}", e.getMessage(), e);
            throw new RuntimeException("获取菜单失败", e);
        }
    }

    @Override
    public List<MenuBO> getAllMenus() {
        try {
            List<Menu> menus = menuMapper.selectByExample(null);
            return menus.stream()
                    .map(Converter.INSTANCE::menuDAOToBO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("获取所有菜单失败: {}", e.getMessage(), e);
            throw new RuntimeException("获取所有菜单失败", e);
        }
    }

    @Override
    public List<MenuBO> getMenusByUserId(Long userId) {
        try {
            // 这里应该根据用户ID查询其角色，然后根据角色查询权限，最后根据权限查询菜单
            // 暂时返回所有菜单
            return getAllMenus();
        } catch (Exception e) {
            logger.error("根据用户ID获取菜单失败: {}", e.getMessage(), e);
            throw new RuntimeException("根据用户ID获取菜单失败", e);
        }
    }

    @Override
    public void saveMenu(MenuBO menuBO) {
        try {
            Menu menu = Converter.INSTANCE.menuBOToDAO(menuBO);
            menuMapper.insert(menu);
            logger.info("保存菜单成功: {}", menuBO.getName());
        } catch (Exception e) {
            logger.error("保存菜单失败: {}", e.getMessage(), e);
            throw new RuntimeException("保存菜单失败", e);
        }
    }

    @Override
    public void updateMenu(MenuBO menuBO) {
        try {
            Menu menu = Converter.INSTANCE.menuBOToDAO(menuBO);
            int rowsAffected = menuMapper.updateByPrimaryKey(menu);
            if (rowsAffected == 0) {
                throw new RuntimeException("未找到要更新的菜单");
            }
            logger.info("更新菜单成功: {}", menuBO.getId());
        } catch (RuntimeException e) {
            logger.error("更新菜单失败: {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            logger.error("更新菜单失败: {}", e.getMessage(), e);
            throw new RuntimeException("更新菜单失败", e);
        }
    }

    @Override
    public void deleteMenu(Long id) {
        try {
            int rowsAffected = menuMapper.deleteByPrimaryKey(id);
            if (rowsAffected == 0) {
                throw new RuntimeException("未找到要删除的菜单");
            }
            logger.info("删除菜单成功: {}", id);
        } catch (RuntimeException e) {
            logger.error("删除菜单失败: {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            logger.error("删除菜单失败: {}", e.getMessage(), e);
            throw new RuntimeException("删除菜单失败", e);
        }
    }
}
