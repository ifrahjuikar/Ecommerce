package com.springbootproject.springbootproject.Controller;

import org.springframework.web.bind.annotation.RestController;

import com.springbootproject.springbootproject.Entitities.User;
import com.springbootproject.springbootproject.Service.UserService;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostConstruct
    public void initRolesAndUser(){
        userService.initRolesAndUser();
    }

    @PostMapping("/registerNewUser")
    public User registerNewUser(@RequestBody User user) {
        
        
        return userService.registerNewUser(user);
    }

    @GetMapping("/forAdmin")
    public String forAdmin() {
        return "for admin";
    }
    
    @GetMapping("/forUser")
    public String forUser() {
        return "for user";
    }
    
    
    
}
