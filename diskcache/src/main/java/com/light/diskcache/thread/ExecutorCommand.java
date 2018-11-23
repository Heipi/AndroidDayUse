package com.light.diskcache.thread;

import android.os.SystemClock;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ExecutorCommand {

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            SystemClock.sleep(2000);
        }
    };

public void exeFixedThread(){
    ExecutorService fixedThreadPool = Executors.newFixedThreadPool(4);
    fixedThreadPool.execute(runnable);

}
public void exeCacheThread(){
    ExecutorService cacheThreadPool = Executors.newCachedThreadPool();
    cacheThreadPool.execute(runnable);
}
public void exeScheduledExecutor(){
    ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(4);
    scheduledExecutorService.schedule(runnable,2000, TimeUnit.MILLISECONDS);
    scheduledExecutorService.scheduleAtFixedRate(runnable,10,1000,TimeUnit.MILLISECONDS);

}

public void exeSingleThreadExecutor(){
    ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
    singleThreadExecutor.execute(runnable);
}

}
