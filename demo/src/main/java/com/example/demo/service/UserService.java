package com.example.demo.service;

import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public void createUser(User user) {
        userMapper.insertUser(user);
    }

    public User getUserById(Integer id) {
        return userMapper.selectUserById(id);
    }

    public List<User> getAllUsers() {
        return userMapper.selectAllUsers();
    }

    public void updateUser(User user) {
        userMapper.updateUser(user);
    }

    public void deleteUser(Integer id) {
        userMapper.deleteUser(id);
    }
}