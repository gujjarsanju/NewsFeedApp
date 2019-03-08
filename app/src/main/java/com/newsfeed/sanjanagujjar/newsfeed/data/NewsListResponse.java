package com.newsfeed.sanjanagujjar.newsfeed.data;

import java.util.List;

public class NewsListResponse {
    private String status;
    private List<NewsInfo> articles;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<NewsInfo> getArticles() {
        return articles;
    }

    public void setArticles(List<NewsInfo> articles) {
        this.articles = articles;
    }
}
