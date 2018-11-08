package com.fight.light.net;

import com.fight.light.R;
import com.fight.light.entity.Result;
import com.fight.light.entity.User;
import com.fight.light.entity.User2;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by yawei.kang on 2018/2/24.
 */

public interface PostApi {
    @POST("user/new")
    Call<Result> postUser(@Body User2 user);
   @POST("user/edit")
    Call<Result> editUser(@Field("id")int id,@Field("name") String name);

   @FormUrlEncoded
    @POST("user/edit")
    Call<User2> updateUser(@Field("first_name")String first,@Field("last_name") String last);
   @Multipart
    @PUT("user/photo")
    Call<User2> updateUser(@Part("photo")RequestBody photo,@Part("description") RequestBody description);
   @Headers("Cache-Control:max-age=640000")
    @GET("widget/list")
    Call<List<String>> widgetList();
    @POST("user/edit")
    Call<User2> updateUser2(@Path("first_name") String first, @Field("last_name") String last);
}
