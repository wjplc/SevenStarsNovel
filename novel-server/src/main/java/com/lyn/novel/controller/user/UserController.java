package com.lyn.novel.controller.user;

import com.lyn.novel.service.UserService;
import com.lyn.novel.result.RestResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 生成验证码并发送
     * @param phone
     * @return
     */
    @PostMapping("register/sendVerifyCode/1/{phone}")
    public RestResult<Void> sendRandomVerifyCodeForPhone(@PathVariable String phone){
        userService.sendRandomVerifyCodeForPhone(phone);
        return RestResult.success();
    }

    @PostMapping("/register/sendVerifyCode/2/{email}")
    public RestResult<Void> sendRandomVerifyCodeForEmail(@PathVariable String email){
        userService.sendRandomVerifyCodeForEmail(email);
        return RestResult.success();
    }
}
