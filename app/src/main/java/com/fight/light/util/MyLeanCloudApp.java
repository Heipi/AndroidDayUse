package com.fight.light.util;

import android.app.Application;
import android.net.Uri;
import android.support.multidex.MultiDexApplication;

import com.avos.avoscloud.AVOSCloud;

/**
 * Created by yawei.kang on 2017/12/5.
 */

public class MyLeanCloudApp extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(this,"IXiRCK1WVTDRqIKfFqAfMj76-gzGzoHsz","gSFWJnDrE3DYS2XBgzaNiIaq");
        AVOSCloud.setDebugLogEnabled(true);
        //Utils 初始化
        Utils.init(this);
    }
}
