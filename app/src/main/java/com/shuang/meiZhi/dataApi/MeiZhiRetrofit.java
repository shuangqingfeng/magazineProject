package com.shuang.meiZhi.dataApi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;

/**
 * @author feng
 * @Description: retrofit 专注请求网络
 * @date 2017/3/23
 */
public class MeiZhiRetrofit {
    private static Gson gson;
    private static MeiZhiApi meiZhiApi;

    private MeiZhiRetrofit() {

    }

    private static Gson getGson() {
        if (null == gson) {
            gson = new GsonBuilder().create();
        }
        return gson;
    }

    public static MeiZhiApi getMeiZhiApi() {
        OkHttpClient.Builder httpClient = new OkHttpClient().newBuilder();
        if (Logger.init().getLogLevel() != LogLevel.NONE) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(logging);
        }
        httpClient.connectTimeout(12, TimeUnit.SECONDS);
        OkHttpClient client = httpClient.build();
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl("http://gank.io/api/")
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create(getGson()));
        Retrofit retrofit = builder.build();
        meiZhiApi = retrofit.create(MeiZhiApi.class);
        return meiZhiApi;

    }


}

