package com.example.crud_mybites.persistence;

import com.example.crud_mybites.model.UserRole;

import java.util.List;

public interface UserRoleMapper {
    void createRole(UserRole userRole);
    void updateUserRole(UserRole userRole);
    void deleteUserRole(UserRole userRole);
    UserRole getUserRoleById(int id);
    List<UserRole> getAllUserRoles();
}
