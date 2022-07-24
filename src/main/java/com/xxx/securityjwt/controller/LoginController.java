package com.xxx.securityjwt.controller;

import com.xxx.securityjwt.common.ApiRestResponse;
import com.xxx.securityjwt.model.req.UserReq;
import com.xxx.securityjwt.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;
    @PostMapping("user/login")
    public ApiRestResponse login(@RequestBody UserReq userReq){
        return loginService.login(userReq);
    }

    @PostMapping("/user/logout")
    public ApiRestResponse logout(){
        return loginService.logout();
    }
}
