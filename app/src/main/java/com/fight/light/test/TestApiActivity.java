package com.fight.light.test;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.fight.light.R;
import com.fight.light.base.BaseActivity;
import com.fight.light.entity.GankBean;
import com.fight.light.entity.Result;
import com.fight.light.entity.User2;
import com.fight.light.http.ApiClient;
import com.fight.light.http.HttpCallBack;
import com.fight.light.net.ApiService;
import com.fight.light.net.PostApi;
import com.fight.light.util.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TestApiActivity extends BaseActivity {

    @BindView(R.id.btn_request)
    Button  btn_request;
    @BindView(R.id.tv_result)
    TextView tv_result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_api);
        ButterKnife.bind(this);
      /*  Retrofit retrofit = new Retrofit.Builder().baseUrl("http://gank.io/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create()).build();
        RequestService service = retrofit.create(RequestService.class);*/
        /*Call<ResponseBody> response = service.getAndroidInfo();*/

      /*  Call<GankBean> response = ApiClient.getInstance().getApiService().getAndroidInfo();*/
        Call<GankBean> response = ApiClient.getInstance().getHttpService(ApiService.class).getAndroidInfo();
       /* Call<GankBean> response =  ApiClient.getInstance().getAPService(ApiService.class).getAndroidInfo();*/
       ;
//        Call<GankBean> response = service.getAndroid2Info("http://gank.io/api/data/Android/10/1");

        response.enqueue(new Callback<GankBean>() {
            @Override
            public void onResponse(Call<GankBean> call, Response<GankBean> response) {
                try {
//                    String result = response.body().string();
                   GankBean bean = response.body();
                   String result =  bean.getResults().get(0).get_id() + bean.getResults().get(0).getType();

                    LogUtils.i(result);
                    tv_result.setText(result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<GankBean> call, Throwable t) {

            }
        });

    }
    public void testPost(){
        User2 user2 = new User2();
        user2.setId(1);
        user2.setName("name");
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://gank.io/").addConverterFactory(GsonConverterFactory.create()).build();
        PostApi service = retrofit.create(PostApi.class);
        service.postUser(user2).enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
               response.body().getYes();
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

            }
        });
    }


}
