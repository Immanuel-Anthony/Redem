package com.example.Redem.service;

import com.example.Redem.apiResponse.NewsResponse;
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

    private static final String apiUrl = "https://newsapi.org/v2/top-headlines?country=COUNTRY&apiKey=API_KEY";

    @Autowired
    private RestTemplate restTemplate;

    public NewsResponse getNews(String country){
        String final_API = apiUrl.replace("COUNTRY" , country).replace("API_KEY" , apiKey);
        ResponseEntity<NewsResponse> response = restTemplate.exchange(final_API, HttpMethod.GET, null, NewsResponse.class);
        NewsResponse newsResponse = response.getBody();
        return newsResponse;
    }

}
