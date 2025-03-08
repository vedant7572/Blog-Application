package com.vedant.blogApp.controller;

import com.vedant.blogApp.api.response.WeatherResponse;
import com.vedant.blogApp.entity.User;
import com.vedant.blogApp.repository.UserRepository;
import com.vedant.blogApp.service.UserService;
import com.vedant.blogApp.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController  {
    //controller calls the service which in turn uses the repo to do the operations on the db

    @Autowired
    private UserService userService;

    @Autowired
    private WeatherService weatherService;
    @Autowired
    private UserRepository userRepository;

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        System.out.println("✅ Authenticated user: " + userName);

        User userInDb = userService.findByUserName(userName);
        userInDb.setUserName(user.getUserName());
        userInDb.setPassword(user.getPassword());
        userService.saveNewUser(userInDb);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userRepository.deleteByUserName(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<?> greeting(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WeatherResponse weatherResponse=weatherService.getWeather("pune");
        
        String greeting="";
        double temp = 0;
        if(weatherResponse!=null){
            greeting="Weather feels like "+ weatherResponse.getCurrent().getCondition().getText();
            temp=weatherResponse.getCurrent().getTemp_c();
        }

        return new ResponseEntity<>("hello "+ authentication.getName() + "\nWeather is " + greeting + " and temperature is "+ temp,HttpStatus.OK);
    }

}
