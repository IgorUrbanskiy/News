package com.igor.news24.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.igor.news24.R;
import com.igor.news24.Utils;
import com.igor.news24.model.data.News;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NewsActivity extends AppCompatActivity {
    @Bind(R.id.tv_title)
    TextView title;
    @Bind(R.id.tv_date)
    TextView date;
    @Bind(R.id.web_View)
    WebView mWebView;
    private News mNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        mNews = (News) intent.getSerializableExtra(MainActivity.INTENT_TAG_NEWS_LIST);
        if (Utils.isOnline(this)) {
            mWebView.loadUrl(mNews.getLink());
        } else {
            title.setVisibility(View.VISIBLE);
            date.setVisibility(View.VISIBLE);
            title.setText(mNews.getTitle());
            date.setText(mNews.getPubDate());
            String htmlText = "" + Html.fromHtml(mNews.getDescription());
            mWebView.loadDataWithBaseURL(null, htmlText, "text/html", "en_US", null);
        }
    }
}
