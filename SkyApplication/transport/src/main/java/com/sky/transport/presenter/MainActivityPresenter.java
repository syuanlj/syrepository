package com.sky.transport.presenter;

import android.content.Context;

import com.sky.app.library.base.presenter.BasePresenter;
import com.sky.transport.bean.DistanceMoneyIn;
import com.sky.transport.bean.DistanceMoneyOut;
import com.sky.transport.contract.MainContract;
import com.sky.transport.model.MainModel;

/**
 * Created by sky on 2017/2/10.
 * 处理业务逻辑
 */

public class MainActivityPresenter extends BasePresenter<MainContract.IMainView> implements MainContract.IMainPresenter {

    private MainContract.IMainModel iMainModel;

    /**
     * 构造
     */
    public MainActivityPresenter(Context context, MainContract.IMainView mIDemoView) {
        super(context, mIDemoView);
        this.iMainModel = new MainModel(context, this);
    }

    @Override
    public void destroy() {
        super.destroy();
        if (null != iMainModel){
            iMainModel.destroy();
        }
    }

    @Override
    public void showError(String error) {
        getView().showError(error);
        getView().hideProgress();
    }

    @Override
    public void requestDistanceMoney(DistanceMoneyIn distanceMoneyIn) {
        getView().showProgress();
        iMainModel.money(distanceMoneyIn);
    }

    @Override
    public void responseDistanceMoney(DistanceMoneyOut distanceMoneyOut) {
        getView().responseMoney(distanceMoneyOut);
        getView().hideProgress();
    }

    @Override
    public void refreshUserInfo() {
        getView().showProgress();
        iMainModel.getUserData();
    }

    @Override
    public void updateInfo() {
        getView().showUserInfo();
        getView().hideProgress();
    }
}