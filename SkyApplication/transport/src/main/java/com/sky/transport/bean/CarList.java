package com.sky.transport.bean;

import java.util.List;

/**
 * 分页
 * Created by sky on 2017/3/29.
 */

public class CarList extends Page{
    private List<CarBean> list;

    public List<CarBean> getList() {
        return list;
    }

    public void setList(List<CarBean> list) {
        this.list = list;
    }
}
