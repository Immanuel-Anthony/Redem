package com.example.Redem;

import jdk.jfr.RecordingState;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableTransactionManagement
public class RedemApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedemApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}



	@Bean
	public PlatformTransactionManager mongoTransactions(MongoDatabaseFactory dbFactory){
		return new MongoTransactionManager(dbFactory);
	}


}