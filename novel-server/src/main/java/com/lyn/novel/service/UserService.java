package com.lyn.novel.service;

public interface UserService {

    void sendRandomVerifyCodeForPhone(String phone);

    void sendRandomVerifyCodeForEmail(String email);
}
