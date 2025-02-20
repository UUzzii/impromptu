package com.impromptu.admin.vo;

import com.impromptu.admin.entity.AdminUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 后台用户表(AdminUser)表视图对象
 * @author 石鹏
 * @date 2025/2/20 14:48
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AdminUserVO extends AdminUser {

    private String sexText;

    private String statusText;
}
