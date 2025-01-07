package com.common.exception;

import com.common.enums.ResultEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 自定义异常
 * @author 石鹏
 * @date 2025/1/7 10:39
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BusinessException extends RuntimeException {

    private Integer code;

    public BusinessException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public BusinessException(ResultEnum resultEnum, String message) {
        super(message);
        this.code = resultEnum.getCode();
    }

    public BusinessException(ResultEnum resultEnum, Throwable cause) {
        super(resultEnum.getMessage(), cause);
        this.code = resultEnum.getCode();
    }
}
