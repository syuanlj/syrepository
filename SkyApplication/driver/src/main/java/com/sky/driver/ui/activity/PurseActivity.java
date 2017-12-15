package com.sky.driver.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.sky.app.library.base.ui.BaseViewActivity;
import com.sky.app.library.utils.AppUtils;
import com.sky.driver.R;
import com.sky.driver.contract.UserContract;
import com.sky.driver.presenter.PurseActivityPresenter;
import butterknife.BindView;

/**
 * 钱包【司机端】
 */
public class PurseActivity extends BaseViewActivity<UserContract.IPursePresenter> implements UserContract.IPurseView{

    @BindView(R.id.normal_toolbar)
    Toolbar toolbar;
    @BindView(R.id.driver_title)
    TextView driverTitle;
    @BindView(R.id.driver_remain_money)
    TextView remainMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_purse);
    }

    @Override
    protected UserContract.IPursePresenter presenter() {
        return new PurseActivityPresenter(this, this);
    }

    @Override
    protected void init() {
        driverTitle.setText(R.string.driver_purse);
        toolbar.setNavigationIcon(R.mipmap.driver_back_arrow_icon);
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
    public void refresh(double money) {
        remainMoney.setText(AppUtils.parseDouble(money/100) + "元");
    }
}
