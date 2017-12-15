package com.sky.transport.presenter;

import android.content.Context;

import com.sky.app.library.base.presenter.BasePresenter;
import com.sky.transport.contract.UserContract;
import com.sky.transport.model.PurseModel;

/**
 * Created by sky on 2017/2/10.
 * 处理业务逻辑
 * 账户
 */

public class PurseActivityPresenter extends BasePresenter<UserContract.IPurseView> implements UserContract.IPursePresenter {

    private UserContract.IPurseModel iPurseModel;

    /**
     * 构造
     */
    public PurseActivityPresenter(Context context,
                                  UserContract.IPurseView iPurseView) {
        super(context, iPurseView);
        this.iPurseModel = new PurseModel(context, this);
    }

    @Override
    public void destroy() {
        if (null != iPurseModel){
            iPurseModel.destroy();
        }
        super.destroy();
    }

    @Override
    public void getMoney() {
        getView().showProgress();
        iPurseModel.getMoney();
    }

    @Override
    public void showSuccess(double money) {
        getView().refresh(money);
        getView().hideProgress();
    }

    @Override
    public void showError(String msg) {
        getView().showError(msg);
        getView().hideProgress();
    }
}