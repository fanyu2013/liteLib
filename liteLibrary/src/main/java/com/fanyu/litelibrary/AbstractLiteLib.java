package com.fanyu.litelibrary;

import com.fanyu.litelibrary.net.Http;
import com.fanyu.litelibrary.util.InfoTool;

/**
 *
 * Created by fanyu on 16/12/8.
 */
public abstract class AbstractLiteLib {

    public abstract Http http();

    public abstract InfoTool infoTool();
}
