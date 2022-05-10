package com.example.forum.network;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtil {
    private static volatile Retrofit retrofit;
    private static volatile OkHttpClient okHttpClient;
    private static final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

    private RetrofitUtil() {
        createRetrofit();
    }

    private static void createRetrofit() {
        okHttpClient = new OkHttpClient.Builder()
                .readTimeout(3, TimeUnit.SECONDS)
                .writeTimeout(3, TimeUnit.SECONDS)
                .connectTimeout(3, TimeUnit.SECONDS)
                .cookieJar(new CookieJar() {
                    @Override
                    public void saveFromResponse(@NotNull HttpUrl httpUrl, @NotNull List<Cookie> list) {
                        cookieStore.put(httpUrl.host(), list);
                    }

                    @NotNull
                    @Override
                    public List<Cookie> loadForRequest(@NotNull HttpUrl httpUrl) {
                        List<Cookie> cookies = cookieStore.get(httpUrl.host());
                        return cookies != null ? cookies : new ArrayList<>();
                    }
                })
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl(com.example.forum.network.NetApplication.http_ip)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    public static Retrofit getRetrofit() {
        getInstance();
        return retrofit;
    }

    private static class RetrofitUtilHolder {
        private static final RetrofitUtil INSTANCE = new RetrofitUtil();
    }

    private static RetrofitUtil getInstance() {
        return RetrofitUtilHolder.INSTANCE;
    }

    public static OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    public static void reset() {
        createRetrofit();
    }
}
