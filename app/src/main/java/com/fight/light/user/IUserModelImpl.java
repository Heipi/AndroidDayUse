package com.fight.light.user;

import com.fight.light.entity.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yawei.kang on 2018/5/10.
 */

public class IUserModelImpl implements IUserModel{

    @Override
    public void loadGirl(OnUserLoadListener userLoadListener) {
        //load data

        //传递data
        userLoadListener.onComplete(new ArrayList<User>());
    }
}
