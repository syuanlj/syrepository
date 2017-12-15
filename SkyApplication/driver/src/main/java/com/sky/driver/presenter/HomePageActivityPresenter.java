package com.sky.driver.presenter;

import android.content.Context;

import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.presenter.BasePresenter;
import com.sky.driver.bean.CatchOrder;
import com.sky.driver.bean.LocationBean;
import com.sky.driver.bean.OrderFilter;
import com.sky.driver.bean.OrderList;
import com.sky.driver.bean.UserBean;
import com.sky.driver.contract.OrderContract;
import com.sky.driver.model.HomePageOrderListModel;

/**
 * Created by sky on 2017/2/10.
 * 处理业务逻辑
 * 注册
 */

public class HomePageActivityPresenter extends BasePresenter<OrderContract.IHomePageOrderListView>
        implements OrderContract.IHomePageOrderListPresenter {

    private OrderContract.IHomePageOrderListModel iHomePageOrderListModel;
    private int page = 1;
    private float total = 1;
    private int rows = 20;

    /**
     * 构造
     */
    public HomePageActivityPresenter(Context context,
                                     OrderContract.IHomePageOrderListView iHomePageOrderListView) {
        super(context, iHomePageOrderListView);
        this.iHomePageOrderListModel = new HomePageOrderListModel(context, this);
    }

    @Override
    public void destroy() {
        if (null != iHomePageOrderListModel){
            iHomePageOrderListModel.destroy();
        }
        super.destroy();
    }

    @Override
    public void loadMore(OrderFilter orderFilter) {
        getView().showProgress();
        orderFilter.setPage(page + 1);
        orderFilter.setRows(rows);
        iHomePageOrderListModel.getOrderList(orderFilter, Constants.ListStatus.LOADMORE);
    }

    @Override
    public void loadData(OrderFilter orderFilter) {
        getView().showProgress();
        orderFilter.setPage(1);
        orderFilter.setRows(rows);
        iHomePageOrderListModel.getOrderList(orderFilter, Constants.ListStatus.REFRESH);
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
    public void catchOrder(CatchOrder catchOrder) {
        getView().showProgress();
        catchOrder.setShop_user_id(UserBean.getInstance().getCacheUid());
        iHomePageOrderListModel.catchOrder(catchOrder);
    }

    @Override
    public void showSuccess(String msg) {
        getView().showSuccess(msg);
        getView().hideProgress();
    }

    @Override
    public void locationLoadMore(LocationBean locationBean) {
        getView().showProgress();
        locationBean.setPage(page + 1);
        locationBean.setRows(rows);
        iHomePageOrderListModel.getLocationOrderList(locationBean, Constants.ListStatus.LOADMORE);
    }

    @Override
    public void locationLoadData(LocationBean locationBean) {
        getView().showProgress();
        locationBean.setPage(1);
        locationBean.setRows(rows);
        iHomePageOrderListModel.getLocationOrderList(locationBean, Constants.ListStatus.REFRESH);
    }

    @Override
    public void locationRefreshData(OrderList orderList) {
        this.page = orderList.getPage();
        this.total = orderList.getTotal();
        getView().getRefreshData(orderList.getList());
        getView().hideProgress();
    }

    @Override
    public void locationLoadMoreData(OrderList orderList) {
        this.page = orderList.getPage();
        this.total = orderList.getTotal();
        getView().getLoadMoreData(orderList.getList());
        getView().hideProgress();
    }
}