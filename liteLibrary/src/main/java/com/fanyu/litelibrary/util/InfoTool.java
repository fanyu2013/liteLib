package com.fanyu.litelibrary.util;

import android.app.ProgressDialog;
import android.content.Context;

/**
 *
 * Created by fanyu on 16/12/9.
 */

public abstract class InfoTool {
    private ProgressDialog prgDialog;

    public abstract void showPrgDialog(Context context, String msg);

    public abstract void prgDialogUpdate(String str);

    public abstract void closePrgDialog();

    public abstract void showToast(Context context, String msg);
}
