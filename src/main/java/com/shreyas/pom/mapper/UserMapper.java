package com.shreyas.pom.mapper;

import com.shreyas.pom.modal.User;
import com.shreyas.pom.payload.dto.UserDto;

public class UserMapper {
    public static UserDto toDto(User savedUser) {
        UserDto userDto=new UserDto();
        userDto.setId(savedUser.getId());
        userDto.setEmail(savedUser.getEmail());
        userDto.setRole(savedUser.getRole());
        userDto.setCreatedAt(savedUser.getCreatedAt());
        userDto.setUpdatedAt(savedUser.getUpdatedAt());
        userDto.setLastLogin(savedUser.getLastLogin());
        userDto.setPhone(savedUser.getPhone());

        return userDto;
    }
}
