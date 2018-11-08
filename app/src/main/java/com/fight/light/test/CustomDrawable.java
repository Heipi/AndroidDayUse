package com.fight.light.test;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by yawei.kang on 2018/6/26.
 */

public class CustomDrawable extends Drawable {

    private Paint mPaint;
    public CustomDrawable(int color) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);//抗锯齿
        mPaint.setColor(color);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        Rect r = getBounds();
        float cx = r.exactCenterX();//矩形的精确水平中心作为浮点数。
        float cy = r.exactCenterY();
        canvas.drawCircle(cx,cy,Math.min(cx,cy),mPaint);
    }

    @Override
    public void setAlpha(int alpha) {
      mPaint.setAlpha(alpha);
      invalidateSelf();
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
        invalidateSelf();
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}
