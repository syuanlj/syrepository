package com.sky.transport.model;

import android.content.Context;

import com.google.gson.Gson;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.model.BaseModel;
import com.sky.app.library.utils.http.HttpUtils;
import com.sky.transport.api.ApiService;
import com.sky.transport.bean.CatchOrder;
import com.sky.transport.bean.OrderDetail;
import com.sky.transport.bean.UserBean;
import com.sky.transport.contract.OrderContract;
import com.sky.transport.presenter.OrderDetailActivityPresenter;

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
                        getPresenter().showSuccess(new Gson().fromJson(success, OrderDetail.class));
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }));
    }
}
