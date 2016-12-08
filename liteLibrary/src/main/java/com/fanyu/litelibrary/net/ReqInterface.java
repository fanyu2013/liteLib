package com.fanyu.litelibrary.net;

/**
 *
 * Created by fanyu on 16/12/8.
 */

public interface ReqInterface {
    void onSuccess(String response);
    void onFail(Throwable ex);
    void onNoNetWork(String tip);
}
