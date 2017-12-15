package com.sky.driver.model;

import android.content.Context;

import com.google.gson.Gson;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.model.BaseModel;
import com.sky.app.library.utils.http.HttpUtils;
import com.sky.driver.api.ApiService;
import com.sky.driver.bean.IdCardIn;
import com.sky.driver.bean.UserBean;
import com.sky.driver.contract.UserContract;
import com.sky.driver.presenter.IdCardActivityPresenter;

/**
 * 个人中心
 * Created by sky on 2017/2/18.
 */

public class IdCardModel extends BaseModel<IdCardActivityPresenter> implements UserContract.IIdCardModel{

    public IdCardModel(Context context, IdCardActivityPresenter idCardActivityPresenter){
        super(context, idCardActivityPresenter);
    }

    @Override
    public void idCard() {
        IdCardIn idCardIn = new IdCardIn();
        idCardIn.setUser_id(UserBean.getInstance().getCacheUid());
        idCardIn.setIdcard_front(UserBean.getInstance().getIdcard_front());
        idCardIn.setIdcard_back(UserBean.getInstance().getIdcard_back());
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .requestCard(UserBean.getInstance().getCacheUid(), idCardIn),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        UserBean.getInstance().setUserInfo(new Gson().fromJson(success, UserBean.class));
                        getPresenter().showSuccess();
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }));
    }
}
