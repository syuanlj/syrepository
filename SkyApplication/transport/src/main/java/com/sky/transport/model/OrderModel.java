package com.sky.transport.model;

import android.content.Context;

import com.google.gson.Gson;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.model.BaseModel;
import com.sky.app.library.utils.http.HttpUtils;
import com.sky.transport.api.ApiService;
import com.sky.transport.bean.MyOrderFilter;
import com.sky.transport.bean.MyOrderIn;
import com.sky.transport.bean.OrderList;
import com.sky.transport.bean.UserBean;
import com.sky.transport.contract.OrderContract;
import com.sky.transport.presenter.OrderPresenter;

/**
 * 订单
 * Created by sky on 2017/2/18.
 */

public class OrderModel extends BaseModel<OrderPresenter> implements OrderContract.IOrderListModel{

    public OrderModel(Context context, OrderPresenter orderFragmentPresenter){
        super(context, orderFragmentPresenter);
    }

    @Override
    public void getOrderList(MyOrderFilter orderFilter, final int flag) {
        orderFilter.setUser_id(UserBean.getInstance().getCacheUid());
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class).requestMyOrder(
                        UserBean.getInstance().getCacheUid(), orderFilter),
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
    public void finishOrder(MyOrderIn orderIn) {
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .requestFinishOrder(UserBean.getInstance().getCacheUid(), orderIn),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        getPresenter().showSuccess("完成订单");
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }));
    }

    @Override
    public void cancelOrder(MyOrderIn orderIn) {
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .requestCancelOrder(UserBean.getInstance().getCacheUid(), orderIn),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        getPresenter().showSuccess("取消订单");
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }));
    }
}
