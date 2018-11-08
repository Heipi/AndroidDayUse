// IOnNewBookArrivedListener.aidl
package com.fight.light;

// Declare any non-default types here with import statements
import com.fight.light.Book;
interface IOnNewBookArrivedListener {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void onNewBookArrived(in Book newBook);
}
