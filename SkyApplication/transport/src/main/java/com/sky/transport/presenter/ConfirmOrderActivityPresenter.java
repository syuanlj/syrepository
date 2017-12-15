package com.sky.transport.presenter;

import android.content.Context;

import com.sky.app.library.base.presenter.BasePresenter;
import com.sky.transport.bean.AlipayOut;
import com.sky.transport.bean.CatchOrder;
import com.sky.transport.bean.OrderIn;
import com.sky.transport.bean.UserBean;
import com.sky.transport.contract.OrderContract;
import com.sky.transport.model.ConfirmOrderModel;

import org.json.JSONObject;

/**
 * Created by sky on 2017/2/10.
 * 处理业务逻辑
 * 确认订单
 */

public class ConfirmOrderActivityPresenter extends BasePresenter<OrderContract.IConfirmOrderView>
        implements OrderContract.IConfirmPresenter {

    private OrderContract.IConfirmModel iConfirmModel;

    /**
     * 构造
     */
    public ConfirmOrderActivityPresenter(Context context,
                                         OrderContract.IConfirmOrderView iConfirmOrderView) {
        super(context, iConfirmOrderView);
        this.iConfirmModel = new ConfirmOrderModel(context, this);
    }

    @Override
    public void destroy() {
        if (null != iConfirmModel){
            iConfirmModel.destroy();
        }
        super.destroy();
    }

    @Override
    public void showError(String error) {
        getView().showError(error);
        getView().hideProgress();
    }

    @Override
    public void requestOrder(OrderIn orderIn) {
        getView().showProgress();
        orderIn.setUser_id(UserBean.getInstance().getCacheUid());
        iConfirmModel.order(orderIn);
    }

    @Override
    public void responseOrder(CatchOrder catchOrder) {
        getView().responseOrder(catchOrder);
        getView().hideProgress();
    }

    @Override
    public void weixinPay(CatchOrder catchOrder) {
        getView().showProgress();
        iConfirmModel.weixinPay(catchOrder);
    }

    @Override
    public void responseWeixinPay(JSONObject jsonObject) {
        getView().responseWeixinPay(jsonObject);
        getView().hideProgress();
    }

    @Override
    public void alipay(CatchOrder catchOrder) {
        getView().showProgress();
        iConfirmModel.alipay(catchOrder);
    }

    @Override
    public void responseAlipay(AlipayOut alipayOut) {
        getView().responseAlipay(alipayOut);
        getView().hideProgress();
    }
}