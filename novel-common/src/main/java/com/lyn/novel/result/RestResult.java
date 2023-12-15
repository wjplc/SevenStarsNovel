package com.lyn.novel.result;

import com.lyn.novel.constant.ResponseCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class RestResult <T> implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 响应码
     */
    @Schema(description = "错误码，00000-没有错误")
    private Integer code;

    /**
     * 响应消息
     */
    @Schema(description = "响应消息")
    private String message;

    /**
     * 响应数据
     */
    @Schema(description = "响应数据")
    private T data;

    private RestResult() {

    }

    private RestResult(ResponseCode code) {
        this.code = code.getCode();
        this.message = code.getMessage();
    }

//    private RestResult(T data) {
//        this();
//        this.data = data;
//    }

    private RestResult(ResponseCode responseCode, T data){
        this.code = responseCode.getCode();
        this.message = responseCode.getMessage();
        this.data = data;
    }

    /**
     * 业务处理成功,无数据返回
     */
    public static RestResult<Void> success() {
        return new RestResult<>(ResponseCode.OK);
    }

    /**
     * 业务处理成功，有数据返回
     */
    public static <T> RestResult<T> success(T data) {
        return new RestResult<>(ResponseCode.OK, data);
    }

    /**
     * 业务处理失败
     */

    public static RestResult<Void> failure(ResponseCode failCode) {
        return new RestResult<>(failCode);
    }

    public static RestResult<Void> failure(ResponseCode failCode, String message) {
        RestResult<Void> restResult = new RestResult<>(failCode);
        restResult.message = restResult.getMessage() + "：" + message;
        return restResult;
    }

    public static RestResult<Void> error(ResponseCode errorCode) {
        return new RestResult<>(errorCode);
    }

    public static RestResult<Void> error(ResponseCode errorCode, String message) {
        RestResult<Void> restResult = new RestResult<>(errorCode);
        restResult.message = restResult.getMessage() + "：" + message;
        return restResult;
    }


//
//    /**
//     * 判断是否成功
//     */
//    public boolean isOk() {
//        return Objects.equals(this.code, ResponseCode.OK.getCode());
//    }
}
