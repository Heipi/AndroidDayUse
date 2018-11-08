package com.fight.light.one;

import com.fight.light.entity.DesignRes;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by yawei.kang on 2018/2/2.
 */

public class OnePresenter implements OneContract.Presenter{

    private OneContract.View view;
    public List<DesignRes> datas;

    public OnePresenter(OneContract.View view){
        this.view = view;
        this.view.setPresenter(this);
    }

    @Override
    public void loadList(int page) {
      if (page == 1){
          view.showProgress();
      }
      loadData(page);
    }

    @Override
    public void pullToLoadList() {
           loadData(1);
    }

    /**
     * 加载数据
     * @param page
     */
    private void loadData(int page){
//        Observable<List<DesignRes>> observable =


    }
}
