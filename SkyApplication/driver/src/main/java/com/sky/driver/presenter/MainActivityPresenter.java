package com.sky.driver.presenter;

import android.content.Context;

import com.sky.app.library.base.presenter.BasePresenter;
import com.sky.driver.contract.MainContract;
import com.sky.driver.model.MainModel;

/**
 * 主页
 * Created by sky on 2017/2/10.
 * 处理业务逻辑
 */

public class MainActivityPresenter extends BasePresenter<MainContract.IMainView> implements MainContract.IMainPresenter {

    private MainContract.IMainModel iMainModel;

    /**
     * 构造
     */
    public MainActivityPresenter(Context context, MainContract.IMainView mIMainView) {
        super(context, mIMainView);
        this.iMainModel = new MainModel(context, this);
    }

    @Override
    public void destroy() {
        if (null != iMainModel){
            iMainModel.destroy();
        }
        super.destroy();
    }
}