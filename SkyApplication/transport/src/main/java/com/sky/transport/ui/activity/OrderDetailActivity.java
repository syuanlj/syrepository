package com.sky.transport.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.sky.app.library.base.ui.BaseViewActivity;
import com.sky.app.library.utils.AppUtils;
import com.sky.app.library.utils.DialogUtils;
import com.sky.app.library.utils.T;
import com.sky.transport.R;
import com.sky.transport.bean.CatchOrder;
import com.sky.transport.bean.OrderDetail;
import com.sky.transport.contract.OrderContract;
import com.sky.transport.presenter.OrderDetailActivityPresenter;

import butterknife.BindView;

/**
 * 订单详情【货运端】
 */
public class OrderDetailActivity extends BaseViewActivity<OrderContract.IOrderDetailPresenter>
        implements OrderContract.IOrderDetailView{

    @BindView(R.id.normal_toolbar)
    Toolbar toolbar;
    @BindView(R.id.transport_title)
    TextView driverTitle;
    @BindView(R.id.driver_money_tv)
    TextView money;
    @BindView(R.id.driver_begin_addr_name_tv)
    TextView beginAddrName;
    @BindView(R.id.driver_end_addr_name_tv)
    TextView endAddrName;
    @BindView(R.id.driver_begin_addr_detail_tv)
    TextView beginDetail;
    @BindView(R.id.driver_end_addr_detail_tv)
    TextView endDetail;
    @BindView(R.id.driver_extra_tv)
    TextView extraTv;
    @BindView(R.id.driver_remark_tv)
    TextView remark;
    @BindView(R.id.driver_begin_time_tv)
    TextView beginTime;
    @BindView(R.id.driver_end_time_tv)
    TextView endTime;

    OrderDetail orderDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transport_order_detail);
    }

    @Override
    protected OrderContract.IOrderDetailPresenter presenter() {
        return new OrderDetailActivityPresenter(this, this);
    }

    @Override
    protected void init() {
        driverTitle.setText(R.string.transport_order_detail);
        toolbar.setNavigationIcon(R.mipmap.transport_back_arrow_icon);
    }

    @Override
    public void initViewsAndEvents() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void showError(String error) {
        T.showShort(context, error);
    }

    @Override
    protected void onDestoryActivity() {

    }

    @Override
    public void showSuccess(OrderDetail orderDetail) {
        this.orderDetail = orderDetail;
        money.setText("¥" + AppUtils.parseDouble(orderDetail.getMoney()/100));
        beginAddrName.setText(orderDetail.getFrom_address());
        endAddrName.setText(orderDetail.getTo_address());
        beginDetail.setText(orderDetail.getFrom_address());
        endDetail.setText(orderDetail.getTo_address());
        extraTv.setText("额外需求：" + orderDetail.getOther_desc());
        remark.setText("备注：" + orderDetail.getRemark());
        beginTime.setText("发布时间：" + orderDetail.getCreate_time());
        endTime.setText("截止时间：" + orderDetail.getCreate_time());
    }

    @Override
    public void showOrderSuccess(String msg) {
        T.showShort(context, msg);
    }

    /*@OnClick(R.id.driver_call_car_tv)
    void clickCallCar(){
        if (null != orderDetail){
            CatchOrder catchOrder = new CatchOrder();
            catchOrder.setOrder_id(orderDetail.getOrder_id());
            getPresenter().catchOrder(catchOrder);
        }else{
            T.showShort(context, "数据有误");
        }
    }*/

    @Override
    protected void onResume() {
        super.onResume();
        Bundle bundle = getIntent().getExtras();
        if (null != bundle){
            String orderId = bundle.getString("orderId");
            CatchOrder catchOrder = new CatchOrder();
            catchOrder.setOrder_id(orderId);
            getPresenter().loadOrderDetail(catchOrder);
        }
    }

    @Override
    public void showProgress() {
        super.showProgress();
        DialogUtils.showLoading(this);
    }

    @Override
    public void hideProgress() {
        super.hideProgress();
        DialogUtils.hideLoading();
    }
}