package com.example.sample_api.repository;

import com.example.sample_api.dto.User;
import com.example.sample_api.mapper.UserMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    private final UserMapper userMapper;

    public UserRepository(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public User selectUserByUsername(String username){
        return userMapper.selectUserByUsername(username);
    }
}
