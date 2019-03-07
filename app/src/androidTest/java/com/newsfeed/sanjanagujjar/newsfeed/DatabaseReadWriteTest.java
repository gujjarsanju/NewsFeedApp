package com.newsfeed.sanjanagujjar.newsfeed;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.newsfeed.sanjanagujjar.newsfeed.model.NewsDao;
import com.newsfeed.sanjanagujjar.newsfeed.model.NewsDatabase;
import com.newsfeed.sanjanagujjar.newsfeed.model.NewsInfo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class DatabaseReadWriteTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.newsfeed.sanjanagujjar.newsfeed", appContext.getPackageName());
    }

    private NewsDao userDao;
    private NewsDatabase db;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        db = Room.inMemoryDatabaseBuilder(context, NewsDatabase.class).build();
        userDao = db.newsDao();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void writeUserAndReadInList() throws Exception {
        NewsInfo info = new NewsInfo(0,"ajhsgd","asdasd","asdasdas","dasdasdasd","jsadybas");
        userDao.insertAll(info);
        userDao.getAllUsers().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<NewsInfo>>() {
            @Override
            public void accept(List<NewsInfo> newsInfos) throws Exception {
                for(int i= 0; i < newsInfos.size();i++){
                    assertThat(newsInfos.get(i),equalTo(info));
                }
            }
        });

    }
}
