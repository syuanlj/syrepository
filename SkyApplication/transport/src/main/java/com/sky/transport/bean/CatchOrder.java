package com.sky.transport.bean;

/**
 * 接单入参
 * Created by sky on 2017/3/21.
 */

public class CatchOrder {
    private String shop_user_id;
    private String order_id;

    public String getShop_user_id() {
        return shop_user_id;
    }

    public void setShop_user_id(String shop_user_id) {
        this.shop_user_id = shop_user_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }
}
