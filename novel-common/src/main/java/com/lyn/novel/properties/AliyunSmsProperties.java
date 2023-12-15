package com.lyn.novel.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "novel.aliyun.sms")
public class AliyunSmsProperties {
    private String regionId;
    private String endpoint;
    private String accessKeyId;
    private String secret;
    private String signName;
    private String templateCode;
    private String codeName;
}
