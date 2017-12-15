package com.sky.transport.bean;

/**
 * Created by sky on 2017/3/18.
 */
public class AppKey {

    public static class XmlName {
        /**
         * 系统配置
         */
        public static final String TRANSPORT_SETTING = "transport_xml_setting";

        /**
         * 统一缓存对象
         */
        public final static String TRANSPORT_HISTORY_DATA = "transport_history_data";

        /**
         * 用户缓存对象
         */
        public final static String TRANSPORT_CACHE_USER_INFO = "transport_cache_user_info";
    }

    public static class KeyName {

        /**
         * 第一次安装标示
         */
        public static final String ISFIRST_INSTALL = "isfirst_install_config";

    }
}
