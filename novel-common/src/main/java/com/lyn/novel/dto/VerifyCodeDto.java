package com.lyn.novel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 发送验证码
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerifyCodeDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String uuid;
    private String contact;
    private String verifyCode;
}
