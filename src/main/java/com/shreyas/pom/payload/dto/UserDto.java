package com.shreyas.pom.payload.dto;

import com.shreyas.pom.domain.UserRole;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDto {

    private  Long id;
    private String fullname;
    private String email;
    private  String phone;
    private UserRole role;
    private  String password;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private LocalDateTime lastLogin;

}
