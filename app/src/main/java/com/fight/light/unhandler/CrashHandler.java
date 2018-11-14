package com.fight.light.unhandler;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;

import com.fight.light.app.LightApplication;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;


public class CrashHandler implements UncaughtExceptionHandler{
	public static final String TAG = "CrashHandler";
	private static final boolean DEBUG = true ;
	private static final String PATH = Environment.getExternalStorageDirectory().getPath()+"/CrashFile/log/";
	private static final String FILENAME = "crash";
	private static final String FILE_NAME_SUFFIX =".trace" ;
    private Context mContext;
	private UncaughtExceptionHandler mExceptionHandler;
	private static final CrashHandler ourInstance = new CrashHandler();

	public static CrashHandler getInstance() {
		return ourInstance;
	}
	private CrashHandler() {

	}
    public void init(Context context){
    	mExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
    	Thread.setDefaultUncaughtExceptionHandler(this);
        this.mContext = context.getApplicationContext();
    }
	@SuppressLint("WrongConstant")
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {

		handleException(ex);
		if (mExceptionHandler != null) {
			 //如果用户没有处理则让系统默认的异常处理器来处理    
			mExceptionHandler.uncaughtException(thread, ex);
		}else {
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
			Log.e(TAG, "error"+e);
			
			}
			
//			Intent  intent = new Intent(application.getApplicationContext(),SplashActivity.class);
//			 PendingIntent restartIntent = PendingIntent.getActivity(application.getApplicationContext(),
//					0, intent,Intent.FLAG_ACTIVITY_NEW_TASK);
//			//退出程序
//
//			AlarmManager mgr = (AlarmManager) application.getSystemService(Context.ALARM_SERVICE);
//			mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000, restartIntent); //1秒钟后重启应用
			android.os.Process.killProcess(android.os.Process.myPid());
		}
	}
  
	/**
	 * 自定义错误处理，收集错误信息，发送错误报告
	 */
	
	private boolean handleException(Throwable ex){
		if (ex == null) {
			return false;
		}
		dumpExceptionToSDCard(ex);
		uploadExceptionToServer();
		//使用Toast来显示异常信息
		return true;
	}
	//save exception
	private void dumpExceptionToSDCard(Throwable ex)  {
		if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			 if (DEBUG){
			 	Log.w(TAG,"sdcard unmounted，skip dump exception");
			 }
           return;
		}

		File dir = new File(PATH);
		if (!dir.exists()){
			dir.mkdirs();
		}
        long current = System.currentTimeMillis();
		String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(current));
        File file = new File(PATH+FILENAME+time+FILE_NAME_SUFFIX);

		try {
			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
			pw.println(time);//// 将字符串+回车符，写入到输出流中
			dumpPhoneInfo(pw);
			pw.println();
			ex.printStackTrace(pw);
			pw.close();
		} catch (IOException e) {
//			e.printStackTrace();
			Log.e(TAG,"dump crash info failed");
		}


	}

	private void dumpPhoneInfo(PrintWriter pw)  {
		try {
		PackageManager pm = mContext.getPackageManager();
		PackageInfo pi = null;
		pi = pm.getPackageInfo(mContext.getPackageName(),PackageManager.GET_ACTIVITIES);
		pw.print("App Version: ");
	    pw.print(pi.versionName);
	    pw.print("_");
		pw.println(pi.versionCode);
        //Android 版本号
		pw.print("OS Version: ");
		pw.print(Build.VERSION.RELEASE);
        pw.print("_");
        pw.println(Build.VERSION.SDK_INT);
        // 手机制造商
		pw.print("Vendor: ");
		pw.println(Build.MANUFACTURER);
		//手机型号
		pw.print("Model: ");
		pw.println(Build.MODEL);
		//CPU 架构
		pw.print("CPU ABI: ");
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			pw.println(Arrays.toString(Build.SUPPORTED_ABIS));
		}else{
			pw.println(Build.CPU_ABI);
		}
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
	}
   private void uploadExceptionToServer(){
		//TODO
   }
}
