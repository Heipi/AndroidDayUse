package com.fight.light.okhttp.test;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.fight.light.R;
import com.fight.light.base.BaseActivity;
import com.fight.light.okhttp.RequestClient;
import com.fight.light.okhttp.RequestParams;
import com.fight.light.okhttp.callback.BitmapCallback;
import com.fight.light.okhttp.callback.StringCallback;
import okhttp3.Call;

import static com.fight.light.okhttp.RequestClient.TYPE_GET;
import static com.fight.light.okhttp.RequestClient.TYPE_POST_FORM;

public class TestokActivity extends BaseActivity implements View.OnClickListener {

    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView();
        setContentView(R.layout.activity_test3);
        Log.d("MAX memory",(int) (Runtime.getRuntime().maxMemory()/1024)+"");
        img = (ImageView) findViewById(R.id.img);
        findViewById(R.id.button).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);

       // Md5Util.hashKeyFormUrl("123");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:
                String url = "http://sjbz.fd.zol-img.com.cn/t_s480x320c/g5/M00/00/03/ChMkJlfJVXCIVYavAAUmPqKmhCEAAU93wIdDHwABSZW820.jpg";
                RequestClient.getInstance().requestAsyn(url,TYPE_GET ,null, new BitmapCallback() {
                    @Override
                    public void onFail(Call call, Exception e) {

                    }
                    @Override
                    public void onResponse(Bitmap response) {
                    img.setImageBitmap(response);
                    }
                });
                break;
            case R.id.button2:
                String url2 = "https://www.apps.tl/version.php";
                RequestParams params  = new RequestParams();
                params.put("name","");
                RequestClient.getInstance().requestAsyn(url2,TYPE_POST_FORM ,params, new StringCallback() {
                    @Override
                    public void onFail(Call call, Exception e) {

                    }
                    @Override
                    public void onResponse(String response) {
                        Log.d("str",response.toString());
                      //  img.setImageBitmap(response);
                    }
                });
                break;

            case R.id.button3:

                break;
        }
    }
}
