package com.example.forum.network;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class NetApplication extends Application {
    public static final String http_ip = "http://127.0.0.1:9999/";
    //public static final String http_ip = "http://127.0.0.1:7000/";
    public static final String websocket_ip = "ws://127.0.0.1:520/ws";
//    public static final String websocket_ip = "ws://0:0:0:0:0:0:0:0:520/ws";
    private static WebSocket mwebSocket;
    private static final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();
    private static String cookie;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static void openWebsocket() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
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
        Request request = new Request.Builder().url(websocket_ip).build();
        WebSocketListener socketListener = new WebSocketListener() {
            @Override
            public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
                super.onOpen(webSocket, response);
                mwebSocket = webSocket;
                Log.d("WebSocket", "open socket");
                Log.d("WebSocket", "Thread.id: " + android.os.Process.myPid());
            }

            @Override
            public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
                super.onMessage(webSocket, text);
                Log.d("WebSocket", "received: " + text);
                Log.d("WebSocket", "Thread.id: " + android.os.Process.myPid());
            }
        };
        okHttpClient.newWebSocket(request, socketListener);
//        okHttpClient.dispatcher().executorService().shutdown();
    }

    public static WebSocket getWebSocket() {
        return mwebSocket;
    }

    public static void setCookie(String cookie) {
        NetApplication.cookie = cookie;
    }

    public static String getCookie() {
        return cookie;
    }

    public static String getHttp_ip() {
        return http_ip;
    }
}
