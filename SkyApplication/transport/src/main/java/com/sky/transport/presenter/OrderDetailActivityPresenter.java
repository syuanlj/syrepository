package com.sky.transport.presenter;

import android.content.Context;

import com.sky.app.library.base.presenter.BasePresenter;
import com.sky.transport.bean.CatchOrder;
import com.sky.transport.bean.OrderDetail;
import com.sky.transport.bean.UserBean;
import com.sky.transport.contract.OrderContract;
import com.sky.transport.model.OrderDetailModel;

/**
 * Created by sky on 2017/2/10.
 * 处理业务逻辑
 * 订单详情
 */

public class OrderDetailActivityPresenter extends BasePresenter<OrderContract.IOrderDetailView>
        implements OrderContract.IOrderDetailPresenter {

    private OrderContract.IOrderDetailModel iOrderDetailModel;

    /**
     * 构造
     */
    public OrderDetailActivityPresenter(Context context,
                                        OrderContract.IOrderDetailView iOrderDetailView) {
        super(context, iOrderDetailView);
        this.iOrderDetailModel = new OrderDetailModel(context, this);
    }

    @Override
    public void destroy() {
        if (null != iOrderDetailModel){
            iOrderDetailModel.destroy();
        }
        super.destroy();
    }

    @Override
    public void showError(String error) {
        getView().showError(error);
        getView().hideProgress();
    }

    @Override
    public void showSuccess(OrderDetail orderDetail) {
        getView().showSuccess(orderDetail);
        getView().hideProgress();
    }

    @Override
    public void showOrderSuccess(String msg) {
        getView().showOrderSuccess(msg);
    }

    @Override
    public void loadOrderDetail(CatchOrder catchOrder) {
        getView().showProgress();
        catchOrder.setShop_user_id(UserBean.getInstance().getCacheUid());
        iOrderDetailModel.getOrderDetail(catchOrder);
    }
}