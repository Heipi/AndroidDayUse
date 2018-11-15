package com.fight.light.resload;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class LoadResource {

     private  AssetManager mAsserManager;
     private Resources mResources;
     public void loadResource(Context context,String resPath){
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPath = assetManager.getClass().getMethod("addAssetPath",String.class);
            addAssetPath.invoke(assetManager,resPath);
            mAsserManager = assetManager;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Resources  resources = context.getResources();
         mResources = new Resources(mAsserManager,resources.getDisplayMetrics(),resources.getConfiguration());
         Resources.Theme theme =  mResources.newTheme();
         theme.setTo(context.getTheme());
    }


}
