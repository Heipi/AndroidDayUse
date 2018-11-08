package com.fight.light.http;

import android.content.Context;
import android.net.http.HttpResponseCache;

import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.HTTP;

import static com.fight.light.http.HttpConfig.HTTP_FAIL_CODE;
import static com.fight.light.http.HttpConfig.HTTP_SUCCESS_CODE;

/**
 * Created by yawei.kang on 2018/2/27.
 */

public abstract class HttpCallBack<T> implements Callback<HttpResponse<T>> {
    private final String TAG = HttpCallBack.class.getSimpleName();
    private Context mContext;
    private final int RESPONSE_CODE_OK = 0;//自定义的业务逻辑，成功返回积极数据
    private final int RESPONSE_CODE_FAILED = -1;
    //是否需要显示Http 请求的进度，默认的是需要，但是Service 和 预取数据不需要
    private boolean showProgress = true;
    public HttpCallBack() {
    }
    /**
     * @param mContext
     */
    public HttpCallBack(Context mContext) {
        this.mContext = mContext;
        if (showProgress) {
            //show your progress bar
//            HttpUiTips.showDialog(mContext,true, "loading...");
        }
    }

    /**
     * @param mContext
     * @param showProgress 默认需要显示进程，不要的话请传 false
     */
    public HttpCallBack(Context mContext, boolean showProgress) {
        this.showProgress = showProgress;
        this.mContext = mContext;
        if (showProgress) {
//            HttpUiTips.showDialog(mContext,true, null);
        }
    }

    @Override
    public void onResponse(Call<HttpResponse<T>> call, Response<HttpResponse<T>> response) {

        if (response.raw().code() == HttpConfig.HTTP_OK){
           if (response.body().getCode() == HTTP_SUCCESS_CODE) {
             onSuccess(response);
            }else if (response.body().getCode() == HTTP_FAIL_CODE){
             onFailure(response.body().getError());
           }
        }else {
            onFailure(call, new RuntimeException("response error,detail = " + response.raw().toString()));

        }



    }

    @Override
    public void onFailure(Call<HttpResponse<T>> call, Throwable t) {
        if(t instanceof SocketTimeoutException){
            //超时
        }else if(t instanceof ConnectException){
            //
        }else if(t instanceof RuntimeException){
            //
        }

    }

    public abstract void onSuccess(Response<HttpResponse<T>> response);

    public abstract void onFailure(String message);

    public abstract void onAutoLogin();
}
