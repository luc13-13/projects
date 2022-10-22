package com.lc.auth.center.web;

import com.lc.auth.center.config.GlobalPropertiesConfig;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@Slf4j
public class BaseController {
    @Value("${auth.config.name}")
    private String name;
    @GetMapping
    public String test() {
        log.info(name);
        return "hello";
    }
}
