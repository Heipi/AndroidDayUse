package com.fight.light.base;

/**
 * Created by yawei.kang on 2018/1/26.
 */

public interface BaseView<T> {
  void setPresenter(T presenter);
  boolean isActive();
  void showProgress();
  void dismisProgress();
  void showTip(String message);
}
