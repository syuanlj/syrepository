package com.sky.transport.bean;

/**
 * 我的订单筛选
 * Created by sky on 2017/3/21.
 */
public class MyOrderFilter {
    private int page;//当前分页
    private int rows;//分页条数
    private String user_id;
    private String state;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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
