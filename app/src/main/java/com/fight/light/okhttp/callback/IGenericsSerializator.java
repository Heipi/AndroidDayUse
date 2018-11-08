package com.fight.light.okhttp.callback;

import java.lang.reflect.Type;

/**
 * Created by JimGong on 2016/6/23.
 */
public interface IGenericsSerializator {
   /* <T> T transform(String response, Class<T> classOfT);*/
   <T> T transform(String response, Type type);
}
