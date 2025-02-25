package com.vedant.blogApp.service;

import com.vedant.blogApp.entity.User;
import com.vedant.blogApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepo;

    private static final PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
    public void saveNewUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER"));
        userRepo.save(user);
    }
    public void saveUser(User user){
        userRepo.save(user);
    }

    public List<User> getAll(){
        return userRepo.findAll();
    }

    public User findById(ObjectId id){
        return userRepo.findById(id).orElse(null);
    }

    public void deleteById(ObjectId id){
        userRepo.deleteById(id);

    }

    public User findByUserName(String userName){
        return userRepo.findByUserName(userName);
    }

    public void saveAdmin(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER","ADMIN"));
        userRepo.save(user);
    }
}
