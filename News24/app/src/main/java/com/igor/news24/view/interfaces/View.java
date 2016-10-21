package com.igor.news24.view.interfaces;

import com.igor.news24.model.data.News;
import com.igor.news24.model.data.Rss;

import java.util.List;

/**
 * Created by Igor on 21.10.2016.
 */

public interface View {

    void showData(Rss list);

    void showLocalData(List<News> list);

    void showError(String error);

    void showProgress();

    void hideProgress();
}