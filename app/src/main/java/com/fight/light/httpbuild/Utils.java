package com.fight.light.httpbuild;

import android.support.annotation.Nullable;

import okhttp3.OkHttpClient;

/**
 * Created by yawei.kang on 2018/6/12.
 */

public class Utils {

    static <T> T checkNotNull(T object, String message) {
        if (object == null) {
            throw new NullPointerException(message);
        }
        return object;
    }

}
