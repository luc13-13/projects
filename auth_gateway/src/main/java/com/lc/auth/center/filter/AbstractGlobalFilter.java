package com.lc.auth.center.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;

@Slf4j
public abstract class AbstractGlobalFilter {

    /**
     * 获取请求头中的参数
     * @param request
     * @param headerName
     * @return
     */
    protected String getHeaderValue(ServerHttpRequest request, String headerName){
        HttpHeaders headers = request.getHeaders();
        // 请求头为空则返回空字符串
        if(headers.isEmpty()) {
            return "";
        }
        String headerValue = headers.getFirst(headerName);
        if(!"".equals(headerValue) || headerName != null) {
            log.info("获取header{}:{}",headerName,headerValue);
            return headerValue;
        }
        // 如果请求头中没有headerName, 则去请求参数中查找
        return request.getQueryParams().getFirst(headerName);
    }
    protected class TokenException extends RuntimeException{
        public TokenException(String message) {
            super(message);
        }

    }
}
