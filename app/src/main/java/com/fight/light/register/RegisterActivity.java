package com.fight.light.register;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.fight.light.R;
import com.fight.light.base.BaseActivity;
import com.fight.light.entity.User;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends BaseActivity implements RegisterContract.View{

    @BindView(R.id.et_username)
    EditText et_username;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.btn_next)
    Button btn_next;
    private RegisterContract.Presenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        initView();
    }
    private void initView(){
        presenter = new RegisterPresenter(this);
           btn_next.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                  next();
               }
           });

    }
      private void next(){
        String phone = et_username.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        presenter.requestSms(phone,password);

      }



    @Override
    public void setPresenter(RegisterContract.Presenter presenter) {
         this.presenter = presenter;
    }



    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void showProgress() {
     showProgressDialog();
    }

    @Override
    public void dismisProgress() {
     dismissProgressDialog();
    }

    @Override
    public void showTip(String message) {
          showToast(message);
    }

    @Override
    public void requestSmsSuccess(String phone, String password) {
        // 短信验证码发送成功后,跳转到短信验证页,同时传递所需数据
       /* Intent intent = new Intent(this, RegisterStep2Activity.class);
        intent.putExtra("phone", phone);
        intent.putExtra("password", password);
        startActivity(intent);*/
    }

    @Override
    public void registerSuccess(User user) {
       //do nothing
    }
}
