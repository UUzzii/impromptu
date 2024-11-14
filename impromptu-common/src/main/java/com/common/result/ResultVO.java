package com.common.result;

import lombok.Data;

/**
 * 通用返回实体类
 * @author 石鹏
 * @date 2024/11/13 17:46
 */
@Data
public class ResultVO<T> {

    private Integer code;

    private String msg;

    private T data;
}
