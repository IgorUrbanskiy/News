package com.igor.news24.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.igor.news24.R;
import com.igor.news24.Utils;
import com.igor.news24.model.data.News;
import com.igor.news24.model.data.Rss;
import com.igor.news24.presenter.NewsPresenter;
import com.igor.news24.presenter.Presenter;
import com.igor.news24.view.adapters.RecyclerViewAdapter;
import com.igor.news24.view.interfaces.View;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View, RecyclerViewAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    public static final String INTENT_TAG_NEWS_LIST = "intent_mews_list";
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        initToolbar();
        mPresenter = new NewsPresenter(MainActivity.this, this);
        mPresenter.show();
        initRecycler();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mPresenter != null) {
            mPresenter.onStop();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
    }

    private void initRecycler() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        adapter = new RecyclerViewAdapter(this).withClickListener(this);
        LinearLayoutManager llm = new LinearLayoutManager(this, LinearLayout.VERTICAL, false);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapter);
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public void showData(Rss list) {
        adapter.setRss(list);
    }

    @Override
    public void showLocalData(List<News> list) {
        adapter.setNewsList(list);
    }

    @Override
    public void showError(String error) {
        Log.d("Mylog", "error " + error);
    }

    @Override
    public void showProgress() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onItemClick(News news) {
        Intent intent = new Intent(this, NewsActivity.class);
        intent.putExtra(INTENT_TAG_NEWS_LIST, news);
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        mPresenter.show();
        mSwipeRefreshLayout.setRefreshing(false);
        if (!Utils.isOnline(this)){
            Toast.makeText(this,"Impossible to refressh Offline mode", Toast.LENGTH_SHORT).show();
        }
    }
}
