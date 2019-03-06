package com.example.homediy;

import android.app.Application;

public class MyApplication extends Application
{
    private static MyApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    private MyPreferenceManager pref;

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    public MyPreferenceManager getPrefManager() {
        if (pref == null) {
            pref = new MyPreferenceManager(this);
        }
        return pref;
    }

}
