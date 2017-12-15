package com.sky.driver.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.sky.app.library.base.adaptor.BaseRecyclerAdapter;
import com.sky.app.library.base.ui.BaseViewFragment;
import com.sky.app.library.component.DashlineItemDivider;
import com.sky.app.library.component.recycler.interfaces.OnLoadMoreListener;
import com.sky.app.library.component.recycler.recyclerview.LuRecyclerView;
import com.sky.app.library.component.recycler.recyclerview.LuRecyclerViewAdapter;
import com.sky.app.library.utils.AppUtils;
import com.sky.app.library.utils.DialogUtils;
import com.sky.app.library.utils.L;
import com.sky.app.library.utils.T;
import com.sky.driver.R;
import com.sky.driver.adaptor.HomePageOrderAdaptor;
import com.sky.driver.bean.CatchOrder;
import com.sky.driver.bean.LocationBean;
import com.sky.driver.bean.OrderDetail;
import com.sky.driver.bean.OrderFilter;
import com.sky.driver.component.DropdownButton;
import com.sky.driver.component.DropdownButtonsController;
import com.sky.driver.contract.OrderContract;
import com.sky.driver.presenter.HomePageActivityPresenter;
import com.sky.driver.ui.activity.OrderDetailActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 主页
 * Created by Administrator on 2017/2/30.
 */
public class HomePageFragment extends BaseViewFragment<OrderContract.IHomePageOrderListPresenter>
    implements OrderContract.IHomePageOrderListView, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.driver_bg_layout)
    View driverBgLayout;                            //背景
    @BindView(R.id.driver_default_sort_btn)
    DropdownButton driverDefaultSortBtn;            //排序按钮
    @BindView(R.id.driver_distance_sort_btn)
    DropdownButton driverDistanceSortBtn;           //距离筛选按钮
    @BindView(R.id.driver_filter_btn)
    DropdownButton driverFilterBtn;                 //筛选按钮
    @BindView(R.id.driver_list_sort_dv)
    ListView driverListSortDv;                      //排序列表
    @BindView(R.id.driver_filter_layout)
    RelativeLayout driverFilterLayout;              //筛选下拉框
//    @BindView(R.id.driver_catch_order_btn)
//    Button driverCatchOrderBtn;                     //开始下单
    @BindView(R.id.driver_swipe)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.driver_order_list_rv)
    LuRecyclerView mRecyclerView;
    @BindView(R.id.driver_price_begin_et)
    EditText beginPrice;
    @BindView(R.id.driver_price_end_et)
    EditText endPrice;
    HomePageOrderAdaptor homePageOrderAdaptor;
    OrderFilter orderFilter;

    List<OrderDetail> orderDetailList = new ArrayList<>();
    private LuRecyclerViewAdapter mLuRecyclerViewAdapter = null;

//    private boolean flag = false;//定时开关

    //定位
    LocationBean locationBean = new LocationBean();
    private LocationClient locationClient = null;
    private static final int UPDATE_TIME = 5000;
    private double longitude;
    private double latitude;
    private int tag = -1;
    /**
     * 定时刷新列表
     */
    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            onRefresh();
        }

    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
 
    @Override
    protected void init() {
        //筛选条件
        orderFilter = new OrderFilter();

        //初始化UI
        DropdownButtonsController.getInstance().setOnClickItem(new DropdownButtonsController.OnClickItem() {
            @Override
            public void item(String[] arrays, int position) {
                orderFilter.setMoney_end("");
                orderFilter.setMoney_start("");
                switch (position){
                    case 1:
                        orderFilter.setOrderBy("money desc");
                        break;
                    case 2:
                        orderFilter.setOrderBy("money asc");
                        break;
                    default:
                        orderFilter.setOrderBy("");
                        break;
                }
                getPresenter().loadData(orderFilter);
                DropdownButtonsController.getInstance().hide();
                DropdownButtonsController.getInstance().setSortTxt(arrays[position]);
            }
        });
        DropdownButtonsController.getInstance().setDriverBgLayout(driverBgLayout);
        DropdownButtonsController.getInstance().setDriverDefaultSortBtn(driverDefaultSortBtn);
        DropdownButtonsController.getInstance().setDriverDistanceSortBtn(driverDistanceSortBtn);
        DropdownButtonsController.getInstance().setDriverFilterBtn(driverFilterBtn);
        DropdownButtonsController.getInstance().setDriverFilterLayout(driverFilterLayout);
        DropdownButtonsController.getInstance().setDriverListSortDv(driverListSortDv);
        DropdownButtonsController.getInstance().init(context);

        //设置刷新时动画的颜色，可以设置4个
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setProgressViewOffset(false, 0, AppUtils.dip2px(context, 48));
            mSwipeRefreshLayout.setColorSchemeResources(R.color.driver_colorPrimary);
            mSwipeRefreshLayout.setOnRefreshListener(this);
        }
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.addItemDecoration(new DashlineItemDivider(context,
                AppUtils.dip2px(context, 12), AppUtils.dip2px(context, 12)));
        mRecyclerView.setHasFixedSize(true);
    }

    @Override
    protected void initViewsAndEvents() {
        homePageOrderAdaptor = new HomePageOrderAdaptor(context, orderDetailList);
        homePageOrderAdaptor.setOnClickListener(new HomePageOrderAdaptor.OnClickListener() {
            @Override
            public void onClick(View itemView, int pos) {
                CatchOrder catchOrder = new CatchOrder();
                catchOrder.setOrder_id(orderDetailList.get(pos).getOrder_id());
                getPresenter().catchOrder(catchOrder);
            }
        });
        homePageOrderAdaptor.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int pos) {
                Intent i = new Intent(context, OrderDetailActivity.class);
                i.putExtra("orderId", orderDetailList.get(pos).getOrder_id());
                startActivity(i);
            }
        });
        mLuRecyclerViewAdapter = new LuRecyclerViewAdapter(homePageOrderAdaptor);
        mRecyclerView.setAdapter(mLuRecyclerViewAdapter);
        mRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (getPresenter().hasMore()){
                    if (tag == 1){
                        getPresenter().locationLoadMore(locationBean);
                    }else{
                        getPresenter().loadMore(orderFilter);
                    }
                } else {
                    mRecyclerView.setNoMore(true);
                }
            }
        });

        //设置底部加载颜色
        mRecyclerView.setFooterViewColor(R.color.main_colorAccent, R.color.main_colorAccent ,android.R.color.white);
        //设置底部加载文字提示
        mRecyclerView.setFooterViewHint("拼命加载中", "已经全部加载完", "网络不给力啊，点击再试一次吧");
        onRefresh();

        //定位
        locationClient = new LocationClient(getActivity());
        //设置定位条件
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);        //是否打开GPS
        option.setCoorType("bd09ll");       //设置返回值的坐标类型。
        option.setPriority(LocationClientOption.NetWorkFirst);  //设置定位优先级
        option.setProdName("LocationDemo"); //设置产品线名称。强烈建议您使用自定义的产品线名称，方便我们以后为您提供更高效准确的定位服务。
        option.setScanSpan(UPDATE_TIME);    //设置定时定位的时间间隔。单位毫秒
        locationClient.setLocOption(option);
        //注册位置监听器
        locationClient.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation location) {
                if (location == null) {
                    return;
                }
                longitude = location.getLongitude();
                latitude = location.getLatitude();
                locationBean.setCur_longitude(longitude);
                locationBean.setCur_latitude(latitude);
                L.msg("定位：[latitude]" + longitude + "===>[longitude]" + latitude);
            }
        });
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.driver_home_page;
    }

    @Override
    protected void onDestoryFragment() {
        if (locationClient != null && locationClient.isStarted()) {
            locationClient.stop();
            locationClient = null;
        }
    }

    @Override
    protected OrderContract.IHomePageOrderListPresenter presenter() {
        return new HomePageActivityPresenter(context, this);
    }

    @OnClick(R.id.driver_bg_layout)
    public void dismissBackground(){
        DropdownButtonsController.getInstance().hide();
    }

    @OnClick(R.id.driver_default_sort_btn)
    public void defaultSort(){
        tag = 0;
        DropdownButtonsController.getInstance().show(0);
    }

    @OnClick(R.id.driver_distance_sort_btn)
    public void distanceSort(){
        tag = 1;
        DropdownButtonsController.getInstance().show(1);
//        orderFilter.setOrderBy("distance asc");
//        orderFilter.setMoney_end("");
//        orderFilter.setMoney_start("");
//        getPresenter().loadData(orderFilter);
        getPresenter().locationLoadData(locationBean);
    }

    @OnClick(R.id.driver_filter_btn)
    public void filterSort(){
        tag = 2;
        DropdownButtonsController.getInstance().show(2);
    }

//    @OnClick(R.id.driver_catch_order_btn)
//    public void catchOrder(){
//        if (!flag){
//            TimeTaskUtils.getInstance().startTimer(handler);
//            driverCatchOrderBtn.setText("暂不抢单");
//        }else{
//            TimeTaskUtils.getInstance().stopTimer();
//            driverCatchOrderBtn.setText("开始抢单");
//        }
//        flag = !flag;
//    }

    @Override
    public void getRefreshData(List<OrderDetail> t) {
        homePageOrderAdaptor.add(t);
        if(mSwipeRefreshLayout.isRefreshing()){
            mSwipeRefreshLayout.setRefreshing(false);
        }
        mRecyclerView.refreshComplete(20);
        mLuRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void getLoadMoreData(List<OrderDetail> t) {
        homePageOrderAdaptor.addAll(t);
        mSwipeRefreshLayout.setRefreshing(false);
        mRecyclerView.refreshComplete(20);
        mLuRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void showSuccess(String msg) {
        T.showShort(context, msg);
        onRefresh();
    }

    @OnClick(R.id.driver_reset_tv)
    void clickReset(){
        beginPrice.setText("");
        endPrice.setText("");
    }

    @OnClick(R.id.driver_comfirm_tv)
    void clickConfirm(){
        if (TextUtils.isEmpty(beginPrice.getText().toString()) && TextUtils.isEmpty(endPrice.getText().toString())){
            T.showShort(context, "必须填写其中的一个金额");
            return;
        }
        orderFilter.setMoney_start(beginPrice.getText().toString());
        orderFilter.setMoney_end(endPrice.getText().toString());
        orderFilter.setOrderBy("");
        getPresenter().loadData(orderFilter);
        DropdownButtonsController.getInstance().hide();
        tag = 2;
    }

    @Override
    public void showError(String error) {
        super.showError(error);
        T.showShort(context, error);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        mRecyclerView.setRefreshing(true);
        if (tag == 1){ //距离
            getPresenter().locationLoadData(locationBean);
            return;
        }
        getPresenter().loadData(orderFilter);
    }

    @Override
    public void onResume() {
        super.onResume();
        onRefresh();

        if (locationClient == null) {
            return;
        }
        locationClient.start();
        locationClient.requestLocation();
    }

    @Override
    public void showProgress() {
        super.showProgress();
        DialogUtils.showLoading(getActivity());
    }

    @Override
    public void hideProgress() {
        super.hideProgress();
        DialogUtils.hideLoading();
    }
}