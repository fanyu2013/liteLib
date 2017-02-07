package com.fanyu.litelibrary.util;

import android.util.Log;

import com.fanyu.litelibrary.lite;

/**
 * 统一处理日志
 * @author fanyu
 * 
 */
public class LogUtil {
	public static void i(String tag, Object msg) {
		if (lite.isRelease()) {
			return;
		}
		Log.i(tag, String.valueOf(msg));
	}


}
