package com.fight.light.base;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.fight.light.R;
import com.fight.light.util.DialogSimpleUtils;
import com.fight.light.util.ToastUtils;

public class BaseActivity extends AppCompatActivity {

    protected String TAG = getClass().getSimpleName();
    private Dialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        init();
    }


    public void showToast(String msg) {
        ToastUtils.showShortToast(msg);
    }
    private void init(){
        progressDialog = DialogSimpleUtils.createProgressDialog(this);
    }
    public void showProgressDialog() {
        progressDialog.show();
    }

    public void dismissProgressDialog() {

        if (progressDialog!= null){progressDialog.dismiss();}
    }
}
