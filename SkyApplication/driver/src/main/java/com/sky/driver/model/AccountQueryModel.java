package com.sky.driver.model;

import android.content.Context;

import com.google.gson.Gson;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.model.BaseModel;
import com.sky.app.library.utils.http.HttpUtils;
import com.sky.driver.api.ApiService;
import com.sky.driver.bean.ApplyRecordIn;
import com.sky.driver.bean.ApplyRecordOut;
import com.sky.driver.bean.UserBean;
import com.sky.driver.contract.AccountContract;
import com.sky.driver.presenter.AccountQueryFragmentPresenter;

/**
 * 账户中心
 * Created by sky on 2017/2/18.
 */

public class AccountQueryModel extends BaseModel<AccountQueryFragmentPresenter> implements AccountContract.IAccountQueryModel{

    public AccountQueryModel(Context context, AccountQueryFragmentPresenter accountQueryFragmentPresenter){
        super(context, accountQueryFragmentPresenter);
    }

    @Override
    public void queryRecord(ApplyRecordIn applyRecordIn, final int flag) {
        applyRecordIn.setUser_id(UserBean.getInstance().getCacheUid());
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .queryRecord(UserBean.getInstance().getCacheUid(), applyRecordIn),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        switch (flag){
                            case Constants.ListStatus.REFRESH:
                                getPresenter().refreshData(new Gson().fromJson(success, ApplyRecordOut.class));
                                break;
                            case Constants.ListStatus.LOADMORE:
                                getPresenter().loadMoreData(new Gson().fromJson(success, ApplyRecordOut.class));
                                break;
                        }
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }
        ));
    }
}
