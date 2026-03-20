package com.qiubw.controller;

import com.qiubw.constant.Constants;
import com.qiubw.constant.ErrorMessage;
import com.qiubw.domain.Converter;
import com.qiubw.domain.bo.MenuBO;
import com.qiubw.domain.dto.MenuDTO;
import com.qiubw.domain.WebResult;
import com.qiubw.repository.service.MenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menu")
public class MenuController {
    private static final Logger logger = LoggerFactory.getLogger(MenuController.class);
    
    @Autowired
    private MenuService menuService;

    @GetMapping("/list")
    public WebResult<List<MenuDTO>> getMenusByUserId(@RequestParam Long userId) {
        try {
            List<MenuBO> menuBOList = menuService.getMenusByUserId(userId);
            List<MenuDTO> menuDTOList = Converter.INSTANCE.menuBOListToDTOList(menuBOList);
            return WebResult.success(menuDTOList);
        } catch (Exception e) {
            logger.error("获取菜单列表失败: {}", e.getMessage(), e);
            return WebResult.error(Constants.INTERNAL_SERVER_ERROR, ErrorMessage.MENU_LIST_FAILED);
        }
    }

    @GetMapping("/all")
    public WebResult<List<MenuDTO>> getAllMenus() {
        try {
            List<MenuBO> menuBOList = menuService.getAllMenus();
            List<MenuDTO> menuDTOList = Converter.INSTANCE.menuBOListToDTOList(menuBOList);
            return WebResult.success(menuDTOList);
        } catch (Exception e) {
            logger.error("获取所有菜单失败: {}", e.getMessage(), e);
            return WebResult.error(Constants.INTERNAL_SERVER_ERROR, ErrorMessage.MENU_LIST_FAILED);
        }
    }

    @GetMapping("/detail")
    public WebResult<MenuDTO> getMenuById(@RequestParam Long id) {
        try {
            MenuBO menuBO = menuService.getMenuById(id);
            MenuDTO menuDTO = Converter.INSTANCE.menuBOToDTO(menuBO);
            return WebResult.success(menuDTO);
        } catch (Exception e) {
            logger.error("获取菜单详情失败: {}", e.getMessage(), e);
            return WebResult.error(Constants.INTERNAL_SERVER_ERROR, "获取菜单详情失败");
        }
    }

    @PostMapping("/create")
    public WebResult<Void> create(@RequestBody MenuDTO menuDTO) {
        try {
            MenuBO menuBO = Converter.INSTANCE.menuDTOToBO(menuDTO);
            menuService.saveMenu(menuBO);
            logger.info("创建菜单成功: {}", menuDTO.getName());
            return WebResult.success("创建菜单成功");
        } catch (Exception e) {
            logger.error("创建菜单失败: {}", e.getMessage(), e);
            return WebResult.error(Constants.INTERNAL_SERVER_ERROR, "创建菜单失败");
        }
    }

    @PutMapping("/update")
    public WebResult<Void> update(@RequestBody MenuDTO menuDTO) {
        try {
            MenuBO menuBO = Converter.INSTANCE.menuDTOToBO(menuDTO);
            menuService.updateMenu(menuBO);
            logger.info("更新菜单成功: {}", menuDTO.getId());
            return WebResult.success("更新菜单成功");
        } catch (Exception e) {
            logger.error("更新菜单失败: {}", e.getMessage(), e);
            return WebResult.error(Constants.INTERNAL_SERVER_ERROR, "更新菜单失败");
        }
    }

    @DeleteMapping("/delete")
    public WebResult<Void> delete(@RequestParam Long id) {
        try {
            menuService.deleteMenu(id);
            logger.info("删除菜单成功: {}", id);
            return WebResult.success("删除菜单成功");
        } catch (Exception e) {
            logger.error("删除菜单失败: {}", e.getMessage(), e);
            return WebResult.error(Constants.INTERNAL_SERVER_ERROR, "删除菜单失败");
        }
    }
}

