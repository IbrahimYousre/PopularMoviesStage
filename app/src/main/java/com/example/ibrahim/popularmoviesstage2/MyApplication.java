package com.example.ibrahim.popularmoviesstage2;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by ibrahim on 3/12/18.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
