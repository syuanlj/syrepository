package com.sky.driver.presenter;

import android.content.Context;

import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.presenter.BasePresenter;
import com.sky.driver.bean.MyOrderFilter;
import com.sky.driver.bean.OrderIn;
import com.sky.driver.bean.OrderList;
import com.sky.driver.contract.OrderContract;
import com.sky.driver.model.OrderModel;

/**
 * Created by sky on 2017/2/10.
 * 处理业务逻辑
 * 注册
 */

public class OrderFragmentPresenter extends BasePresenter<OrderContract.IOrderListView>
        implements OrderContract.IOrderListPresenter {

    private OrderContract.IOrderListModel iOrderListModel;
    private int page = 1;
    private float total = 1;
    private int rows = 20;

    /**
     * 构造
     */
    public OrderFragmentPresenter(Context context,
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
        orderFilter.setPage(page + 1);
        orderFilter.setRows(rows);
        iOrderListModel.getOrderList(orderFilter, Constants.ListStatus.LOADMORE);
    }

    @Override
    public void loadData(MyOrderFilter orderFilter) {
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
    }

    @Override
    public void loadMoreData(OrderList orderList) {
        this.page = orderList.getPage();
        this.total = orderList.getTotal();
        getView().getLoadMoreData(orderList.getList());
    }

    @Override
    public void showError(String error) {
        getView().showError(error);
    }

    @Override
    public void showSuccess(String msg) {
        getView().showSuccess(msg);
    }

    @Override
    public void finishOrder(OrderIn orderIn) {
        iOrderListModel.finishOrder(orderIn);
    }
}