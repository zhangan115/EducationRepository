package com.xueli.application.mode.api;

import com.xueli.application.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.Cookie;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.protobuf.ProtoConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * api
 * Created by zhangan on 2017-06-21.
 */

public final class Api {

    public static final String HOST = BuildConfig.HOST;
    private static Retrofit mRetrofit;
    private static final int CONNECT_TIME = 5;
    private static final int READ_TIME = 20;
    private static final int WRITE_TIME = 20;

    public static Retrofit createRetrofit() {
        if (mRetrofit == null) {
            initRetrofit();
        }
        return mRetrofit;
    }

    private static void initRetrofit() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(CONNECT_TIME, TimeUnit.SECONDS)
                .readTimeout(READ_TIME, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIME, TimeUnit.SECONDS);
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        }
        String host = Api.HOST;
        if (!host.endsWith("/")) {
            host = host + "/";
        }
        mRetrofit = new Retrofit.Builder()
                .client(builder.build())
                .baseUrl(host)
                .addConverterFactory(ProtoConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }
}
