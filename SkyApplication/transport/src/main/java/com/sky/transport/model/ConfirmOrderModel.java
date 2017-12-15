package com.sky.transport.model;

import android.content.Context;

import com.google.gson.Gson;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.model.BaseModel;
import com.sky.app.library.utils.L;
import com.sky.app.library.utils.http.HttpUtils;
import com.sky.transport.api.ApiService;
import com.sky.transport.bean.AlipayOut;
import com.sky.transport.bean.CatchOrder;
import com.sky.transport.bean.OrderIn;
import com.sky.transport.bean.UserBean;
import com.sky.transport.contract.OrderContract;
import com.sky.transport.presenter.ConfirmOrderActivityPresenter;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 确认订单
 * Created by sky on 2017/2/18.
 */

public class ConfirmOrderModel extends BaseModel<ConfirmOrderActivityPresenter>
        implements OrderContract.IConfirmModel{

    public ConfirmOrderModel(Context context, ConfirmOrderActivityPresenter confirmOrderActivityPresenter){
        super(context, confirmOrderActivityPresenter);
    }

    @Override
    public void order(OrderIn orderIn) {
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .requestAddOrder(UserBean.getInstance().getCacheUid(), orderIn),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        getPresenter().responseOrder(new Gson().fromJson(success, CatchOrder.class));
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }));
    }

    @Override
    public void weixinPay(CatchOrder catchOrder) {
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .weixinPay(UserBean.getInstance().getCacheUid(), catchOrder),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        L.msg("微信返回:" + success);
                        try {
                            getPresenter().responseWeixinPay(new JSONObject(success));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }));
    }

    @Override
    public void alipay(CatchOrder catchOrder) {
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .alipay(UserBean.getInstance().getCacheUid(), catchOrder),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        getPresenter().responseAlipay(new Gson().fromJson(success, AlipayOut.class));
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }));
    }
}
