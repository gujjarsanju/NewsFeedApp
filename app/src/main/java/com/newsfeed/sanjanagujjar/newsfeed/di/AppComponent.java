package com.newsfeed.sanjanagujjar.newsfeed.di;


import com.newsfeed.sanjanagujjar.newsfeed.DaggerApplication;
import com.newsfeed.sanjanagujjar.newsfeed.MainActivity;
import com.newsfeed.sanjanagujjar.newsfeed.network.NetworkCall;
import com.newsfeed.sanjanagujjar.newsfeed.repository.NewsDataRepo;

import javax.inject.Singleton;

import dagger.Component;
@Singleton
@Component(
        modules = {AppModule.class}
)
public interface AppComponent {
    void inject(DaggerApplication application);
    void inject(MainActivity mainActivity);
    void inject(NetworkCall networkCall);
    void inject(NewsDataRepo newsDataRepo);
}
