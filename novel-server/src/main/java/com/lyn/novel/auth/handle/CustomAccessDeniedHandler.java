package com.lyn.novel.auth.handle;


import com.lyn.novel.constant.ResponseCode;
import com.lyn.novel.result.RestResult;
import com.lyn.novel.utils.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 权限不足回调
 * @author wjp
 */
@Component
@Slf4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        log.info("权限不足");
        String message = "您暂无权限访问，请联系管理员或重试";
        RestResult restResult = RestResult.failure(ResponseCode.UNAUTHORIZED, "您暂无权限访问，请联系管理员或重试");
        ResponseUtils.result(response, restResult.getCode(), restResult);
    }

}