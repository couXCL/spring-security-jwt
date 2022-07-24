package com.xxx.securityjwt.model.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxx.securityjwt.model.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper extends BaseMapper<User> {
    List<String> selectPermsByUserId(@Param("userId") Long userId);
}
