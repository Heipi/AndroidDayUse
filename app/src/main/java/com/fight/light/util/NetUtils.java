package com.fight.light.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;

/**
 * Created by yawei.kang on 2017/12/5.
 */

public class NetUtils {

    private NetUtils(){
     /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
  }
    public enum NetType {
        None(1),
        Mobile(2),
        Wifi(4),
        Other(8);
        public int value;

        NetType(int value) {
            this.value = value;
        }

    }
    /**
     * 获取ConnectivityManager
     */
    public static ConnectivityManager getConnectivityManager(Context context) {
        return (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    /**
     * Net Connect Is Or No
     * 判断网络是否连接
     *
     * @return
     */
    public static boolean isNetConnected(Context context) {

        ConnectivityManager connectivity = getConnectivityManager(context);

        if (null != connectivity) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (null != info && info.isConnected()) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * 判断有无网络正在连接中（查找网络、校验、获取IP等）。
     *
     * @return boolean 不管wifi，还是mobile net，只有当前在连接状态（可有效传输数据）才返回true,反之false。
     */
    public static boolean isConnectedOrConnecting(Context context) {
        NetworkInfo[] nets = getConnectivityManager(context).getAllNetworkInfo();
        if (nets != null) {
            for (NetworkInfo net : nets) {
                if (net.isConnectedOrConnecting()) { return true; }
            }
        }
        return false;
    }
    /**
     * Wifi Connect Is Or No
     * 判断是否是wifi连接
     */
    public static boolean isWifiConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm == null)
            return false;
        return cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;

    }
    public static NetType getConnectedType(Context context) {
        NetworkInfo net = getConnectivityManager(context).getActiveNetworkInfo();
        if (net != null) {
            switch (net.getType()) {
                case ConnectivityManager.TYPE_WIFI:
                    return NetType.Wifi;
                case ConnectivityManager.TYPE_MOBILE:
                    return NetType.Mobile;
                default:
                    return NetType.Other;
            }
        }
        return NetType.None;
    }
    /**
     * 打开网络设置界面
     */
    public static void openSetting(Activity activity) {

        Intent intent;
        if (Build.VERSION.SDK_INT > 10){
            intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
        }else {
            intent = new Intent();
            ComponentName cm = new ComponentName("com.android.settings",
                    "com.android.settings.WirelessSettings");
            intent.setComponent(cm);
            intent.setAction("android.intent.action.VIEW");
        }
        activity.startActivityForResult(intent, 0);
    }

}
