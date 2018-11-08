package com.fight.light.one;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fight.light.R;
import com.fight.light.base.BaseFragment;
import com.fight.light.entity.DesignRes;
import com.fight.light.util.ToastUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yawei.kang on 2018/1/29.
 */

public class OneFragment extends BaseFragment implements OneContract.View{



    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private View view;
    private OneContract.Presenter presenter;
    private int curpage = 1;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
           view =  LayoutInflater.from(getActivity()).inflate(R.layout.one_frag_layout,container,false);
           ButterKnife.bind(this,view);
          initView();
           refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
               @Override
               public void onRefresh() {
                   presenter.pullToLoadList();
               }
           });

          return view;
    }

    private void initView(){

        presenter = new OnePresenter(this);


    }
     private void initData(){
        presenter.loadList(1);
     }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void loadListSuccess(int page, List<DesignRes> datas) {

    }

    @Override
    public void setPresenter(OneContract.Presenter presenter) {
      this.presenter = presenter;
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void showProgress() {
           refreshLayout.post(new Runnable() {
               @Override
               public void run() {
               refreshLayout.setRefreshing(true);
               }
           });
    }

    @Override
    public void dismisProgress() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void showTip(String message) {

    }
}
