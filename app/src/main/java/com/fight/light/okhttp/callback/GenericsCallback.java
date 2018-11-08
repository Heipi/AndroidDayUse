package com.fight.light.okhttp.callback;

import com.fight.light.okhttp.callback.Callback;
import com.google.gson.internal.$Gson$Types;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Response;

public abstract class GenericsCallback<T> extends Callback<T> {
    IGenericsSerializator mIGenericsSerializator;
    public GenericsCallback(IGenericsSerializator serializator){
        mIGenericsSerializator = serializator;
    }

    @Override
    public T parseNetworkResponse(Response response) throws IOException {
        String string = response.body().string();
        Type type  = getSuperclassTypeParameter(getClass());
       if (type == String.class){
           return (T) string;
       }
         T  bean =  mIGenericsSerializator.transform(string,type);
        return bean;
    }

    /**
     * 通过反射想要的返回类型
     *
     * @param subclass
     * @return
     */
    static Type getSuperclassTypeParameter(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
    }
}
