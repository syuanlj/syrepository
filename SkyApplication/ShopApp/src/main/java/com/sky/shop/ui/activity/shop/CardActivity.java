package com.sky.shop.ui.activity.shop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.sky.app.library.base.ui.BaseViewActivity;
import com.sky.app.library.utils.BaseAppManager;
import com.sky.app.library.utils.DialogUtils;
import com.sky.app.library.utils.ImageHelper;
import com.sky.app.library.utils.T;
import com.sky.shop.R;
import com.sky.shop.bean.ProductIntroduceOut;
import com.sky.shop.bean.UserBean;
import com.sky.shop.contract.SellerContract;
import com.sky.shop.contract.ShopContract;
import com.sky.shop.presenter.CardPresenter;
import com.sky.shop.presenter.activity.MineShopCenterPresenter;
import com.sky.shop.ui.activity.RecordWebActivity;
import com.sky.shop.ui.activity.user.AccountActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 名片
 */
public class CardActivity extends BaseViewActivity<SellerContract.ICardPresenter>
    implements SellerContract.ICardView, ShopContract.IMineShopCenterView{

    private ProductIntroduceOut productIntroduceOut;

    @BindView(R.id.app_icon)
    ImageView appIcon;
    @BindView(R.id.app_user_name)
    TextView userName;
    @BindView(R.id.app_time)
    TextView appTime;
    @BindView(R.id.app_main_sell)
    TextView mainSell;

    @BindView(R.id.qq)
    TextView qq;
    @BindView(R.id.weixin)
    TextView weixin;
    private long exitTime;

    ShopContract.IMineShopCenterPresenter iMineShopCenterPresenter;

    @BindView(R.id.app_drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.app_picture)
    ImageView appPicture;
    @BindView(R.id.app_sider_title)
    TextView siderTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_card);
    }

    @Override
    protected SellerContract.ICardPresenter presenter() {
        iMineShopCenterPresenter = new MineShopCenterPresenter(context, this);
        return new CardPresenter(context, this);
    }

    @Override
    protected void initViewsAndEvents() {
    }

    @Override
    protected void init() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, null, 0, 0);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public void showError(String error) {
        super.showError(error);
        T.showShort(context, error);
    }

    @Override
    protected void onDestoryActivity() {

    }

    @Override
    public void responseCardInfo(ProductIntroduceOut productIntroduceOut) {
        this.productIntroduceOut = productIntroduceOut;

        ImageHelper.getInstance().displayDefinedImage(productIntroduceOut.getPic_url(), appIcon,
                R.mipmap.app_default_icon, R.mipmap.app_default_icon);
        userName.setText(productIntroduceOut.getNick_name());
        appTime.setText("从业时间：" + (TextUtils.isEmpty(productIntroduceOut.getWorktime()) ? "" :
                productIntroduceOut.getWorktime()));
        mainSell.setText("主营业务：" + (TextUtils.isEmpty(productIntroduceOut.getMain_business_desc()) ? "" :
                productIntroduceOut.getMain_business_desc()));

        qq.setText(productIntroduceOut.getQq());
        weixin.setText(productIntroduceOut.getWeixin());
    }

    @Deprecated
    @Override
    public void responseAddCard(String msg) {

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

    @OnClick(R.id.card_edt)
    void edit(){
        startActivity(new Intent(context, CardEditActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        iMineShopCenterPresenter.queryUserInfo();

        //请求个人主页信息
        getPresenter().requestCardInfo();
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
        if (!closeDrawer()) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                T.showShort(context, "再按一次退出应用");
                exitTime = System.currentTimeMillis();
            } else {
                BaseAppManager.getInstance().AppExit(context);
            }
        }
    }

    @Override
    public void responseUserInfo(UserBean userBean) {
        //初始化数据
        ImageHelper.getInstance().displayDefinedImage(userBean.getPic_url(),
                appPicture, R.mipmap.app_default_photo, R.mipmap.app_default_icon);
        siderTitle.setText(UserBean.getInstance().getUserCache().getNick_name());
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

    @OnClick(R.id.app_shop_icon)
    void clickLeft(){
        if (!closeDrawer()){
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }
}
