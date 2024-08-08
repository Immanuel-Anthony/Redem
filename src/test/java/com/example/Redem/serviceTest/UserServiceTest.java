package com.example.Redem.serviceTest;

import com.example.Redem.entity.JournalEntry;
import com.example.Redem.entity.UserEntity;
import com.example.Redem.repository.UserRepository;
import net.bytebuddy.implementation.bind.annotation.IgnoreForBinding;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.convert.DataSizeUnit;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserRepository userRepository;


    public void findIfAdmin(){
        UserEntity user = userRepository.findByUserName("Doodle");
        List<String> collect = user.getRoles().stream().filter(x -> x.equals("ADMIN")).toList();
        assertTrue(!collect.isEmpty());
    }


    public void findIfNotAdmin(){
        UserEntity user = userRepository.findByUserName("Doodle");
        List<String> collect = user.getRoles().stream().filter(x -> x.equals("ADMIN")).toList();
        assertTrue(collect.isEmpty());
    }

    @CsvSource({
            "Doodle",
            "Admin",
            "Rajesh"
    })
    public void validUsers(String x){
        assertNotNull(userRepository.findByUserName(x) , "User not found with the following name " + x);
    }

}
