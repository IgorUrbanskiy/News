package com.igor.news24.model;

import com.igor.news24.model.data.Rss;

import rx.Observable;

/**
 * Created by Igor on 21.10.2016.
 */

public interface Model {

    Observable<Rss> getNews();
}
