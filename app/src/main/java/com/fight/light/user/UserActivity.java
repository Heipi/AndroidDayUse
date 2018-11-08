package com.fight.light.user;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.fight.light.R;
import com.fight.light.basemvp.BaseActivity;
import com.fight.light.entity.User;
import com.fight.light.register.RegisterContract;

import java.util.List;

public class UserActivity extends BaseActivity<IUserView,UserPresenter<IUserView>> implements IUserView{

//    UserPresenter userPresenter;
    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
//               userPresenter.attachView(this);
        presenter.fetch();
    }

    @Override
    public int getContentView() {
        return 0;
    }

    @Override
    protected void initView() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

    @Override
    protected UserPresenter<IUserView> createPresenter() {
        return  new UserPresenter<>();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//      userPresenter.detachView();
    }

    @Override
    public void showLoading() {
        Toast.makeText(this,"load success",Toast.LENGTH_SHORT).show();
    }


    @Override
    public void showGirls(List<User> T) {
         //
    }
}
