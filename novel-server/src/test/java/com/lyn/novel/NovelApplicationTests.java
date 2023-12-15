package com.lyn.novel;


import com.lyn.novel.entity.LoginUser;
import com.lyn.novel.entity.User;
import com.lyn.novel.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NovelApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    void contextLoads() {
        User user = userMapper.selectOne(null);
        System.out.println(user);
        LoginUser loginUser = new LoginUser();
    }

}
