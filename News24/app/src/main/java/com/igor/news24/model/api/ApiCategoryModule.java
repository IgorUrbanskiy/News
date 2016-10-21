package com.igor.news24.model.api;


import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;


public class ApiCategoryModule {

    private static final boolean ENABLE_AUTH = false;
    private static final String AUTH_64 = "***"; //your code here

    public static ApiCategoryInterface getApiInterface() {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Log.d("MyLog", "builder");
                        Request original = chain.request();
                        Request request = original.newBuilder()
                                .header("Authorization", "Basic " + AUTH_64)
                                .method(original.method(), original.body())
                                .build();

                        return chain.proceed(request);
                    }
                })
                .build();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://24tv.ua/rss/")
                .client(new OkHttpClient())
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create());
        if (ENABLE_AUTH) builder.client(httpClient);

        ApiCategoryInterface apiCategoryInterface = builder.build().create(ApiCategoryInterface.class);
        return apiCategoryInterface;
    }

}
