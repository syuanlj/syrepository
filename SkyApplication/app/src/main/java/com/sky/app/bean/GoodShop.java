package com.sky.app.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/11/29 0029.
 */

public class GoodShop {

    /**
     * total : 2.0
     * rows : 20.0
     * page : 1.0
     * all_page : 1.0
     * list : [{"user_id":"170802000028","pic_url":"http://51gongjiang.oss-cn-shanghai.aliyuncs.com/20170805153948..jpg","nick_name":"博木堂","pwd":"a174dd6e3572fb68eb28163ea536d38d","mobile":"13913017765","gender":0,"birthday":"2000-01-01","real_name":"","state":1,"weixin":"13913017765","qq":"1204760648","address":"南京市江宁区谷里街道向阳社区小轩子61号","email":"1204760648qq.com","user_type":"2","driver_license":"","idcard_front":"","idcard_back":"","other_desc":"{\"id_card_positive\":\"\",\"id_card_reverse\":\"\",\"business_license\":\"http://51gongjiang.oss-cn-shanghai.aliyuncs.com/20170802143738..JPG\",\"apply_state\":\"0\",\"apply_type\":\"1\",\"authorization_state\":\"0\",\"authorization_type\":\"1\",\"title_image_url\":\"\",\"seller_info\":\"\",\"shop_image_urls\":\"http://51gongjiang.oss-cn-shanghai.aliyuncs.com/20170926135436..jpg\",\"serve_years\":\"10\",\"contact_name\":\"\",\"contact_mobile\":\"\",\"work_time\":\"\",\"title_image_url1\":\"\",\"title_image_url2\":\"\",\"title_image_url3\":\"\",\"title_image_url4\":\"\",\"keywords\":\"\",\"shop_gift\":\"\",\"sign_gift\":\"\",\"business_time\":\"\",\"serve_region\":\"\",\"prices\":\"\",\"description\":\"\",\"discount\":\"\"}","one_dir_id":"FW500001","two_dir_id":"","area_id":"CS10000106","area_name":"雨花台区","decorative_id":"ZSC1000010113","decorative_name":"板桥红太阳装饰城","other_flag":1,"province_id":"250","province_name":"江苏","city_id":"025","city_name":"南京","num_good":0,"num_comment":0,"num_collect":20,"num_read":1274,"manufacturer_type_id":"SC200002","manufacturer_type_name":"仿古木雕加工厂","three_dir_id":"","login_user_name":"","tell_phone":"","create_time":"2017-08-02 11:21:05.0","operation_time":"2017-11-22 10:39:22.0","company_id":"CP2017021701","key_words":"FW50000112","bind_id":"20170802112105939097","user_level_recommend":"1"},{"user_id":"170802000037","pic_url":"http://51gongjiang.oss-cn-shanghai.aliyuncs.com/20170808161829..jpg","nick_name":"艺铭仿古木雕厂","pwd":"c1c5c50784dc4ba3f97ddd95f193c423","mobile":"13815878716","gender":0,"birthday":"2000-01-01","real_name":"","state":1,"weixin":"649534360","qq":"649534360","address":"江宁区谷里街道向阳家具城","email":"649534360@qq.com","user_type":"2","driver_license":"","idcard_front":"","idcard_back":"","other_desc":"{\"id_card_positive\":\"\",\"id_card_reverse\":\"\",\"business_license\":\"http://51gongjiang.oss-cn-shanghai.aliyuncs.com/20170802121132..bmp\",\"apply_state\":0,\"apply_type\":1,\"authorization_state\":0,\"authorization_type\":1,\"title_image_url\":\"\",\"seller_info\":\"\",\"shop_image_urls\":\"http://51gongjiang.oss-cn-shanghai.aliyuncs.com/20171101114612..jpg\",\"serve_years\":\"\",\"contact_name\":\"\",\"contact_mobile\":\"\",\"work_time\":\"\",\"title_image_url1\":\"\",\"title_image_url2\":\"\",\"title_image_url3\":\"\",\"title_image_url4\":\"\",\"keywords\":\"\",\"shop_gift\":\"\",\"sign_gift\":\"\",\"business_time\":\"\",\"serve_region\":\"\",\"prices\":\"\",\"description\":\"\",\"discount\":\"\"}","one_dir_id":"FW500001","two_dir_id":"","area_id":"","area_name":"","decorative_id":"ZSC1000010113","decorative_name":"其他区域","other_flag":1,"province_id":"250","province_name":"江苏","city_id":"025","city_name":"南京","num_good":0,"num_comment":0,"num_collect":3,"num_read":509,"manufacturer_type_id":"SC200002","manufacturer_type_name":"仿古木雕加工厂","three_dir_id":"","login_user_name":"","tell_phone":"","create_time":"2017-08-02 12:09:33.0","operation_time":"2017-11-27 14:05:36.0","company_id":"CP2017021701","key_words":"FW50000112","bind_id":"20170802120933836918","user_level_recommend":"1"}]
     */

    private double total;
    private double rows;
    private double page;
    private double all_page;
    private List<ListBean> list;

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getRows() {
        return rows;
    }

    public void setRows(double rows) {
        this.rows = rows;
    }

    public double getPage() {
        return page;
    }

    public void setPage(double page) {
        this.page = page;
    }

    public double getAll_page() {
        return all_page;
    }

    public void setAll_page(double all_page) {
        this.all_page = all_page;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * user_id : 170802000028
         * pic_url : http://51gongjiang.oss-cn-shanghai.aliyuncs.com/20170805153948..jpg
         * nick_name : 博木堂
         * pwd : a174dd6e3572fb68eb28163ea536d38d
         * mobile : 13913017765
         * gender : 0.0
         * birthday : 2000-01-01
         * real_name :
         * state : 1.0
         * weixin : 13913017765
         * qq : 1204760648
         * address : 南京市江宁区谷里街道向阳社区小轩子61号
         * email : 1204760648qq.com
         * user_type : 2
         * driver_license :
         * idcard_front :
         * idcard_back :
         * other_desc : {"id_card_positive":"","id_card_reverse":"","business_license":"http://51gongjiang.oss-cn-shanghai.aliyuncs.com/20170802143738..JPG","apply_state":"0","apply_type":"1","authorization_state":"0","authorization_type":"1","title_image_url":"","seller_info":"","shop_image_urls":"http://51gongjiang.oss-cn-shanghai.aliyuncs.com/20170926135436..jpg","serve_years":"10","contact_name":"","contact_mobile":"","work_time":"","title_image_url1":"","title_image_url2":"","title_image_url3":"","title_image_url4":"","keywords":"","shop_gift":"","sign_gift":"","business_time":"","serve_region":"","prices":"","description":"","discount":""}
         * one_dir_id : FW500001
         * two_dir_id :
         * area_id : CS10000106
         * area_name : 雨花台区
         * decorative_id : ZSC1000010113
         * decorative_name : 板桥红太阳装饰城
         * other_flag : 1.0
         * province_id : 250
         * province_name : 江苏
         * city_id : 025
         * city_name : 南京
         * num_good : 0.0
         * num_comment : 0.0
         * num_collect : 20.0
         * num_read : 1274.0
         * manufacturer_type_id : SC200002
         * manufacturer_type_name : 仿古木雕加工厂
         * three_dir_id :
         * login_user_name :
         * tell_phone :
         * create_time : 2017-08-02 11:21:05.0
         * operation_time : 2017-11-22 10:39:22.0
         * company_id : CP2017021701
         * key_words : FW50000112
         * bind_id : 20170802112105939097
         * user_level_recommend : 1
         */

        private String user_id;
        private String pic_url;
        private String nick_name;
        private String pwd;
        private String mobile;
        private double gender;
        private String birthday;
        private String real_name;
        private double state;
        private String weixin;
        private String qq;
        private String address;
        private String email;
        private String user_type;
        private String driver_license;
        private String idcard_front;
        private String idcard_back;
        private String other_desc;
        private String one_dir_id;
        private String two_dir_id;
        private String area_id;
        private String area_name;
        private String decorative_id;
        private String decorative_name;
        private double other_flag;
        private String province_id;
        private String province_name;
        private String city_id;
        private String city_name;
        private double num_good;
        private double num_comment;
        private double num_collect;
        private double num_read;
        private String manufacturer_type_id;
        private String manufacturer_type_name;
        private String three_dir_id;
        private String login_user_name;
        private String tell_phone;
        private String create_time;
        private String operation_time;
        private String company_id;
        private String key_words;
        private String bind_id;
        private String user_level_recommend;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getPic_url() {
            return pic_url;
        }

        public void setPic_url(String pic_url) {
            this.pic_url = pic_url;
        }

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public String getPwd() {
            return pwd;
        }

        public void setPwd(String pwd) {
            this.pwd = pwd;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public double getGender() {
            return gender;
        }

        public void setGender(double gender) {
            this.gender = gender;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getReal_name() {
            return real_name;
        }

        public void setReal_name(String real_name) {
            this.real_name = real_name;
        }

        public double getState() {
            return state;
        }

        public void setState(double state) {
            this.state = state;
        }

        public String getWeixin() {
            return weixin;
        }

        public void setWeixin(String weixin) {
            this.weixin = weixin;
        }

        public String getQq() {
            return qq;
        }

        public void setQq(String qq) {
            this.qq = qq;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getUser_type() {
            return user_type;
        }

        public void setUser_type(String user_type) {
            this.user_type = user_type;
        }

        public String getDriver_license() {
            return driver_license;
        }

        public void setDriver_license(String driver_license) {
            this.driver_license = driver_license;
        }

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

        public String getOther_desc() {
            return other_desc;
        }

        public void setOther_desc(String other_desc) {
            this.other_desc = other_desc;
        }

        public String getOne_dir_id() {
            return one_dir_id;
        }

        public void setOne_dir_id(String one_dir_id) {
            this.one_dir_id = one_dir_id;
        }

        public String getTwo_dir_id() {
            return two_dir_id;
        }

        public void setTwo_dir_id(String two_dir_id) {
            this.two_dir_id = two_dir_id;
        }

        public String getArea_id() {
            return area_id;
        }

        public void setArea_id(String area_id) {
            this.area_id = area_id;
        }

        public String getArea_name() {
            return area_name;
        }

        public void setArea_name(String area_name) {
            this.area_name = area_name;
        }

        public String getDecorative_id() {
            return decorative_id;
        }

        public void setDecorative_id(String decorative_id) {
            this.decorative_id = decorative_id;
        }

        public String getDecorative_name() {
            return decorative_name;
        }

        public void setDecorative_name(String decorative_name) {
            this.decorative_name = decorative_name;
        }

        public double getOther_flag() {
            return other_flag;
        }

        public void setOther_flag(double other_flag) {
            this.other_flag = other_flag;
        }

        public String getProvince_id() {
            return province_id;
        }

        public void setProvince_id(String province_id) {
            this.province_id = province_id;
        }

        public String getProvince_name() {
            return province_name;
        }

        public void setProvince_name(String province_name) {
            this.province_name = province_name;
        }

        public String getCity_id() {
            return city_id;
        }

        public void setCity_id(String city_id) {
            this.city_id = city_id;
        }

        public String getCity_name() {
            return city_name;
        }

        public void setCity_name(String city_name) {
            this.city_name = city_name;
        }

        public double getNum_good() {
            return num_good;
        }

        public void setNum_good(double num_good) {
            this.num_good = num_good;
        }

        public double getNum_comment() {
            return num_comment;
        }

        public void setNum_comment(double num_comment) {
            this.num_comment = num_comment;
        }

        public double getNum_collect() {
            return num_collect;
        }

        public void setNum_collect(double num_collect) {
            this.num_collect = num_collect;
        }

        public double getNum_read() {
            return num_read;
        }

        public void setNum_read(double num_read) {
            this.num_read = num_read;
        }

        public String getManufacturer_type_id() {
            return manufacturer_type_id;
        }

        public void setManufacturer_type_id(String manufacturer_type_id) {
            this.manufacturer_type_id = manufacturer_type_id;
        }

        public String getManufacturer_type_name() {
            return manufacturer_type_name;
        }

        public void setManufacturer_type_name(String manufacturer_type_name) {
            this.manufacturer_type_name = manufacturer_type_name;
        }

        public String getThree_dir_id() {
            return three_dir_id;
        }

        public void setThree_dir_id(String three_dir_id) {
            this.three_dir_id = three_dir_id;
        }

        public String getLogin_user_name() {
            return login_user_name;
        }

        public void setLogin_user_name(String login_user_name) {
            this.login_user_name = login_user_name;
        }

        public String getTell_phone() {
            return tell_phone;
        }

        public void setTell_phone(String tell_phone) {
            this.tell_phone = tell_phone;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getOperation_time() {
            return operation_time;
        }

        public void setOperation_time(String operation_time) {
            this.operation_time = operation_time;
        }

        public String getCompany_id() {
            return company_id;
        }

        public void setCompany_id(String company_id) {
            this.company_id = company_id;
        }

        public String getKey_words() {
            return key_words;
        }

        public void setKey_words(String key_words) {
            this.key_words = key_words;
        }

        public String getBind_id() {
            return bind_id;
        }

        public void setBind_id(String bind_id) {
            this.bind_id = bind_id;
        }

        public String getUser_level_recommend() {
            return user_level_recommend;
        }

        public void setUser_level_recommend(String user_level_recommend) {
            this.user_level_recommend = user_level_recommend;
        }
    }
}
