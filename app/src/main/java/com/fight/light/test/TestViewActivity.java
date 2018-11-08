package com.fight.light.test;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.fight.light.R;
import com.fight.light.base.BaseActivity;
import com.fight.light.util.ToastUtils;

/**
 * Created by yawei.kang on 2018/6/21.
 */

public class TestViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        Log.e("sssss","onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_view_layout);
        ImageView tv_img = (ImageView) findViewById(R.id.tv_img);
        CustomDrawable customDrawable = new CustomDrawable(Color.RED);
        tv_img.setImageDrawable(customDrawable);

        findViewById(R.id.touch_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShortToast("hahahaha");
            }
        });
        findViewById(R.id.touch_view).scrollTo(2,0);
//        System.exit(0);
       /* new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();*/

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("ssss","hhahaa");
    }
}
