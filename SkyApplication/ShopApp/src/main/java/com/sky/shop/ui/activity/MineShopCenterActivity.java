package com.sky.shop.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.mobileim.IYWLoginService;
import com.alibaba.mobileim.YWAPI;
import com.alibaba.mobileim.YWIMKit;
import com.alibaba.mobileim.YWLoginParam;
import com.alibaba.mobileim.channel.event.IWxCallback;
import com.alibaba.mobileim.utility.IMNotificationUtils;
import com.sky.app.library.base.ui.BaseViewActivity;
import com.sky.app.library.component.TabRaidoButton;
import com.sky.app.library.utils.BaseAppManager;
import com.sky.app.library.utils.ImageHelper;
import com.sky.app.library.utils.L;
import com.sky.app.library.utils.T;
import com.sky.shop.R;
import com.sky.shop.bean.AppKey;
import com.sky.shop.bean.UserBean;
import com.sky.shop.contract.ShopContract;
import com.sky.shop.presenter.activity.MineShopCenterPresenter;
import com.sky.shop.ui.activity.OpenIMShop.OpenIMLoginActivity;
import com.sky.shop.ui.activity.shop.SafeCenterActivity;
import com.sky.shop.ui.activity.shop.ShopDataActivity;
import com.sky.shop.ui.activity.user.AccountActivity;
import com.sky.shop.ui.fragment.FloatWebFragment;
import com.sky.shop.ui.fragment.MessageCenterFragment;
import com.sky.shop.ui.fragment.ShopDecorateFragment;
import com.sky.shop.utils.TakePhotoUtils;
import com.trello.rxlifecycle.components.support.RxFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的卖家中心商铺
 */
public class MineShopCenterActivity extends BaseViewActivity<ShopContract.IMineShopCenterPresenter>
        implements ShopContract.IMineShopCenterView{

    @BindView(R.id.app_message)
    TabRaidoButton message;
    @BindView(R.id.app_order)
    TabRaidoButton order;
    @BindView(R.id.app_decorate)
    TabRaidoButton decorate;

    @BindView(R.id.app_drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.app_picture)
    ImageView appPicture;
    @BindView(R.id.app_sider_title)
    TextView siderTitle;

    private RxFragment[] pages;

    private int lastIndexOfPage = -1;
    private int currentPageIndex = 0;
    int flag = -1;
    private long exitTime;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_seller_shop_main);

    }

    @Override
    protected ShopContract.IMineShopCenterPresenter presenter() {
        return new MineShopCenterPresenter(context, this);
    }

    @Override
    protected void initViewsAndEvents() {
        switch (flag) {
            case AppKey.HomePage.message_center:
                message.performClick();
                break;
            case AppKey.HomePage.order_manage:
                order.performClick();
                break;
            case AppKey.HomePage.shop_decorate:
                decorate.performClick();
                break;
        }
    }

    @Override
    protected void init() {
        flag = getIntent().getIntExtra(AppKey.HomePage.APP_TAB_LABEL, AppKey.HomePage.message_center);

        pages = new RxFragment[3];
        pages[AppKey.HomePage.message_center] = new MessageCenterFragment();

        FloatWebFragment floatWebFragment = new FloatWebFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", "http://api.app.51craftsman.com/product_order/to_merchant_orders?user_id=" +
                UserBean.getInstance().getCacheUid());
        floatWebFragment.setArguments(bundle);
        pages[AppKey.HomePage.order_manage] = floatWebFragment;
        pages[AppKey.HomePage.shop_decorate] = new ShopDecorateFragment();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, null, 0, 0);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        IntentFilter filter = new IntentFilter();
        filter.addAction("com.sky.shop.left");
        filter.setPriority(Integer.MAX_VALUE);
        registerReceiver(myReceiver, filter);
    }

    @Override
    public void showError(String error) {
        super.showError(error);
        T.showShort(context, error);
    }

    @Override
    protected void onDestoryActivity() {
        unregisterReceiver(myReceiver);
    }

    @OnClick({R.id.app_message, R.id.app_order, R.id.app_decorate,R.id.app_message_openim})
    public void clickTab(TabRaidoButton btn){
        switch (btn.getId()){
            case R.id.app_message:
                displayPage(AppKey.HomePage.message_center);
                break;
            case R.id.app_order:
                displayPage(AppKey.HomePage.order_manage);
                break;
            case R.id.app_decorate:
                displayPage(AppKey.HomePage.shop_decorate);
                break;
            case R.id.app_message_openim:
                startActivity(new Intent(this, OpenIMLoginActivity.class));
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
                message.setCheckedView(true);
                order.setCheckedView(false);
                decorate.setCheckedView(false);
                break;
            case 1:
                message.setCheckedView(false);
                order.setCheckedView(true);
                decorate.setCheckedView(false);
                break;
            case 2:
                message.setCheckedView(false);
                order.setCheckedView(false);
                decorate.setCheckedView(true);
                break;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    /**
     * 关闭drawer
     */
    private boolean closeDrawer(){
        if (null != drawerLayout && drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if (!closeDrawer()){
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                T.showShort(context, "再按一次退出应用");
                exitTime = System.currentTimeMillis();
            } else {
//            this.moveTaskToBack(true);
                BaseAppManager.getInstance().AppExit(context);
            }
        }
    }

    @OnClick(R.id.app_shop_center)
    void clickShopCenter(){
        startActivity(new Intent(context, ShopDataActivity.class));
    }

    @OnClick(R.id.app_account_center)
    void clickAccountCenter(){
        closeDrawer();
        startActivity(new Intent(context, AccountActivity.class));
    }

    @OnClick(R.id.app_browser_record)
    void clickBrowserRecord(){
        closeDrawer();
        Intent i = new Intent(context, RecordWebActivity.class);
        UserBean.getInstance().getCacheUid();
        i.putExtra("url", "http://api.app.51craftsman.com/h5_shop/un/browse_record?user_id=" +
                UserBean.getInstance().getCacheUid());
        startActivity(i);
    }

    @OnClick(R.id.app_safe_center)
    void clickSafeCenter(){
        closeDrawer();
        startActivity(new Intent(context, SafeCenterActivity.class));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final String msg = TakePhotoUtils.getInstance(this).onActivityResult(requestCode,
                resultCode, data, true);
        L.msg("图片上传===>" + msg);
        Intent a = new Intent();
        a.setAction("com.sky.shop.image");
        a.putExtra("image", msg);
        sendBroadcast(a);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPresenter().queryUserInfo();
    }

    @Override
    public void responseUserInfo(UserBean userBean) {
        //初始化数据
        ImageHelper.getInstance().displayDefinedImage(userBean.getPic_url(),
                appPicture, R.mipmap.app_default_photo, R.mipmap.app_default_icon);
        siderTitle.setText(UserBean.getInstance().getUserCache().getNick_name());
    }

    /**
     * 接口广播
     */
    private BroadcastReceiver myReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if ("com.sky.shop.left".equals(intent.getAction())){
                L.msg("比纳篮");
                if (!closeDrawer()){
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        }

    };

}
