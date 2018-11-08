package com.fight.light.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by yawei.kang on 2017/12/4.
 */

public class ScreenUtil {
    private ScreenUtil() {
        throw new UnsupportedOperationException("ScreenUtil cannot be instantiated");
    }

    /**
     * Get Screen Width
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }
    /**
     * Get Screen Height
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }








    /**
     * dp to px
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     * @param context
     * @param dpValue
     * @return
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px to dp
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
    /**
     * Get Status Bar Height
     * @param context
     * @return
     */
    public static int getStatusHeight(Context context) {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }
    /**
     * Get Status Bar Height
     * @param context
     * @return
     */
    public static int getStatusHeightByResId(Context context) {
        int statusHeight = -1;
        try {
            int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                //根据资源ID获取响应的尺寸值
                statusHeight = context.getResources().getDimensionPixelSize(resourceId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }


    /**
     *  Get Current Screen Bitmap
     *  isDeleteStatusBar, true deleted, false no
     * 获取当前屏幕截图，不包含状态栏 *
     */
    public static Bitmap snapShotAboutStatusBar(Activity activity,boolean isDeleteStatusBar) {
         /*获取windows中最顶层的view*/
        View view = activity.getWindow().getDecorView();
        //允许当前窗口保存缓存信息
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        //获取状态栏高度
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        int width = getScreenWidth(activity);
        int height = getScreenHeight(activity);

        Bitmap bp = null;
        //去掉状态栏
        if (isDeleteStatusBar) {
            bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height - statusBarHeight);
        }else {
            bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
        }
        //销毁缓存信息
        view.destroyDrawingCache();
        view.setDrawingCacheEnabled(false);
        return bp;
    }

    /**
     * 获取手机的基本dpi
     *
     * @param context
     * @return
     */
    public static int getDensityDpi(Context context) {
        final int densityDpi = context.getResources().getDisplayMetrics().densityDpi;
        return densityDpi;
    }
    /**
     * 获取手机的最小宽度DP
     *
     * @param context
     * @return
     */
    public static float getSmallWidthDp(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int widthPixels = dm.widthPixels;
        int heightPixels = dm.heightPixels;
        LogUtils.d("screen width height",widthPixels+"==="+heightPixels);
        float density = dm.density;
        if (widthPixels > heightPixels){
            return heightPixels/density;
        }
        return widthPixels/density;

    }

}
