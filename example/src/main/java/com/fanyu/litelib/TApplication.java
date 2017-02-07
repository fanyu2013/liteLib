package com.fanyu.litelib;

import android.app.Application;

import com.fanyu.litelibrary.lite;

/**
 *
 * Created by fanyu on 17/1/20.
 */
public class TApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        initLiteLib();
    }

    private void initLiteLib() {
        lite.Is.init(this);
        lite.Is.setIsRelease(false);
    }
}
