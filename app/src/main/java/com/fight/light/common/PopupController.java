package com.fight.light.common;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

public class PopupController {
    private int layoutResId;//布局Id
    private Context context;
    private PopupWindow popupWindow;
    View mPopupView;
    private View mView;
    private Window mWindow;

    public PopupController(Context context, PopupWindow popupWindow) {
        this.context = context;
        this.popupWindow = popupWindow;
    }

    public void setView(int layoutResId) {
        this.layoutResId = layoutResId;
        mView = null;
        initPopupView();
    }

    public void setView(View view) {
        this.mView = view;
        this.layoutResId = 0;
        initPopupView();
    }

    private void initPopupView() {
        if (layoutResId != 0) {
            mPopupView = LayoutInflater.from(context).inflate(layoutResId, null);
        } else if (mView != null) {
            mPopupView = mView;
        }
        popupWindow.setContentView(mPopupView);
    }

    /**
     * 设置宽度
     *
     * @param width  宽
     * @param height 高
     */
    public void setWidthAndHeight(int width, int height) {
        if (width == 0 || height == 0) {
            //如果没设置宽高，默认是WRAP_CONTENT
            popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
            popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        } else {
            popupWindow.setWidth(width);
            popupWindow.setHeight(height);
        }
    }

    /**
     * 设置整个window 的透明度，包括状态栏
     *
     * @param alpha 0 - 1
     */
    public void setBackGroundAlpha(float alpha) {
        mWindow = ((Activity) context).getWindow();
        WindowManager.LayoutParams layoutParams = mWindow.getAttributes();
        layoutParams.alpha = alpha;
        mWindow.setAttributes(layoutParams);
    }

    /**
     * 设置动画
     *
     * @param animationStyle
     */
    public void setAnimationStyle(int animationStyle) {
        popupWindow.setAnimationStyle(animationStyle);
    }

    /**
     * 设置OutSide是否可点击
     *
     * @param touchable
     */
    public void setOutSideTouchable(boolean touchable) {
        int color = context.getResources().getColor(android.R.color.transparent);//透明
        popupWindow.setBackgroundDrawable(new ColorDrawable(color));
        popupWindow.setOutsideTouchable(touchable);//设置outside是否可点击
        popupWindow.setTouchable(touchable);
        popupWindow.setFocusable(touchable);
    }

    static class PopupParams {
         public int layoutResId;
         public Context context;
         public int mWidth;
         public int mHeight;
         public View mView;
         public float screen_level;// 屏幕Window灰色程度
         public int animationStyle;
         public boolean isTouchable = true;
         public boolean isShowWindow;//显示灰色背景
        public boolean isShowAnim;//显示动画

        public PopupParams(Context context) {
            this.context = context;
        }
        public void apply(PopupController popupController){

           if (mView != null){
               popupController.setView(mView);
           }else if (layoutResId!= 0){
               popupController.setView(layoutResId);
           }else{
               throw new NullPointerException("PopupWindow's contentView is null");
           }
           popupController.setWidthAndHeight(mWidth,mHeight);
           popupController.setOutSideTouchable(isTouchable);
           if (isShowWindow){
               popupController.setBackGroundAlpha(screen_level);
           }
           if (isShowAnim){
               popupController.setAnimationStyle(animationStyle);
           }

        }


    }

}
