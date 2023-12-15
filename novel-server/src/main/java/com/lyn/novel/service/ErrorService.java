package com.lyn.novel.service;


import javax.servlet.http.HttpServletRequest;

/**
 * @author wjp
 */
public interface ErrorService {

    public void jwtFilterError(HttpServletRequest request);

}
