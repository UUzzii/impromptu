package com.impromptu.admin.controller;

import com.common.result.ResultVO;
import com.impromptu.admin.dto.CategorySelectDTO;
import com.impromptu.admin.entity.Category;
import com.impromptu.admin.service.CategoryService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 分类表(Category)表控制层
 *
 * @author sp
 * @since 2025-01-09 09:38:46
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Resource
    private CategoryService categoryService;


    /**
     * 查询分类树
     * @param dto
     * @return
     */
    @PostMapping("/tree")
    public ResultVO<?> tree(@RequestBody CategorySelectDTO dto) {
        return categoryService.tree(dto);
    }

    /**
     * 新增
     * @param category
     * @return
     */
    @PostMapping("/add")
    public ResultVO<?> add(@RequestBody Category category) {
        return categoryService.add(category);
    }
}

