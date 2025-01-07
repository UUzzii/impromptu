package com.impromptu.admin.controller;

import com.common.result.ResultVO;
import com.impromptu.admin.dto.AdminUserDTO;
import com.impromptu.admin.dto.AdminUserSelectDTO;
import com.impromptu.admin.service.AdminUserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 后台用户表(AdminUser)表控制层
 *
 * @author sp
 * @since 2025-01-03 16:47:30
 */
@RestController
@RequestMapping("/adminUser")
public class AdminUserController {

    @Resource
    private AdminUserService adminUserService;


    /**
     * 新增
     * @param dto
     * @return
     */
    @PostMapping("/add")
    public ResultVO<?> add(@RequestBody AdminUserDTO dto) {
        return adminUserService.add(dto);
    }

    /**
     * 分页查询
     * @param dto
     * @return
     */
    @PostMapping("/page")
    public ResultVO<?> page(@RequestBody AdminUserSelectDTO dto) {
        return adminUserService.page(dto);
    }

    /**
     * 修改
     * @param dto
     * @return
     */
    @PostMapping("/update")
    public ResultVO<?> update(@RequestBody AdminUserDTO dto) {
        return adminUserService.update(dto);
    }
}

