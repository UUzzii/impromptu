package com.common.result;

import com.common.enums.ResultEnum;

/**
 * 返回工具类
 * @author 石鹏
 * @date 2024/11/13 17:48
 */
public class ResultUtil {

    public static <T> ResultVO<T> success(T data) {
        ResultVO<T> resultVO = new ResultVO<>(ResultEnum.SUCCESS);
        resultVO.setData(data);
        return resultVO;
    }

    public static <T> ResultVO<T> success() {
        return success(null);
    }

    public static <T> ResultVO<T> error(String msg) {
        return new ResultVO<>(ResultEnum.ERROR.getCode(), msg);
    }

    public static <T> ResultVO<T> result(Integer code, String msg) {
        return new ResultVO<>(code, msg);
    }
}
