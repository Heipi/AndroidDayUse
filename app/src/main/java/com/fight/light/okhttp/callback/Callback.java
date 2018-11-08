package com.fight.light.okhttp.callback;

import com.google.gson.internal.$Gson$Types;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

public abstract class Callback<T> {
    //这是请求数据的返回类型，包含常见的（Bean，List等）
   // Type mType;

    public Callback() {
     //   mType = getSuperclassTypeParameter(getClass());
    }

    /**
     * 通过反射想要的返回类型
     *
     * @param subclass
     * @return
     */
    static Type getSuperclassTypeParameter(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
    }

    /**
     * 在请求之前的方法，一般用于加载框展示
     *
     * @param request
     */
    public void onBefore(Request request) {
    }

    /***
     *
     * 在请求之后的方法，一般用于加载框隐藏
     *
     **/
    public void onAfter() {
    }
    /**
     * UI Thread
     *
     * @param progress
     */
    public void inProgress(float progress, long total)
    {

    }
    /**
     * 请求失败的时候
     *
     * @param e
     */
    public abstract void onFail(Call call, Exception e);
    /**
     * @param response
     */
    public abstract void onResponse(T response);

    /**
     * Thread Pool Thread
     *
     * @param response
     */
    public abstract T parseNetworkResponse(Response response) throws Exception;

    /*protected abstract void OnRequestBefore(Request request);

    protected abstract void OnResponseAfter(Request request);

    protected abstract void onError(Call call, int statusCode, Exception e);

    protected abstract void inProgress(int progress, long total, int id);*/

}
