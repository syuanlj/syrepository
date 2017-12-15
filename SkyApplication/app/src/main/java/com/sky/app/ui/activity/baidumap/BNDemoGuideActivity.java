package com.sky.app.ui.activity.baidumap;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.baidu.navisdk.adapter.BNRouteGuideManager;
import com.baidu.navisdk.adapter.BNRoutePlanNode;
import com.sky.app.R;

public class BNDemoGuideActivity extends AppCompatActivity {

    private Handler hd;
    private int MSG_SHOW=1;
    private BNRoutePlanNode mBNRoutePlanNode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        create
//        setContentView(R.layout.activity_bndemo_guide);
        createHandler();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {}
        View view = BNRouteGuideManager.getInstance().onCreate(this, new BNRouteGuideManager.OnNavigationListener() {

            @Override
            public void onNaviGuideEnd() {
                finish();
            }

            @Override
            public void notifyOtherAction(int actionType, int arg1, int arg2, Object obj) {

            }
        });

        if ( view != null ) {
            setContentView(view);
        }

        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                mBNRoutePlanNode = (BNRoutePlanNode) bundle.getSerializable(BaiduMapActivity.ROUTE_PLAN_NODE);
            }
        }
    }

    private void createHandler() {
        hd=new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        BNRouteGuideManager.getInstance().onResume();
        super.onResume();

        hd.sendEmptyMessageDelayed(MSG_SHOW, 5000);
    }

    protected void onPause() {
        super.onPause();
        BNRouteGuideManager.getInstance().onPause();
    };

    @Override
    protected void onDestroy() {
        BNRouteGuideManager.getInstance().onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        BNRouteGuideManager.getInstance().onStop();
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        BNRouteGuideManager.getInstance().onBackPressed(false);
    }

    public void onConfigurationChanged(android.content.res.Configuration newConfig) {
        BNRouteGuideManager.getInstance().onConfigurationChanged(newConfig);
        super.onConfigurationChanged(newConfig);
    };
}
