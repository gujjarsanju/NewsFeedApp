package com.newsfeed.sanjanagujjar.newsfeed.di;

import android.content.Context;

import com.newsfeed.sanjanagujjar.newsfeed.data.NewsDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    //private final DaggerApplication application;
    private final Context context;
    private NewsDatabase db;



    public AppModule(Context context) {
       // this.application = applcation;
        this.context = context;
    }

    @Provides @Singleton Context provideApplicationContext(){
        return context;
    }

    @Provides @Singleton
    NewsDatabase providesApplicationDatabase(){
        db = NewsDatabase.getDatabase(context);
        return db;
    }

}
