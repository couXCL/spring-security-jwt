package com.xxx.securityjwt.service.impl;

import com.xxx.securityjwt.common.ApiRestResponse;
import com.xxx.securityjwt.common.Constant;
import com.xxx.securityjwt.common.exception.ExceptionEnum;
import com.xxx.securityjwt.model.entity.User;
import com.xxx.securityjwt.model.req.UserReq;
import com.xxx.securityjwt.model.vo.LoginUserDetails;
import com.xxx.securityjwt.model.vo.UserVO;
import com.xxx.securityjwt.service.LoginService;
import com.xxx.securityjwt.utils.JwtUtil;
import com.xxx.securityjwt.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisCache redisCache;
    @Override
    public ApiRestResponse login(UserReq userReq) {
        //1.使用authenticationManager进行鉴权并获取user信息
        Authentication AuthenticationToken = new UsernamePasswordAuthenticationToken(userReq.getUserName(),userReq.getPassword());
        Authentication authenticate = authenticationManager.authenticate(AuthenticationToken);//执行此方法就会执行到实现UserDetailsService接口的类
        if (authenticate==null){
            return ApiRestResponse.error(ExceptionEnum.USERNAME_PASSWORD_ERROR);
        }
        //2.通过userid生成token以便返回给前端
        LoginUserDetails loginUserDetails = (LoginUserDetails) authenticate.getPrincipal();
        User user = loginUserDetails.getUser();
        String token = JwtUtil.createJWT(user.getId().toString());
        //3.将和user相关的信息存入redis缓存中
        //将UserVO存入redis中，以便JwtAuthenticationTokenFilter过滤器使用
        UserVO userVO = new UserVO(loginUserDetails.getUser(), loginUserDetails.getPermissions(), token);
        redisCache.setCacheObject(Constant.LOGIN_KEY_PRE+user.getId().toString(),userVO);
        //token响应给前端
        Map<String, String> map = new HashMap<>();
        map.put("token",token);
        return ApiRestResponse.success(map);
    }

    @Override
    public ApiRestResponse logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserVO userVO = (UserVO) authentication.getPrincipal();
        User user = userVO.getUser();
        redisCache.deleteObject(Constant.LOGIN_KEY_PRE+user.getId());
        return ApiRestResponse.success();
    }
}
