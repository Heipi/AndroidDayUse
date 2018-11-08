package com.fight.light.user;

import com.fight.light.entity.User;

import java.util.List;

/**
 * Created by yawei.kang on 2018/5/10.
 */

public interface IUserModel {
    void loadGirl(OnUserLoadListener userLoadListener);

    interface OnUserLoadListener{
        void onComplete(List<User> users);
    }


}
