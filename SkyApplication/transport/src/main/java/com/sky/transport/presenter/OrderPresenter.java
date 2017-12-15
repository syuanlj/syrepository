package com.sky.transport.presenter;

import android.content.Context;

import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.presenter.BasePresenter;
import com.sky.transport.bean.MyOrderFilter;
import com.sky.transport.bean.MyOrderIn;
import com.sky.transport.bean.OrderList;
import com.sky.transport.contract.OrderContract;
import com.sky.transport.model.OrderModel;

/**
 * Created by sky on 2017/2/10.
 * 处理业务逻辑
 * 注册
 */

public class OrderPresenter extends BasePresenter<OrderContract.IOrderListView>
        implements OrderContract.IOrderListPresenter {

    private OrderContract.IOrderListModel iOrderListModel;
    private int page = 1;
    private float total = 1;
    private int rows = 20;

    /**
     * 构造
     */
    public OrderPresenter(Context context,
                          OrderContract.IOrderListView iOrderListView) {
        super(context, iOrderListView);
        this.iOrderListModel = new OrderModel(context, this);
    }

    @Override
    public void destroy() {
        if (null != iOrderListModel){
            iOrderListModel.destroy();
        }
        super.destroy();
    }

    @Override
    public void loadMore(MyOrderFilter orderFilter) {
        getView().showProgress();
        orderFilter.setPage(page + 1);
        orderFilter.setRows(rows);
        iOrderListModel.getOrderList(orderFilter, Constants.ListStatus.LOADMORE);
    }

    @Override
    public void loadData(MyOrderFilter orderFilter) {
        getView().showProgress();
        orderFilter.setPage(1);
        orderFilter.setRows(rows);
        iOrderListModel.getOrderList(orderFilter, Constants.ListStatus.REFRESH);
    }

    @Override
    public boolean hasMore() {
        if (Math.ceil(total/rows) > page){
            return true;
        }
        return false;
    }

    @Override
    public void refreshData(OrderList orderList) {
        this.page = orderList.getPage();
        this.total = orderList.getTotal();
        getView().getRefreshData(orderList.getList());
        getView().hideProgress();
    }

    @Override
    public void loadMoreData(OrderList orderList) {
        this.page = orderList.getPage();
        this.total = orderList.getTotal();
        getView().getLoadMoreData(orderList.getList());
        getView().hideProgress();
    }

    @Override
    public void showError(String error) {
        getView().showError(error);
        getView().hideProgress();
    }

    @Override
    public void showSuccess(String msg) {
        getView().showSuccess(msg);
        getView().hideProgress();
    }

    @Override
    public void finishOrder(MyOrderIn orderIn) {
        getView().showProgress();
        iOrderListModel.finishOrder(orderIn);
    }

    @Override
    public void cancelOrder(MyOrderIn orderIn) {
        getView().showProgress();
        iOrderListModel.cancelOrder(orderIn);
    }
}