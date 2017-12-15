package com.sky.driver.presenter;

import android.content.Context;

import com.sky.app.library.base.presenter.BasePresenter;
import com.sky.driver.bean.ApplyAccountIn;
import com.sky.driver.contract.AccountContract;
import com.sky.driver.model.ApplyAccountModel;

/**
 * Created by sky on 2017/2/10.
 * 处理业务逻辑
 * 注册
 */

public class ApplyAccountFragmentPresenter extends BasePresenter<AccountContract.IApplyAccountView>
        implements AccountContract.IApplyAccountPresenter {

    private AccountContract.IApplyAccountModel iApplyAccountModel;

    /**
     * 构造
     */
    public ApplyAccountFragmentPresenter(Context context,
                                         AccountContract.IApplyAccountView iApplyAccountView) {
        super(context, iApplyAccountView);
        this.iApplyAccountModel = new ApplyAccountModel(context, this);
    }

    @Override
    public void destroy() {
        if (null != iApplyAccountModel){
            iApplyAccountModel.destroy();
        }
        super.destroy();
    }

    @Override
    public void applyAccount(ApplyAccountIn applyAccountIn) {
        iApplyAccountModel.applyAccount(applyAccountIn);
    }

    @Override
    public void responseApplyAccount(String msg) {
        getView().responseApplyAccount(msg);
    }

    @Override
    public void requestWithDraw() {
        getView().showProgress();
        iApplyAccountModel.requestWithDraw();
    }

    @Override
    public void responseWithDraw(double rate) {
        getView().responseWithDraw(rate);
        getView().hideProgress();
    }

    @Override
    public void showError(String error) {
        getView().showError(error);
    }
}