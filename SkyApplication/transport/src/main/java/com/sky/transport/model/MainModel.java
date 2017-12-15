package com.sky.transport.model;

import android.content.Context;

import com.google.gson.Gson;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.model.BaseModel;
import com.sky.app.library.utils.http.HttpUtils;
import com.sky.transport.api.ApiService;
import com.sky.transport.bean.BaseUser;
import com.sky.transport.bean.DistanceMoneyIn;
import com.sky.transport.bean.DistanceMoneyOut;
import com.sky.transport.bean.UserBean;
import com.sky.transport.contract.MainContract;
import com.sky.transport.presenter.MainActivityPresenter;

/**
 * Created by sky on 2017/2/18.
 */

public class MainModel extends BaseModel<MainActivityPresenter> implements MainContract.IMainModel{

    public MainModel(Context context, MainActivityPresenter demoActivityPresenter){
        super(context, demoActivityPresenter);
    }

    @Override
    public void money(DistanceMoneyIn distanceMoneyIn) {
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .requestDistanceMoney(distanceMoneyIn),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        getPresenter().responseDistanceMoney(new Gson().fromJson(success, DistanceMoneyOut.class));
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }));
    }

    @Override
    public void getUserData() {
        BaseUser baseUser = new BaseUser();
        baseUser.setUser_id(UserBean.getInstance().getCacheUid());
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .requestCenter(UserBean.getInstance().getCacheUid(), baseUser),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        UserBean userBean = new Gson().fromJson(success, UserBean.class);
                        UserBean.getInstance().setUserInfo(userBean);
                        getPresenter().updateInfo();
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }));
    }
}
