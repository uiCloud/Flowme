package com.qiubw.controller;

import com.qiubw.constant.Constants;
import com.qiubw.constant.ErrorMessage;
import com.qiubw.domain.bo.MenuBO;
import com.qiubw.domain.WebResult;
import com.qiubw.repository.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @GetMapping("/list")
    public WebResult<List<MenuBO>> list(@RequestParam Long userId) {
        try {
            List<MenuBO> menuBOList = menuService.getMenusByUserId(userId);
            return WebResult.success(menuBOList);
        } catch (Exception e) {
            return WebResult.error(Constants.INTERNAL_SERVER_ERROR, ErrorMessage.MENU_LIST_FAILED);
        }
    }
}
