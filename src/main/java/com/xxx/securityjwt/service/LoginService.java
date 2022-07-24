package com.xxx.securityjwt.service;

import com.xxx.securityjwt.common.ApiRestResponse;
import com.xxx.securityjwt.model.req.UserReq;

public interface LoginService {
    ApiRestResponse login(UserReq userReq);

    ApiRestResponse logout();
}
