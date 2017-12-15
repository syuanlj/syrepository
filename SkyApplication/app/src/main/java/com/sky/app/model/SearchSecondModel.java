package com.sky.app.model;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.sky.app.api.ApiService;
import com.sky.app.bean.Category;
import com.sky.app.bean.CategoryList;
import com.sky.app.bean.SearchUser;
import com.sky.app.bean.UserBeanList;
import com.sky.app.contract.UserContract;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.model.BaseModel;
import com.sky.app.library.utils.http.HttpUtils;
import com.sky.app.presenter.SearchSecondPresent;

/**
 * Created by hongbang on 2017/5/7.
 */

public class SearchSecondModel extends BaseModel<SearchSecondPresent> implements UserContract.ISearchSecondModel {


    public SearchSecondModel(Context context, SearchSecondPresent presenter) {
        super(context, presenter);
    }

    @Override
    public void getData(final Category category) {
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .searchSecondCatogory(category),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        CategoryList categoryList = new Gson().fromJson(success, CategoryList.class);
                        getPresenter().success(categoryList);
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().getView().showError(error);
                    }
                }));
    }

    @Override
    public void getUserData(final SearchUser searchUser) {
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .searchUser(searchUser),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        UserBeanList userBeanList = new Gson().fromJson(success, UserBeanList.class);
                        getPresenter().userDataSuccess(userBeanList);
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }));
    }
}
