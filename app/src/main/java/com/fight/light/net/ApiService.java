package com.fight.light.net;

import com.fight.light.entity.GankBean;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by yawei.kang on 2018/2/26.
 */

public interface ApiService {









    @GET("api/data/Android/10/1")
    Call<GankBean> getAndroidInfo();
   @GET("url")
   Observable<ResponseBody> getData(@Query("ip") String ip);
   @POST("{url}")
    Observable<ResponseBody> executePost(@Path("url") String url, @FieldMap Map<String,String> maps);
   @Multipart
    @POST("{url}")
    Observable<ResponseBody> uploadFile(@Path("url") String url, @Part("image\";filename=\"image.jpg")RequestBody avatar);
   @POST("{url}")
    Call<ResponseBody> uploadFiles(@Url String url, @Part("filename") String description, @PartMap Map<String,RequestBody> maps);

}
