package com.example.Redem.controller;

import com.example.Redem.entity.UserEntity;
import com.example.Redem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    public UserService userService;

    @GetMapping("health-check")
    public String healthCheck(){
        return "Status : Okay";
    }

    @PostMapping("/create-user")
    public void createUser(@RequestBody UserEntity user) {
        userService.saveNewUser(user);
    }

}
