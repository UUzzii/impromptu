package com.impromptu.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.impromptu.admin.entity.Category;

import java.util.List;

/**
 * 分类表(Category)表数据库访问层
 *
 * @author sp
 * @since 2025-01-09 09:38:46
 */
public interface CategoryDao extends BaseMapper<Category> {

    /**
    * 批量新增或按主键更新数据
    * @param list
    * @return
    */
    int insertOrUpdateBatch(List<Category> list);
}

