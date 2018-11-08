package com.fight.light.util;

import android.app.Activity;
import android.content.Context;

import java.util.Stack;

/**
 * Created by yawei.kang on 2017/12/4.
 */

public class ActivityManageUtils {
    private static Stack<Activity> activityStack;
    private static ActivityManageUtils instance;

    private ActivityManageUtils() {

    }

    public static ActivityManageUtils getAppManager() {
        if (instance == null) {
            instance = new ActivityManageUtils();
        }
        return instance;
    }

    /**
     * 添加Activity 到栈
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
    }

    /**
     * 获取当前的Activity（堆栈中最后一个压入的)
     */
    public Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();

    }

    /**
     * 结束指定的Activity
     *
     * @param activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity();
            }
        }
    }

    /**
     * 结束所有的Activity
     */
    public void finishAllActivity() {
        int size = activityStack.size();
        for (int i = 0; i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    public void AppExit(Context context) {
        try {
            finishAllActivity();
            android.app.ActivityManager activityManager = (android.app.ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
//            activityManager.restartPackage(context.getPackageName());   //deprecated
//            activityManager.killBackgroundProcesses(context.getPackageName()); //KILL_BACKGROUND_PROCESSES permission
            System.exit(0);
        } catch (Exception e) {

        }
    }



}
