package com.sky.app.bean;

import java.io.Serializable;

/**
 * Created by hongbang on 2017/5/2.
 */

public class Page implements Serializable{

    private int  page;
    private int   rows;
    private int   total;

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

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
