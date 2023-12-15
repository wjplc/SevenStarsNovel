package com.lyn.novel.auth.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lyn.novel.auth.service.CustomUserDetailsService;
import com.lyn.novel.constant.ResponseCode;
import com.lyn.novel.exception.AuthenticationException;
import com.lyn.novel.utils.RedisUtils;
import com.lyn.novel.constant.CacheConsts;
import com.lyn.novel.entity.LoginUser;
import com.lyn.novel.entity.User;
import com.lyn.novel.entity.UserAuth;
import com.lyn.novel.mapper.PermissionMapper;
import com.lyn.novel.mapper.UserAuthMapper;
import com.lyn.novel.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
/**
 * 获取用户信息
 * @author wjp
 */
@Slf4j
@Service
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {

    @Autowired
    private UserAuthMapper userAuthMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public UserDetails loadUserByIdentifier(Integer type, String identifier) {
        //根据登录方式和唯一标识获取用户认证信息
        LambdaQueryWrapper<UserAuth> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserAuth::getIdentityType, type)
                .eq(UserAuth::getIdentifier, identifier);
        UserAuth userAuth = userAuthMapper.selectOne(queryWrapper);
        //获取用户id并根据用户id获取用户基本信息
        if (Objects.isNull(userAuth)) {
            throw new AuthenticationException(ResponseCode.NOT_FOUND, "用户未注册");
        }
        Long userId = userAuth.getUserId();
        User user = userMapper.selectById(userId);
        //根据userid获取用户权限
        List<String> permissions = permissionMapper.getPermissionsByUseId(userId);
        //封装LoginUser
        LoginUser loginUser = new LoginUser();
        BeanUtil.copyProperties(user, loginUser);
        loginUser.setIdentifier(userAuth.getIdentifier());
        loginUser.setCredential(userAuth.getCredential());
        loginUser.setPermissions(permissions);
        log.info(String.valueOf(loginUser));
        return loginUser;
    }

    @Override
    public String loadCodeByIdentifier(String identifier) {
        Object verifyCode = redisUtils.getCacheObject(CacheConsts.VERIFYCODE_EMAIL_CHCHE_NAME + identifier);
        if(verifyCode == null){
            throw new AuthenticationException(ResponseCode.UNAUTHORIZED, "验证码已过期，请重新获取");
        }
        return String.valueOf(verifyCode);
    }



}
