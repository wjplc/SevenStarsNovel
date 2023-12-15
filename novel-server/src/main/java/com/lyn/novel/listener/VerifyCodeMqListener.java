package com.lyn.novel.listener;

import com.alibaba.fastjson.JSON;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.tea.TeaException;
import com.aliyun.teautil.models.RuntimeOptions;
import com.lyn.novel.constant.ResponseCode;
import com.lyn.novel.exception.AuthenticationException;
import com.lyn.novel.properties.AliyunSmsProperties;
import com.lyn.novel.utils.RedisUtils;
import com.lyn.novel.constant.AmqpConsts;
import com.lyn.novel.constant.CacheConsts;
import com.lyn.novel.dto.VerifyCodeDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * rabbitmq监听发送验证码
 * @author wjp
 */
@Slf4j
@Component
public class VerifyCodeMqListener {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private AliyunSmsProperties smsProperties;

    @Autowired
    private com.aliyun.dysmsapi20170525.Client client;

    @Autowired
    private RedisUtils redisUtils;

    @Value("${spring.mail.username}")
    private String emailName;

    /**
     * 发送手机验证码
     * @param verifyCodeDto
     */
    @RabbitListener(queues = AmqpConsts.VerifyCodeMq.QUEUE_VERIFYCODE_PHONE)
    public void sendVerifyCodeForPhone(VerifyCodeDto verifyCodeDto){
        //根据uuid判断消息收否已消费
        Object cacheObject = redisUtils.getCacheObject(CacheConsts.VERIFYCODE_CHCHE_NAME + verifyCodeDto.getUuid());
        int i = Integer.parseInt(String.valueOf(cacheObject));
        if(i == 0){
            //消息已消费
            log.info("消息已消费");
            return;
        }
        //消息未消费，开始发送验证码
        log.info("开始发送手机验证码，手机号：{}，验证码：{}", verifyCodeDto.getContact(), verifyCodeDto.getVerifyCode());
        //设置传入模版的参数
        Map<String, String> map = new HashMap<>();
        map.put(smsProperties.getCodeName(), verifyCodeDto.getVerifyCode());
        //配置相关信息
        SendSmsRequest sendSmsRequest = new com.aliyun.dysmsapi20170525.models.SendSmsRequest()
                //短信签名
                .setSignName(smsProperties.getSignName())
                //短信模版代码
                .setTemplateCode(smsProperties.getTemplateCode())
                //接收手机号
                .setPhoneNumbers(verifyCodeDto.getContact())
                //传入模版的参数--验证码
                .setTemplateParam(JSON.toJSONString(map));
        try {
            //调用API发送短信
            SendSmsResponse smsResponse = client.sendSmsWithOptions(sendSmsRequest, new RuntimeOptions());
            log.info(JSON.toJSONString(smsResponse));
            log.info(String.valueOf(smsResponse));
            //将uuid值设为0防止重复消费，保证幂等性
            redisUtils.setCacheObject(CacheConsts.VERIFYCODE_CHCHE_NAME + verifyCodeDto.getUuid(), 0);
        } catch (Exception e) {
            TeaException error = new TeaException(e.getMessage(), e);
            // 错误 message
            log.info(error.getMessage());
            // 诊断地址
            log.info((String) error.getData().get("Recommend"));
            throw new AuthenticationException(ResponseCode.BAD_REQUEST, "验证码发送失败，请重试");
        }
    }

    /**
     *发送邮箱验证码
     * @param verifyCodeDto
     */
    @RabbitListener(queues = AmqpConsts.VerifyCodeMq.QUEUE_VERIFYCODE_EMAIL)
    public void sendVerifyCodeForEmail(VerifyCodeDto verifyCodeDto){
        //根据uuid判断消息收否已消费
        Object cacheObject = redisUtils.getCacheObject(CacheConsts.VERIFYCODE_CHCHE_NAME + verifyCodeDto.getUuid());
        int i = Integer.parseInt(String.valueOf(cacheObject));
        if(i == 0){
            //消息已消费
            log.info("消息已消费");
            return;
        }
        //消息未消费，开始发送验证码
        log.info("开始发送邮箱验证码，邮箱：{}，验证码：{}", verifyCodeDto.getContact(), verifyCodeDto.getVerifyCode());
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailName);
        message.setTo(verifyCodeDto.getContact());
        message.setSubject("七星小说登录验证码");
        message.setText("您的验证码为" + verifyCodeDto.getVerifyCode() + "，10分钟内有效，请勿泄露。如非本人操作，请忽略本条短信！");
        try {
            mailSender.send(message);
            //将uuid值设为0防止重复消费，保证幂等性
            redisUtils.setCacheObject(CacheConsts.VERIFYCODE_CHCHE_NAME + verifyCodeDto.getUuid(), 0);
        } catch (MailException e) {
            log.info("发送邮箱验证码失败，请重试");
            throw new AuthenticationException(ResponseCode.BAD_REQUEST, "验证码发送失败，请重试");
        }
    }

}
