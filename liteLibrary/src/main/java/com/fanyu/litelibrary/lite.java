package com.fanyu.litelibrary;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.fanyu.litelibrary.ble.BleManager;
import com.fanyu.litelibrary.ble.BleManager_I;
import com.fanyu.litelibrary.net.Request_I;

/**
 *
 * Created by fanyu on 16/12/8.
 */
public final class lite {

    public static boolean isRelease() {
        return Is.isRelease;
    }

    public static Context appContext(){
        return Is.app;
    }

    public static Request_I request() {
        if (Is.request_i == null) {
            //TODO  add impl
        }
        return Is.request_i;
    }

    public static BleManager_I ble(){
        if (Is.bleManager_i == null){
            BleManager.registerInstance();
        }
        return Is.bleManager_i;
    }

    public static class Is {
        private static boolean isRelease;
        @SuppressLint("StaticFieldLeak")//这个地方不会泄露,没问题
        private static Application app;
        private static Request_I request_i;
        private static BleManager_I bleManager_i;

        public static void setIsRelease(boolean isRelease) {
            Is.isRelease = isRelease;
        }

        public static void init(Application app) {
            if (Is.app == null) {
                Is.app = app;
            }
        }

        public static void setRequest_i(Request_I request_i) {
            Is.request_i = request_i;
        }

        public static void setBleManager_i(BleManager_I bleManager_i) {
            Is.bleManager_i = bleManager_i;
        }
    }

}
