package com.sky.transport.presenter;

import android.content.Context;

import com.sky.app.library.base.presenter.BasePresenter;
import com.sky.transport.bean.ForgetIn;
import com.sky.transport.bean.UserBean;
import com.sky.transport.contract.UserContract;
import com.sky.transport.model.RegisterModel;

/**
 * Created by sky on 2017/2/10.
 * 处理业务逻辑
 * 注册
 */

public class RegisterActivityPresenter extends BasePresenter<UserContract.IRegisterView> implements UserContract.IRegisterPresenter {

    private UserContract.IRegisterModel iRegisterModel;

    /**
     * 构造
     */
    public RegisterActivityPresenter(Context context,
                                     UserContract.IRegisterView mIRegisterView) {
        super(context, mIRegisterView);
        this.iRegisterModel = new RegisterModel(context, this);
    }

    @Override
    public void destroy() {
        if (null != iRegisterModel){
            iRegisterModel.destroy();
        }
        super.destroy();
    }

    @Override
    public void register(ForgetIn forgetIn) {
        getView().showProgress();
        iRegisterModel.register(forgetIn);
    }

    @Override
    public void refreshData(UserBean userBean) {
        UserBean.getInstance().clearCode();
        UserBean.getInstance().setUserInfo(userBean);
        UserBean.getInstance().setUserCache(userBean);
        getView().showUserInfo();
        getView().hideProgress();
    }

    @Override
    public void showError(String msg) {
        getView().showError(msg);
        getView().hideProgress();
    }
}