package com.sky.driver.contract;

import com.sky.app.library.base.contract.IBaseModel;
import com.sky.app.library.base.contract.IBasePresenter;
import com.sky.app.library.base.contract.IBaseView;
import com.sky.driver.bean.CatchOrder;
import com.sky.driver.bean.LocationBean;
import com.sky.driver.bean.MyOrderFilter;
import com.sky.driver.bean.OrderDetail;
import com.sky.driver.bean.OrderFilter;
import com.sky.driver.bean.OrderIn;
import com.sky.driver.bean.OrderList;

import java.util.List;

/**
 * Created by sky on 2017/2/10.
 * 订单契约类
 */

public class OrderContract {

    /**
     * 订单列表更新UI方法
     */
    public interface IOrderListView extends IBaseView {
        void getRefreshData(List<OrderDetail> t);
        void getLoadMoreData(List<OrderDetail> t);
        void showSuccess(String msg);
    }

    /**
     * 订单详情更新UI方法
     */
    public interface IOrderDetailView extends IBaseView {
        void showSuccess(OrderDetail orderDetail);
        void showOrderSuccess(String msg);
    }

    /**
     * 首页订单列表更新UI方法
     */
    public interface IHomePageOrderListView extends IBaseView {
        void getRefreshData(List<OrderDetail> t);
        void getLoadMoreData(List<OrderDetail> t);
        void showSuccess(String msg);
    }

    /**
     * 订单列表处理业务逻辑
     */
    public interface IOrderListPresenter extends IBasePresenter {
        void loadMore(MyOrderFilter orderFilter);
        void loadData(MyOrderFilter orderFilter);
        boolean hasMore();
        void refreshData(OrderList orderList);
        void loadMoreData(OrderList orderList);
        void showSuccess(String msg);
        void finishOrder(OrderIn orderIn);
    }

    /**
     * 订单详情处理业务逻辑
     */
    public interface IOrderDetailPresenter extends IBasePresenter {
        void showSuccess(OrderDetail orderDetail);
        void showOrderSuccess(String msg);
        void loadOrderDetail(CatchOrder catchOrder);
        void catchOrder(CatchOrder catchOrder);
    }

    /**
     * 首页订单列表处理业务逻辑
     */
    public interface IHomePageOrderListPresenter extends IBasePresenter {
        void loadMore(OrderFilter orderFilter);
        void loadData(OrderFilter orderFilter);
        boolean hasMore();
        void refreshData(OrderList orderList);
        void loadMoreData(OrderList orderList);
        void catchOrder(CatchOrder catchOrder);
        void showSuccess(String msg);

        //定位
        void locationLoadMore(LocationBean locationBean);
        void locationLoadData(LocationBean locationBean);
        void locationRefreshData(OrderList orderList);
        void locationLoadMoreData(OrderList orderList);
    }

    /**
     * 订单列表网络请求
     */
    public interface IOrderListModel extends IBaseModel{
        void getOrderList(MyOrderFilter orderFilter, int flag);
        void finishOrder(OrderIn orderIn);
    }

    /**
     * 订单详情网络请求
     */
    public interface IOrderDetailModel extends IBaseModel{
        void getOrderDetail(CatchOrder catchOrder);
        void catchOrder(CatchOrder catchOrder);
    }

    /**
     * 首页订单列表网络请求
     */
    public interface IHomePageOrderListModel extends IBaseModel{
        void getOrderList(OrderFilter orderFilter, int flag);
        void catchOrder(CatchOrder catchOrder);
        void getLocationOrderList(LocationBean locationBean, int flag);
    }

}