package com.sky.driver.model;

import android.content.Context;

import com.google.gson.Gson;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.model.BaseModel;
import com.sky.app.library.utils.http.HttpUtils;
import com.sky.driver.api.ApiService;
import com.sky.driver.bean.DriverIn;
import com.sky.driver.bean.UserBean;
import com.sky.driver.contract.UserContract;
import com.sky.driver.presenter.DriverActivityPresenter;

/**
 * 驾驶证
 * Created by sky on 2017/2/18.
 */

public class DriverModel extends BaseModel<DriverActivityPresenter> implements UserContract.IDriverModel{

    public DriverModel(Context context, DriverActivityPresenter driverActivityPresenter){
        super(context, driverActivityPresenter);
    }

    @Override
    public void driver() {
        DriverIn driverIn = new DriverIn();
        driverIn.setUser_id(UserBean.getInstance().getCacheUid());
        driverIn.setDriver_license(UserBean.getInstance().getDriver_license());
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .requestDriverAuth(UserBean.getInstance().getCacheUid(), driverIn),
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
