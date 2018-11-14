package com.fight.light.app;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.fight.light.unhandler.CrashHandler;

public class LightApplication  extends Application {
    private static LightApplication instance;
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //方法数越界
        MultiDex.install(this);
    }
    @Override
    public void onCreate() {
        super.onCreate();
          instance = this;
          //异常处理设置
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(this);

    }
    public static LightApplication getInstance(){
        return  instance;
    }

}
