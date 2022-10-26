package com.lc.auth.center.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.lc.auth.center.config.GlobalPropertiesConfig;
import com.lc.auth.center.enums.RequestAttribute;
import com.plumelog.core.util.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;



@Component
@Slf4j
public class TokenGlobalFilter extends AbstractGlobalFilter implements GlobalFilter, Ordered {

    @Autowired
    private GlobalPropertiesConfig globalPropertiesConfig;


    /**
     *
     * @param exchange 持有request和response的容器，并用map封装路由等属性
     *                 (key的取值参考{@link ServerWebExchangeUtils})，
     *                 维持原有请求实例不变，可以通过{@link ServerWebExchange#mutate()}
     *                 修改内部request， 包括修改请求头;
     * @param chain gateway中的过滤链路
     * @return 将过滤链路继续向下传递
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request =  exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        ServerHttpRequest.Builder builder = request.mutate();

        // 测试向请求头中添加信息， ServerHttpRequest中headers是final, 初始化后不可修改， 因此需要从初始的request中复制一个headers再进行添加请求头
        ServerHttpRequest newRequest = exchange.getRequest().mutate().headers(
                httpHeaders -> httpHeaders.add("newHeader","11111")
        ).build();
        //
        try{
            // 1、可以放行的路径:校验exchange中Authorization属性值是否为LuCheng
            if(skip(exchange, RequestAttribute.SKIP_TOKEN)) {
                return chain.filter(exchange);
            }
            // 2、校验请求头是否符合要求：存在token
            checkHeaders(request);
            log.info("nacos配置中心: {}", globalPropertiesConfig.getName());
            // 3、校验token中的租户是否有效：账号未被锁定
//            Mono<Void> token = parseToken(exchange, chain);
//            if(token != null) {
//                return token;
//            }
            // 3、向认证中心发送请求，校验token是否满足uri的权限要求
        } catch (TokenException e) {
            // token缺失则将请求重定向到登录界面
//            ServerWebExchangeUtils.setResponseStatus(exchange, HttpStatus.FOUND);
//            response = (ServerHttpResponse) exchange.getResponse();
//            response.getHeaders().set("Location","http://localhost:8889/login");
//            return response.setComplete();
            // 如果需要对请求进行转发， 则需要创建exchange返回到过滤链中
//            URI uri = UriComponentsBuilder.newInstance()
//                    .host("localhost")
//                    .port("8889")
//                    .path("/api/login/test").build().toUri();
//            ServerHttpRequest mutateRequest = newRequest.mutate().uri(uri).build();
//            ServerWebExchange mutateExchange = exchange.mutate().request(
//            mutateRequest).response(response).build();
//            log.info(mutateExchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR));
//            mutateExchange.getAttributes().put(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR, )
//            return chain.filter(mutateExchange);
        }

        return  chain.filter(exchange.mutate().request(newRequest).build());
    }

    @Override
    public int getOrder() {
        return 1;
    }

    /**
     * 校验请求头中是否有 Authorization 属性
     * @param request http请求
     * @throws TokenException 缺失token或token过期异常
     */
    private void checkHeaders(ServerHttpRequest request) throws TokenException {
        HttpHeaders headers = request.getHeaders();
        if(headers.isEmpty()) {
            log.info("请求头为空");
            throw new TokenException("请求头为空");
        }
        if (StringUtils.isEmpty(getHeaderValue(request, "Authorization"))) {
            log.info("缺失token");
            throw new TokenException("缺失token");
        }
    }

    private Mono<Void> parseToken(ServerWebExchange exchange, GatewayFilterChain chain) {
        String token = getHeaderValue(exchange.getRequest(), "Authorization");
        // 请求的验证已在parseToken方法前执行，
        // 可以直接从token中解析租户信息， 如果租户失效， 则抛出租户失效的异常
        return null;
    }



    private Mono<Void> errorResponse(ServerHttpResponse response) {
        return null;
    }
}
