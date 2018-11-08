package com.fight.light.entity;

import com.fight.light.base.BaseResult;

/**
 * Created by yawei.kang on 2018/2/24.
 */

public class User2 extends BaseResult{
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
