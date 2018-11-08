package com.fight.light.net;

import com.fight.light.entity.GankBean;
import com.fight.light.entity.User2;

import java.util.List;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by yawei.kang on 2018/2/23.
 */

public interface RequestService {
   //http://gank.io/api/data/Android/10/1
    /*@GET("api/data/Android/10/1")
    Call<ResponseBody> getAndroidInfo();*/
    @GET("api/data/Android/10/1")
    Call<GankBean> getAndroidInfo();
    @GET("api/data/Android/10/{page}")
    Call<GankBean> getAndroidInfo(@Path("page") int page);
    @GET("api/data/query?cityname=深圳")
    Call<ResponseBody> getWeather(@Query("key") String  key,@Query("area") String area);
    @GET("group/{id}/users")
    Call<List<User2>> groupList(@Field("id") int groupId, @QueryMap Map<String,String> options);
    @GET
    Call<GankBean> getAndroid2Info(@Url String url);

}
