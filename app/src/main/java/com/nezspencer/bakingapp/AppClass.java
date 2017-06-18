package com.nezspencer.bakingapp;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by nezspencer on 6/15/17.
 */

public class AppClass extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }
}
