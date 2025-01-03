package com.impromptu.admin.dto;

import lombok.Data;

/**
 * 后台用户表(AdminUser)表数据传输对象
 * @author 石鹏
 * @date 2025/1/3 16:52
 */
@Data
public class AdminUserDTO {

    /**
     * 主键
     */
    private Integer id;

    /**
     * 账号
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 名称
     */
    private String name;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 性别：0女，1男
     */
    private Integer sex;

    /**
     * 头像
     */
    private String avatar;
}
