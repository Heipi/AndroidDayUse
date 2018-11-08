package com.fight.light.fragment;

import com.fight.light.base.BaseView;
import com.fight.light.base.BasePresenter;

import java.util.List;

/**
 * Created by yawei.kang on 2018/1/26.
 */

public interface TaskModel {

    interface View extends BaseView<Presenter> {
        void showList(List<String> list);
        void clearTaskItem(int position);
    }

     interface Presenter extends BasePresenter{
            void clearTask();
     }
}



















