package com.sky.driver.presenter;

import android.content.Context;

import com.sky.app.library.base.presenter.BasePresenter;
import com.sky.driver.contract.UserContract;
import com.sky.driver.model.MineModel;

/**
 * Created by sky on 2017/2/10.
 * 处理业务逻辑
 * 个人中心
 */

public class MineActivityPresenter extends BasePresenter<UserContract.IMineView> implements UserContract.IMinePresenter {

    private UserContract.IMineModel iMineModel;

    /**
     * 构造
     */
    public MineActivityPresenter(Context context, UserContract.IMineView mIMineView) {
        super(context, mIMineView);
        this.iMineModel = new MineModel(context, this);
    }

    @Override
    public void refreshUserInfo() {
        iMineModel.getUserData();
    }

    @Override
    public void updateInfo() {
        getView().showUserInfo();
    }

    @Override
    public void showError(String msg) {
        getView().showError(msg);
    }

    @Override
    public void destroy() {
        if (null != iMineModel){
            iMineModel.destroy();
        }
        super.destroy();
    }
}