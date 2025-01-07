package com.common.enums;

import lombok.Getter;

/**
 * 异常code枚举
 * @author 石鹏
 * @date 2025/1/7 10:42
 */
@Getter
public enum ResultEnum {

    SUCCESS(200, "操作成功"),
    ERROR(500, "系统内部错误，请稍后重试");

    private final Integer code;

    private final String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
