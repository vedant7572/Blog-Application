package com.vedant.blogApp.service;

import com.vedant.blogApp.repository.UserRepository;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

public class UserDetailsServiceImplTest {

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Mock
    private UserRepository userRepository;


    void loadUserByUsername() {
        UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername("ram");
    }
}
