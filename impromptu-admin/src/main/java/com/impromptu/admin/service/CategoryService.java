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
}

