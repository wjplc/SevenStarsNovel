package com.lyn.novel.auth.filter;


import com.alibaba.fastjson.JSON;
import com.lyn.novel.auth.token.CustomUsernamePasswordAuthenticationToken;
import com.lyn.novel.constant.ResponseCode;
import com.lyn.novel.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

/**
 * @author wjp
 */
@Slf4j
public class CustomUsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private boolean postOnly = true;

    public CustomUsernamePasswordAuthenticationFilter() {
        super(new AntPathRequestMatcher("/login/username", "POST"));
    }

    @Autowired
    @Override
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        log.info("用户名登录请求被过滤器拦截");
        if (this.postOnly && !request.getMethod().equals("POST")) {
            throw new BusinessException(ResponseCode.BAD_REQUEST);
        } else {
            //从请求体中获取用户名密码
            String identifier = null;
            String credential = null;
            Integer type = null;
            try {
                StringBuilder builder = new StringBuilder();
                BufferedReader reader = request.getReader();
                String input = null;
                while ((input = reader.readLine()) != null) {
                    builder.append(input);
                }
                Map<String, String> params = JSON.parseObject(builder.toString(), Map.class);
                identifier = params.get("identifier");
                credential = params.get("credential");
                type = Integer.valueOf(params.get("type"));
                System.out.println(params);
            } catch (IOException e) {
                throw new BusinessException(ResponseCode.BAD_REQUEST);
            }

            /*
            封装 token
            将identityType和identifier拼接在一起
             */
            CustomUsernamePasswordAuthenticationToken authRequest = new CustomUsernamePasswordAuthenticationToken(identifier, credential);
            authRequest.setDetails(type);
            /*
            交给 manager 发证
             */
            return this.getAuthenticationManager().authenticate(authRequest);
        }
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }
}
