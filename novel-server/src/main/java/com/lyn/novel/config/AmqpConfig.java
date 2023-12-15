package com.lyn.novel.config;

import com.lyn.novel.constant.AmqpConsts;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *AMQP配置类
 * @author wjp
 */
@Configuration
public class AmqpConfig {

    @Bean
    public DirectExchange verifyDirectExchange(){
        return new DirectExchange(AmqpConsts.VerifyCodeMq.EXCHANGE_DIRECT_VERIFYCODE);
    }

    @Bean
    public Queue verifyForPhoneQueue(){
        return new Queue(AmqpConsts.VerifyCodeMq.QUEUE_VERIFYCODE_PHONE);
    }

    @Bean
    public Binding verifyForPhoneBinding(){
        return BindingBuilder
                .bind(verifyForPhoneQueue())
                .to(verifyDirectExchange())
                .with(AmqpConsts.VerifyCodeMq.KEY_VERIFYCODE_PHONE);
    }

    @Bean
    public Queue verifyForEmailQueue(){
        return new Queue(AmqpConsts.VerifyCodeMq.QUEUE_VERIFYCODE_EMAIL);
    }

    @Bean
    public Binding verifyForEmailBinding(){
        return BindingBuilder
                .bind(verifyForEmailQueue())
                .to(verifyDirectExchange())
                .with(AmqpConsts.VerifyCodeMq.KEY_VERIFYCODE_EMAIL);
    }

}
