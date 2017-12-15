package com.sky.transport.contract;

import com.sky.app.library.base.contract.IBaseModel;
import com.sky.app.library.base.contract.IBasePresenter;
import com.sky.app.library.base.contract.IBaseView;
import com.sky.transport.bean.AlipayOut;
import com.sky.transport.bean.CatchOrder;
import com.sky.transport.bean.MyOrderFilter;
import com.sky.transport.bean.MyOrderIn;
import com.sky.transport.bean.OrderDetail;
import com.sky.transport.bean.OrderIn;
import com.sky.transport.bean.OrderList;

import org.json.JSONObject;

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
     * 订单列表处理业务逻辑
     */
    public interface IOrderListPresenter extends IBasePresenter {
        void loadMore(MyOrderFilter orderFilter);
        void loadData(MyOrderFilter orderFilter);
        boolean hasMore();
        void refreshData(OrderList orderList);
        void loadMoreData(OrderList orderList);
        void showSuccess(String msg);
        void finishOrder(MyOrderIn orderIn);
        void cancelOrder(MyOrderIn orderIn);
    }

    /**
     * 订单列表网络请求
     */
    public interface IOrderListModel extends IBaseModel {
        void getOrderList(MyOrderFilter orderFilter, int flag);
        void finishOrder(MyOrderIn orderIn);
        void cancelOrder(MyOrderIn orderIn);
    }

    /**
     * 订单详情更新UI方法
     */
    public interface IOrderDetailView extends IBaseView {
        void showSuccess(OrderDetail orderDetail);
        void showOrderSuccess(String msg);
    }

    /**
     * 订单详情处理业务逻辑
     */
    public interface IOrderDetailPresenter extends IBasePresenter {
        void showSuccess(OrderDetail orderDetail);
        void showOrderSuccess(String msg);
        void loadOrderDetail(CatchOrder catchOrder);
    }

    /**
     * 订单详情网络请求
     */
    public interface IOrderDetailModel extends IBaseModel{
        void getOrderDetail(CatchOrder catchOrder);
    }

    /**
     * 确认订单
     */
    public interface IConfirmOrderView extends IBaseView{
        void responseOrder(CatchOrder catchOrder);
        void responseWeixinPay(JSONObject jsonObject);
        void responseAlipay(AlipayOut alipayOut);
    }

    /**
     * 确认订单
     */
    public interface IConfirmPresenter extends IBasePresenter{
        void requestOrder(OrderIn orderIn);
        void responseOrder(CatchOrder catchOrder);
        void weixinPay(CatchOrder catchOrder);
        void responseWeixinPay(JSONObject jsonObject);
        void alipay(CatchOrder catchOrder);
        void responseAlipay(AlipayOut alipayOut);
    }

    /**
     * 确认订单
     */
    public interface IConfirmModel extends IBaseModel{
        void order(OrderIn orderIn);
        void weixinPay(CatchOrder catchOrder);
        void alipay(CatchOrder catchOrder);
    }
}