package com.sky.shop.ui.activity.OpenIMShop;

/**
 * Created by Administrator on 2017/11/10 0010.
 */

public class OpenIMUserBean {
    /**
     {
     user_id
     pwd
     user_type  1 -代表用户    2 --代表商户
     }
     */


    private String user_id;
    private String pwd;
    private String user_type;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String mobile) {
        this.user_id = mobile;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    @Override
    public String toString() {
        return "OpenIMUserBean{" +
                "user_id='" + user_id + '\'' +
                ", pwd='" + pwd + '\'' +
                ", user_type='" + user_type + '\'' +
                '}';
    }
}