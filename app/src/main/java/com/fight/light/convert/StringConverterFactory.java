package com.fight.light.convert;

import com.google.gson.Gson;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import io.reactivex.annotations.Nullable;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yawei.kang on 2018/2/26.
 */

public final class StringConverterFactory extends Converter.Factory{
    private  static final StringConverterFactory instance = new StringConverterFactory();

    public static StringConverterFactory create() {
        return instance;
    }

    @Nullable
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        if (type == String.class){
            return new StringConverter();
        }
        return  null;
    }
}
