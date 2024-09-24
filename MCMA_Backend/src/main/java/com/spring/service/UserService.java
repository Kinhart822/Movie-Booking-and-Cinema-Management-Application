package com.spring.service;

import com.spring.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService{
    UserDetailsService userDetailsService();
}
