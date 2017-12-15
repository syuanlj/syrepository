package com.sky.app.presenter;

import android.content.Context;

import com.sky.app.bean.Category;
import com.sky.app.bean.CategoryList;
import com.sky.app.bean.SearchUser;
import com.sky.app.bean.UserBeanList;
import com.sky.app.contract.UserContract;
import com.sky.app.library.base.presenter.BasePresenter;
import com.sky.app.model.SearchSecondModel;

/**
 * Created by hongbang on 2017/5/7.
 */

public class SearchSecondPresent extends BasePresenter<UserContract.ISearchSecond>  implements  UserContract.ISearchSecondPresent {

    private UserContract.ISearchSecondModel  iSearchSecondModel;

    public SearchSecondPresent(Context context, UserContract.ISearchSecond view) {
        super(context, view);
        iSearchSecondModel=new SearchSecondModel(context,this);

    }

    @Override
    public void getData(Category category) {
        iSearchSecondModel.getData(category);
    }

    @Override
    public void success(CategoryList categoryList) {
            getView().success(categoryList);
    }

    @Override
    public void getUserData(SearchUser searchUser) {
        getView().showProgress();
        iSearchSecondModel.getUserData(searchUser);

    }

    @Override
    public void userDataSuccess(UserBeanList categoryList) {
        getView().userDataSuccess(categoryList);
        getView().hideProgress();
    }

    @Override
    public void showError(String error) {
        super.showError(error);
        getView().showError(error);
        getView().hideProgress();
    }
}
