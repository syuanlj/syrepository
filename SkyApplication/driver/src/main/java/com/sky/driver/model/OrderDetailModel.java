package com.sky.driver.model;

import android.content.Context;

import com.google.gson.Gson;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.model.BaseModel;
import com.sky.app.library.utils.L;
import com.sky.app.library.utils.http.HttpUtils;
import com.sky.driver.api.ApiService;
import com.sky.driver.bean.CatchOrder;
import com.sky.driver.bean.OrderDetail;
import com.sky.driver.bean.UserBean;
import com.sky.driver.contract.OrderContract;
import com.sky.driver.presenter.OrderDetailActivityPresenter;

/**
 * 订单详情
 * Created by sky on 2017/2/18.
 */

public class OrderDetailModel extends BaseModel<OrderDetailActivityPresenter> implements OrderContract.IOrderDetailModel{

    public OrderDetailModel(Context context, OrderDetailActivityPresenter orderDetailActivityPresenter){
        super(context, orderDetailActivityPresenter);
    }

    @Override
    public void getOrderDetail(CatchOrder catchOrder) {
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .requestOrderDetail(UserBean.getInstance().getCacheUid(), catchOrder),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        L.msg("成功:" + success);
                        getPresenter().showSuccess(new Gson().fromJson(success, OrderDetail.class));
                    }

                    @Override
                    public void onFailure(String error) {
                        L.msg("失败:" + error);
                        getPresenter().showError(error);
                    }
                }));
    }

    @Override
    public void catchOrder(final CatchOrder catchOrder) {
        catchOrder.setShop_user_id(UserBean.getInstance().getCacheUid());
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .requestCatchOrder(UserBean.getInstance().getCacheUid(), catchOrder),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        getPresenter().showOrderSuccess("接单成功");
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }));
    }
}
