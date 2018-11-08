package com.fight.light.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import static android.support.v4.util.Preconditions.checkNotNull;

/**
 * Created by yawei.kang on 2018/1/26.
 */

public class FragmentUtils {

    public static void addFragmentToActivity(FragmentManager fragmentManager, Fragment fragment,int frameid){
        if (fragmentManager!= null && fragment != null){
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(frameid,fragment);
            transaction.commit();
        }

    }

}
