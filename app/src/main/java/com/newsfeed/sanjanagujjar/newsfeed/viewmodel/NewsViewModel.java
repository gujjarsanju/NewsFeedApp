package com.newsfeed.sanjanagujjar.newsfeed.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.newsfeed.sanjanagujjar.newsfeed.MainActivity;
import com.newsfeed.sanjanagujjar.newsfeed.model.NewsInfo;
import com.newsfeed.sanjanagujjar.newsfeed.repository.NewsDataRepo;

import java.util.List;

public class NewsViewModel extends AndroidViewModel {
    private MutableLiveData<List<NewsInfo>> mNewsFeedLiveData;

    public NewsViewModel(@NonNull Application application) {
        super(application);
        mNewsFeedLiveData = new MutableLiveData<>();
    }

    public LiveData<List<NewsInfo>> getNewsFeedLiveData() {
        NewsDataRepo.getInstance().init();
        NewsDataRepo.getInstance().getAllNews(this);
        return mNewsFeedLiveData;
    }

    public void setNewsFeedLiveData(List<NewsInfo> newsFeedLiveData) {
        mNewsFeedLiveData.setValue(newsFeedLiveData);
    }

}
