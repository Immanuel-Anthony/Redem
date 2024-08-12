package com.example.Redem.repository;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserRepoImplTest {

    @Autowired
    private UserRepoImpl userRepo;

    @Test
    public void testMongoTemplate(){
        userRepo.getUsersForSA();
    }

}
