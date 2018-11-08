package com.fight.light.convert;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by yawei.kang on 2018/2/26.
 */

public final class StringConverter implements Converter<ResponseBody,String>{

    public StringConverter() {
    }

    @Override
    public String convert(ResponseBody value) throws IOException {
        return value.string();
    }
}
