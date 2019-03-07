package com.newsfeed.sanjanagujjar.newsfeed.repository;

import android.content.Context;
import android.util.Log;

import com.newsfeed.sanjanagujjar.newsfeed.DaggerApplication;
import com.newsfeed.sanjanagujjar.newsfeed.model.NewsDao;
import com.newsfeed.sanjanagujjar.newsfeed.model.NewsDatabase;
import com.newsfeed.sanjanagujjar.newsfeed.model.NewsInfo;
import com.newsfeed.sanjanagujjar.newsfeed.model.NewsListResponse;
import com.newsfeed.sanjanagujjar.newsfeed.network.NetworkCall;
import com.newsfeed.sanjanagujjar.newsfeed.network.NetworkInterface;
import com.newsfeed.sanjanagujjar.newsfeed.viewmodel.NewsViewModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsDataRepo {
    private static String TAG = NewsDataRepo.class.getSimpleName();
    private static final NewsDataRepo sInstance = new NewsDataRepo();
    @Inject
    Context context;
    @Inject NewsDatabase db;

    public static NewsDataRepo getInstance() {
        return sInstance;
    }

    public void init(){
        DaggerApplication.getApp().getAppComponent().inject(this);
    }

    public void getAllNews(final NewsViewModel newsViewModel) {
        Map<String, String> data = new HashMap<>();
        data.put("apiKey", "27362c6d57fa4b20b7a279202def0abf");
        data.put("country", "us");
        NetworkInterface apiService =
                new NetworkCall().getClient().create(NetworkInterface.class);

        Call<NewsListResponse> call = apiService.getAllNewsFeed(data);
        call.enqueue(new Callback<NewsListResponse>() {
            @Override
            public void onResponse(Call<NewsListResponse> call, Response<NewsListResponse> response) {
                if(response.body().getArticles() != null && response.body().getArticles().size() > 0){
                    insetAllNewsFromdb(newsViewModel,response.body().getArticles());
                    Log.d(TAG, "Number of news received: " + response.body().getArticles().size());
                }
            }

            @Override
            public void onFailure(Call<NewsListResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
                if(db != null){
                    loadData(db.newsDao(),newsViewModel);
                }
            }
        });
    }

    private void insetAllNewsFromdb(final NewsViewModel newsViewModel, final List<NewsInfo> newsInfos){
        final NewsDao newsDao = db.newsDao();
        Completable.fromRunnable(new Runnable() {
            @Override
            public void run() {
                for(int i=0; i < newsInfos.size();i++){
                    NewsInfo info = newsInfos.get(i);
                    newsDao.insertAll(info);
                }
            }
        }).subscribeOn(Schedulers.io()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {
                loadData(newsDao,newsViewModel);
            }

            @Override
            public void onError(Throwable e) {
                newsViewModel.setNewsFeedLiveData(newsInfos);
            }
        });
    }

    private void loadData(NewsDao newsDao, final NewsViewModel newsViewModel) {
        newsDao.getAllUsers().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<NewsInfo>>() {
            @Override
            public void accept(List<NewsInfo> newsInfos) throws Exception {
                newsViewModel.setNewsFeedLiveData(newsInfos);
            }
        });
    }

}
