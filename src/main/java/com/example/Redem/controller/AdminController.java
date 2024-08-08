package com.example.Redem.controller;

import com.example.Redem.entity.UserEntity;
import com.example.Redem.repository.UserRepository;
import com.example.Redem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.HTML;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers(){
        List<UserEntity> usersFound = userService.getAll();
        if(usersFound != null && !usersFound.isEmpty()){
            return new ResponseEntity<>(usersFound , HttpStatus.FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create-admin")
    public ResponseEntity<?> makeAdmin(@RequestBody UserEntity user){
        userService.createAdmin(user);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
