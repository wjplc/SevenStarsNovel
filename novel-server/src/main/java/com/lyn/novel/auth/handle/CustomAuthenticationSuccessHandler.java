package com.lyn.novel.auth.handle;

import com.lyn.novel.constant.CacheConsts;
import com.lyn.novel.entity.LoginUser;
import com.lyn.novel.properties.JwtProperties;
import com.lyn.novel.result.RestResult;
import com.lyn.novel.utils.JwtUtils;
import com.lyn.novel.utils.RedisUtils;
import com.lyn.novel.utils.ResponseUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 处理登录成功处理
 * @Author: crush
 * @Date: 2021-09-09 9:21
 * version 1.0
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtProperties jwtProperties;

    private final RedisUtils<LoginUser> redisUtils;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("认证成功，将为您生成token");
        //根据用户id生成jwt
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String token = JwtUtils.createToken(String.valueOf(loginUser.getId()), jwtProperties.getSecretKey(), jwtProperties.getTtl());
        log.info("token = {}",token);
        //将用户信息保存到redis
        String cacheKey = CacheConsts.REDIS_CACHE_PREFIX + CacheConsts.USER_CACHE_NAME + String.valueOf(loginUser.getId());
        redisUtils.setCacheObject(cacheKey, loginUser, jwtProperties.getTtl(), TimeUnit.SECONDS);
        //将token返回给前端
        Map<String, String> map = new HashMap<>();
        map.put(jwtProperties.getTokenName(), token);
        RestResult<Map> restResult = RestResult.success(map);
        ResponseUtils.result(response,restResult.getCode(),restResult);
    }
}
