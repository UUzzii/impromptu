package com.impromptu.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.common.result.ResultVO;
import com.impromptu.admin.dto.CategorySelectDTO;
import com.impromptu.admin.entity.Category;

/**
 * 分类表(Category)表服务接口
 *
 * @author sp
 * @since 2025-01-09 09:38:46
 */
public interface CategoryService extends IService<Category> {

    /**
     * 查询分类树
     * @param dto
     * @return
     */
    ResultVO<?> tree(CategorySelectDTO dto);

    /**
     * 新增
     * @param category
     * @return
     */
    ResultVO<?> add(Category category);

    /**
     * 修改
     * @param category
     * @return
     */
    ResultVO<?> update(Category category);

    /**
     * 删除
     * @param category
     * @return
     */
    ResultVO<?> delete(Category category);
}

