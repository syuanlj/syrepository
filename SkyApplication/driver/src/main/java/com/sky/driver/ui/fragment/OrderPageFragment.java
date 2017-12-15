package com.sky.driver.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.sky.app.library.base.adaptor.BaseRecyclerAdapter;
import com.sky.app.library.base.ui.BaseViewFragment;
import com.sky.app.library.component.DashlineItemDivider;
import com.sky.app.library.component.RecycleViewDivider;
import com.sky.app.library.component.recycler.interfaces.OnLoadMoreListener;
import com.sky.app.library.component.recycler.recyclerview.LuRecyclerView;
import com.sky.app.library.component.recycler.recyclerview.LuRecyclerViewAdapter;
import com.sky.app.library.utils.AppUtils;
import com.sky.app.library.utils.T;
import com.sky.driver.R;
import com.sky.driver.adaptor.MyOrderAdaptor;
import com.sky.driver.adaptor.SimpleOrderFragmentPagerAdapter;
import com.sky.driver.bean.MyOrderFilter;
import com.sky.driver.bean.OrderDetail;
import com.sky.driver.bean.OrderIn;
import com.sky.driver.contract.OrderContract;
import com.sky.driver.presenter.OrderFragmentPresenter;
import com.sky.driver.ui.activity.OrderDetailActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 订单Tab页
 * Created by Administrator on 2017/2/30.
 */
public class OrderPageFragment extends BaseViewFragment<OrderContract.IOrderListPresenter>
        implements OrderContract.IOrderListView, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.app_swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.driver_order_list_rv)
    LuRecyclerView mRecyclerView;

    MyOrderAdaptor myOrderAdaptor;                 //订单进行中／订单完成／订单取消

    List<OrderDetail> orderDetailList = new ArrayList<>();
    MyOrderFilter orderFilter = new MyOrderFilter();

    private int mPage = -1;
    private LuRecyclerViewAdapter mLuRecyclerViewAdapter = null;
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mPage = getArguments().getInt(SimpleOrderFragmentPagerAdapter.APP_ARG_PAGE);
        orderFilter.setState(String.valueOf(this.mPage));
    }
 
    @Override
    protected void init() {
        //设置刷新时动画的颜色，可以设置4个
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setProgressViewOffset(false, 0, AppUtils.dip2px(context, 48));
            mSwipeRefreshLayout.setColorSchemeResources(R.color.driver_colorPrimary);
            mSwipeRefreshLayout.setOnRefreshListener(this);
        }
    }

    @Override
    protected void initViewsAndEvents() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.setHasFixedSize(true);
        if (0 == mPage){
            mRecyclerView.addItemDecoration(
                    new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL, AppUtils.dip2px(context, 12),
                            AppUtils.getSystemColor(context, R.color.sky_color_f2f2f2)));
        }else{
            mRecyclerView.addItemDecoration(
                    new DashlineItemDivider(context,
                            AppUtils.dip2px(context, 12), AppUtils.dip2px(context, 12)));
        }
        myOrderAdaptor = new MyOrderAdaptor(context, mPage, orderDetailList);
        myOrderAdaptor.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View itemView, int pos) {
                Intent i = new Intent(context, OrderDetailActivity.class);
                i.putExtra("orderId", orderDetailList.get(pos).getOrder_id());
                startActivity(i);
            }

        });
        myOrderAdaptor.setOnClickListener(new BaseRecyclerAdapter.OnClickListener() {
            @Override
            public void onClick(View itemView, int pos) {
                switch (itemView.getId()){
                    case R.id.driver_finished_btn:
                        OrderIn orderIn = new OrderIn();
                        orderIn.setOrder_id(orderDetailList.get(pos).getOrder_id());
                        getPresenter().finishOrder(orderIn);
                        break;
                }
            }
        });
        mLuRecyclerViewAdapter = new LuRecyclerViewAdapter(myOrderAdaptor);
        mRecyclerView.setAdapter(mLuRecyclerViewAdapter);
        mRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (getPresenter().hasMore()){
                    getPresenter().loadMore(orderFilter);
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
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.driver_order_page;
    }

    @Override
    protected void onDestoryFragment() {

    }

    @Override
    protected OrderContract.IOrderListPresenter presenter() {
        return new OrderFragmentPresenter(context, this);
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        mRecyclerView.setRefreshing(true);
        getPresenter().loadData(orderFilter);
    }

    @Override
    public void getRefreshData(List<OrderDetail> t) {
        myOrderAdaptor.add(t);
        if(mSwipeRefreshLayout.isRefreshing()){
            mSwipeRefreshLayout.setRefreshing(false);
        }
        mRecyclerView.refreshComplete(20);
        mLuRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void getLoadMoreData(List<OrderDetail> t) {
        myOrderAdaptor.addAll(t);
        mSwipeRefreshLayout.setRefreshing(false);
        mRecyclerView.refreshComplete(20);
        mLuRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void showSuccess(String msg) {
        T.showShort(context, msg);
        onRefresh();
    }

    @Override
    public void showError(String error) {
        super.showError(error);
        T.showShort(context, error);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        onRefresh();
    }
}