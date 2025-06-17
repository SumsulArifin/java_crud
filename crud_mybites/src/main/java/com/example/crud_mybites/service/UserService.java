package com.example.crud_mybites.service;

import com.example.crud_mybites.dto.UserWithRole;
import com.example.crud_mybites.model.User;
import com.example.crud_mybites.model.UserRole;
import com.example.crud_mybites.persistence.UserMapper;
import com.example.crud_mybites.persistence.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {


    private final UserMapper userMapper;
    private final UserRoleMapper userRoleMapper;

    public UserService(UserMapper userMapper, UserRoleMapper userRoleMapper) {
        this.userMapper = userMapper;
        this.userRoleMapper = userRoleMapper;
    }
    public void create(User user) throws Exception {
        this.userMapper.createUser(user);
    }

    @Transactional
    public void createUserWithRole(User user, UserRole role) throws Exception {
        // create user first
        userMapper.createUser(user);
        // create role next
        userRoleMapper.createRole(role);
    }
    public List<User> getAllUsers() {
        return userMapper.getAllUsers();
    }
    public void deleteUser(int id) {
        userMapper.deleteUser(id);
    }
    public void updateUser(User user) {
        userMapper.updateUser(user);
    }

    public User getUser(int id) {
        return userMapper.getUser(id);
    }
}
