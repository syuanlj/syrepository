package com.sky.app.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/11/30 0030.
 */

public class Goods {

    /**
     * total : 1.0
     * rows : 20.0
     * page : 1.0
     * all_page : 1.0
     * list : [{"product_id":"170805000258","product_name":"红木桌子","product_image_url":"http://51gongjiang.oss-cn-shanghai.aliyuncs.com/20170805141013..jpg","product_price_old":100000,"product_price_now":90000,"num_good":0,"num_collect":0,"state":1}]
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
         * product_id : 170805000258
         * product_name : 红木桌子
         * product_image_url : http://51gongjiang.oss-cn-shanghai.aliyuncs.com/20170805141013..jpg
         * product_price_old : 100000.0
         * product_price_now : 90000.0
         * num_good : 0.0
         * num_collect : 0.0
         * state : 1.0
         */

        private String product_id;
        private String product_name;
        private String product_image_url;
        private double product_price_old;
        private double product_price_now;
        private double num_good;
        private double num_collect;
        private double state;

        public String getProduct_id() {
            return product_id;
        }

        public void setProduct_id(String product_id) {
            this.product_id = product_id;
        }

        public String getProduct_name() {
            return product_name;
        }

        public void setProduct_name(String product_name) {
            this.product_name = product_name;
        }

        public String getProduct_image_url() {
            return product_image_url;
        }

        public void setProduct_image_url(String product_image_url) {
            this.product_image_url = product_image_url;
        }

        public double getProduct_price_old() {
            return product_price_old;
        }

        public void setProduct_price_old(double product_price_old) {
            this.product_price_old = product_price_old;
        }

        public double getProduct_price_now() {
            return product_price_now;
        }

        public void setProduct_price_now(double product_price_now) {
            this.product_price_now = product_price_now;
        }

        public double getNum_good() {
            return num_good;
        }

        public void setNum_good(double num_good) {
            this.num_good = num_good;
        }

        public double getNum_collect() {
            return num_collect;
        }

        public void setNum_collect(double num_collect) {
            this.num_collect = num_collect;
        }

        public double getState() {
            return state;
        }

        public void setState(double state) {
            this.state = state;
        }

        @Override
        public String toString() {
            return "ListBean{" +
                    "product_id='" + product_id + '\'' +
                    ", product_name='" + product_name + '\'' +
                    ", product_image_url='" + product_image_url + '\'' +
                    ", product_price_old=" + product_price_old +
                    ", product_price_now=" + product_price_now +
                    ", num_good=" + num_good +
                    ", num_collect=" + num_collect +
                    ", state=" + state +
                    '}';
        }
    }
}
