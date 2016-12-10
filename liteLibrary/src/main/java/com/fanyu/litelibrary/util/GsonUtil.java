package com.fanyu.litelibrary.util;

import android.content.Context;

/**
 *
 * Created by fanyu on 16/12/10.
 */

public abstract class GsonUtil {

    public abstract <T> T json2Obj(String json,Class<T> clazz);

    public abstract String Obj2Json(Object object);
}
