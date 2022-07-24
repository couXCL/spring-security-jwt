package com.xxx.securityjwt.common.expression;
import com.xxx.securityjwt.model.vo.UserVO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *
 * 自定义鉴权方法
 */
@Component("ex")
public class SGExpressionRoot{

    public boolean  hasAuthority(String authority){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserVO userVO = (UserVO) authentication.getPrincipal();
        List<String> permissions = userVO.getPermissions();
        return permissions.contains(authority);
    }


}
