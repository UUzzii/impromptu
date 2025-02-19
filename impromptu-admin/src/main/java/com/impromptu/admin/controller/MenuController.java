package com.impromptu.admin.controller;

import com.common.result.ResultVO;
import com.impromptu.admin.dto.CategorySelectDTO;
import com.impromptu.admin.service.MenuService;
import org.springframework.web.bind.annotation.*;
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
}

