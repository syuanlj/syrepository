package com.sky.driver.bean;

/**
 * 驾驶证入餐入参
 * Created by sky on 2017/3/21.
 */

public class IdCardIn extends BaseUser{
    private String idcard_front;
    private String idcard_back;

    public String getIdcard_front() {
        return idcard_front;
    }

    public void setIdcard_front(String idcard_front) {
        this.idcard_front = idcard_front;
    }

    public String getIdcard_back() {
        return idcard_back;
    }

    public void setIdcard_back(String idcard_back) {
        this.idcard_back = idcard_back;
    }
}
