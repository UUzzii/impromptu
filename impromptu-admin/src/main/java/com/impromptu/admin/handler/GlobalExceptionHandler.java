package com.impromptu.admin.handler;

import com.common.enums.ResultEnum;
import com.common.exception.BusinessException;
import com.common.result.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 * @author 石鹏
 * @date 2025/1/7 10:53
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 业务异常处理
     */
    @ExceptionHandler(BusinessException.class)
    public ResultVO<Void> handleBusinessException(BusinessException e) {
        log.error("业务异常", e);
        return ResultVO.result(e.getCode(), e.getMessage());
    }

    /**
     * 未知异常处理
     */
    @ExceptionHandler(Exception.class)
    public ResultVO<Void> handleException(Exception e) {
        log.error("系统异常", e);
        return ResultVO.result(ResultEnum.ERROR.getCode(), ResultEnum.ERROR.getMessage());
    }
}
