// IBookManager.aidl
package com.fight.light;

// Declare any non-default types here with import statements
import com.fight.light.Book;
import com.fight.light.IOnNewBookArrivedListener;
interface IBookManager {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

      List<Book> getBookList();
      void addBook(in Book book);

      void registerListener(IOnNewBookArrivedListener listener);
      void unregisterListener(IOnNewBookArrivedListener listener);

}
