package com.exercise.p.citicup.dao;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by p on 2017/5/11.
 */

public class RetrofitInstance {
    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://104.236.132.15:8080/meet/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build();

    public static Retrofit getRetrofit() {
        return retrofit;
    }
}
