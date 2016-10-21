package com.igor.news24.model.api;

import com.igor.news24.model.data.Rss;

import retrofit2.http.GET;
import rx.Observable;

public interface ApiCategoryInterface {

    @GET("all.xml")
    Observable<Rss> getNews();

}
