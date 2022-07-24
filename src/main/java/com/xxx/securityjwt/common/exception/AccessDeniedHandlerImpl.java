package com.xxx.securityjwt.common.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xxx.securityjwt.common.ApiRestResponse;
import com.xxx.securityjwt.utils.WebUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 访问拒绝的异常处理，没有访问权限都会经过此异常返回
 * 如果是没有访问权限的异常会被封装成AccessDeniedException
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        //将ApiRestResponse.error(ExceptionEnum.ACCESS_DENIED)转换成json格式响应给前端
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResult = objectMapper.writeValueAsString(ApiRestResponse.error(403,accessDeniedException.getMessage()));
        WebUtils.renderString(response,jsonResult);
    }
}
