package com.fight.light.test;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by yawei.kang on 2018/7/24.
 */

public class HorizontalScrollViewEx extends ViewGroup {
    public HorizontalScrollViewEx(Context context) {
        super(context);
    }

    public HorizontalScrollViewEx(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HorizontalScrollViewEx(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public HorizontalScrollViewEx(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measureWidth = 0;
        int measureHeight = 0;
        int childCount = getChildCount();
        measureChildren(widthMeasureSpec,heightMeasureSpec);

         int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
         int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
         int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
         int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);

         if (childCount== 0){
             setMeasuredDimension(0,0);
         }else if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST){
             View childView = getChildAt(0);
             measureWidth = childView.getMeasuredWidth() * childCount;
             measureHeight = childView.getMeasuredHeight();
             setMeasuredDimension(measureWidth,measureHeight);
         }else if (heightSpecMode == MeasureSpec.AT_MOST){
             View childView = getChildAt(0);
            measureHeight = childView.getMeasuredHeight();
            setMeasuredDimension(widthSpecSize,measureHeight);
         } else if (widthSpecMode == MeasureSpec.AT_MOST ){
             View childView = getChildAt(0);
             measureWidth = childView.getMeasuredWidth()* childCount;
             setMeasuredDimension(measureWidth,heightSpecSize);

         }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
         int childLeft = 0;
         int childCount = getChildCount();
         int mChildrenSize = childCount;

        for (int i = 0; i < childCount; i++) {
           View childView = getChildAt(i);
           if (childView.getVisibility() != View.GONE ){
               int childWidth = childView.getMeasuredWidth();
               childView.layout(childLeft,0,childLeft +  childWidth,childView.getMeasuredHeight());
               childLeft+=childWidth;
           }
        }

    }
}
