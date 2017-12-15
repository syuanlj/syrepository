package com.sky.driver.model;

import android.content.Context;

import com.google.gson.Gson;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.model.BaseModel;
import com.sky.app.library.utils.http.HttpUtils;
import com.sky.driver.api.ApiService;
import com.sky.driver.bean.MyOrderFilter;
import com.sky.driver.bean.OrderIn;
import com.sky.driver.bean.OrderList;
import com.sky.driver.bean.UserBean;
import com.sky.driver.contract.OrderContract;
import com.sky.driver.presenter.OrderFragmentPresenter;

/**
 * 订单
 * Created by sky on 2017/2/18.
 */

public class OrderModel extends BaseModel<OrderFragmentPresenter> implements OrderContract.IOrderListModel{

    public OrderModel(Context context, OrderFragmentPresenter orderFragmentPresenter){
        super(context, orderFragmentPresenter);
    }

    @Override
    public void getOrderList(MyOrderFilter orderFilter, final int flag) {
        orderFilter.setShop_user_id(UserBean.getInstance().getCacheUid());
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
    public void finishOrder(OrderIn orderIn) {
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
}
