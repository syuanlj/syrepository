package com.sky.driver.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.sky.app.library.base.ui.BaseViewActivity;
import com.sky.app.library.utils.AppUtils;
import com.sky.driver.R;
import com.sky.driver.adaptor.SimpleAccountFragmentPagerAdapter;
import com.sky.driver.bean.AccountMoney;
import com.sky.driver.contract.AccountContract;
import com.sky.driver.presenter.AccountActivityPresenter;

import butterknife.BindView;

/**
 * 账户中心【司机端】
 */
public class AccountActivity extends BaseViewActivity<AccountContract.IAccountPresenter>
    implements AccountContract.IAccountView{

    private SimpleAccountFragmentPagerAdapter pagerAdapter;

    @BindView(R.id.normal_toolbar)
    Toolbar toolbar;
    @BindView(R.id.driver_title)
    TextView driverTitle;

    @BindView(R.id.driver_account_list_vp)
    ViewPager viewPager;
    @BindView(R.id.driver_account_title_tl)
    TabLayout tabLayout;

    @BindView(R.id.ensure_money)
    TextView ensureMoney;
    @BindView(R.id.frozen_money)
    TextView frozenMoney;
    @BindView(R.id.balance)
    TextView balance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_account);
    }

    @Override
    protected AccountContract.IAccountPresenter presenter() {
        return new AccountActivityPresenter(context, this);
    }

    @Override
    protected void init() {
        driverTitle.setText(R.string.driver_account_string);
        toolbar.setNavigationIcon(R.mipmap.driver_back_arrow_icon);

        pagerAdapter = new SimpleAccountFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setOffscreenPageLimit(3);
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
        onRefresh();
    }

    @Override
    public void responseAccountMoney(AccountMoney accountMoney) {
        ensureMoney.setText(AppUtils.parseDouble(accountMoney.getEnsure_money()/100));
        frozenMoney.setText(AppUtils.parseDouble(accountMoney.getFrozen_money()/100));
        balance.setText(AppUtils.parseDouble(accountMoney.getBalance()/100));

        Intent a = new Intent();
        a.setAction("com.sky.driver.account");
        a.putExtra("yue", accountMoney.getBalance());
        sendBroadcast(a);
    }

    /**
     * 刷新数据
     */
    private void onRefresh(){
        getPresenter().requestAccountMoney();
    }
}
