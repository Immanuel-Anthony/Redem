package com.example.Redem.serviceTest;

import com.example.Redem.service.EmailService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTest {

    @Autowired
    private EmailService emailService;

    @Disabled
    @Test
    public void testEmail(){
        emailService.sendEmail("immanuelantony883@gmail.com", "Hello nigga", "Test email 123 123 123");
    }

}
