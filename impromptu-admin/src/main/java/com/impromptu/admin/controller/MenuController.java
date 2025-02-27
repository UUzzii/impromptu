package com.impromptu.admin.controller;

import com.common.result.ResultVO;
import com.impromptu.admin.entity.Menu;
import com.impromptu.admin.service.MenuService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 菜单表(Menu)表控制层
 *
 * @author sp
 * @since 2025-02-05 14:24:52
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Resource
    private MenuService menuService;


    /**
     * 获取菜单树
     * @return
     */
    @GetMapping("/tree")
    public ResultVO<?> tree() {
        return menuService.tree();
    }

    /**
     * 新增
     * @param menu
     * @return
     */
    @PostMapping("/add")
    public ResultVO<?> add(@RequestBody Menu menu) {
        return menuService.add(menu);
    }
}

