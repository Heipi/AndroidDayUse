package com.fight.light.one;

import com.fight.light.base.BasePresenter;
import com.fight.light.base.BaseView;
import com.fight.light.entity.DesignRes;

import java.util.List;

/**
 * Created by yawei.kang on 2018/1/29.
 */

public interface OneContract {

    interface  View extends BaseView<Presenter>{
     void loadListSuccess(int page, List<DesignRes> datas);
    }

    interface Presenter extends BasePresenter{
        void loadList(int page);
        void pullToLoadList();
    }

}
