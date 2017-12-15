package com.sky.driver.model;

import android.content.Context;

import com.google.gson.Gson;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.model.BaseModel;
import com.sky.app.library.utils.http.HttpUtils;
import com.sky.driver.api.ApiService;
import com.sky.driver.bean.CatchOrder;
import com.sky.driver.bean.LocationBean;
import com.sky.driver.bean.OrderFilter;
import com.sky.driver.bean.OrderList;
import com.sky.driver.bean.UserBean;
import com.sky.driver.contract.OrderContract;
import com.sky.driver.presenter.HomePageActivityPresenter;

/**
 * 首页订单列表
 * Created by sky on 2017/2/18.
 */

public class HomePageOrderListModel extends BaseModel<HomePageActivityPresenter>
        implements OrderContract.IHomePageOrderListModel{

    public HomePageOrderListModel(Context context, HomePageActivityPresenter homePageActivityPresenter){
        super(context, homePageActivityPresenter);
    }

    @Override
    public void getOrderList(OrderFilter orderFilter, final int flag) {
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .requestWaitOrderList(UserBean.getInstance().getCacheUid(), orderFilter),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        if (flag == Constants.ListStatus.REFRESH){//下拉刷新
                            getPresenter().refreshData(new Gson().fromJson(success, OrderList.class));
                        }else{
                            getPresenter().loadMoreData(new Gson().fromJson(success, OrderList.class));
                        }
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }));
    }

    @Override
    public void catchOrder(CatchOrder catchOrder) {
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .requestCatchOrder(UserBean.getInstance().getCacheUid(), catchOrder),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        getPresenter().showSuccess("接单成功");
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }));
    }

    @Override
    public void getLocationOrderList(LocationBean locationBean, final int flag) {
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .getLocation(UserBean.getInstance().getCacheUid(), locationBean),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        if (flag == Constants.ListStatus.REFRESH){//下拉刷新
                            getPresenter().refreshData(new Gson().fromJson(success, OrderList.class));
                        }else{
                            getPresenter().loadMoreData(new Gson().fromJson(success, OrderList.class));
                        }
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }));
    }
}
