package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Generates getters, setters, toString, equals, and hashCode
@NoArgsConstructor // Generates no-argument constructor
@AllArgsConstructor // Generates all-argument constructor
public class User {
    private Integer id;
    private String name;
    private String email;
}
