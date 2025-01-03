package com.impromptu.admin.entity;

import com.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 后台用户表(AdminUser)实体类
 *
 * @author sp
 * @since 2025-01-03 16:47:30
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AdminUser extends BaseEntity {

    private static final long serialVersionUID = -52745331135443421L;

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

