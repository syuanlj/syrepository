package com.sky.transport.bean;

import java.io.Serializable;

/**
 * 地理位置
 * @author wjx
 *
 */
public class Location implements Serializable{
    private double longitude;//经度
    private double latitude;//纬度

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}