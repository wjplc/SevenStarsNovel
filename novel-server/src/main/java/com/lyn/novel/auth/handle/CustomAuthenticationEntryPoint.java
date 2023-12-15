package com.lyn.novel.auth.handle;


import com.lyn.novel.constant.ResponseCode;
import com.lyn.novel.result.RestResult;
import com.lyn.novel.utils.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 身份验证没有通过回调
 * @author crush
 */
@Component
@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        log.info("身份验证失败：" + e.getMessage());
        RestResult restResult = RestResult.failure(ResponseCode.UNAUTHORIZED, "请先登录");
        ResponseUtils.result(response, restResult.getCode(), restResult);
    }
}
