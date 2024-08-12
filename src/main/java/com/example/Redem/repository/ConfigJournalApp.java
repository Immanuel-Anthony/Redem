package com.example.Redem.repository;


import com.example.Redem.entity.ConfigJournalAppEntity;
import com.example.Redem.entity.UserEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfigJournalApp extends MongoRepository<ConfigJournalAppEntity, ObjectId> {

}

