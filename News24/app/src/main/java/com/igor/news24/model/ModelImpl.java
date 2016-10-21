package com.igor.news24.model;


import com.igor.news24.model.api.ApiCategoryInterface;
import com.igor.news24.model.api.ApiCategoryModule;
import com.igor.news24.model.data.Rss;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ModelImpl implements Model {

    ApiCategoryInterface mApiCategoryInterface = ApiCategoryModule.getApiInterface();

    @Override
    public Observable<Rss> getNews() {
        return mApiCategoryInterface.getNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


}
