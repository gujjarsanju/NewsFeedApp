package com.newsfeed.sanjanagujjar.newsfeed.network;

import com.newsfeed.sanjanagujjar.newsfeed.model.NewsListResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface NetworkInterface {
    @GET("v2/top-headlines")
    Call<NewsListResponse> getAllNewsFeed(@QueryMap Map<String, String> options);
}
