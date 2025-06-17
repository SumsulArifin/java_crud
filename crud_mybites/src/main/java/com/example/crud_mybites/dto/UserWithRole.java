package com.example.crud_mybites.dto;

import com.example.crud_mybites.model.User;
import com.example.crud_mybites.model.UserRole;

public class UserWithRole {

        private User user;
        private UserRole userRole;

        // getters and setters
        public User getUser() {
            return user;
        }
        public void setUser(User user) {
            this.user = user;
        }

        public UserRole getUserRole() {
            return userRole;
        }
        public void setUserRole(UserRole userRole) {
            this.userRole = userRole;
        }
    }

