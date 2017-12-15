package com.sky.transport.ui.activity.user;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.sky.app.library.base.ui.BaseViewActivity;
import com.sky.app.library.utils.AppUtils;
import com.sky.app.library.utils.DialogUtils;
import com.sky.transport.R;
import com.sky.transport.contract.UserContract;
import com.sky.transport.presenter.PurseActivityPresenter;

import butterknife.BindView;

/**
 * 钱包【货运端】
 */
public class PurseActivity extends BaseViewActivity<UserContract.IPursePresenter> implements UserContract.IPurseView{

    @BindView(R.id.normal_toolbar)
    Toolbar toolbar;
    @BindView(R.id.transport_title)
    TextView transportTitle;
    @BindView(R.id.transport_remain_money)
    TextView remainMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transport_purse);
    }

    @Override
    protected void init() {
        transportTitle.setText(R.string.transport_sider_purse);
        toolbar.setNavigationIcon(R.mipmap.transport_back_arrow_icon);
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

    @Override
    protected void onResume() {
        super.onResume();
        getPresenter().getMoney();
    }

    @Override
    protected UserContract.IPursePresenter presenter() {
        return new PurseActivityPresenter(context, this);
    }

    @Override
    public void refresh(double money) {
        remainMoney.setText(AppUtils.parseDouble(money/100) + "元");
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
