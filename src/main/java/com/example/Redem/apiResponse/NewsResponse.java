package com.example.Redem.apiResponse;

import lombok.Data;

import java.util.ArrayList;

@Data
public class NewsResponse{

    private ArrayList<Article> articles;

    @Data
    public static class Article {
        private String title;
    }


}


