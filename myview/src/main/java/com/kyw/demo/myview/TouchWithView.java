package com.kyw.demo.myview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;


/**
 * Created by yawei.kang on 2018/9/21.
 */

public class TouchWithView extends android.support.v7.widget.AppCompatTextView {

  private static final String TAG = TouchWithView.class.getSimpleName();

    private int mLastX;
    private int mLastY;

    public TouchWithView(Context context) {
        super(context);
    }

    public TouchWithView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TouchWithView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d("sss",x+"===="+y+"");
                int deltaX = x - mLastX;
                int deltaY = y - mLastY;
                Log.d(TAG,"deltax=="+deltaX+"==="+deltaY);
//                Log.d(TAG,"getTranslationX()"+getTranslationX()+"getTranslationY"+getTranslationY());
//                int translationX = (int) (getTranslationX() + deltaX);
//                int translationY = (int) (getTranslationY()+deltaY);
//                setTranslationX(translationX);
//                setTranslationY(translationY);
                scrollTo(deltaX,deltaY);
                break;
            case MotionEvent.ACTION_UP:
                Log.d("ontouch",""+super.onTouchEvent(event));
             break;
        }
           mLastX = x;
          mLastY = y;

        return true;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        Log.d("onScroll",""+l+""+t+""+oldl+""+oldt);
    }
}
