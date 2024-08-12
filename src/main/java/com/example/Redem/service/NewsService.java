package com.example.Redem.service;

import com.example.Redem.apiResponse.NewsResponse;
import com.example.Redem.cache.ApplicationCache;
import com.example.Redem.constants.Placeholders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NewsService {

    @Value("${news_api}")
    private String apiKey;

    @Autowired
    private ApplicationCache applicationCache;

    @Autowired
    private RestTemplate restTemplate;

    public NewsResponse getNews(String country){
        String final_API = applicationCache.appCache.get(ApplicationCache.keys.news_api.toString()).replace(Placeholders.country, country).replace(Placeholders.apiKey , apiKey);
        ResponseEntity<NewsResponse> response = restTemplate.exchange(final_API, HttpMethod.GET, null, NewsResponse.class);
        NewsResponse newsResponse = response.getBody();
        return newsResponse;
    }

}
