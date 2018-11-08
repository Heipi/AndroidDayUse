package com.fight.light.util;

import android.util.Log;

import com.fight.light.BuildConfig;

/**
 * Created by yawei.kang on 2018/1/29.
 */

public class LogUtils {
        static String className;//类名
        static String methodName;//方法名
        static int lineNumber;//行数

        private LogUtils(){
        /* Protect from instantiations */
        }

        public static boolean isDebuggable() {
            return BuildConfig.DEBUG;
        }

        private static String createLog( String log ) {
            StringBuffer buffer = new StringBuffer();
            buffer.append(methodName);
            buffer.append("(").append(className).append(":").append(lineNumber).append(")");
            buffer.append(log);
            return buffer.toString();
        }

        private static void getMethodNames(StackTraceElement[] sElements){
            className = sElements[1].getFileName();
            methodName = sElements[1].getMethodName();
            lineNumber = sElements[1].getLineNumber();
        }
    private static void getMethodThreadNames(StackTraceElement[] sElements){
        className = sElements[3].getFileName();
        methodName = sElements[3].getMethodName();
        lineNumber = sElements[3].getLineNumber();
    }

    public static void ebyThread(String message){
        if (!isDebuggable())
            return;

        // Throwable instance must be created before any methods
        getMethodThreadNames(Thread.currentThread().getStackTrace());
        Log.e(className, createLog(message));
    }
        public static void e(String message){
            if (!isDebuggable())
                return;

            // Throwable instance must be created before any methods
            getMethodNames(new Throwable().getStackTrace());
            Log.e(className, createLog(message));
        }


        public static void i(String message){
            if (!isDebuggable())
                return;

            getMethodNames(new Throwable().getStackTrace());
            Log.i(className, createLog(message));
        }

        public static void d(String message){
            if (!isDebuggable())
                return;

            getMethodNames(new Throwable().getStackTrace());
            Log.d(className, createLog(message));
        }

        public static void v(String message){
            if (!isDebuggable())
                return;

            getMethodNames(new Throwable().getStackTrace());
            Log.v(className, createLog(message));
        }

        public static void w(String message){
            if (!isDebuggable())
                return;

            getMethodNames(new Throwable().getStackTrace());
            Log.w(className, createLog(message));
        }

        public static void wtf(String message){
            if (!isDebuggable())
                return;

            getMethodNames(new Throwable().getStackTrace());
            Log.wtf(className, createLog(message));
        }

    /**
     * 普通 tag，message
     * @param tag
     * @param message
     */
    public static void d(String tag,String message){
        if (!isDebuggable())
            return;
//        getMethodNames(new Throwable().getStackTrace());
        Log.d(tag, message);
    }

    /**
     * 普通 tag，message
     * @param tag
     * @param message
     */
    public static void e(String tag,String message){
        if (!isDebuggable())
            return;
//        getMethodNames(new Throwable().getStackTrace());
        Log.e(tag, message);
    }
    /**
     * 普通 tag，message
     * @param tag
     * @param message
     */
    public static void w(String tag,String message){
        if (!isDebuggable())
            return;
//        getMethodNames(new Throwable().getStackTrace());
        Log.w(tag, message);
    }
}
