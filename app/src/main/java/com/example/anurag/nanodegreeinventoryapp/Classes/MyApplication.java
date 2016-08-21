package com.example.anurag.nanodegreeinventoryapp.Classes;

import android.app.Application;
import android.content.Context;

import com.example.anurag.nanodegreeinventoryapp.DataBase.PostDatabase;

/**
 * Created by anurag on 8/21/2016.
 */
public class MyApplication extends Application {
    private static PostDatabase mDatabase;
    private static MyApplication sInstance;

    public synchronized static PostDatabase getWritableDatabase() {
        if (mDatabase == null) {
            mDatabase = new PostDatabase(getAppContext());
        }
        return mDatabase;
    }

    public static Context getAppContext() {
        return sInstance.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }
}
