package com.sky.driver.model;

import android.content.Context;

import com.google.gson.Gson;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.model.BaseModel;
import com.sky.app.library.utils.http.HttpUtils;
import com.sky.driver.api.ApiService;
import com.sky.driver.bean.BaseUser;
import com.sky.driver.bean.Purse;
import com.sky.driver.bean.UserBean;
import com.sky.driver.contract.UserContract;
import com.sky.driver.presenter.PurseActivityPresenter;

/**
 * 账户
 * Created by sky on 2017/2/18.
 */

public class PurseModel extends BaseModel<PurseActivityPresenter> implements UserContract.IPurseModel{

    public PurseModel(Context context, PurseActivityPresenter purseActivityPresenter){
        super(context, purseActivityPresenter);
    }

    @Override
    public void getMoney() {
        BaseUser baseUser = new BaseUser();
        baseUser.setUser_id(UserBean.getInstance().getCacheUid());
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class).requestQueryAccount(
                        UserBean.getInstance().getCacheUid(), baseUser),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        Purse purse = new Gson().fromJson(success, Purse.class);
                        getPresenter().showSuccess(purse.getBalance());
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }

                }));
    }
}
