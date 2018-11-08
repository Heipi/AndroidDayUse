package com.fight.light.util;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

/**
 * Created by yawei.kang on 2017/12/4.
 */

public class ClickUtils {
    private static long mLastClickTime = 0;
    private static long mFirstClickTime = 0;
    private ClickUtils() {

    }

    /**
     * Determine Double Click IS Or NO
     * 判断是否快速连续点击
     * @return
     */
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - mLastClickTime;
        if (0 < timeD && timeD < 500) {
            return true;
        }
        mLastClickTime = time;
        return false;
    }

    /**
     * Double Click Exit APP
     * 双击退出
     */
    public static void delayClickDoubleExit(Activity activity){
        long tempTime = System.currentTimeMillis();
        if ((tempTime - mFirstClickTime) > 2000){
            Toast.makeText(activity, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mFirstClickTime = tempTime;
        }else{
            activity.finish();
            System.exit(0);
        }
    }

}
