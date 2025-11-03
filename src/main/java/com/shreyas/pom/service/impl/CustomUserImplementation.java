package com.shreyas.pom.service.impl;


import com.shreyas.pom.modal.User;
import com.shreyas.pom.repositry.UserRepositry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
public class CustomUserImplementation implements UserDetailsService {

    @Autowired
    private UserRepositry userRepositry;
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        User user=userRepositry.findByEmail(username);
        if(user==null){
            throw  new UsernameNotFoundException("email is not present");
        }
        GrantedAuthority authority=new SimpleGrantedAuthority(
                user.getRole().toString()
        );
        Collection<GrantedAuthority> authorities= Collections
                .singletonList(authority);
        return  new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                authorities

        );

    }
}
