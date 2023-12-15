package com.lyn.novel.exception;

import com.lyn.novel.constant.ResponseCode;
import com.lyn.novel.result.RestResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 *
 * @author wjp
 * @since 2023/12/09
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public RestResult<Void> handlerBusinessException(BusinessException e) {
        log.info("捕获业务异常");
        log.error(e.getMessage(), e);
        return RestResult.failure(e.getResponseCode(), e.getMessage());
    }

    @ExceptionHandler(AuthenticationException.class)
    public RestResult<Void> handleAuthenticationExcetion(AuthenticationException e){
        log.info("捕获认证异常");
        log.error(e.getMessage(), e);
        return RestResult.failure(ResponseCode.UNAUTHORIZED, e.getMessage());
    }

    //    /**
    //     * 处理数据校验异常
    //     */
    //    @ExceptionHandler(BindException.class)
    //    public RestResp<Void> handlerBindException(BindException e) {
    //        log.error(e.getMessage(), e);
    //        return RestResp.fail(ErrorCodeEnum.USER_REQUEST_PARAM_ERROR);
    //    }

    /**
     * 处理系统异常
     */
    @ExceptionHandler(Exception.class)
    public RestResult<Void> handlerException(Exception e) {
        log.info("捕获系统异常");
        log.error(e.getMessage(), e);
        return RestResult.failure(ResponseCode.SERVICE_UNAVAILABLE);
    }

}