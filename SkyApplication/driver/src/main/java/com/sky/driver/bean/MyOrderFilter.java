package com.sky.driver.bean;

/**
 * 我的订单筛选
 * Created by sky on 2017/3/21.
 */
public class MyOrderFilter {
    private int page;//当前分页
    private int rows;//分页条数
    private String shop_user_id;
    private String state;

    public String getShop_user_id() {
        return shop_user_id;
    }

    public void setShop_user_id(String shop_user_id) {
        this.shop_user_id = shop_user_id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }
}
