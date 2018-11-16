package com.fight.light.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fight.light.R;
import com.fight.light.base.BaseActivity;
import com.fight.light.util.BitmapUtils;
import com.fight.light.util.NetUtils;
import com.fight.light.util.ScreenUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BitmapActivity extends BaseActivity {
    @BindView(R.id.hello_world)
    TextView hello_world;
    @BindView(R.id.task)
    Button task;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap);





        ButterKnife.bind(this);
        Log.e("TAG", ""+ ScreenUtil.getStatusHeight(this));
        Log.e("TAG2", ""+ScreenUtil.getStatusHeightByResId(this));
        Log.e("sssssss",""+ScreenUtil.getScreenHeight(this));
        hello_world.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("sssss", ""+ NetUtils.isWifiConnected(BitmapActivity.this));
//                NetUtils.openSetting(MainActivity.this);
                Bitmap bitmap =  ScreenUtil.snapShotAboutStatusBar(BitmapActivity.this,true);
                Log.e("sssssssss", Environment.getExternalStorageDirectory().getPath());
                if (ContextCompat.checkSelfPermission(BitmapActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(BitmapActivity.this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                } else {
                    BitmapUtils.saveBitmap(bitmap, Environment.getExternalStorageDirectory().getPath()+"/hh.jpeg");
                }



            }
        });

        task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BitmapActivity.this,TaskActivity.class));
            }
        });
    }
}
