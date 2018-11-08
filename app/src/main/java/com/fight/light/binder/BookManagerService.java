package com.fight.light.binder;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.fight.light.*;
import com.fight.light.IBookManager;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by yawei.kang on 2018/6/1.
 */

public class BookManagerService extends Service {

    private static  final String TAG = "BMS";
    private AtomicBoolean mIsServiceDestroyed = new AtomicBoolean(false);
    private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<>();

    private CopyOnWriteArrayList<IOnNewBookArrivedListener>  mListener = new CopyOnWriteArrayList<>();

    private Binder mBinder = new IBookManager.Stub() {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public List<Book> getBookList() throws RemoteException {
            return mBookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
               mBookList.add(book);
        }

        @Override
        public void registerListener(IOnNewBookArrivedListener listener) throws RemoteException {
               if (!mListener.contains(listener)){
                   mListener.add(listener);


               }else {
                   Log.d(TAG,"already exists");
               }
            Log.d(TAG,"registerListener,size:"+mListener.size());
        }

        @Override
        public void unregisterListener(IOnNewBookArrivedListener listener) throws RemoteException {
            if (!mListener.contains(listener)){
                mListener.add(listener);
                Log.d(TAG,"unregister listener succeed");

            }else {
                Log.d(TAG,"not found,can not unregister");
            }
            Log.d(TAG,"unregisterListener,current size:"+mListener.size());
        }


    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mBookList.add(new Book(1,"android"));
        mBookList.add(new Book(2,"ios"));
        new Thread(new ServiceWorker()).start();
    }

    private void onNewBookArrived(Book book) throws RemoteException {
        mBookList.add(book);
        Log.d(TAG,"onNewBookArrived,notify listeners:"+mListener.size());
        for (int i = 0; i < mListener.size(); i++) {
            IOnNewBookArrivedListener listener = mListener.get(i);
            Log.d(TAG,"onNewBookArrived, notify listener:"+listener);
            listener.onNewBookArrived(book);
        }

    }
    private class ServiceWorker implements Runnable{

        @Override
        public void run() {
            while (!mIsServiceDestroyed.get()){
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                int bookId = mBookList.size()+1;
                Book newBook = new Book(bookId,"new book#"+bookId);

                try {
                    onNewBookArrived(newBook);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
