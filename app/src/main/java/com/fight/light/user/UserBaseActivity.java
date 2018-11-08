package com.fight.light.user;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.fight.light.R;
import com.fight.light.basemvp.BaseView;

/**
 * Created by yawei.kang on 2018/5/11.
 */

public  abstract class UserBaseActivity extends AppCompatActivity implements BaseView {

    //表示层的引用

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base2);

    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showToast(String msg) {

    }

    @Override
    public void showError() {

    }
}