package com.newsfeed.sanjanagujjar.newsfeed.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.newsfeed.sanjanagujjar.newsfeed.R;
import com.newsfeed.sanjanagujjar.newsfeed.data.NewsInfo;

public class NewsDetailsFragment extends Fragment {
    private static final String NEWS_URL = "NEWS_URL";
    private View mRootView;
    private ProgressBar progressBar;
    private RelativeLayout relativeProgress;
    private WebView webView;


    public static NewsDetailsFragment newInstance(NewsInfo newsInfo) {
        NewsDetailsFragment newsDetailFragment = new NewsDetailsFragment();
        Bundle args = new Bundle();
        args.putString(NEWS_URL, newsInfo.getUrl());
        newsDetailFragment.setArguments(args);
        return newsDetailFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (mRootView == null) {
            if (getArguments() != null) {
                mRootView = inflater.inflate(R.layout.fragment_webview, container, false);
                progressBar = mRootView.findViewById(R.id.progressBar1);
                relativeProgress = mRootView.findViewById(R.id.relative_progress);
                webView = (WebView) mRootView.findViewById(R.id.webview);
                WebSettings webSettings = webView.getSettings();
                webSettings.setJavaScriptEnabled(true);
                webView.setWebViewClient(new myWebClient());

                webView.loadUrl(getArguments().getString(NEWS_URL));
            }
        }
        return mRootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public class myWebClient extends WebViewClient
    {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            progressBar.setVisibility(View.VISIBLE);
            relativeProgress.setVisibility(View.VISIBLE);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            progressBar.setVisibility(View.VISIBLE);
            relativeProgress.setVisibility(View.VISIBLE);
            view.loadUrl(url);
            return true;

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            relativeProgress.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
        }
    }
}
