package com.lyn.novel.properties;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 跨域域名属性配置
 * @author wjp
 */
@Data
@Component
@ConfigurationProperties(prefix = "novel.cors")
public class CorsProperties {
    private List<String> allowOrigins;
}
