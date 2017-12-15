package com.sky.driver.model;

import android.content.Context;

import com.google.gson.Gson;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.model.BaseModel;
import com.sky.app.library.utils.http.HttpUtils;
import com.sky.driver.api.ApiService;
import com.sky.driver.bean.ForgetIn;
import com.sky.driver.bean.UserBean;
import com.sky.driver.contract.UserContract;
import com.sky.driver.presenter.ForgetActivityPresenter;

/**
 * 忘记密码
 * Created by sky on 2017/2/18.
 */

public class ForgetModel extends BaseModel<ForgetActivityPresenter> implements UserContract.IForgetModel{

    public ForgetModel(Context context, ForgetActivityPresenter forgetActivityPresenter){
        super(context, forgetActivityPresenter);
    }

    @Override
    public void forget(ForgetIn forgetIn) {
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class).requestForget(forgetIn),
                new HttpUtils.IHttpCallBackListener() {

                    @Override
                    public void onSuccess(String success) {
                        UserBean info = new Gson().fromJson(success, UserBean.class);
                        UserBean.getInstance().setUserInfo(info);
                        UserBean.getInstance().setUserCache(info);
                        getPresenter().showSuccess();
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }));
    }
}
