package com.fight.light.register;

import android.text.TextUtils;

import com.fight.light.entity.User;

/**
 * Created by yawei.kang on 2018/2/22.
 */

public class RegisterPresenter implements RegisterContract.Presenter{

    private RegisterContract.View view;
    public  RegisterPresenter(RegisterContract.View view){
        this.view = view;
        this.view.setPresenter(this);
    }


    @Override
    public void requestSms(String phone, String password) {
        if (TextUtils.isEmpty(phone)){
            view.showTip("请输入用户名");
            return;
        }
       if (TextUtils.isEmpty(password) || password.length() < 6){
           view.showTip("请设置登录密码，不少于6位");
           return;
       }
        view.showProgress();


    }

    @Override
    public void register(String phone, String password, String code) {
        if (TextUtils.isEmpty(code)) {
            view.showTip("请输入验证码");
            return;
        }
         view.showProgress();
        // 注册接口
        User user = new User();
        user.setMobilePhoneNumber(phone);
        user.setUsername(phone);
        user.setPassword(password);
        user.setSmsCode(code);

    }
}
