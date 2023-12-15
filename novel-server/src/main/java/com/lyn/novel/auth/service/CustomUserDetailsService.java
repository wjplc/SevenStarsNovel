package com.lyn.novel.auth.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 获取用户信息
 * @author wjp
 */
public interface CustomUserDetailsService {

    /**
     * 根据登录方式和标识获取用户信息
     * @param identityType
     * @param identifier
     * @return
     * @throws UsernameNotFoundException
     */
    UserDetails loadUserByIdentifier(Integer identityType, String identifier);

    /**
     * 获取验证码
     * @param identifier
     * @return
     */
    String loadCodeByIdentifier(String identifier);

}
