package com.sky.driver.bean;

/**
 * 驾驶证入餐入参
 * Created by sky on 2017/3/21.
 */

public class DriverIn extends BaseUser{
    public String getDriver_license() {
        return driver_license;
    }

    public void setDriver_license(String driver_license) {
        this.driver_license = driver_license;
    }

    private String driver_license;
}
