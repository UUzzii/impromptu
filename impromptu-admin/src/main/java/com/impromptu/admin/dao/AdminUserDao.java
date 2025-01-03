package com.impromptu.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.impromptu.admin.entity.AdminUser;

import java.util.List;

/**
 * 后台用户表(AdminUser)表数据库访问层
 *
 * @author sp
 * @since 2025-01-03 16:47:30
 */
public interface AdminUserDao extends BaseMapper<AdminUser> {

    /**
    * 批量新增或按主键更新数据
    * @param list
    * @return
    */
    int insertOrUpdateBatch(List<AdminUser> list);
}

