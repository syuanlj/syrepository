package com.sky.driver.bean;

/**
 * 首页订单筛选
 * Created by sky on 2017/3/21.
 */

public class MyAccountIn extends BaseUser{
    private int page;//当前分页
    private int rows;//分页条数

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
