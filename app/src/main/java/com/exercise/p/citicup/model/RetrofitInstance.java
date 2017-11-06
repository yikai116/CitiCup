package com.exercise.p.citicup.model;

import com.exercise.p.citicup.Helper;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by p on 2017/5/11.
 */

public class RetrofitInstance {
    private static Retrofit retrofit = null;


    private static Retrofit retrofitWithToken = null;

    /**
     * @return 不带验证码的Retrofit
     */
    public static Retrofit getRetrofit() {
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://104.236.132.15:8080/CitiCup/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }

    /**
     * @return 带验证码的Retrofit
     */
    public static Retrofit getRetrofitWithToken() {
        if (retrofitWithToken == null) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request request = chain.request();
                            Request.Builder builder1 = request.newBuilder();
                            Request newRequest = builder1.addHeader("token", Helper.IMEI).build();
                            return chain.proceed(newRequest);
                        }
                    })
                    .build();
            retrofitWithToken = new Retrofit.Builder()
                    .client(client)
                    .baseUrl("http://104.236.132.15:8080/CitiCup/api/set/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }
        return retrofitWithToken;
    }
}
