package com.sky.app;

import com.alibaba.mobileim.YWAPI;
import com.alibaba.wxlib.util.SysUtil;
import com.sky.app.bean.ProvCityArea;
import com.sky.app.library.base.MyApplication;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.utils.ImageHelper;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;

/**
 * Created by sky on 2017/2/9.
 */

public class MainApplication extends MyApplication {

    public static ProvCityArea provCityArea;

    @Override
    public void onCreate() {
        super.onCreate();

        //图片缓存配置
        ImageHelper.getInstance().init(ImageHelper.getInstance().getImageLoaderConfiguration(this,
                Constants.Path.APP_IMAGE_CACHE_PATH).build());
        Config.DEBUG = true;
//        PlatformConfig.setWeixin("wxc2cc69d02bdd7f2c", "6c29c4b61d4d27522b9d19019137b2e9");
//        PlatformConfig.setQQZone("1106153558", "nfqZog4vPTtvKbLV");

        initAlibaba();

    }

    private void initAlibaba() {
        final String APP_KEY = "24629506";

//必须首先执行这部分代码, 如果在":TCMSSevice"进程中，无需进行云旺（OpenIM）和app业务的初始化，以节省内存;
        SysUtil.setApplication(this);
        if (SysUtil.isTCMSServiceProcess(this)) {
            return;
        }
//第一个参数是Application Context
//这里的APP_KEY即应用创建时申请的APP_KEY，同时初始化必须是在主进程中
        if (SysUtil.isMainProcess()) {
            YWAPI.init(MainApplication.this, APP_KEY);
        }

    }




}
