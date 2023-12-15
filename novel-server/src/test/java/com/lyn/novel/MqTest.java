package com.lyn.novel;

import com.alibaba.fastjson.JSON;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.tea.TeaException;
import com.lyn.novel.dto.VerifyCodeDto;
import com.lyn.novel.properties.AliyunSmsProperties;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@SpringBootTest
public class MqTest {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${spring.mail.username}")
    private String emailName;

    @Autowired
    private com.aliyun.dysmsapi20170525.Client client;

    @Autowired
    private AliyunSmsProperties smsProperties;

    @Test
    public void sendEmail(){
        log.info(emailName);
        VerifyCodeDto verifyCodeDto = new VerifyCodeDto("3273700548@qq.com", "123456");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailName);
        message.setTo(verifyCodeDto.getContact());
        message.setSubject("七星小说登录验证码");
        message.setText("验证码" + verifyCodeDto.getVerifyCode() + ", 您正在使用邮箱验证码登录，有效期10分钟，请勿泄露。");
        mailSender.send(message);
    }

    @Test
    public void sendPhone(){
        Map<String, String> map = new HashMap<>();
        map.put(smsProperties.getCodeName(), "010203");
        SendSmsRequest sendSmsRequest = new com.aliyun.dysmsapi20170525.models.SendSmsRequest()
                .setSignName(smsProperties.getSignName())
                .setTemplateCode(smsProperties.getTemplateCode())
                .setPhoneNumbers("18703985131")
                .setTemplateParam(JSON.toJSONString(map));
        try {
            // 复制代码运行请自行打印 API 的返回值
            client.sendSmsWithOptions(sendSmsRequest, new com.aliyun.teautil.models.RuntimeOptions());
        } catch (TeaException error) {
            // 错误 message
            System.out.println(error.getMessage());
            // 诊断地址
            System.out.println(error.getData().get("Recommend"));
            com.aliyun.teautil.Common.assertAsString(error.message);
        } catch (Exception _error) {
            TeaException error = new TeaException(_error.getMessage(), _error);
            // 错误 message
            System.out.println(error.getMessage());
            // 诊断地址
            System.out.println(error.getData().get("Recommend"));
            com.aliyun.teautil.Common.assertAsString(error.message);
        }
    }




}
