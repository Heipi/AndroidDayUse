package com.fight.light.basemvp;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by yawei.kang on 2018/5/11.
 */

public abstract class BaseLazyFragment extends Fragment {


    // if view init done
    private boolean isViewCreated;
    // if data load complete
    private boolean isDataLoadCompleted;

    /**
     * Load data.
     */
    protected abstract void loadData();



    /**
     * Called when the fragment's activity has been created and this
     * fragment's view hierarchy instantiated.
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewCreated = true;
        lazyLoad();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    /**
     * 优先于oncreate方法执行，且每次切换fragment都会执行此方法！
     *
     * @param isVisibleToUser fragment visible or not
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
       lazyLoad();

    }

    /**
     * load data, only when fragment visible and view created and data never load completed
     * you must set true, when your data load complete, otherwise it will load every time
     *
     * fragment实现懒加载的关键,只有fragment 可见才会调用onLazyLoad() 加载数据
     */
    private void lazyLoad() {
        if (getUserVisibleHint() && isViewCreated && !isDataLoadCompleted) {
            loadData();
        }
    }
    /**
     * Set data load completed.
     * you must set true, when your data load complete, otherwise it will load every time
     *
     * @param completed the completed
     */
    protected void setDataLoadCompleted(boolean completed) {
        isDataLoadCompleted = completed;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isViewCreated = false;
    }
}
