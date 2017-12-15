package com.sky.transport.contract;

import com.sky.app.library.base.contract.IBaseModel;
import com.sky.app.library.base.contract.IBasePresenter;
import com.sky.app.library.base.contract.IBaseView;
import com.sky.transport.bean.DistanceMoneyIn;
import com.sky.transport.bean.DistanceMoneyOut;

/**
 * Created by sky on 2017/2/10.
 * 主页契约类
 */

public class MainContract {

    /**
     * 主页更新UI方法
     */
    public interface IMainView extends IBaseView {
        void responseMoney(DistanceMoneyOut distanceMoneyOut);
        void showUserInfo();
    }

    /**
     * 主页处理业务逻辑
     */
    public interface IMainPresenter extends IBasePresenter {
        void requestDistanceMoney(DistanceMoneyIn distanceMoneyIn);
        void responseDistanceMoney(DistanceMoneyOut distanceMoneyOut);
        void refreshUserInfo();
        void updateInfo();
    }

    /**
     * 网络请求
     */
    public interface IMainModel extends IBaseModel{
        void money(DistanceMoneyIn distanceMoneyIn);
        void getUserData();
    }

}