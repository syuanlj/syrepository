package com.sky.transport.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.sky.app.library.base.contract.IBasePresenter;
import com.sky.app.library.base.ui.BaseViewActivity;
import com.sky.transport.R;
import com.sky.transport.adapter.SimpleOrderPagerAdapter;

import butterknife.BindView;

/**
 * 订单【货运端】
 */
public class OrderActivity extends BaseViewActivity{

    private SimpleOrderPagerAdapter pagerAdapter;

    @BindView(R.id.normal_toolbar)
    Toolbar toolbar;
    @BindView(R.id.transport_title)
    TextView transportTitle;
    @BindView(R.id.transport_order_list_vp)
    ViewPager viewPager;
    @BindView(R.id.transport_order_title_tl)
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transport_order);
    }

    @Override
    protected IBasePresenter presenter() {
        return null;
    }

    @Override
    protected void init() {
        transportTitle.setText(R.string.transport_order_string);
        toolbar.setNavigationIcon(R.mipmap.transport_back_arrow_icon);

        pagerAdapter = new SimpleOrderPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
    }

    @Override
    protected void onDestoryActivity() {

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
}
