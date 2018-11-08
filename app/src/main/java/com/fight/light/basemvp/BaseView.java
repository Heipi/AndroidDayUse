package com.fight.light.basemvp;

/**
 * Created by yawei.kang on 2018/5/10.
 */

public interface BaseView {
    /**
     * 显示正在加载view
     */
   void showLoading();
    /**
     * 关闭正在加载view
     */
    void hideLoading();
    /**
     * 显示提示
     * @param msg
     */
    void showToast(String msg);
    /**
     * 显示请求错误提示
     */
    void showError();




}
