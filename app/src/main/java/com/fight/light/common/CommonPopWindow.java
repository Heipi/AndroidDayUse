package com.fight.light.common;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.PopupWindow;

public class CommonPopWindow extends PopupWindow {
    private static final String TAG = "CommonPopWindow";
    private PopupController controller;
    public CommonPopWindow(Context context){
      controller = new PopupController(context,this);
    }

    @Override
    public int getWidth() {
        return controller.mPopupView.getMeasuredWidth();
    }

    @Override
    public int getHeight() {
        return controller.mPopupView.getMeasuredHeight();
    }

    public interface ViewListener {
        void getChildView(View view, int layoutResId);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        controller.setBackGroundAlpha(1.0f);
    }

    public static class Builder {

        private final PopupController.PopupParams params;
        private ViewListener listener;

        public Builder(Context context) {
            this.params = new PopupController.PopupParams(context);
        }

        /**
         * @param layoutResId 设置PopupWindow 布局ID
         * @return Builder
         */
        public Builder setView(int layoutResId) {
            params.mView = null;
            params.layoutResId = layoutResId;
            return this;
        }

        /**
         * @param view 设置PopupWindow布局
         * @return Builder
         */
        public Builder setView(View view) {
            params.mView = view;
            params.layoutResId = 0;
            return this;
        }

        /**
         * 设置宽度和高度 如果不设置 默认是wrap_content
         *
         * @param width 宽
         * @return Builder
         */
        public Builder setWidthAndHeight(int width, int height) {
            params.mWidth = width;
            params.mHeight = height;
            return this;
        }

        /**
         * 设置背景灰色程度
         *
         * @param alpha 0.0f-1.0f
         * @return Builder
         */
        public Builder setBackGroundAlpha(float alpha) {
            params.isShowWindow = true;
            params.screen_level = alpha;
            return this;
        }

        /**
         * 是否可点击Outside消失
         *
         * @param touchable 是否可点击
         * @return Builder
         */
        public Builder setOutSideTouchable(boolean touchable) {
            params.isTouchable = touchable;
            return this;
        }

        public Builder setAnimationStyle(int animationStyle) {
            params.isShowAnim = true;
            params.animationStyle = animationStyle;
            return this;
        }

        public CommonPopWindow create() {
            final CommonPopWindow popWindow = new CommonPopWindow(params.context);
            params.apply(popWindow.controller);

            if (listener != null && params.layoutResId != 0){
                listener.getChildView(popWindow.controller.mPopupView,params.layoutResId);
            }
                measureWidthAndHeight(popWindow.controller.mPopupView);
                Log.d(TAG,"宽:"+popWindow.getWidth()+"高:"+popWindow.getHeight());

            return popWindow;

        }
        private static void measureWidthAndHeight(View view) {
            int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            view.measure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
