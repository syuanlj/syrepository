package com.sky.driver.bean;

/**
 * 忘记密码入参
 * Created by sky on 2017/3/21.
 */

public class UpdatePwdIn extends BaseUser{
    private String old_password;
    private String new_password;

    public String getOld_password() {
        return old_password;
    }

    public void setOld_password(String old_password) {
        this.old_password = old_password;
    }

    public String getNew_password() {
        return new_password;
    }

    public void setNew_password(String new_password) {
        this.new_password = new_password;
    }
}
