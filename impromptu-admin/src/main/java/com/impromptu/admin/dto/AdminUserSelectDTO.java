package com.impromptu.admin.dto;

import com.common.dto.PageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 后台用户表(AdminUser)查询DTO
 * @author 石鹏
 * @date 2025/1/3 17:10
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AdminUserSelectDTO extends PageDTO {

    /**
     * 账号
     */
    private String account;

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

    /** 状态 */
    private Integer status;

    /** 创建时间-开始 */
    private String createTimeStart;

    /** 创建时间-结束 */
    private String createTimeEnd;
}
