package com.vedant.blogApp.controller;

import com.vedant.blogApp.entity.User;
import com.vedant.blogApp.repository.UserRepository;
import com.vedant.blogApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/public")
public class PublicController {

    @GetMapping("/health-check")
    public String healthCheck(){
        return "Ok";
    }

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUser(){
        return userService.getAll();
    }

    @PostMapping("/create-user")
    public ResponseEntity<User> createUser(@RequestBody User user){
        userService.saveNewUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PostMapping
    public void test(){
        System.out.println("hello");
    }

}
