package com.light.diskcache.thread;

import android.os.AsyncTask;
import android.util.Log;

import java.util.Arrays;

public class DownloadTask extends AsyncTask<String,Integer,Object> {


    private static final String TAG = DownloadTask.class.getSimpleName();

    @Override
    protected Object doInBackground(String... strings) {
        int count = strings.length;
        long totalSize = 0;
        for (int i = 0; i < count; i++) {
          //  totalSize += Download.downlaod(strings[i]);
            publishProgress((int) (((float)i/count) *100));
            if (isCancelled())
                break;
        }
        return totalSize;



    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        Log.d(TAG,"progress:"+ Arrays.toString(values));
    }

    @Override
    protected void onPostExecute(Object o) {
        //TODO
        Log.d(TAG,o.toString());
    }
}
