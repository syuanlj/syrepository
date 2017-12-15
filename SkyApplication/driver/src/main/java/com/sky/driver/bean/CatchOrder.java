package com.sky.driver.bean;

/**
 * 接单入参
 * Created by sky on 2017/3/21.
 */

public class CatchOrder extends OrderIn{
    private String shop_user_id;

    public String getShop_user_id() {
        return shop_user_id;
    }

    public void setShop_user_id(String shop_user_id) {
        this.shop_user_id = shop_user_id;
    }
}
