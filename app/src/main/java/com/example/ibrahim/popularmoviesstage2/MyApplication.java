package com.example.ibrahim.popularmoviesstage2;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import okhttp3.OkHttpClient;

/**
 * Created by ibrahim on 3/12/18.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);

        OkHttpClient okClient = new OkHttpClient
                .Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();

        Picasso picasso = new Picasso.Builder(this)
                .downloader(new OkHttp3Downloader(okClient))
                .build();

        Picasso.setSingletonInstance(picasso);
    }
}
