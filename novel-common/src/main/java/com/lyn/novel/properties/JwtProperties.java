package com.lyn.novel.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "novel.jwt")
public class JwtProperties {
    private String secretKey;
    private long ttl;
    private String tokenName;
}
