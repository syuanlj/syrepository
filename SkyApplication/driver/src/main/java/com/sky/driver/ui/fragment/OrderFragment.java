package com.sky.driver.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.sky.app.library.base.contract.IBasePresenter;
import com.sky.app.library.base.ui.BaseViewFragment;
import com.sky.driver.R;
import com.sky.driver.adaptor.SimpleOrderFragmentPagerAdapter;

import butterknife.BindView;

/**
 * 订单页
 * Created by Administrator on 2017/2/30.
 */
public class OrderFragment extends BaseViewFragment {

    private SimpleOrderFragmentPagerAdapter pagerAdapter;

    @BindView(R.id.driver_order_list_vp)
    ViewPager viewPager;
    @BindView(R.id.driver_order_title_tl)
    TabLayout tabLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
 
    @Override
    protected void init() {
        pagerAdapter = new SimpleOrderFragmentPagerAdapter(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
    }

    @Override
    protected void initViewsAndEvents() {
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.driver_order;
    }

    @Override
    protected void onDestoryFragment() {

    }

    @Override
    protected IBasePresenter presenter() {
        return null;
    }

}