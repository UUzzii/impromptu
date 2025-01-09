package com.common.result;

import com.common.enums.ResultEnum;
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

    public ResultVO(ResultEnum resultEnum) {
        this.code = resultEnum.getCode();
        this.msg = resultEnum.getMessage();
    }

    public ResultVO(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

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
