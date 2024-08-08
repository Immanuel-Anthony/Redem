package com.example.Redem.service;

import com.example.Redem.entity.UserEntity;
import com.example.Redem.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public void saveNewUser(UserEntity user){
        try {
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
            user.setRoles(Arrays.asList("USER"));
            userRepository.save(user);
        } catch (Exception e){
            log.error("Nigga balllzlzlzlzlzlz");
        }
    }

    public void createAdmin(UserEntity user){
        String encodedPassword  = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setRoles(Arrays.asList("ADMIN"));
        userRepository.save(user);
    }

    public void saveUser(UserEntity user){
        userRepository.save(user);
    }

    public List<UserEntity> getAll(){
        return userRepository.findAll();
    }

    public Optional<UserEntity> getByID(ObjectId id){
        return userRepository.findById(id);
    }

    public void deleteById(ObjectId id){
        userRepository.deleteById(id);
    }

    public UserEntity findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public void deleteByUsername(String userName){
        userRepository.deleteByUserName(userName);
    }

}
