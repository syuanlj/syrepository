package com.sky.driver.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;

import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.ui.BaseViewActivity;
import com.sky.app.library.component.TabRaidoButton;
import com.sky.app.library.utils.BaseAppManager;
import com.sky.app.library.utils.T;
import com.sky.driver.R;
import com.sky.driver.bean.AppKey;
import com.sky.driver.bean.UserBean;
import com.sky.driver.contract.MainContract;
import com.sky.driver.presenter.MainActivityPresenter;
import com.sky.driver.ui.activity.user.LoginActivity;
import com.sky.driver.ui.fragment.HomePageFragment;
import com.sky.driver.ui.fragment.MineFragment;
import com.sky.driver.ui.fragment.OrderFragment;
import com.trello.rxlifecycle.components.support.RxFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 主页【货运司机端】
 */
public class MainActivity extends BaseViewActivity<MainContract.IMainPresenter> implements MainContract.IMainView{

    @BindView(R.id.driver_home_page)
    TabRaidoButton driverHomePage;
    @BindView(R.id.driver_order)
    TabRaidoButton driverOrder;
    @BindView(R.id.driver_center)
    TabRaidoButton driverCenter;

    private RxFragment[] pages;

    private int lastIndexOfPage = -1;
    private int currentPageIndex = 0;
    private long exitTime;

    int flag = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_main);
    }

    @Override
    protected MainContract.IMainPresenter presenter() {
        return new MainActivityPresenter(this, this);
    }

    @Override
    protected void init() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    Constants.Permission.LOACTION_PERMISSION);
        }

        flag = getIntent().getIntExtra(AppKey.HomePage.DRIVER_TAB_LABEL, AppKey.HomePage.index);

        pages = new RxFragment[3];
        pages[AppKey.HomePage.index] = getIndexFragment(flag);
        pages[AppKey.HomePage.order] = new OrderFragment();
        pages[AppKey.HomePage.mime] = new MineFragment();
    }

    @Override
    protected void onDestoryActivity() {

    }

    /**
     * 获取
     *
     * @param flag 是否需要更新
     * @return 首页
     */
    private HomePageFragment getIndexFragment(int flag) {
        HomePageFragment indexFragment = new HomePageFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(AppKey.HomePage.DRIVER_TAB_LABEL, flag);
        indexFragment.setArguments(bundle);
        return indexFragment;
    }

    @Override
    public void initViewsAndEvents() {
        switch (flag) {
            case AppKey.HomePage.index:
                driverHomePage.performClick();
                break;
            case AppKey.HomePage.order:
                driverOrder.performClick();
                break;
            case AppKey.HomePage.mime:
                driverCenter.performClick();
                break;
        }
    }

    @OnClick({R.id.driver_home_page, R.id.driver_order, R.id.driver_center})
    public void clickTab(TabRaidoButton btn){
        switch (btn.getId()){
            case R.id.driver_home_page:
                displayPage(AppKey.HomePage.index);
                break;
            case R.id.driver_order:
                displayPage(AppKey.HomePage.order);
                break;
            case R.id.driver_center:
                displayPage(AppKey.HomePage.mime);
                break;
        }
    }

    /**
     * 显示Tab页
     * @param pageIndex
     */
    public void displayPage(int pageIndex) {
        if (pageIndex == lastIndexOfPage) return;

        currentPageIndex = pageIndex;

        switchTab(pageIndex);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        if (pages[pageIndex].isAdded()){
            ft.show(pages[pageIndex]);
        } else {
            ft.add(R.id.driver_content_fl, pages[pageIndex], String.valueOf(pageIndex));
        }
        if (lastIndexOfPage >= 0){
            ft.hide(pages[lastIndexOfPage]);
        }
        ft.commitAllowingStateLoss();
        lastIndexOfPage = pageIndex;
    }

    /**
     * 切换标签选择
     * @param position 位置
     */
    private void switchTab(int position) {
        switch (position) {
            case 0:
                driverHomePage.setCheckedView(true);
                driverOrder.setCheckedView(false);
                driverCenter.setCheckedView(false);
                break;
            case 1:
                driverHomePage.setCheckedView(false);
                driverOrder.setCheckedView(true);
                driverCenter.setCheckedView(false);
                break;
            case 2:
                driverHomePage.setCheckedView(false);
                driverOrder.setCheckedView(false);
                driverCenter.setCheckedView(true);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            T.showShort(context, "再按一次退出应用");
            exitTime = System.currentTimeMillis();
        } else {
//            this.moveTaskToBack(true);
            BaseAppManager.getInstance().AppExit(context);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent); //这一句必须的，否则Intent无法获得最新的数据
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!UserBean.getInstance().checkUserLogin()){
            startActivity(new Intent(context, LoginActivity.class));
            return;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case Constants.Permission.LOACTION_PERMISSION:
                if (grantResults.length == 0){
                    T.showShort(context, "权限申请失败！");
                }
                break;
        }
    }
}
