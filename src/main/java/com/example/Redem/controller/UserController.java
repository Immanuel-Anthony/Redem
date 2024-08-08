package com.example.Redem.controller;

import com.example.Redem.apiResponse.NewsResponse;
import com.example.Redem.entity.UserEntity;
import com.example.Redem.service.NewsService;
import com.example.Redem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private NewsService newsService;

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody UserEntity user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        UserEntity userInDB = userService.findByUserName(userName);
        userInDB.setUserName(user.getUserName());
        userInDB.setPassword(user.getPassword());
        userService.saveNewUser(userInDB);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUserByName(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        userService.deleteByUsername(authentication.getName());
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping
    public ResponseEntity<?> dailyGreeting() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        NewsResponse news = newsService.getNews("in");
        String newsReport = null;
        if (news != null) {
            newsReport = "\n\n\nNews of the day : " + news.getArticles().get(0).getTitle();
        }

        return new ResponseEntity<>("Hi " + authentication.getName() + newsReport , HttpStatus.OK);
    }


}
