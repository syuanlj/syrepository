package com.sky.driver.contract;

import com.sky.app.library.base.contract.IBaseModel;
import com.sky.app.library.base.contract.IBasePresenter;
import com.sky.app.library.base.contract.IBaseView;

/**
 * Created by sky on 2017/2/10.
 * 主页契约类
 */

public class MainContract {

    /**
     * 主页更新UI方法
     */
    public interface IMainView extends IBaseView {

    }

    /**
     * 主页处理业务逻辑
     */
    public interface IMainPresenter extends IBasePresenter {

    }

    /**
     * 网络请求
     */
    public interface IMainModel extends IBaseModel{
        void getData();
    }

}