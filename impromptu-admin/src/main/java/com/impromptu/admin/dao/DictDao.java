package com.impromptu.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.common.entity.Dict;

import java.util.List;

/**
 * 字典数据表(Dict)表数据库访问层
 *
 * @author sp
 * @since 2025-02-20 10:24:05
 */
public interface DictDao extends BaseMapper<Dict> {

    /**
    * 批量新增或按主键更新数据
    * @param list
    * @return
    */
    int insertOrUpdateBatch(List<Dict> list);
}

