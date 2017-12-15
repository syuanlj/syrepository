package com.sky.driver.model;

import android.content.Context;

import com.google.gson.Gson;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.model.BaseModel;
import com.sky.app.library.utils.http.HttpUtils;
import com.sky.driver.api.ApiService;
import com.sky.driver.bean.BaseUser;
import com.sky.driver.bean.UserBean;
import com.sky.driver.contract.UserContract;
import com.sky.driver.presenter.MineActivityPresenter;

/**
 * Created by sky on 2017/2/18.
 * 个人中心
 */

public class MineModel extends BaseModel<MineActivityPresenter> implements UserContract.IMineModel{

    public MineModel(Context context, MineActivityPresenter mineActivityPresenter){
        super(context, mineActivityPresenter);
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
