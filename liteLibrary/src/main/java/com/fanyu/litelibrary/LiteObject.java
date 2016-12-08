package com.fanyu.litelibrary;

import com.fanyu.litelibrary.net.Http;

/**
 * 项目的控制中心
 * Created by fanyu on 16/12/8.
 */

public class LiteObject {
    private static Http http;

    public static void setHttp(Http http) {
        LiteObject.http = http;
    }

    public static Http getHttp() {
        return http;
    }
}
