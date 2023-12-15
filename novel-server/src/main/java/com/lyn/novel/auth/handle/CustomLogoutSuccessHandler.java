package com.lyn.novel.auth.handle;


import com.lyn.novel.entity.LoginUser;
import com.lyn.novel.result.RestResult;
import com.lyn.novel.utils.RedisUtils;
import com.lyn.novel.utils.ResponseUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author crush
 * 退出调用方法
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    private final RedisUtils redisUtils;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userId = loginUser.getId();
        log.info("用户{}退出登录", loginUser.getId());
        //将用户缓存清除
        redisUtils.deleteObject(String.valueOf(userId));
        //清除当前安全上下文
        SecurityContextHolder.clearContext();
        //返回结果
        RestResult restResult = RestResult.success();
        ResponseUtils.result(response, restResult.getCode(), restResult);

    }
}
