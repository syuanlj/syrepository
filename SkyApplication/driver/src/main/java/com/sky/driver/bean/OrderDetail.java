package com.sky.driver.bean;

/**
 * Created by sky on 2017/3/21.
 */

public class OrderDetail extends BaseUser{
    private int total;
    private int rows;
    private int page;
    private int start;
    private String orderBy;
    private String card_id;
    private int pay_state;
    private String order_id;
    private String user_name;
    private String user_mobile;
    private String shop_user_id;
    private String shop_user_name;
    private String shop_user_mobile;
    private String from_address;
    private String from_longitude;
    private String from_latitude;
    private String to_address;
    private String to_longitude;
    private String to_latitude;
    private String state;
    private String distance;
    private double money;
    private String order_type;
    private String end_time;
    private String create_time;
    private String other_desc;
    private String remark;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getCard_id() {
        return card_id;
    }

    public void setCard_id(String card_id) {
        this.card_id = card_id;
    }

    public int getPay_state() {
        return pay_state;
    }

    public void setPay_state(int pay_state) {
        this.pay_state = pay_state;
    }
    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_mobile() {
        return user_mobile;
    }

    public void setUser_mobile(String user_mobile) {
        this.user_mobile = user_mobile;
    }

    public String getShop_user_id() {
        return shop_user_id;
    }

    public void setShop_user_id(String shop_user_id) {
        this.shop_user_id = shop_user_id;
    }

    public String getShop_user_name() {
        return shop_user_name;
    }

    public void setShop_user_name(String shop_user_name) {
        this.shop_user_name = shop_user_name;
    }

    public String getShop_user_mobile() {
        return shop_user_mobile;
    }

    public void setShop_user_mobile(String shop_user_mobile) {
        this.shop_user_mobile = shop_user_mobile;
    }

    public String getFrom_address() {
        return from_address;
    }

    public void setFrom_address(String from_address) {
        this.from_address = from_address;
    }

    public String getFrom_longitude() {
        return from_longitude;
    }

    public void setFrom_longitude(String from_longitude) {
        this.from_longitude = from_longitude;
    }

    public String getFrom_latitude() {
        return from_latitude;
    }

    public void setFrom_latitude(String from_latitude) {
        this.from_latitude = from_latitude;
    }

    public String getTo_address() {
        return to_address;
    }

    public void setTo_address(String to_address) {
        this.to_address = to_address;
    }

    public String getTo_longitude() {
        return to_longitude;
    }

    public void setTo_longitude(String to_longitude) {
        this.to_longitude = to_longitude;
    }

    public String getTo_latitude() {
        return to_latitude;
    }

    public void setTo_latitude(String to_latitude) {
        this.to_latitude = to_latitude;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
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
