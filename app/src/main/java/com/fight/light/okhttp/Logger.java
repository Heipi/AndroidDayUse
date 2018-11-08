package com.fight.light.okhttp;

import android.util.Log;

/**
 * Created by weiwen.liu on 2017/4/18.
 */

public class Logger {
    private static boolean _androidLogEnabled = true;

    public static void infow(String tag, String msg) {

        if (_androidLogEnabled) {
//            if (BuildConfig.DEBUG) {
            Log.w(tag, msg== null?"null":msg);
//           }
        }
    }
    public static void infoe(String tag, String msg) {

        if (_androidLogEnabled) {
//            if (BuildConfig.DEBUG) {
                Log.e(tag, msg== null?"null":msg);
//            }
        }
    }
    public static void infod(String tag, String msg) {

        if (_androidLogEnabled) {
          /*  if (BuildConfig.DEBUG) {*/
                Log.d(tag, msg== null?"null":msg);
//            }
        }
    }
}
