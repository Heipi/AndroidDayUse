package com.fight.light.okhttp;

import android.os.Build;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CommonRequest {

    /**
     * Get
     *
     * @param url
     * @param paramsMap
     * @return
     */
    public static Request createGetRequest(String url, RequestParams paramsMap) {

        StringBuilder paramsBuilder = new StringBuilder();
        if (paramsMap != null) {
            for (Map.Entry<String, String> entry : paramsMap.urlParams.entrySet()) {
                //将请求参数遍历添加到我们的请求构件类中
                paramsBuilder.append(entry.getKey()).append("=").
                        append(entry.getValue()).append("&");
            }
        }
        //补全请求地址
        String requestUrl = String.format("%s?%s", url, paramsBuilder.toString());
        return addHeaders().url(requestUrl).build();

    }

    /**
     * Post
     * 表单
     * @param url
     * @param params
     * @return
     */
    public static Request createFormRequest(String url, RequestParams params) {
            //创建一个FormBody.Builder
            FormBody.Builder builder = new FormBody.Builder();
            if (params != null) {
                for (Map.Entry<String, String> entry : params.urlParams.entrySet()) {
                    //将请求参数遍历添加到我们的请求构件类中
                    builder.add(entry.getKey(), entry.getValue());
                }
            }
            //生成表单实体对象
            RequestBody formBody = builder.build();
            //创建一个请求
            return addHeaders().url(url).post(formBody).build();
    }

    /**
     * Post
     *  MediaType JSON,xml
     * @param url
     * @param params
     * @return
     */
    public static Request createPostTypeRequest(String url,MediaType mediaType, RequestParams params) throws UnsupportedEncodingException {
        StringBuilder paramsBuilder = new StringBuilder();
        if (params != null) {
            for (Map.Entry<String, Object> entry : params.fileParams.entrySet()) {
                //将请求参数遍历添加到我们的请求构件类中
              //  String encoder =    URLEncoder.encode(entry.getValue().toString(),"utf-8");
                paramsBuilder.append(String.format("%s=%s", entry.getKey(), URLEncoder.encode(entry.getValue().toString(), "utf-8")));
            }
        }
        //生成实体对象
        RequestBody requestBody = RequestBody.create(mediaType,paramsBuilder.toString());
        //创建一个请求
        return addHeaders().url(url).post(requestBody).build();
    }

    /**
     * 参数含有文件类型
     * @param url
     * @param params
     * @return
     */
    public static Request createMultiRequest(String url, RequestParams params) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        for (String key: params.fileParams.keySet()){
            Object value = params.fileParams.get(key);
            if (value instanceof File){
                File file = (File) value;
                //MediaType.parse("application/octet-stream")以二进制的形式上传文件
                builder.addFormDataPart(key, file.getName(), RequestBody.create(MediaType.parse("application/octet-stream"), file));
            }else{
                builder.addFormDataPart(key, value.toString());
            }

        }
        //生成表单实体对象
        RequestBody formBody = builder.build();
     return addHeaders().url(url).post(formBody).build();
    }
    /**
     * 统一为请求添加头信息
     *
     * @return
     */
    private static Request.Builder addHeader() {
        Request.Builder builder = new Request.Builder();
        return builder;
    }

    /**
     * 统一为请求添加头信息
     *
     * @return
     */
    private static Request.Builder addHeaders() {
        Request.Builder builder = new Request.Builder()
                .addHeader("Connection", "keep-alive")
                .addHeader("platform", "2")
                .addHeader("phoneModel", Build.MODEL)
                .addHeader("systemVersion", Build.VERSION.RELEASE)
                .addHeader("appVersion", "1.0.0");
        return builder;
    }
}
