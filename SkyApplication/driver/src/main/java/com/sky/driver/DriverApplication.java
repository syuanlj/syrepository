package com.sky.driver;

import com.sky.app.library.base.MyApplication;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.utils.ImageHelper;

/**
 * Created by sky on 2017/2/9.
 */

public class DriverApplication extends MyApplication {

    private static DriverApplication instance = null;

    public synchronized static DriverApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        //图片缓存配置
        ImageHelper.getInstance().init(ImageHelper.getInstance().getImageLoaderConfiguration(this,
                Constants.Path.DRIVER_IMAGE_CACHE_PATH).build());
    }
}
