package com.lyn.novel.service.impl;

import com.lyn.novel.service.UserService;
import com.lyn.novel.utils.RedisUtils;
import com.lyn.novel.constant.AmqpConsts;
import com.lyn.novel.constant.CacheConsts;
import com.lyn.novel.dto.VerifyCodeDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service

public class UserServiceImpl implements UserService {
    private static final int CAPTCHA_LENGTH = 6;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private RabbitTemplate rabbitTemplate;


    @Override
    public void sendRandomVerifyCodeForPhone(String phone) {
        //生成随机验证码
        String verifyCode = generateRandomVerifyCode();
        log.info("生成的手机验证码为：{}", verifyCode);
        //将验证码保存到redis
        redisUtils.setCacheObject(CacheConsts.VERIFYCODE_PHONE_CHCHE_NAME + phone,
                verifyCode,
                (long) CacheConsts.CacheEnum.VERIFYCODE_CHCHE.getTtl(),
                TimeUnit.SECONDS);
        //设置一个全局唯一id，初始设为一，业务执行后改为0，ttl为60s保证幂等性
        String uuid = UUID.randomUUID().toString();
        redisUtils.setCacheObject(CacheConsts.VERIFYCODE_CHCHE_NAME + uuid, 1, 60L, TimeUnit.SECONDS);
        //使用mq发送验证码
        VerifyCodeDto verifyCodeDto = new VerifyCodeDto(uuid, phone, verifyCode);
        rabbitTemplate.convertAndSend(AmqpConsts.VerifyCodeMq.EXCHANGE_DIRECT_VERIFYCODE,
                AmqpConsts.VerifyCodeMq.KEY_VERIFYCODE_PHONE,
                verifyCodeDto);
    }

    @Override
    public void sendRandomVerifyCodeForEmail(String email) {
        //生成随机验证码
        String verifyCode = generateRandomVerifyCode();
        log.info("生成的邮箱验证码为：{}", verifyCode);
        //将验证码保存到redis
        redisUtils.setCacheObject(CacheConsts.VERIFYCODE_EMAIL_CHCHE_NAME + email,
                verifyCode,
                (long) CacheConsts.CacheEnum.VERIFYCODE_CHCHE.getTtl(),
                TimeUnit.SECONDS);
        //设置一个全局唯一id，初始设为一，业务执行后改为0，ttl为60s保证幂等性
        String uuid = UUID.randomUUID().toString();
        redisUtils.setCacheObject(CacheConsts.VERIFYCODE_CHCHE_NAME + uuid, 1, 60L, TimeUnit.SECONDS);
        //使用mq发送验证码
        VerifyCodeDto verifyCodeDto = new VerifyCodeDto(uuid, email, verifyCode);
        rabbitTemplate.convertAndSend(AmqpConsts.VerifyCodeMq.EXCHANGE_DIRECT_VERIFYCODE,
                AmqpConsts.VerifyCodeMq.KEY_VERIFYCODE_EMAIL,
                verifyCodeDto);
    }

    public static String generateRandomVerifyCode() {
        //使用SecureRandom生成随机验证码
        SecureRandom random = new SecureRandom();
        StringBuilder verifyCode = new StringBuilder();
        for (int i = 0; i < CAPTCHA_LENGTH; i++) {
            verifyCode.append(random.nextInt(10));
        }
        return verifyCode.toString();

    }
}
