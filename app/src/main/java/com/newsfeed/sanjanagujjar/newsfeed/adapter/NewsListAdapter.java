package com.newsfeed.sanjanagujjar.newsfeed.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.newsfeed.sanjanagujjar.newsfeed.R;
import com.newsfeed.sanjanagujjar.newsfeed.interfaces.NewsClickInterface;
import com.newsfeed.sanjanagujjar.newsfeed.model.NewsInfo;

import java.util.List;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ViewHolder> {
    private List<NewsInfo> mNewsInfoList;
    private Context mContext;
    private NewsClickInterface mNewsClickInterface;

    public NewsListAdapter(@NonNull List<NewsInfo> mNewsInfoList, Context mContext) {
        this.mNewsInfoList = mNewsInfoList;
        this.mContext = mContext;
        if (mContext instanceof NewsClickInterface) {
            mNewsClickInterface = (NewsClickInterface) mContext;
        }
    }

    @NonNull
    @Override
    public NewsListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View rootView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.news_list_row, viewGroup, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsListAdapter.ViewHolder viewHolder, int i) {
        viewHolder.newsTitle.setText(mNewsInfoList.get(i).getTitle());
        Glide.with(mContext)
                .load(mNewsInfoList.get(i).getUrlToImage())
                .into(viewHolder.newsIcon);

    }

    @Override
    public int getItemCount() {
        return mNewsInfoList.size();
    }

    public void setData(List<NewsInfo> infos) {
        this.mNewsInfoList.addAll(infos);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView newsTitle;
        ImageView newsIcon;

        ViewHolder(View itemView) {
            super(itemView);
            newsIcon = itemView.findViewById(R.id.news_image);
            newsTitle = itemView.findViewById(R.id.news_title);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mNewsClickInterface != null) {
                        mNewsClickInterface.onNewsFeedClick(mNewsInfoList.get(getAdapterPosition()));
                    }
                }
            });
        }
    }
}
