package com.lc.auth.center.filter;

import com.lc.auth.center.constant.LucGatewayProperties;
import com.lc.auth.center.enums.RequestAttribute;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.annotation.Order;

@Slf4j
@Order(0)
public class AuthorizationFilterFactory extends AbstractGatewayFilterFactory<AuthorizationFilterFactory.Config> {

    private final LucGatewayProperties lucGatewayProperties;

    public AuthorizationFilterFactory(LucGatewayProperties lucGatewayProperties) {
        super();
        this.lucGatewayProperties = lucGatewayProperties;
    }

    @Override
    public GatewayFilter apply(Config config) {

        return (exchange, chain) -> {
            // 1、开启白名单则校验：如果lucGatewayProperties中包含了request的路径
            if (Boolean.parseBoolean(config.getEnable())) {
                log.info("已开启白名单");
                // 判断请求路径是否在白名单内
                String url = AbstractGlobalFilter.getRequestUrl(exchange.getRequest());
                if (lucGatewayProperties.getWhiteUrl().contains(url)) {
                    exchange.getAttributes().put(RequestAttribute.SKIP_TOKEN.getAttribute(), RequestAttribute.SKIP_TOKEN.getWhiteValue());
                }
            }
            // 2、没有开启白名单则跳过检验放行
            return chain.filter(exchange);
        };
    }

    public static class Config{
        private String enable;

        public String getEnable() {
            return enable;
        }

        public void setEnable(String enable) {
            this.enable = enable;
        }
    }
}
