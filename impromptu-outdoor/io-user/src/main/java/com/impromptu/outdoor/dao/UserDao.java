package com.impromptu.outdoor.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.impromptu.outdoor.entity.User;

import java.util.List;

/**
 * 用户表(User)表数据库访问层
 *
 * @author sp
 * @since 2024-12-30 09:46:34
 */
public interface UserDao extends BaseMapper<User> {

    /**
    * 批量新增或按主键更新数据
    * @param list
    * @return
    */
    int insertOrUpdateBatch(List<User> list);
}

