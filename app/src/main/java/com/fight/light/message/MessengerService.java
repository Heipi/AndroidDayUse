package com.fight.light.message;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by yawei.kang on 2018/5/31.
 */

public class MessengerService extends Service {

    private static final String TAG = "MessengerService";
    private static class MessengerHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        switch (msg.what){
            case 1:
                Log.e(TAG,"receiver msg from Client"+msg.getData().toString() +"==="+msg.obj);
                Messenger client  = msg.replyTo;
                Message replyMessage = Message.obtain(null,2);
                Bundle bundle = new Bundle();
                bundle.putString("reply","恩，你的消息我已经收到，稍后会回复你.");
                replyMessage.setData(bundle);
                try {
                    client.send(replyMessage);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            default:
                super.handleMessage(msg);
        }
        }
    }
   private final Messenger mMessenger  = new Messenger(new MessengerHandler());
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }
}
