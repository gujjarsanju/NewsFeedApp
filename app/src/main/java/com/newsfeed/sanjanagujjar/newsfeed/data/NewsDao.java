package com.newsfeed.sanjanagujjar.newsfeed.data;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(NewsInfo newsInfos);

    @Query("SELECT * from news_info ")
    Single<List<NewsInfo>> getAllUsers();

    @Query("DELETE FROM news_info")
    void deleteAll();
}
