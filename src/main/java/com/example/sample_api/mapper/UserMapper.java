package com.example.sample_api.mapper;

import com.example.sample_api.dto.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("select user_id, username, password, created_at form users where username = #{username}")
    User selectUserByUsername(String username);

}
