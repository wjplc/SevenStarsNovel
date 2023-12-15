package com.lyn.novel.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author: crush
 * @Date: 2021-09-08 20:41
 * version 1.0
 */
public class ResponseUtils {

    private  final static String RESPONSE_TYPE= "application/json;charset=utf-8";

    public static void result(HttpServletResponse response, int code, Object msg) throws IOException {
        response.setStatus(code);
        response.setContentType(RESPONSE_TYPE);
        PrintWriter writer = response.getWriter();
        writer.write(new ObjectMapper().writeValueAsString(msg));
    }


}
