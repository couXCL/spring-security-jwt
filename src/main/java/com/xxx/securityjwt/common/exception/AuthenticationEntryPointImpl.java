package com.xxx.securityjwt.common.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xxx.securityjwt.common.ApiRestResponse;
import com.xxx.securityjwt.utils.WebUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证过程出现异常会走这里
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        //将ApiRestResponse.error(ExceptionEnum.NEED_LOGIN)响应给前端
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResult = objectMapper.writeValueAsString(ApiRestResponse.error(401, authException.getMessage()));
        System.out.println(authException.getMessage());
        WebUtils.renderString(response,jsonResult);

    }
}
