package com.fight.light.util;

import android.view.View;
import android.view.ViewGroup;

public class ViewUtils {

    /**
     * 设置view大小。
     * @param view  View。
     * @param width 指定宽
     * @param width 指定高
     */
    public static void requestLayout(View view, int width, int height) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new ViewGroup.LayoutParams(width, height);
            view.setLayoutParams(layoutParams);
        } else {
                layoutParams.width = width;
            layoutParams.height = height;
            view.setLayoutParams(layoutParams);
//            view.requestLayout();
        }
    }



}
