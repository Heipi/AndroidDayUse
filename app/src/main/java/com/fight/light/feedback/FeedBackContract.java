package com.fight.light.feedback;

import com.fight.light.base.BasePresenter;
import com.fight.light.base.BaseView;

/**
 * Created by yawei.kang on 2018/2/5.
 */

public interface FeedBackContract {

    interface View extends BaseView<Presenter>{
         void addFeedBackSuccess();
    }

    interface Presenter extends BasePresenter{
        void addFeedBack(String content,String email);
    }

}
