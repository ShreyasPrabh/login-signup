package com.shreyas.pom.service;

import com.shreyas.pom.Exception.UserException;
import com.shreyas.pom.payload.dto.UserDto;
import com.shreyas.pom.payload.responce.AuthResponce;

public interface AuthService {
    AuthResponce signup( UserDto userDto) throws UserException;
    AuthResponce login(UserDto userDto) throws UserException;
}
