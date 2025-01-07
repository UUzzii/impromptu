package com.impromptu.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.common.result.ResultVO;
import com.impromptu.admin.dto.AdminUserDTO;
import com.impromptu.admin.dto.AdminUserSelectDTO;
import com.impromptu.admin.entity.AdminUser;

/**
 * 后台用户表(AdminUser)表服务接口
 *
 * @author sp
 * @since 2025-01-03 16:47:30
 */
public interface AdminUserService extends IService<AdminUser> {

    /**
     * 新增
     * @param dto
     * @return
     */
    ResultVO<?> add(AdminUserDTO dto);

    /**
     * 分页查询
     * @param dto
     * @return
     */
    ResultVO<?> page(AdminUserSelectDTO dto);

    /**
     * 修改
     * @param dto
     * @return
     */
    ResultVO<?> update(AdminUserDTO dto);
}

