package com.xxx.securityjwt.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/hello")
    @PreAuthorize("hasAuthority('system:test:list')")
    public String hello(){
        return "hello word";
    }

    @GetMapping("/test")
    @PreAuthorize("@ex.hasAuthority('system:test:list')")//自定义认证方法
    public String test(){
        return "hello word";
    }
}
