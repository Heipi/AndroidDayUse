package com.fight.light.binder;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import com.fight.light.Book;

import java.util.List;

/**
 * Created by yawei.kang on 2018/5/30.
 */

public class BookManagerImpl extends Binder implements IBookManager{

    /**
     * Construct the stub at attach it to the interface
     */
    public BookManagerImpl(){
        this.attachInterface(this,DESCRIPTOR);
    }
    /**
     * Cast an IBinder object into an com.fight.light.IBookManager interface,
     * generating a proxy if needed.
     */
    public static IBookManager asInterface(IBinder obj){
        if ((obj==null)){
            return  null;
        }

        IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
        if (((iin != null) && (iin instanceof IBookManager))){
            return ((IBookManager)iin);
        }
      return  new BookManagerImpl.Proxy(obj);

    }



    @Override
    public List<Book> getBookList() throws RemoteException {
        return null;
    }

    @Override
    public void addBook(Book book) throws RemoteException {

    }

    @Override
    public IBinder asBinder() {
        return this;
    }

    private static class Proxy implements IBookManager{

        private IBinder mRemote;

        public Proxy(IBinder mRemote) {
            this.mRemote = mRemote;
        }
        @Override
        public IBinder asBinder() {
            return mRemote;
        }
        public java.lang.String getInterfaceDescriptor() {
            return DESCRIPTOR;
        }
        @Override
        public List<Book> getBookList() throws RemoteException {
            Parcel data = Parcel.obtain();
            Parcel reply = Parcel.obtain();
            List<Book> result;
            try {
                data.writeInterfaceToken(DESCRIPTOR);
                mRemote.transact(TRANSACTION_getBookList, data, reply, 0);
                reply.readException();
                result = reply.createTypedArrayList(Book.CREATOR);

            }finally {
                reply.recycle();
                data.recycle();
            }
            return result;
        }

        @Override
        public void addBook(Book book) throws RemoteException {

            Parcel data = Parcel.obtain();
            Parcel reply = Parcel.obtain();

            try {
                data.writeInterfaceToken(DESCRIPTOR);
              if ((book!=null)){
                data.writeInt(1);
                book.writeToParcel(data,0);
              }else{
                   data.writeInt(0);
              }
             mRemote.transact(TRANSACTION_addBook,data,reply,0);
              reply.readException();

            }finally {
                reply.recycle();
                data.recycle();
            }


        }


    }
private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient(){


    @Override
    public void binderDied() {
    }
};

}
