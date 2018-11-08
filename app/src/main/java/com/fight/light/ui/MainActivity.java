package com.fight.light.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
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

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.SaveCallback;
import com.fight.light.R;
import com.fight.light.base.BaseActivity;
import com.fight.light.test.TestApiActivity;
import com.fight.light.util.BitmapUtils;
import com.fight.light.util.ClickUtils;
import com.fight.light.util.NetUtils;
import com.fight.light.util.ScreenUtil;

import java.security.Permission;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.hello_world)
    TextView hello_world;
    @BindView(R.id.task)
    Button task;
    @BindView(R.id.test)
    Button test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            startActivity(new Intent(MainActivity.this, TestApiActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        ClickUtils.delayClickDoubleExit(this);
    }
}
