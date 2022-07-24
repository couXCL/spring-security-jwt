package com.xxx.securityjwt.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xxx.securityjwt.model.entity.User;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public class UserVO implements Serializable {
    private User user;
    private List<String> permissions;
    private String token;

    public UserVO() {
    }

    public UserVO(User user, List<String> permissions, String token) {
        this.user = user;
        this.permissions = permissions;
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
