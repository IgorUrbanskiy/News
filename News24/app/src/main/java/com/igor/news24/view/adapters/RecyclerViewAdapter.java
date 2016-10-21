package com.igor.news24.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.igor.news24.R;
import com.igor.news24.Utils;
import com.igor.news24.model.data.News;
import com.igor.news24.model.data.Rss;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    OnItemClickListener mOnItemClickListener;
    Context mContext;
    Rss mRss;
    List<News> mNewsList;

    public RecyclerViewAdapter(Context context) {
        mContext = context;
    }

    public RecyclerViewAdapter withClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
        return this;
    }

    public void setRss(Rss list) {
        mRss = list;
        notifyDataSetChanged();
    }

    public void setNewsList(List<News> list) {
        mNewsList = list;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(News news);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_news_list, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        if (Utils.isOnline(mContext)) {
            Log.d("MYLOG", "ONLINE");
            final News news = mRss.getmChannel().getNewsItems().get(position);
            showList(news, viewHolder);
        } else {
            Log.d("MYLOG", "OFFLINE");
            final News news = mNewsList.get(position);
            showList(news, viewHolder);
        }
    }

    private void showList(final News news, ViewHolder viewHolder) {
        Log.d("MYLOG", "SHOWLIST");
        String imageLink = news.getDescription();
        String resultImage = imageLink.substring(imageLink.indexOf("src=") + 5, imageLink.indexOf("jpg") + 3);
        if (news.getDescription() != null && !resultImage.equals("")) {
            Picasso.with(viewHolder.mImageView.getContext())
                    .load(resultImage)
                    .resize(250, 250)
                    .into(viewHolder.mImageView);
        } else {
            Picasso.with(viewHolder.mImageView.getContext())
                    .load(R.drawable.image_preview)
                    .resize(250, 250)
                    .into(viewHolder.mImageView);
        }
        viewHolder.title.setText(news.getTitle());
        String date = news.getPubDate();
        String resultDate = date.replace("GMT", "");
        viewHolder.date.setText(resultDate);
        viewHolder.mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MYLOG", "OnItemClick");
                mOnItemClickListener.onItemClick(news);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mRss != null) {
            return mRss.getmChannel().getNewsItems().size();
        } else if (mNewsList != null) {
            Log.d("MYLOG", "mNewsList.size = " + mNewsList.size());
            return mNewsList.size();
        } else return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.title)
        TextView title;
        @Bind(R.id.date)
        TextView date;
        @Bind(R.id.image)
        ImageView mImageView;
        @Bind(R.id.layout_item)
        LinearLayout mLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}