package com.lc.auth.center.filter;

import com.lc.auth.center.enums.RequestAttribute;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;

@Slf4j
public abstract class AbstractGlobalFilter {

    /**
     * 获取请求头中的参数
     * @param request http请求
     * @param headerName 目标请求头
     * @return 返回空字符串说明request中没有目标请求头， 否则返回目标请求头的值
     */
    protected String getHeaderValue(ServerHttpRequest request, String headerName){
        HttpHeaders headers = request.getHeaders();
        // 请求头为空则返回空字符串
        if(headers.isEmpty()) {
            return "";
        }
        String headerValue = headers.getFirst(headerName);
        log.info("获取header{}:{}",headerName,headerValue);
        return headerValue;
        // 如果请求头中没有headerName, 则去请求参数中查找
    }

    protected static String getRequestUrl(ServerHttpRequest request) {
        String url = request.getPath().value();
        log.info("path.value: {}",url);
        log.info("uri.path: {}",request.getURI().getPath());
        return url;
    }

    /**
     * 判断是否被跳过
     * @param exchange 过滤链路中封装这request
     * @return true表示跳过该filter， false表示不跳过
     */
    protected boolean skip(ServerWebExchange exchange, RequestAttribute attribute) {

        return attribute.getWhiteValue()
                .equals(exchange.getAttribute(attribute.getAttribute()));
    }
    protected class TokenException extends RuntimeException{
        public TokenException(String message) {
            super(message);
        }

    }
}
