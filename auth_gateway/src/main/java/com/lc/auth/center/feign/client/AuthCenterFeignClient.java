package com.lc.auth.center.feign.client;

import com.luc.framework.core.mvc.WebResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "auth-center")
public interface AuthCenterFeignClient {

    @GetMapping("/feign/checkPerm")
    WebResult<String> checkPermission(String token, String url);
}
