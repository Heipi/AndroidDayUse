package com.fight.light.test;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.fight.light.R;

/**
 * Created by yawei.kang on 2018/6/22.
 */

public class MyView extends View {

        private static final String TAG = "BARRY";

        private Paint mPaint = null;
        private Bitmap mBitmap = null;

        public MyView(Context context) {
            this(context, null);
        }

        public MyView(Context context, AttributeSet attrs) {
            super(context, attrs);
                    mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.apps_logo);
            init();
        }

        public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        private void init() {
            mPaint = new Paint();
            mPaint.setColor(Color.BLUE);
            mPaint.setAntiAlias(true);
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setStrokeWidth(20);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            //第1次保存，并通过canvas.getSaveCount的到当前状态栈容量
            canvas.save();
            Log.i(TAG, "Current SaveCount = " + canvas.getSaveCount());
            canvas.translate(400, 400);
            RectF rectF = new RectF(0,0,600,600);
            canvas.drawBitmap(mBitmap, null, rectF, mPaint);
            canvas.restore();
            //第2次保存，并通过canvas.getSaveCount的到当前状态栈容量
            canvas.save();
            Log.i(TAG, "Current SaveCount = " + canvas.getSaveCount());

            canvas.rotate(45);
            canvas.drawBitmap(mBitmap, null, rectF, mPaint);


            //第3次保存，并通过canvas.getSaveCount的到当前状态栈容量
            canvas.save();
            Log.i(TAG, "Current SaveCount = " + canvas.getSaveCount());


            canvas.translate(800, 400);
            canvas.drawBitmap(mBitmap, null, rectF, mPaint);
          /*   //第4次保存，并通过canvas.getSaveCount的到当前状态栈容量
            canvas.save();
            Log.i(TAG, "Current SaveCount = " + canvas.getSaveCount());
            //通过canvas.restoreToCount出栈到第三层状态

            canvas.restoreToCount(3);
            Log.i(TAG, "restoreToCount--Current SaveCount = " + canvas.getSaveCount());

            canvas.translate(0, 200);

            //rectF = new RectF(0,0,600,600);
            canvas.drawBitmap(mBitmap, null, rectF, mPaint);
            //通过canvas.restoreToCount出栈到第1层(最原始的那一层)状态
            canvas.restoreToCount(1);
            Log.i(TAG, "restoreToCount--Current SaveCount = " + canvas.getSaveCount());
            canvas.drawBitmap(mBitmap, null, rectF, mPaint);*/


    }




}
