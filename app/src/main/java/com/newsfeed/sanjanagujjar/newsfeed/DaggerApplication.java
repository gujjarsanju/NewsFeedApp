package com.newsfeed.sanjanagujjar.newsfeed;

import android.app.Application;

import com.newsfeed.sanjanagujjar.newsfeed.di.AppComponent;
import com.newsfeed.sanjanagujjar.newsfeed.di.AppModule;
import com.newsfeed.sanjanagujjar.newsfeed.di.DaggerAppComponent;

public class DaggerApplication extends Application {

    AppComponent appComponent;
    static DaggerApplication app;

    public static DaggerApplication getApp() {
        return app;
    }

    @Override

    public void onCreate() {
        super.onCreate();
        app = this;
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
        appComponent.inject(this);
    }


    public AppComponent getAppComponent() {
        return appComponent;
    }
}
