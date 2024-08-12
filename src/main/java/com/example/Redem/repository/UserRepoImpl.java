package com.example.Redem.repository;

import com.example.Redem.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserRepoImpl {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<UserEntity> getUsersForSA(){
        Query query = new Query();
        query.addCriteria(Criteria.where("email").regex("^[a-zA-Z0-9_.±]+@[a-zA-Z0-9-]+.[a-zA-Z0-9-.]+$"));
        query.addCriteria(Criteria.where("sentimentAnalysis").is(true));
        List<UserEntity> users = mongoTemplate.find(query , UserEntity.class);
        return users;
    }

}
