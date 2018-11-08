package com.fight.light.http;

import android.text.TextUtils;

import com.fight.light.util.LogUtils;
import com.fight.light.util.NetUtils;
import com.fight.light.util.SPUtils;
import com.fight.light.util.Utils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yawei.kang on 2018/2/28.
 */

public class RetrofitClient {
    private static  Retrofit retrofit;
    public static  Retrofit getRetrofit() {
        if (retrofit== null){
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override public void log(String message) {
                    try {
                        String text = URLDecoder.decode(message, "utf-8");
                        LogUtils.e(text);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                        LogUtils.e(message);
                    }
                }
            });
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            File cacheFile = new File(Utils.getContext().getCacheDir(),"cacheFile");
            Cache cache = new Cache(cacheFile,1024 * 1024 * 20);//50M
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .readTimeout(HttpConfig.DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                    .connectTimeout(HttpConfig.DEFAULT_TIMEOUT,TimeUnit.MILLISECONDS)
                    .addInterceptor(httpLoggingInterceptor)
                    .addInterceptor(new HttpHeaderInterceptor())
                    .addNetworkInterceptor(new HttpNetWorkInterceptor())
                    .cache(cache)
                    .build();
            retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(HttpConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return  retrofit;
    }

    /**
     * Interceptors can add, remove, or replace request headers.
     * Symmetrically, interceptors can rewrite response headers and transform the response body.
     * This is generally more dangerous than rewriting request headers because it may violate the webserver's expectations!
     *
     * https://github.com/square/okhttp/wiki/Interceptors
     */
    static class HttpNetWorkInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {

            Request originalRequest = chain.request();


            if (!NetUtils.isNetConnected(Utils.getContext())){
                originalRequest = originalRequest.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
                LogUtils.e("no network");
            }
            Response originalResponse = chain.proceed(originalRequest);

            if (NetUtils.isNetConnected(Utils.getContext())){
                LogUtils.e("network cache");
                //有网络时 设置缓存超时时间为0;
                return  originalResponse.newBuilder()
                        .removeHeader("Pragma") //清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                        .header("Cache-Control","public,max-age="+0)
                        .build();
            }else{
                LogUtils.e("no network cache");
                //设置无网络的缓存时间
                int maxStale = 60 * 60 * 24; // 无网络时，设置超时为1天
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public,only-if-cached,max-stale="+maxStale)
                        .build();
            }

        }
    }
    private static class HttpHeaderInterceptor implements Interceptor{
        @Override
        public Response intercept(Chain chain) throws IOException {
            //  将token统一放到请求头
            String token= SPUtils.getInstance().getString("token");
            //  也可以统一配置用户名
//            String user_id="123456";
            Request originalRequest = chain.request();
            Response originalResponse = chain.proceed(originalRequest);
            if (TextUtils.isEmpty(token)){
                return originalResponse;
            }
           /* originalRequest = originalRequest.newBuilder()
                    .header("token", token)
                    .header("user_id", user_id)
                    .build();
            return chain.proceed(originalRequest);*/
            return originalResponse.newBuilder()
                    .header("token", token)
//                    .header("user_id", user_id)
                    .build();
        }
    }


}
