package com.lyn.novel.exception;


import com.lyn.novel.constant.ResponseCode;
import lombok.Getter;

/**
 * 自定义业务异常，用于处理用户请求时，业务错误时抛出
 *
 * @author wjp
 * @date 2023/12/9
 */
@Getter
public class BusinessException extends RuntimeException {

    private final ResponseCode responseCode;

    public BusinessException(ResponseCode responseCode) {
        // 不调用父类 Throwable的fillInStackTrace() 方法生成栈追踪信息，提高应用性能
        // 构造器之间的调用必须在第一行
        super(responseCode.getMessage());
        this.responseCode = responseCode;
    }


}
