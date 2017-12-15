package com.sky.transport.bean;

import java.io.Serializable;

/**
 * 计算车费出参
 * Created by sky on 2017/3/29.
 */

public class DistanceMoneyOut implements Serializable{
    private double start_money;
    private double single_money;
    private double money;
    private String car_name;
    private double distance;

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getCar_name() {
        return car_name;
    }

    public void setCar_name(String car_name) {
        this.car_name = car_name;
    }

    public double getStart_money() {
        return start_money;
    }

    public void setStart_money(double start_money) {
        this.start_money = start_money;
    }

    public double getSingle_money() {
        return single_money;
    }

    public void setSingle_money(double single_money) {
        this.single_money = single_money;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }
}
