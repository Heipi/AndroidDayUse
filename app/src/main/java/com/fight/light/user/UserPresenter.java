package com.fight.light.user;

import android.util.Log;

import com.fight.light.basemvp.BasePresenter;
import com.fight.light.basemvp.BaseView;
import com.fight.light.entity.User;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by yawei.kang on 2018/5/10.
 */

public class UserPresenter <T extends IUserView> extends BasePresenter<T>{

   /* //弱引用
    protected WeakReference<T> mViewRef;*/

//    IUserView userView;
    IUserModel userModel = new IUserModelImpl();
    public UserPresenter(){//T view
//        mViewRef = new WeakReference<T>(view);
//        this.userView = view;
    }

  /*  //进行绑定
    public void attachView(T view){
        mViewRef = new WeakReference<>(view);
    }
   public void detachView(){
        mViewRef.clear();
   }*/


    public void fetch() {
        try {
            if (mViewRef.get() != null) {
//        if (userView!= null){
                getOuterReference().showLoading();
                if (userModel != null) {
                    userModel.loadGirl(new IUserModel.OnUserLoadListener() {
                        @Override
                        public void onComplete(List<User> users) {
                            if (mViewRef.get() != null) {
                                mViewRef.get().showGirls(users);
                            }
                        }
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
