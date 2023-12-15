package com.lyn.novel.controller.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {
    @GetMapping
    public String test(){
        return "success";
    }

    @GetMapping("/auth/1")
    public String test1(){
        log.info("auth success");
        return "auth success";
    }

    @GetMapping("/auth/2")
    public String test2(){
        log.info("auth success");
        return "auth success";
    }

}
