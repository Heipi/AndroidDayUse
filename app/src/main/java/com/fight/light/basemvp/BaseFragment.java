package com.fight.light.basemvp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fight.light.util.LogUtils;

import butterknife.ButterKnife;

/**
 * Created by yawei.kang on 2018/5/11.
 */

public abstract class BaseFragment<V,T extends BasePresenter<V>> extends BaseLazyFragment implements BaseView{

    protected Context mContext;
    /**
     * The presenter P in MVP
     */

    protected T presenter;
    // if keep view
    private boolean isKeepRootView = true;
    // root view of Fragment
    private View mRootView;

    /**
     * 返回一个用于显示界面的布局id
     * fragment layout.
     * @return the layout
     */
    protected abstract int getContentView();

    /**
     * Init views.
     *
     * @param view the view
     */
    protected abstract void initViews(View view, Bundle savedInstanceState);

    /**
     * Init data.
     */
    protected abstract void initData();
    /**
     * Create presenter t that extends {@link BasePresenter}
     *
     * @return the t
     */
    protected abstract T createPresenter();
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        LogUtils.d("onAttach");

        if (mContext!= null){
            this.mContext = context;
        }else {
            this.mContext = getActivity();
        }

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        presenter = createPresenter();
        presenter.attachView((V) this);
        super.onCreate(savedInstanceState);
        LogUtils.d("onCreate");
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView!= null && isKeepRootView){
            return  mRootView;
        }
        if (mRootView!= null){
            mRootView = inflater.inflate(getContentView(), container , false);
            // init view
            initViews(mRootView,savedInstanceState);
            ButterKnife.bind(this, mRootView);
        }

        return mRootView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // init data
        initData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * if you keep, root view will not recreate if root view is not null.
     * viewpager will keep current and before, after frament.
     * if you keep is true, it will not recreate event frament detathed from activiey, because fragment object is still exit
     *
     * @param keep keep or not
     */
    protected void setKeepRootView(boolean keep) {
        isKeepRootView = keep;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showToast(String msg) {

    }

    @Override
    public void showError() {

    }
}
