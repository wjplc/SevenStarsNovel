package com.lyn.novel.service.impl;

import com.lyn.novel.exception.AuthenticationException;
import com.lyn.novel.service.ErrorService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;


/**
 * @author wjp
 */
@Service
public class ErrorServiceImpl implements ErrorService {
    @Override
    public void jwtFilterError(HttpServletRequest request) {
        throw (AuthenticationException) request.getAttribute("error.jwtFilter");
    }
}
