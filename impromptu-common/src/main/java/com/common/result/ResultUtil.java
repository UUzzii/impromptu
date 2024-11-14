package com.common.result;

/**
 * 返回工具类
 * @author 石鹏
 * @date 2024/11/13 17:48
 */
public class ResultUtil {

    private static final Integer SUCCESS_CODE = 200;
    private static final Integer ERROR_CODE = 500;

    public static <T> ResultVO<T> success(T data) {
        ResultVO<T> resultVO = new ResultVO<>();
        resultVO.setCode(SUCCESS_CODE);
        resultVO.setMsg("success");
        resultVO.setData(data);
        return resultVO;
    }

    public static <T> ResultVO<T> success() {
        return success(null);
    }

    public static <T> ResultVO<T> error(Integer code, String msg) {
        ResultVO<T> resultVO = new ResultVO<>();
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        return resultVO;
    }

    public static <T> ResultVO<T> error(String msg) {
        return error(ERROR_CODE, msg);
    }
}
