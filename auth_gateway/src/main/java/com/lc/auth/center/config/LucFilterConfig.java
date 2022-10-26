package com.lc.auth.center.config;

import com.lc.auth.center.constant.LucGatewayProperties;
import com.lc.auth.center.filter.AuthorizationFilterFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** 将自定义的 GatewayFilterFactory 注册到 Spring 容器中
 * @Author : Lu Cheng
 * @Date : 2022/10/26 21:34
 * @Version : 1.0
 */

@Configuration
public class LucFilterConfig {

    @Bean
    @ConditionalOnMissingBean(AuthorizationFilterFactory.class)
    public AuthorizationFilterFactory authorizationFilterFactory(LucGatewayProperties lucGatewayProperties) {
        return new AuthorizationFilterFactory(lucGatewayProperties);
    }


}
