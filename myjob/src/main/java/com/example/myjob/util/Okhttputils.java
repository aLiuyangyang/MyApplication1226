package com.example.myjob.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class Okhttputils {
    private Handler handler=new Handler(Looper.getMainLooper());
    private OkHttpClient mClient;
    private static volatile Okhttputils mInstance;
    public static Okhttputils getmInstance(){
        if (mInstance==null){
            synchronized (Okhttputils.class){
                mInstance=new Okhttputils();
            }
        }
        return mInstance;
    }
    public static boolean hasNetWork(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        @SuppressLint("MissingPermission") NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
        return activeNetworkInfo!=null && activeNetworkInfo.isAvailable();
    }

    public Okhttputils(){
        HttpLoggingInterceptor httpLoggingInterceptor=new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        mClient=new OkHttpClient.Builder()
                .writeTimeout(10,TimeUnit.SECONDS)
                .connectTimeout(10,TimeUnit.SECONDS)
                .readTimeout(10,TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor)
                .build();
    }
    //get请求
    public void getEnQueue(String path, final Class clazz, final ICallback iCallback){
        Request request=new Request.Builder()
                .url(path)
                .get()
                .build();

        Call call=mClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iCallback.setfiee(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                Gson gson=new Gson();
                final Object o = gson.fromJson(json, clazz);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iCallback.setSuccess(o);
                    }
                });
            }
        });
    }
    //post请求
    public void postEnQueue(String path, Map<String,String> mar, final Class clzz, final ICallback iCallback){
        FormBody.Builder builder=new FormBody.Builder();
        for (Map.Entry<String,String> entry:mar.entrySet()) {
            builder.add(entry.getKey(),entry.getValue());
        }

        RequestBody body=builder.build();
        Request request=new Request.Builder()
                .url(path)
                .post(body)
                .build();

        Call call=mClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iCallback.setfiee(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
               Gson gson=new Gson();
                final Object o = gson.fromJson(json, clzz);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iCallback.setSuccess(o);
                    }
                });
            }
        });



    }

}
