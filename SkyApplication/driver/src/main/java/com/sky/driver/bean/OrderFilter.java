package com.sky.driver.bean;

/**
 * 首页订单筛选
 * Created by sky on 2017/3/21.
 */

public class OrderFilter{
    private String orderBy;//排序
    private int page;//当前分页
    private int rows;//分页条数
    private String money_start;//价格区间开始
    private String money_end;//价格区间结束

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
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

    public String getMoney_start() {
        return money_start;
    }

    public void setMoney_start(String money_start) {
        this.money_start = money_start;
    }

    public String getMoney_end() {
        return money_end;
    }

    public void setMoney_end(String money_end) {
        this.money_end = money_end;
    }
}
