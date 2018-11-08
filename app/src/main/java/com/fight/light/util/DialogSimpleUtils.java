package com.fight.light.util;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by yawei.kang on 2018/2/23.
 */

public class DialogSimpleUtils {


    public static Dialog createProgressDialog(Context context) {
        return createProgressDialog(context, true);
    }

    public static Dialog createProgressDialog(Context context,boolean  needCancle){
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("Loading");
        dialog.setCancelable(needCancle);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }


}
