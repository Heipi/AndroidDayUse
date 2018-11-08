package com.fight.light.binder;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.widget.TextView;

import com.fight.light.*;
import com.fight.light.IBookManager;

import java.util.List;

import static android.text.Html.FROM_HTML_MODE_LEGACY;

public class BookManagerActivity extends AppCompatActivity {

   private static final String TAG = "BookManagerActivity";
   private static final  int MESSAGE_NEW_BOOK_ARRIVED = 1;
  private IBookManager mRemoteBookManager;

   private Handler mHandler = new Handler(){
       @Override
       public void handleMessage(Message msg) {
           super.handleMessage(msg);
           switch (msg.what){
               case MESSAGE_NEW_BOOK_ARRIVED:
                  Log.d(TAG,"receiver new book:"+msg.obj);
                   break;
           }

       }
   };

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            com.fight.light.IBookManager bookManager = IBookManager.Stub.asInterface(service);
            try {
                mRemoteBookManager = bookManager;
                List<Book> list = bookManager.getBookList();
                Log.e(TAG,"query bool list, list type:"+list.getClass());
                Log.e(TAG,"query book list:"+list.get(0).toString());

                Book newBook = new Book(3,"Android 开发艺术探索");
                bookManager.addBook(newBook);
                List<Book> newList = bookManager.getBookList();
                Log.e(TAG,"query book list2:"+newList.toString());

                bookManager.registerListener(mOnNewBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }


        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
          mRemoteBookManager = null;
          Log.e(TAG,"binder died");
        }
    };

    private IOnNewBookArrivedListener mOnNewBookArrivedListener = new IOnNewBookArrivedListener.Stub() {
        @Override
        public void onNewBookArrived(Book newBook) throws RemoteException {
           mHandler.obtainMessage(MESSAGE_NEW_BOOK_ARRIVED,newBook).sendToTarget();
        }
    };
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_manager);
        String ss ="<p></p><ol><li>ads<br></li><li>bvcbfg</li><li>sdfwe</li><li>sfsgdfg</li></ol><p></p>";
        Spanned text = Html.fromHtml(ss,FROM_HTML_MODE_LEGACY);
        TextView textView = new TextView(this);
        textView.setText(text);
        ( (TextView)findViewById(R.id.text)).setText(text);
       Intent intent = new Intent(this,BookManagerService.class);
       bindService(intent,mConnection ,Context.BIND_AUTO_CREATE);

    }

    @Override
    protected void onDestroy() {
        if (mRemoteBookManager!= null && mRemoteBookManager.asBinder().isBinderAlive()){
            try {
                Log.i(TAG,"unregister listener:"+mOnNewBookArrivedListener);
                mRemoteBookManager.unregisterListener(mOnNewBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        unbindService(mConnection);
        super.onDestroy();
    }
}
