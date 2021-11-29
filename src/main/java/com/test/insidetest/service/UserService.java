package com.test.insidetest.service;

import com.test.insidetest.repository.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserEntity createUser(UserEntity userDetails);
}
