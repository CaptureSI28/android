package com.example.josephrocca.multiviewapptest;

import android.app.Application;
import android.content.Context;

/**
 * Created by josephrocca on 30/04/15.
 */
public class App extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getContext(){
        return mContext;
    }
}