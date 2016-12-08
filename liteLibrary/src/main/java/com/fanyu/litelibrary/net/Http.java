package com.fanyu.litelibrary.net;

import android.content.Context;

/**
 *
 * Created by fanyu on 16/12/8.
 */

public interface Http {

    public void doReq(Context context, Object params, String type, ReqInterface reqInterface);
}
