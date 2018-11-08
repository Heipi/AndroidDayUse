package com.fight.light.base;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.fight.light.util.ToastUtils;

/**
 * Created by yawei.kang on 2018/1/29.
 */

public class BaseFragment extends Fragment {
    public String TAG;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = getClass().getSimpleName();

    }
    public void showToast(String msg) {
        ToastUtils.showShortToast(msg);
    }
}
