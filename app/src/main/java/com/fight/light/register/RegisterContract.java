package com.fight.light.register;

import com.fight.light.base.BasePresenter;
import com.fight.light.base.BaseView;
import com.fight.light.entity.User;

/**
 * Created by yawei.kang on 2018/2/22.
 */

public interface RegisterContract {
    interface View extends BaseView<Presenter>{
        void requestSmsSuccess(String phone,String password);
        void registerSuccess(User user);
    }
   interface Presenter extends BasePresenter{
        void requestSms(String phone,String password);
        void register(String phone,String password,String code);
   }
}
