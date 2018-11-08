package com.fight.light.user;

import com.fight.light.basemvp.BaseView;
import com.fight.light.entity.User;

import java.util.List;

/**
 * Created by yawei.kang on 2018/5/10.
 */

public interface IUserView extends BaseView{
    void showLoading();
    void showGirls(List<User> T);
}
