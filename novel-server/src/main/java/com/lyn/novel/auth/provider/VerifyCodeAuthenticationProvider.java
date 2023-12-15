package com.lyn.novel.auth.provider;

import com.lyn.novel.auth.token.VerifyCodeAuthenticationToken;
import com.lyn.novel.auth.service.CustomUserDetailsService;
import com.lyn.novel.constant.ResponseCode;
import com.lyn.novel.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

@Slf4j
public class VerifyCodeAuthenticationProvider implements AuthenticationProvider {

    private CustomUserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("验证码登录请求开始验证...");
        if (!supports(authentication.getClass())){
            return null;
        }
        VerifyCodeAuthenticationToken token = (VerifyCodeAuthenticationToken) authentication;
        //根据手机号从redis获取验证码 TODO
        String verificationCode = userDetailsService.loadCodeByIdentifier(token.getName());
        if(!verificationCode.equals(token.getCredentials())){
            //对比验证码如果错误
            throw new BusinessException(ResponseCode.CUSTOM_ERROR);
        }
        //对比验证码如果正确，从数据库获取用户信息
        Integer identityType = (Integer) token.getDetails();
        UserDetails userDetails = this.userDetailsService.loadUserByIdentifier(identityType, token.getName());
        if(userDetails == null){
            throw new BusinessException(ResponseCode.NOT_FOUND);
        }
        //封装验证成功后的token
        VerifyCodeAuthenticationToken successToken = new VerifyCodeAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        successToken.setDetails(token.getDetails());
        return successToken;

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return VerifyCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public void setUserDetailsService(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    protected CustomUserDetailsService getUserDetailsService() {
        return this.userDetailsService;
    }
}
