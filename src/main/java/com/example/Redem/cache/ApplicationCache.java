package com.example.Redem.cache;

import com.example.Redem.entity.ConfigJournalAppEntity;
import com.example.Redem.repository.ConfigJournalApp;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ApplicationCache {

    public enum keys {
        news_api;
    }

    @Autowired
    private ConfigJournalApp configJournalApp;

    public Map<String , String> appCache;

    @PostConstruct
    public void init(){
        appCache = new HashMap<>();
        List<ConfigJournalAppEntity> all = configJournalApp.findAll();
        for(ConfigJournalAppEntity configJournalAppEntity : all){
            appCache.put(configJournalAppEntity.getKey() , configJournalAppEntity.getValue());
        }
    }

}
