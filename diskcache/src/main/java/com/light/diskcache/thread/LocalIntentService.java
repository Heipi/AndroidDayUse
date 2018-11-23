package com.light.diskcache.thread;

import android.app.IntentService;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

public class LocalIntentService extends IntentService {


    private static final String TAG = "LocalIntentService";
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     */
    public LocalIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent( Intent intent) {
        String action = intent.getStringExtra("task_action");
        Log.d(TAG,"receive task:"+action);
        SystemClock.sleep(3000);
        if ("com.action.task1".equals(action)){
            Log.d(TAG,"handle task:"+action);
        }
    }

    @Override
    public void onDestroy() {
        Log.d(TAG,"service destroyed");
        super.onDestroy();
    }
}
