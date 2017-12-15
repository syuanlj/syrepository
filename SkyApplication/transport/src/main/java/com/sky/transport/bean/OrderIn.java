package com.sky.transport.bean;

/**
 * 下单入参
 * Created by sky on 2017/3/29.
 */

public class OrderIn extends BaseUser{
    private String order_type;
    private String card_id;
    private String from_address;
    private String from_detail;
    private double from_longitude;
    private double from_latitude;
    private String to_address;
    private String to_detail;
    private double to_longitude;
    private double to_latitude;
    private String other_desc;
    private String remark;
    private String order_time;
    private String need_use_time;

    public String getNeed_use_time() {
        return need_use_time;
    }

    public void setNeed_use_time(String need_use_time) {
        this.need_use_time = need_use_time;
    }

    public String getOrder_time() {
        return order_time;
    }

    public void setOrder_time(String order_time) {
        this.order_time = order_time;
    }

    public String getFrom_detail() {
        return from_detail;
    }

    public void setFrom_detail(String from_detail) {
        this.from_detail = from_detail;
    }

    public String getTo_detail() {
        return to_detail;
    }

    public void setTo_detail(String to_detail) {
        this.to_detail = to_detail;
    }

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

    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
    }

    public String getCard_id() {
        return card_id;
    }

    public void setCard_id(String card_id) {
        this.card_id = card_id;
    }

    public String getFrom_address() {
        return from_address;
    }

    public void setFrom_address(String from_address) {
        this.from_address = from_address;
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

    public String getTo_address() {
        return to_address;
    }

    public void setTo_address(String to_address) {
        this.to_address = to_address;
    }

    public String getOther_desc() {
        return other_desc;
    }

    public void setOther_desc(String other_desc) {
        this.other_desc = other_desc;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
