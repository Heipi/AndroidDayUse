package com.fight.light.okhttp;

import android.os.Handler;
import android.os.Looper;
import com.fight.light.okhttp.callback.Callback;
import com.fight.light.util.LogUtils;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class RequestClient {
    private static final int COONECT_TIME_OUT = 10;//设置超时时间
    private static final int READ_TIME_OUT = 10;//设置读取超时时间
    private static final int WRITE_TIME_OUT = 10;

    public static final int TYPE_GET = 0;//get请求
    public static final int TYPE_POST_JSON = 1;//post请求参数为json
    public static final int TYPE_POST_FORM = 2;//post请求参数为表单
    public static final int TYPE_POST_MULTI = 3;// post 参数含有文件
    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");//mdiatype 这个需要和服务端保持一致
    private static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");//mdiatype 这个需要和服务端保持一致
    private static final String TAG = "RequestClient";
    private static final String SESSION_KEY = "Set-Cookie";
    private static final String mSessionKey = "JSESSIONID";
    private static volatile RequestClient mInstance;//单利引用
    private static OkHttpClient mOkHttpClient;//okHttpClient 实例
    private Handler okHttpHandler;//全局处理子线程和M主线程通信
    private Map<String, String> mSessions = new HashMap<String, String>();

    /**
     * 初始化RequestClient
     */
    public RequestClient() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                try {
                    String text = URLDecoder.decode(message, "utf-8");
                    LogUtils.e(text);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    LogUtils.e(message);
                }
            }
        });
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);

        SSLUtils.SSLParams sslParams = SSLUtils.createSSLSocketFactory(null, null, null);
        //初始化OkHttpClient
        mOkHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(COONECT_TIME_OUT, TimeUnit.SECONDS)//设置超时时间
                .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS)//设置写入超时时间
                .addInterceptor(httpLoggingInterceptor)
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .hostnameVerifier(new SSLUtils.TrustAllHostnameVerifier())
                .build();
        //初始化Handler
        okHttpHandler = new Handler(Looper.getMainLooper());
    }

    public static RequestClient getInstance() {
        if (mInstance == null) {
            synchronized (RequestClient.class) {
                if (mInstance == null) {
                    mInstance = new RequestClient();
                }
            }
        }
        return mInstance;
    }



    /**
     * okHttp异步请求统一入口
     *
     * @param actionUrl   接口地址
     * @param requestType 请求类型
     * @param paramsMap   请求参数
     * @param callBack    请求返回数据回调
     * @param <T>         数据泛型
     **/
    public <T> void requestAsyn(String actionUrl, int requestType, RequestParams paramsMap, Callback callBack) {
        switch (requestType) {
            case TYPE_GET:
                getByAsyn(actionUrl, paramsMap, callBack);
                break;
            case TYPE_POST_JSON:
                postByAsyn(actionUrl, paramsMap, callBack);
                break;
            case TYPE_POST_FORM:
                postByAsynWithForm(actionUrl, paramsMap, callBack);
                break;
            case TYPE_POST_MULTI:
                postMultiAsyn(actionUrl, paramsMap, callBack);
                break;
        }
    }

    /**
     * 统一处理失败信息
     *
     * @param callBack
     * @param <T>
     */
    private <T> void failedCallBack(final Call call, final Exception e, final Callback callBack) {
        if (callBack == null) return;
        okHttpHandler.post(new Runnable() {
            @Override
            public void run() {
                callBack.onFail(call, e);
                callBack.onAfter();
            }
        });
    }

    /**
     * 统一同意处理成功信息
     *
     * @param result
     * @param callBack
     * @param <T>
     */
    private <T> void successCallBack(final T result, final Callback callBack) {
        if (callBack == null)
            return;
        okHttpHandler.post(new Runnable() {
            @Override
            public void run() {
                callBack.onResponse(result);
                callBack.onAfter();
            }
        });
    }



    /**
     * okHttp get异步请求
     *
     * @param actionUrl 接口地址
     * @param callBack  请求返回数据回调
     * @param <T>       数据泛型
     * @return
     */
    public <T> void getByAsyn(String actionUrl, RequestParams params, final Callback callBack) {
        final Request request = CommonRequest.createGetRequest(actionUrl, params);
        final Call call = mOkHttpClient.newCall(request);
        call.enqueue(new OkHttpCallBack(callBack));
    }

    /**
     * okHttp post异步请求
     *
     * @param actionUrl 接口地址
     * @param callBack  请求返回数据回调
     * @param <T>       数据泛型
     * @return
     */
    public <T> void postByAsyn(String actionUrl, RequestParams params, final Callback callBack) {
        Request request;
        try {
            request = CommonRequest.createPostTypeRequest(actionUrl, MEDIA_TYPE_JSON, params);
            final Call call = mOkHttpClient.newCall(request);
            call.enqueue(new OkHttpCallBack(callBack));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * okHttp post异步请求表单提交
     *
     * @param actionUrl 接口地址
     * @param paramsMap 请求参数
     * @param callBack  请求返回数据回调
     * @return
     */
    private void postByAsynWithForm(String actionUrl, RequestParams paramsMap, final Callback callBack) {
        try {
            final Request request = CommonRequest.createFormRequest(actionUrl, paramsMap);
            final Call call = mOkHttpClient.newCall(request);
            call.enqueue(new OkHttpCallBack(callBack));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 上传文件
     * okhttp中一个key 可以对应多张图片
     * @param actionUrl
     * @param callback
     */
    public void postMultiAsyn (String actionUrl, RequestParams params, Callback callback){
        Request request = CommonRequest.createMultiRequest(actionUrl, params);
        mOkHttpClient.newCall(request).enqueue(new OkHttpCallBack(callback));
    }

        /**
         * okHttp同步请求统一入口
         *
         * @param actionUrl   接口地址
         * @param requestType 请求类型
         */
        public void requestSyn(String actionUrl,int requestType, RequestParams params) throws
        IOException {
            switch (requestType) {
                case TYPE_GET:
                    requestGetBySyn(actionUrl, params).execute();
                    break;
                case TYPE_POST_JSON:
                    requestPostBySyn(actionUrl, params);
                    break;
                case TYPE_POST_FORM:
                    requestPostFormBySyn(actionUrl, params);
                    break;
            }
        }
        /**
         * okHttp post同步请求
         *
         * @param actionUrl 接口地址
         */
        private void requestPostFormBySyn (String actionUrl, RequestParams params){
            try {
                //创建一个请求
                final Request request = CommonRequest.createFormRequest(actionUrl, params);
                //创建一个Call
                final Call call = mOkHttpClient.newCall(request);
                //执行请求
                Response response = call.execute();
                //请求执行成功
                if (response.isSuccessful()) {
                    //获取返回数据 可以是String，bytes ,byteStream
                    Logger.infoe(TAG, "response ----->" + response.body().string());
                }
            } catch (Exception e) {
                Logger.infoe(TAG, e.toString());
            }
        }
        /**
         * okHttp post同步请求
         * @param actionUrl 接口地址
         */
        private void requestPostBySyn (String actionUrl, RequestParams params){
            try {
                //创建一个请求实体对象 RequestBody
                //   RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, params);
                Request request = CommonRequest.createPostTypeRequest(actionUrl, MEDIA_TYPE_JSON, params);
                //创建一个Call
                final Call call = mOkHttpClient.newCall(request);
                //执行请求
                Response response = call.execute();
                //请求执行成功
                if (response.isSuccessful()) {
                    //获取返回数据 可以是String，bytes ,byteStream
                    Logger.infoe(TAG, "response ----->" + response.body().string());
                }
            } catch (Exception e) {
                Logger.infoe(TAG, e.toString());
            }
        }


        /**
         * okHttp get同步请求
         *
         * @param actionUrl 接口地址
         */
        private Call requestGetBySyn (String actionUrl, RequestParams params) throws IOException {
            //创建一个请求
            Request request = CommonRequest.createGetRequest(actionUrl, params);
            //创建一个Call
            return mOkHttpClient.newCall(request);
        }

        /**
         * 同步的Get请求
         *
         * @param url
         * @return Response
         */
        private Response requestGetBySyn (String url) throws IOException {
            return requestGetBySyn(url, null).execute();
        }
        /**
         * 同步的Get请求
         * @param url
         * @return 字符串
         */
        private String requestStringGetBySyn (String url) throws IOException {
            return requestGetBySyn(url).body().string() == null ? "" : requestGetBySyn(url).body().string();
        }

        private String getFileName (String path){
            int separatorIndex = path.lastIndexOf("/");
            return (separatorIndex < 0) ? path : path.substring(separatorIndex + 1, path.length());
        }

        /**
         * 主动关闭之前请求的同一个接口,通过tag进行判断是否是同一个，而call中存储的时https或者http接口
         * @param tag
         */
        public void cancel (Object tag){
            if (mOkHttpClient == null)
                return;
            try {
                for (Call call : mOkHttpClient.dispatcher().queuedCalls()) {
                    if (call.request().tag() != null && call.request().tag().toString().contains(tag.toString())) {
                        call.cancel();
                    }
                }
                for (Call call : mOkHttpClient.dispatcher().runningCalls()) {
                    if (call.request().tag() != null && call.request().tag().toString().contains(tag.toString())) {
                        call.cancel();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void cancelTag (Object tag)
        {
            for (Call call : mOkHttpClient.dispatcher().queuedCalls()) {
                if (tag.equals(call.request().tag())) {
                    call.cancel();
                }
            }
            for (Call call : mOkHttpClient.dispatcher().runningCalls()) {
                if (tag.equals(call.request().tag())) {
                    call.cancel();
                }
            }
        }

        /** 取消所有请求请求 */
        public void cancelAll () {
            try {
                mOkHttpClient.dispatcher().cancelAll();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private class OkHttpCallBack implements okhttp3.Callback {
            private Callback callBack;

            public OkHttpCallBack(Callback callback) {
                this.callBack = callback;
            }

            @Override
            public void onFailure(Call call, IOException e) {
                failedCallBack(call, e, callBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    if (call.isCanceled()) {
                        failedCallBack(call, new IOException("Canceled!"), callBack);
                        return;
                    }
                    if (!response.isSuccessful()) {
                        failedCallBack(call, new IOException("reponse's code is : " + response.code()), callBack);
                    } else {
                        Object o = callBack.parseNetworkResponse(response);
                        successCallBack(o, callBack);
                    }
                } catch (Exception e) {
                    failedCallBack(call, e, callBack);
                    //  Logger.infoe(TAG, e.toString());
                } finally {
                    response.body().close();
                }
            }
        }


    }
