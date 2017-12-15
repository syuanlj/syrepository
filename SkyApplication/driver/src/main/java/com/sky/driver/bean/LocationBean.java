package com.sky.driver.bean;

/**
 * 定位
 * Created by sky on 2017/3/21.
 */

public class LocationBean extends Page{
    private double cur_longitude;
    private double cur_latitude;

    public double getCur_longitude() {
        return cur_longitude;
    }

    public void setCur_longitude(double cur_longitude) {
        this.cur_longitude = cur_longitude;
    }

    public double getCur_latitude() {
        return cur_latitude;
    }

    public void setCur_latitude(double cur_latitude) {
        this.cur_latitude = cur_latitude;
    }
}