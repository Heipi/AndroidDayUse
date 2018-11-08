package com.fight.light.okhttp.callback;

import okhttp3.Response;

public abstract class StringCallback extends Callback<String> {
    @Override
    public String parseNetworkResponse(Response response) throws Exception {
        return response.body().string();
    }
}
