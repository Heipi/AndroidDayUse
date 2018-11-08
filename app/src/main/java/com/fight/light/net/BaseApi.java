package com.fight.light.net;

import android.media.Image;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by yawei.kang on 2018/2/26.
 *
 * url  (完整的路径) http://host:port/aa/apath ，baseUrl Invalid  http://host:port/a/b   ； Url，  http://host:port/aa/apath
 * url  (绝对的路径)/apath ,  baseUrl  http://host:port/a/b   Url，  http://host:port/apath
 * url  （相对的路径）apath ，baseUrl http://host:port/a/b   Url，http://host:port/a/b/apath
 *
 * #@GET注解的作用:采用Get方法发送网络请求
 * getData(...) = 接收网络请求数据的方法
 *其中返回类型为Call<Bean>，Bean是接收数据的类
 *如果想直接获得ResponseBody中的内容，可以定义网络请求返回值为Call<ResponseBody>
 */

public interface BaseApi {
    /** url
     * api/data/query?cityname=深圳&param = param
     *
     * 当有URL注解时，@GET传入的URL就可以省略
     * 当GET、POST...HTTP等方法中没有设置Url时，则必须使用 {@link Url}提供
     */
    @GET("url")
    Call<ResponseBody> getDataByQuery(@Query("param") String param);
    @GET
    Call<ResponseBody> getDataByUrl(@Url String url,@Query("param") String param);
    @GET("url")
    Call<ResponseBody> getDataByMap(@Url String url,@QueryMap Map<String,String> param);
    /**
     *  api/data/Android/10/{page}
     *
     * 访问的API是：https://api.github.com/users/{user}/repos
     * 在发起请求时， {user} 会被替换为方法的第一个参数 user（被@Path注解作用）
     * @param param
     * @return
     */
    @GET("url/{param}")
    Call<ResponseBody> getDataByPath(@Path("param") String param);

    /**
     * @Header作用于方法的参数,用于添加不固定的请求头
     * @return
     */
    //@Header("Authorization: authorization")
    @GET("url")
    Call<ResponseBody> getByHeader(@Header("Authorization") String authorization);

    /**
     * @Headers 用于修饰方法,用于设置多个Header值,用于添加固定的请求头
     * @return
     *
     * @Headers("Authorization: authorization")
     */
    @Headers({
            "Accept: application/vnd.github.v3.full+json",
            "User-Agent: Retrofit-Sample-App"
    })
    @GET("url")
    Call<ResponseBody> getByHeaders();


    /**
     * 使用POST方式将json字符串作为请求体发送到服务器，将实例对象根据转换方式转换为对应的json字符串参数，
     * 这个转化方式是GsonConverterFactory定义的。
     *  object 可以是 实例对象
     * @param object
     * @return
     */
    @POST("url")
    Call<ResponseBody> postByBody(@Body Object object);

    /**
     * 请求体是form表单
     * @param param
     * @param param2
     * @return
     */
    @FormUrlEncoded
    @POST("url")
    Call<ResponseBody>  postByForm(@Field("param") String param,@Field("param2") String param2);
    @FormUrlEncoded
    @POST("url")
    Call<ResponseBody>  postByForm(@FieldMap Map<String,String> param);
    /**
     *  支持有文件上传的表单
     *  {@link Part} 后面支持三种类型，{@link RequestBody}、{@link okhttp3.MultipartBody.Part} 、任意类型
     * 除 {@link okhttp3.MultipartBody.Part} 以外，其它类型都必须带上表单字段（@Part("param")） ({@link okhttp3.MultipartBody.Part} 中已经包含了表单字段的信息 )
     * example:
     *   File file = new File(Environment.getExternalStorageDirectory(), "icon.png");
     *  RequestBody photoRequestBody = RequestBody.create(MediaType.parse("image/png"), file);
     * MultipartBody.Part photo = MultipartBody.Part.createFormData("photos", "icon.png", photoRequestBody);
     * @return
     */
    @Multipart
    @POST("url")
    Call<ResponseBody> postByMultipart(@Part("param")RequestBody param, @Part("param2") RequestBody param2, @Part MultipartBody.Part file);
    @Multipart
    @POST("url")
    Call<ResponseBody> postByMultipart(@Part("param")RequestBody param, @Part("photo") Image photo, @Part MultipartBody.Part file);

    /**
     * 多文件上传
     * example
     * File file = new File(Environment.getExternalStorageDirectory(), "local.png");
     * RequestBody photo = RequestBody.create(MediaType.parse("image/png", file);
     * Map<String, RequestBody> map = new HashMap<>(String, RequestBody);
     * map.put("photos\"; filename=\"icon.png", photo);
     * map.put("username",  RequestBody.create(null, "abc"));
     * @param params
     * @param param
     * @return
     */
    @Multipart
    @POST("url")
    Call<ResponseBody> postByMultipartMore(@PartMap Map<String, RequestBody> params, @Part("param") RequestBody param);


    @HTTP(method = "GET", path = "url/{id}", hasBody = false)
    Call<ResponseBody> getDataByHttp(@Path("id") int id);
}
