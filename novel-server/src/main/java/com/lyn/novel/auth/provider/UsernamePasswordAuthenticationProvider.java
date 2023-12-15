package com.lyn.novel.auth.provider;

import com.lyn.novel.auth.service.CustomUserDetailsService;
import com.lyn.novel.auth.token.CustomUsernamePasswordAuthenticationToken;
import com.lyn.novel.constant.ResponseCode;
import com.lyn.novel.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {
    private CustomUserDetailsService userDetailsService;

    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("用户名密码登录请求开始验证...");
        if (!supports(authentication.getClass())){
            return null;
        }
        CustomUsernamePasswordAuthenticationToken token = (CustomUsernamePasswordAuthenticationToken) authentication;
        //从数据库获取用户信息
        Integer identityType = (Integer) token.getDetails();
        UserDetails userDetails = this.userDetailsService.loadUserByIdentifier(identityType, token.getName());
        if(userDetails == null){
            throw new BusinessException(ResponseCode.NOT_FOUND);
        }
        String presentedPassword = authentication.getCredentials().toString();
        //对比密码
        if(!this.passwordEncoder.matches(presentedPassword, userDetails.getPassword())){
            log.info("密码错误，请重试");
//            throw new BusinessException(ResponseCode.UNAUTHORIZED);
            throw new BadCredentialsException("密码错误，请重试");
        }
        //封装验证成功后的token
        CustomUsernamePasswordAuthenticationToken successToken = new CustomUsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        successToken.setDetails(token.getDetails());
        return successToken;

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return CustomUsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public void setUserDetailsService(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    protected CustomUserDetailsService getUserDetailsService() {
        return this.userDetailsService;
    }

    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public PasswordEncoder getPasswordEncoder() {
        return this.passwordEncoder;
    }
}
