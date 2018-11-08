package com.fight.light.binder;

import android.os.IInterface;

import com.fight.light.Book;

import java.util.List;

/**
 * Created by yawei.kang on 2018/5/30.
 */

public interface IBookManager extends IInterface{
     static final String DESCRIPTOR = "com.fight.light.binder.IBookManager";
    static final int TRANSACTION_getBookList = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
    static final int TRANSACTION_addBook = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);

    public List<Book> getBookList() throws android.os.RemoteException;

    public void addBook(Book book) throws android.os.RemoteException;
}
