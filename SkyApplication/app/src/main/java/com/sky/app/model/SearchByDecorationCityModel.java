package com.sky.app.model;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.sky.app.api.ApiService;
import com.sky.app.bean.BannerIn;
import com.sky.app.bean.BannerOut;
import com.sky.app.bean.DecorationCityList;
import com.sky.app.bean.DecorationTwoButique;
import com.sky.app.bean.Empty;
import com.sky.app.bean.SearchDecorationCity;
import com.sky.app.bean.SearchDecorationTwoLeft;
import com.sky.app.bean.SearchUser;
import com.sky.app.bean.UserBeanList;
import com.sky.app.contract.UserContract;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.model.BaseModel;
import com.sky.app.library.utils.http.HttpUtils;
import com.sky.app.presenter.SearchByDecorationCityPresenter;
import com.sky.app.ui.activity.search.RequestBoutiqueBean;


/**
 * 通过搜索工厂查询
 * Created by sky on 2017/2/18.
 */

public class SearchByDecorationCityModel extends BaseModel<SearchByDecorationCityPresenter> implements UserContract.ISearchByDecorationCityModel {


    public SearchByDecorationCityModel(Context context, SearchByDecorationCityPresenter presenter) {
        super(context, presenter);
    }

    @Override
    public void getData(String id) {
        SearchDecorationCity searchDecorationCity = new SearchDecorationCity();
        searchDecorationCity.setTwo_dir_id(id);
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .searchDecorationCity(searchDecorationCity),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        DecorationCityList categoryList = new Gson().fromJson(success, DecorationCityList.class);
                        getPresenter().success(categoryList);
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().getView().showError(error);
                    }
                }));
    }

    //装饰城中的二级分类
    @Override
    public void getSearchTwo() {
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class).searchDecorationTwo(new Empty()),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        SearchDecorationTwoLeft list = new Gson().fromJson(success, SearchDecorationTwoLeft.class);
                        getPresenter().showDecorationTwoLeft(list);

                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }));
    }



    //请求精品店的数据
    @Override
    public void requestBoutique(SearchUser searchUser) {
        RequestBoutiqueBean requestBoutiqueBean = new RequestBoutiqueBean();
        requestBoutiqueBean.setTp(searchUser.getTp());
        requestBoutiqueBean.setDecorative_id(searchUser.getDecorative_id());
        requestBoutiqueBean.setUser_level_recommend(searchUser.getUser_level_recommend());
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .searchUserDecoration(requestBoutiqueBean.getDecorative_id(),null,requestBoutiqueBean.getUser_level_recommend()),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        UserBeanList categoryList = new Gson().fromJson(success, UserBeanList.class);
                        getPresenter().userDataSuccess(categoryList);
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }));
    }

    @Override
    public void requestBanner(BannerIn bannerIn) {

        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class).requestBanner(bannerIn),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {

                        BannerOut bannerOut = new Gson().fromJson(success, BannerOut.class);
                        getPresenter().showBannersuccess(bannerOut);
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }));
    }


    @Override
    public void getUserData(SearchUser searchUser) {
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .searchUser(searchUser),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        UserBeanList categoryList = new Gson().fromJson(success, UserBeanList.class);
                        getPresenter().userDataSuccess(categoryList);
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }));


    }
}
