package com.lyn.novel.filter;

import com.lyn.novel.constant.ResponseCode;
import com.lyn.novel.exception.AuthenticationException;
import com.lyn.novel.properties.JwtProperties;
import com.lyn.novel.utils.JwtUtils;
import com.lyn.novel.entity.LoginUser;
import com.lyn.novel.utils.RedisUtils;
import com.lyn.novel.constant.CacheConsts;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Slf4j
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private JwtProperties jwtProperties;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取token
        String token = request.getHeader(jwtProperties.getTokenName());
        if(!StringUtils.hasText(token)){
            filterChain.doFilter(request,response);
            return;
        }
        //解析token获取userId
        Claims claims;
        try {
            claims = JwtUtils.parseToken(jwtProperties.getSecretKey(), token);
        } catch (Exception e) {
            log.error("token无效");
            request.setAttribute("error.jwtFilter", new AuthenticationException(ResponseCode.UNAUTHORIZED, "token无效"));
            request.getRequestDispatcher("/error/jwtFilter").forward(request, response);
            return;
        }
        String userId = claims.getSubject();
        log.info("userId ========> {}",userId);
        //从redis中获取用户信息
        String cacheKey = CacheConsts.REDIS_CACHE_PREFIX + CacheConsts.USER_CACHE_NAME + String.valueOf(userId);
        LoginUser loginUser = (LoginUser) redisUtils.getCacheObject(cacheKey);
        if(Objects.isNull(loginUser)){
            log.error("用户未登录");
            request.setAttribute("error.jwtFilter", new AuthenticationException(ResponseCode.UNAUTHORIZED, "用户未登录"));
            request.getRequestDispatcher("/error/jwtFilter").forward(request, response);
            return;
        }
        //存入SecurityContextHolder
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request,response);
    }
}
