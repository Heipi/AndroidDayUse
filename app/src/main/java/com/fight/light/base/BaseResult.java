package com.fight.light.base;

import com.fight.light.http.HttpConfig;

import java.io.Serializable;


/**
 * Created by yawei.kang on 2018/5/10.
 */

public class BaseResult<T> implements Serializable {

    private String code;
    private String msg;
    private T data;

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    /**
     * 请求是否成功
     *
     * @return
     */
    public boolean  isSuccess(){
        return code.equals(HttpConfig.REQUEST_SUCCESS_CODE);
    }


}
