package com.shreyas.pom.payload.responce;

import com.shreyas.pom.payload.dto.UserDto;
import lombok.Data;

@Data
public class AuthResponce {

    private  String jwt;
    private  String message;
    private UserDto user;
}
