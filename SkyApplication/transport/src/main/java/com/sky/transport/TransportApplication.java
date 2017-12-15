package com.sky.transport;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;
import com.sky.app.library.base.MyApplication;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.utils.ImageHelper;

/**
 * Created by sky on 2017/2/9.
 */

public class TransportApplication extends MyApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        //图片缓存配置
        ImageHelper.getInstance().init(ImageHelper.getInstance().getImageLoaderConfiguration(this,
                Constants.Path.TRANSPORT_IMAGE_CACHE_PATH).build());

        //地图初始化
        SDKInitializer.initialize(this);
    }

    public Application getApp(){
        return (Application) getApplicationContext();
    }
}
