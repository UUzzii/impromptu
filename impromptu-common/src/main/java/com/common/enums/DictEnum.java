package com.common.enums;

import com.common.utils.DictUtil;
import lombok.Getter;

/**
 * @author 石鹏
 * @date 2025/2/20 11:12
 */
@Getter
public enum DictEnum {

    ADMIN_USER_STATUS("admin_user_status", DictUtil.FROM_SYSTEM_ADMIN, "用户的状态"),
    ADMIN_USER_SEX("admin_user_sex", DictUtil.FROM_SYSTEM_ADMIN, "用户的性别"),
    ;

    private final String code;

    private final String fromSystem;

    private final String name;

    DictEnum(String code, String fromSystem, String name) {
        this.code = code;
        this.fromSystem = fromSystem;
        this.name = name;
    }
}
