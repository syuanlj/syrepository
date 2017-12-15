package com.sky.transport.bean;

import java.util.List;

/**
 * 首页订单列表
 * Created by sky on 2017/3/29.
 */

public class OrderList extends Page{
    private List<OrderDetail> list;

    public List<OrderDetail> getList() {
        return list;
    }

    public void setList(List<OrderDetail> list) {
        this.list = list;
    }
}
