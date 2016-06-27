package com.linheimx.app.linlock.api;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by LJIAN on 2016/5/16.
 */
public class ServiceGenerator {

    public static String URL_BASE = "http://apis.baidu.com/";


    private static OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();

    private static Retrofit.Builder getBuilder() {
        return new Retrofit.Builder()
                .baseUrl(URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create());
    }


    public static <S> S createService(Class<S> serviceClass) {

        // set ok builder
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        okBuilder.addInterceptor(logging);
        okBuilder.connectTimeout(30 * 1000, TimeUnit.MILLISECONDS);
        okBuilder.readTimeout(30 * 1000, TimeUnit.MILLISECONDS);
        okBuilder.writeTimeout(30 * 1000, TimeUnit.MILLISECONDS);

        Retrofit retrofit = getBuilder().client(okBuilder.build()).build();
        return retrofit.create(serviceClass);
    }

}
