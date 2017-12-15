package com.sky.transport.bean;

import java.io.Serializable;

/**
 * 计算车费入参
 * Created by sky on 2017/3/29.
 */

public class DistanceMoneyIn implements Serializable{
    private String card_id;
    private double from_longitude;
    private double from_latitude;
    private double to_longitude;
    private double to_latitude;

    public double getTo_longitude() {
        return to_longitude;
    }

    public void setTo_longitude(double to_longitude) {
        this.to_longitude = to_longitude;
    }

    public double getTo_latitude() {
        return to_latitude;
    }

    public void setTo_latitude(double to_latitude) {
        this.to_latitude = to_latitude;
    }

    public String getCard_id() {
        return card_id;
    }

    public void setCard_id(String card_id) {
        this.card_id = card_id;
    }

    public double getFrom_longitude() {
        return from_longitude;
    }

    public void setFrom_longitude(double from_longitude) {
        this.from_longitude = from_longitude;
    }

    public double getFrom_latitude() {
        return from_latitude;
    }

    public void setFrom_latitude(double from_latitude) {
        this.from_latitude = from_latitude;
    }
}
