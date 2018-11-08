package com.fight.light.message;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.fight.light.R;
import com.fight.light.http.ApiClient;

public class MessengerActivity extends AppCompatActivity {

    private static final String TAG = "MessengerActivity";
    private Messenger mService;

    private Messenger mGetReplyMessenger = new Messenger(new MessengerHandler());

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = new Messenger(service);
            Message msg = Message.obtain(null,1);
            Bundle data  = new Bundle();
            data.putString("msg","HELLO, THIS IS CLIENT!");
            msg.setData(data);
            //注意下面这句
            msg.replyTo = mGetReplyMessenger;
            try {
                mService.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);

        Intent intent = new Intent(this,MessengerService.class);
        bindService(intent,mConnection, Context.BIND_AUTO_CREATE);


        ApiClient.getInstance().getApiService();

    }

    @Override
    protected void onDestroy() {
        unbindService(mConnection);
        super.onDestroy();

    }

    private static class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 2:
                    Log.e(TAG,"receive msg from Service" +msg.getData());
                    break;

            }
        }
    }
}
