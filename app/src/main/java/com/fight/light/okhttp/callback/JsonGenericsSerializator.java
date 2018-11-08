package com.fight.light.okhttp.callback;

import com.google.gson.Gson;

import java.lang.reflect.Type;

public class JsonGenericsSerializator implements IGenericsSerializator {
    Gson gson = new Gson();
    @Override
    public <T> T transform(String response, Type type) {
        return gson.fromJson(response,type);
    }
}
