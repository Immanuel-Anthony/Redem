package com.example.Redem.scheduler;

import com.example.Redem.cache.ApplicationCache;
import com.example.Redem.entity.JournalEntry;
import com.example.Redem.entity.UserEntity;
import com.example.Redem.enums.Sentiment;
import com.example.Redem.repository.UserRepoImpl;
import com.example.Redem.service.EmailService;
import com.example.Redem.service.SentimentAnalysis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserScheduler {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepoImpl userRepo;

    @Autowired
    private SentimentAnalysis sentimentAnalysis;

    @Autowired
    private ApplicationCache applicationCache;

    @Scheduled(cron = "0 0 9 * * SUN")
    public void fetchUsersForMail(){
        List<UserEntity> usersForSA = userRepo.getUsersForSA();
        for(UserEntity user : usersForSA){
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<Sentiment> sentiments = journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x -> x.getSentiment()).collect(Collectors.toList());
            Map<Sentiment , Integer> sentimentCount = new HashMap<>();
            for(Sentiment sentiment : sentiments){
                sentimentCount.put(sentiment , sentimentCount.getOrDefault(sentiment , 0) + 1);
            }
            Sentiment mostFrequentSentiment = null;
            int maxCount = 0;
            for(Map.Entry<Sentiment , Integer> entry : sentimentCount.entrySet()){
                if(entry.getValue() > maxCount){
                    maxCount = entry.getValue();
                    mostFrequentSentiment = entry.getKey();
                }
            }
            if(mostFrequentSentiment != null){
                emailService.sendEmail(user.getEmail() , "Sentiment of this Week!" , mostFrequentSentiment.toString());
            }
        }
    }

    @Scheduled(cron = "0 0/10 * ? * *")
    public void refreshAppCache(){
        applicationCache.init();
    }

}
