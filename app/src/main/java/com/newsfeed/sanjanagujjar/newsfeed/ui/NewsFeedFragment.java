package com.newsfeed.sanjanagujjar.newsfeed.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.newsfeed.sanjanagujjar.newsfeed.MainActivity;
import com.newsfeed.sanjanagujjar.newsfeed.R;
import com.newsfeed.sanjanagujjar.newsfeed.adapter.NewsListAdapter;
import com.newsfeed.sanjanagujjar.newsfeed.data.NewsInfo;
import com.newsfeed.sanjanagujjar.newsfeed.viewmodel.NewsViewModel;

import java.util.ArrayList;
import java.util.List;

public class NewsFeedFragment extends Fragment {
    public static final String NEWS_FRAGMENT_TAG = NewsFeedFragment.class.getSimpleName();
    private View mRootView;
    private NewsListAdapter mNewsListAdapter;
    private MainActivity mMainActivity;
    private ProgressBar mProgressBar;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mMainActivity = (MainActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_news, container, false);
            RecyclerView mRecyclerView = mRootView.findViewById(R.id.news_recycler_view);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            mNewsListAdapter = new NewsListAdapter(new ArrayList<NewsInfo>(),mMainActivity);
            mRecyclerView.setAdapter(mNewsListAdapter);
            NewsViewModel newsViewModel = ViewModelProviders.of(this).get(NewsViewModel.class);
            mProgressBar = mRootView.findViewById(R.id.progressBar);
            mProgressBar.setVisibility(View.VISIBLE);
            newsViewModel.getNewsFeedLiveData().observe(this, new Observer<List<NewsInfo>>() {
                @Override
                public void onChanged(@Nullable List<NewsInfo> newsInfos) {
                    mProgressBar.setVisibility(View.GONE);
                    mNewsListAdapter.setData(newsInfos);
                }
            });
        }
        return mRootView;
    }

}
