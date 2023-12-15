package com.lyn.novel.config;

import com.lyn.novel.properties.AliyunSmsProperties;
import com.lyn.novel.properties.CorsProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * webmvc配置类
 * @author wjp
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private CorsProperties corsProperties;

    @Autowired
    private AliyunSmsProperties smsProperties;

    /**
     * cors配置
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {

        // 配置允许的跨域域名
        for (String allowOrigin : corsProperties.getAllowOrigins()) {
            registry
                    .addMapping("*")
                    .allowedOrigins(allowOrigin);
        }

        registry
                // 配置允许跨域的路径，这里是所有路径
                .addMapping("*")
                // 允许的请求方法
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                // 是否允许发送 Cookie
                .allowCredentials(true)
                // 允许的请求头
                .allowedHeaders("*")
                // 预检请求的有效期，单位秒
                .maxAge(3600);
    }

    /**
     * aliyun短信服务--使用AK&SK初始化账号Client
     * @return Client
     * @throws Exception
     */
    @Bean
    public com.aliyun.dysmsapi20170525.Client createClient() throws Exception {
        com.aliyun.teaopenapi.models.Config config = new com.aliyun.teaopenapi.models.Config()
                // 必填，您的 AccessKey ID
                .setAccessKeyId(smsProperties.getAccessKeyId())
                // 必填，您的 AccessKey Secret
                .setAccessKeySecret(smsProperties.getSecret());
        // Endpoint 请参考 https://api.aliyun.com/product/Dysmsapi
        config.endpoint = smsProperties.getEndpoint();
        return new com.aliyun.dysmsapi20170525.Client(config);
    }

}
