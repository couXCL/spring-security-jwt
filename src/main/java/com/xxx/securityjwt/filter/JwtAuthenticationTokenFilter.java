package com.xxx.securityjwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xxx.securityjwt.common.ApiRestResponse;
import com.xxx.securityjwt.common.Constant;

import com.xxx.securityjwt.common.exception.ExceptionEnum;
import com.xxx.securityjwt.model.vo.UserVO;
import com.xxx.securityjwt.utils.JwtUtil;
import com.xxx.securityjwt.utils.RedisCache;
import com.xxx.securityjwt.utils.WebUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 此过滤器主要职责是将token转换为id，然后从redis取出相关信息保存到SecurityContextHolder中
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //1.获取token
        String token = request.getHeader("token");
        if (!StringUtils.hasText(token)) {
            //放行，因为可能会有未登录的没有携带token的，所以放行让后面的过滤器来处理，比如说登陆界面
            filterChain.doFilter(request, response);
            return;
        }
        //2.解析token
        String userid;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            userid = claims.getSubject();
        } catch (Exception e) {
            //抛出异常，交由AccessDeniedHandler接口的实现类处理向前端抛出
//            response.setStatus(200);
//            response.setContentType("application/json");
//            response.setCharacterEncoding("UTF-8");
//            PrintWriter writer = response.getWriter();
//            writer.write("{\n" +
//                    "    \"status\": 401,\n" +
//                    "    \"msg\": \"TOKEN_ERROR\",\n" +
//                    "    \"data\": \"\"\n" +
//                    "}");
//            writer.flush();
//            writer.close();
            throw new RuntimeException("token非法");
        }
        //3.用token得到的userid从redis中获取用户信息，并进行用户登陆判断
        String redisKey = Constant.LOGIN_KEY_PRE + userid;
        UserVO userVO = redisCache.getCacheObject(redisKey);
        //如果用户信息为空，则报错
        if(Objects.isNull(userVO)){
            //抛出异常，交由AccessDeniedHandler接口的实现类处理向前端抛出
//            //将java对象转换成json,输出到前端
//            ObjectMapper objectMapper = new ObjectMapper();
//            String responseString = objectMapper.writeValueAsString(ApiRestResponse.error(ExceptionEnum.NEED_LOGIN));
//            WebUtils.renderString(response,responseString);
            throw new RuntimeException("用户未登录");
        }
        //4.存入SecurityContextHolder
        //获取权限信息封装到Authentication中,以下是用了流处理将信息转换为GrantedAuthority类型
//        List<GrantedAuthority> authorities = userVO.getPermissions().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        //也可以使用原来的方法
        List<GrantedAuthority> authorities =new ArrayList<>();
        for (String permission: userVO.getPermissions()) {
            authorities.add(new SimpleGrantedAuthority(permission));
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userVO,null,authorities);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        //放行
        filterChain.doFilter(request, response);
    }
}
