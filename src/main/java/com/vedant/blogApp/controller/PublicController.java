package com.vedant.blogApp.controller;

import com.vedant.blogApp.entity.User;
import com.vedant.blogApp.repository.UserRepository;
import com.vedant.blogApp.service.UserDetailsServiceImpl;
import com.vedant.blogApp.service.UserService;
import com.vedant.blogApp.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;


    @GetMapping("/health-check")
    public String healthCheck(){
        return "Ok";
    }
    @GetMapping
    public List<User> getAllUser(){
        return userService.getAll();
    }

    @PostMapping("/sign-up")
    public ResponseEntity<User> signup(@RequestBody User user){
        userService.saveNewUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUserName());
            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Incorrect username or password", HttpStatus.BAD_REQUEST);
        }
    }

}
