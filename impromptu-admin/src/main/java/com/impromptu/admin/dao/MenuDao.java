package com.impromptu.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.impromptu.admin.entity.Menu;

import java.util.List;

/**
 * 菜单表(Menu)表数据库访问层
 *
 * @author sp
 * @since 2025-02-05 14:24:52
 */
public interface MenuDao extends BaseMapper<Menu> {

    /**
    * 批量新增或按主键更新数据
    * @param list
    * @return
    */
    int insertOrUpdateBatch(List<Menu> list);
}

