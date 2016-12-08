package com.fanyu.litelibrary.net;

import android.content.Context;

import java.util.List;

/**
 *
 * Created by fanyu on 16/12/8.
 */

public interface Http {

    public void doReq(Context context, List<Object> params, String type, ReqInterface reqInterface);
}
