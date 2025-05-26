package com.example.demo.mapper;

import com.example.demo.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    void insertUser(User user);
    User selectUserById(Integer id);
    List<User> selectAllUsers();
    void updateUser(User user);
    void deleteUser(Integer id);
}

