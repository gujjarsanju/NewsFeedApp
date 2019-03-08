package com.newsfeed.sanjanagujjar.newsfeed;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.newsfeed.sanjanagujjar.newsfeed.interfaces.NewsClickInterface;
import com.newsfeed.sanjanagujjar.newsfeed.data.NewsInfo;
import com.newsfeed.sanjanagujjar.newsfeed.ui.NewsDetailsFragment;
import com.newsfeed.sanjanagujjar.newsfeed.ui.NewsFeedFragment;

import static com.newsfeed.sanjanagujjar.newsfeed.ui.NewsFeedFragment.NEWS_FRAGMENT_TAG;

public class MainActivity extends AppCompatActivity implements NewsClickInterface{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((DaggerApplication) getApplication()).getAppComponent().inject(this);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container, new NewsFeedFragment(), NEWS_FRAGMENT_TAG);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) finish();
    }

    @Override
    public void onNewsFeedClick(NewsInfo info) {

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container, NewsDetailsFragment.newInstance(info), NEWS_FRAGMENT_TAG);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
