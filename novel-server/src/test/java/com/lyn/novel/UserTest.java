package com.lyn.novel;

import com.lyn.novel.utils.RedisUtils;
import com.lyn.novel.mapper.PermissionMapper;
import com.lyn.novel.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@SpringBootTest
public class UserTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private RedisUtils redisUtils;

    @Test
    public void sendRandomVerifyCodeForEmailTest(){
        String email = "3273700548@qq.com";
        userService.sendRandomVerifyCodeForEmail(email);
    }

    @Test
    public void sendRandomVerifyCodeForPhoneTest(){
        String phone = "18703985131";
        userService.sendRandomVerifyCodeForPhone(phone);

    }



}
