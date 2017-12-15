package com.sky.driver.model;

import android.content.Context;

import com.sky.app.library.base.model.BaseModel;
import com.sky.driver.contract.MainContract;
import com.sky.driver.presenter.MainActivityPresenter;

/**
 * Created by sky on 2017/2/18.
 * 主页
 */

public class MainModel extends BaseModel<MainActivityPresenter> implements MainContract.IMainModel{

    public MainModel(Context context, MainActivityPresenter mainActivityPresenter){
        super(context, mainActivityPresenter);
    }

    @Override
    public void getData() {
//        HttpUtils.getInstance().exec(object, HttpUtils.getInstance().createApi(ApiService.class)
//                .requestLog(HttpData.getMap(data.toMap())), new IHttpCallBackListener(){
//                    @Override
//                    public void onSuccess(String success) {
//
//                    }
//
//                    @Override
//                    public void onFail(String error) {
//                    }
//                }, true);
    }
}
