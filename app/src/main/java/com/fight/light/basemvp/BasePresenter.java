package com.fight.light.basemvp;

import java.lang.ref.WeakReference;

/**
 * Created by yawei.kang on 2018/5/10.
 */
//T 引用的View
public class BasePresenter<V> {
    //弱引用
    /**
     * The weak reference of V in MVP.
     */
  protected  WeakReference<V> mViewRef;

    //进行绑定
    /**
     * Attach reference.
     *
     * @param view the ref
     */
    public void attachView(V view){
        mViewRef = new WeakReference<>(view);
    }
    //解绑
    /**
     * Detach reference.
     */
    public void detachView(){
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }
    /**
     * Gets outer reference.
     *
     * @return the outer reference
     */
    protected V getOuterReference() {

        return mViewRef.get();
    }

    /**
     * Is out reference active boolean.
     *
     * @return the boolean
     */
    public boolean isOutReferenceActive() {
        return mViewRef != null && mViewRef.get() != null;
    }

}
