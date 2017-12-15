package com.sky.driver.bean;

/**
 * Created by sky on 2017/3/18.
 */
public class AppKey {

    public static class HomePage{
        public final static String DRIVER_TAB_LABEL = "driver_tab_label";
        public final static int index = 0;
        public final static int order = 1;
        public final static int mime = 2;
    }

    public static class XmlName {
        /**
         * 系统配置
         */
        public static final String DRIVER_SETTING = "driver_xml_setting";

        /**
         * 统一缓存对象
         */
        public final static String DRIVER_HISTORY_DATA = "driver_history_data";

        /**
         * 用户缓存对象
         */
        public final static String DRIVER_CACHE_USER_INFO = "driver_cache_user_info";
    }

    public static class KeyName {

        /**
         * 第一次安装标示
         */
        public static final String ISFIRST_INSTALL = "isfirst_install_config";

    }
}
