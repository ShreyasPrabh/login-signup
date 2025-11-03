package com.shreyas.pom.service.impl;

import com.shreyas.pom.Exception.UserException;
import com.shreyas.pom.configaration.JwtProvider;
import com.shreyas.pom.domain.UserRole;
import com.shreyas.pom.mapper.UserMapper;
import com.shreyas.pom.modal.User;
import com.shreyas.pom.payload.dto.UserDto;
import com.shreyas.pom.payload.responce.AuthResponce;
import com.shreyas.pom.repositry.UserRepositry;
import com.shreyas.pom.service.AuthService;
import lombok.RequiredArgsConstructor;
import com.shreyas.pom.configaration.SecurityConfig;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {


    private final UserRepositry userRepositry;
    private final JwtProvider jwtProvider;
    private final CustomUserImplementation customUserImplementation;
    private final PasswordEncoder passwordEncoder;



    @Override
    public AuthResponce signup(UserDto userDto) throws UserException {
        User user =userRepositry.findByEmail(userDto.getEmail());

        if(user!=null) {
            throw new UserException("email is already registered");

        }
        if(userDto.getRole().equals(UserRole.ROLE_ADMIN)){
            throw new UserException("Admin is not allowed");
        }
        User newUser=new User();
        newUser.setEmail(userDto.getEmail());
        newUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        newUser.setRole(userDto.getRole());
        newUser.setFullname(userDto.getFullname());
        newUser.setPhone(userDto.getPhone());
        newUser.setCreatedAt(LocalDateTime.now());
        newUser.setLastLogin(LocalDateTime.now());
        newUser.setUpdatedAt(LocalDateTime.now());
        User savedUser= userRepositry.save(newUser);
        Authentication authentication=
                new UsernamePasswordAuthenticationToken(userDto.getEmail(),userDto.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt=jwtProvider.generateToken(authentication);
        AuthResponce authResponce=new AuthResponce();
        authResponce.setJwt(jwt);
        authResponce.setMessage("Registered successfully");
        authResponce.setUser(UserMapper.toDto(savedUser));

        return authResponce;
    }



    @Override
    public AuthResponce login(UserDto userDto) throws UserException {
        String email=userDto.getEmail();
        String password=userDto.getPassword();
        Authentication authentication=authenticate(email,password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Collection<? extends GrantedAuthority>authorities=authentication.getAuthorities();
        String role=authorities.iterator().next().getAuthority();
        String jwt=jwtProvider.generateToken(authentication);
        User user=userRepositry.findByEmail(email);
        user.setLastLogin(LocalDateTime.now());
        userRepositry.save(user);
        AuthResponce authResponce=new AuthResponce();
        authResponce.setJwt(jwt);
        authResponce.setMessage("login successfully");
        authResponce.setUser(UserMapper.toDto(user));

        return authResponce;
    }

    private Authentication authenticate(String email, String password) throws UserException {
        UserDetails userDetails=customUserImplementation.loadUserByUsername(email);
        if(userDetails==null){
            throw new UserException("email does not exists"+email);
        }
        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new UserException("password does not match");
        }
        return  new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());

    }
}
