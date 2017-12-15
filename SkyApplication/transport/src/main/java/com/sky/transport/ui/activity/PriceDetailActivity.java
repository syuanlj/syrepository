package com.sky.transport.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.sky.app.library.base.contract.IBasePresenter;
import com.sky.app.library.base.ui.BaseViewActivity;
import com.sky.app.library.utils.AppUtils;
import com.sky.transport.R;
import com.sky.transport.bean.DistanceMoneyOut;

import butterknife.BindView;

/**
 * 价格明细【货运端】
 */
public class PriceDetailActivity extends BaseViewActivity{

    @BindView(R.id.normal_toolbar)
    Toolbar toolbar;
    @BindView(R.id.transport_title)
    TextView transportTitle;
    @BindView(R.id.transport_total_price_tv)
    TextView price;
    @BindView(R.id.transport_total_distance)
    TextView distance;
    @BindView(R.id.transport_car_type)
    TextView carType;
    @BindView(R.id.transport_money)
    TextView money;

    private DistanceMoneyOut distanceMoneyOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transport_price_detail);
    }

    @Override
    protected IBasePresenter presenter() {
        return null;
    }

    @Override
    protected void init() {
        transportTitle.setText(R.string.transport_price_detail_string);
        toolbar.setNavigationIcon(R.mipmap.transport_back_arrow_icon);

        Bundle bundle = getIntent().getExtras();
        this.distanceMoneyOut = (DistanceMoneyOut) bundle.getSerializable("money");

        //初始化界面
        price.setText(distanceMoneyOut.getMoney()/100 + "");
        distance.setText("(" + distanceMoneyOut.getDistance() + "公里)");
        carType.setText("起步价(" + distanceMoneyOut.getCar_name() + ")");
        money.setText("¥" + AppUtils.parseDouble(distanceMoneyOut.getStart_money()/100));
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
