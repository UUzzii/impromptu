package com.impromptu.admin.controller;

import com.common.result.ResultVO;
import com.impromptu.admin.dto.AdminUserDTO;
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
    @PostMapping()
    public ResultVO<?> add(@RequestBody AdminUserDTO dto) {
        return adminUserService.add(dto);
    }
}

