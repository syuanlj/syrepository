package com.sky.driver.model;

import android.content.Context;

import com.google.gson.Gson;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.model.BaseModel;
import com.sky.app.library.utils.http.HttpUtils;
import com.sky.driver.api.ApiService;
import com.sky.driver.bean.AccountMoney;
import com.sky.driver.bean.BaseUser;
import com.sky.driver.bean.UserBean;
import com.sky.driver.contract.AccountContract;
import com.sky.driver.presenter.AccountActivityPresenter;

/**
 * 账户中心
 * Created by sky on 2017/2/18.
 */

public class AccountModel extends BaseModel<AccountActivityPresenter> implements AccountContract.IAccountModel{

    public AccountModel(Context context, AccountActivityPresenter accountActivityPresenter){
        super(context, accountActivityPresenter);
    }

    @Override
    public void requestAccountMoney() {
        BaseUser baseUser = new BaseUser();
        baseUser.setUser_id(UserBean.getInstance().getCacheUid());
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .requestAccountMoney(UserBean.getInstance().getCacheUid(), baseUser),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        getPresenter().responseAccountMoney(new Gson().fromJson(success, AccountMoney.class));
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }
        ));
    }
}
