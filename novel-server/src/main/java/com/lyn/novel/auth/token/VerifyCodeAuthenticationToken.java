package com.lyn.novel.auth.token;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * 验证码登录的token对象
 * @author wjp
 */
public class VerifyCodeAuthenticationToken extends UsernamePasswordAuthenticationToken {

    public VerifyCodeAuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public VerifyCodeAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }


}
