package com.lyn.novel.controller.user;

import com.lyn.novel.service.ErrorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 86187
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/error")
public class ErrorController {

    private final ErrorService errorService;
    @RequestMapping("/jwtFilter")
    public void jwtFilterError(HttpServletRequest request){
        errorService.jwtFilterError(request);
    }

}
