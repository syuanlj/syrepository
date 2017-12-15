package com.sky.shop.presenter.activity;

import android.content.Context;

import com.sky.app.library.base.presenter.BasePresenter;
import com.sky.shop.bean.ForgetIn;
import com.sky.shop.bean.UserBean;
import com.sky.shop.contract.UserContract;
import com.sky.shop.model.RegisterModel;

/**
 * Created by sky on 2017/2/10.
 * 处理业务逻辑
 * 注册
 */

public class RegisterActivityPresenter extends BasePresenter<UserContract.IRegisterView>
        implements UserContract.IRegisterPresenter {

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
        iRegisterModel.register(forgetIn);
    }

    @Override
    public void showSuccess() {
        //清理缓存
        UserBean.getInstance().clearCode();
        getView().showResult();
    }

    @Override
    public void showError(String msg) {
        getView().showError(msg);
    }
}