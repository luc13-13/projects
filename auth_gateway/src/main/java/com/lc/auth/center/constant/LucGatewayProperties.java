package com.lc.auth.center.constant;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author : Lu Cheng
 * @Date : 2022/10/26 21:07
 * @Version : 1.0
 */
@Component
@Data
@ConfigurationProperties(prefix = "luc-gateway")
public class LucGatewayProperties {
    private List<String> whiteUrl;
    private List<String> whiteResource;
}
