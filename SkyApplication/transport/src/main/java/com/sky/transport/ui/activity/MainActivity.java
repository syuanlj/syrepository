package com.sky.transport.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.ui.BaseViewActivity;
import com.sky.app.library.component.CircleImageView;
import com.sky.app.library.utils.BaseAppManager;
import com.sky.app.library.utils.DialogUtils;
import com.sky.app.library.utils.ImageHelper;
import com.sky.app.library.utils.T;
import com.sky.transport.R;
import com.sky.transport.bean.DistanceMoneyIn;
import com.sky.transport.bean.DistanceMoneyOut;
import com.sky.transport.bean.Location;
import com.sky.transport.bean.OrderIn;
import com.sky.transport.bean.UserBean;
import com.sky.transport.contract.MainContract;
import com.sky.transport.presenter.MainActivityPresenter;
import com.sky.transport.ui.activity.user.CenterActivity;
import com.sky.transport.ui.activity.user.LoginActivity;
import com.sky.transport.ui.activity.user.PurseActivity;
import com.sky.transport.ui.activity.user.SettingsActivity;
import com.sky.transport.utils.CarRaidoButton;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 主页【货运端】
 */
public class MainActivity extends BaseViewActivity<MainContract.IMainPresenter>
        implements MainContract.IMainView, ActivityCompat.OnRequestPermissionsResultCallback{

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.transport_price_detail_tv)
    TextView transportPriceDetailTv;
    @BindView(R.id.transport_drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.transport_begin_location1_tv)
    TextView beginLocation;
    @BindView(R.id.transport_begin_detail1_tv)
    TextView beginDetail;
    @BindView(R.id.transport_begin_location2_tv)
    TextView endLocation;
    @BindView(R.id.transport_begin_detail2_tv)
    TextView endDetail;
    @BindView(R.id.transport_remark_et)
    EditText remark;
    @BindView(R.id.transport_distance_money_rl)
    RelativeLayout distanceMoney;
//    @BindView(R.id.transport_1_btn)
//    CarRaidoButton car1;
//    @BindView(R.id.transport_2_btn)
//    CarRaidoButton car2;
    @BindView(R.id.transport_3_btn)
    CarRaidoButton car3;
    @BindView(R.id.transport_4_btn)
    CarRaidoButton car4;
    @BindView(R.id.transport_5_btn)
    CarRaidoButton car5;
    @BindView(R.id.transport_6_btn)
    CarRaidoButton car6;

    @BindView(R.id.transport_picture)
    CircleImageView circleImageView;
    @BindView(R.id.transport_sider_title)
    TextView siderTitle;

    String carType;//汽车类型
    String extra = "";//额外需求
    String need_use_time = "";
    DistanceMoneyOut distanceMoneyOut = null;

    public static final int BEGIN_LOCATION = 1;//起点定位
    public static final int END_LOCATION = 2;//终点定位
    private long exitTime;//退出时间

    /**
     * 请求码
     */
    private final static int REQUEST_CODE = 0x123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transport_home_page);
    }

    @Override
    protected MainContract.IMainPresenter presenter() {
        return new MainActivityPresenter(this, this);
    }

    @Override
    protected void init() {
        transportPriceDetailTv.setText(Html.fromHtml("<u>价格明细</u>"));
       distanceMoney.setVisibility(View.GONE);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.transport_navigation_drawer_open,
                R.string.transport_navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public void initViewsAndEvents() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    Constants.Permission.LOACTION_PERMISSION);
        }
    }

    @Override
    public void onBackPressed() {
        if (!closeDrawer()){
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                T.showShort(context, "再按一次退出应用");
                exitTime = System.currentTimeMillis();
            } else {
//                this.moveTaskToBack(true);
                BaseAppManager.getInstance().AppExit(context);
            }
        }
    }

    /**
     * 关闭drawer
     */
    private boolean closeDrawer(){
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.transport_drawer_layout);
        if (null != drawer && drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }
        return false;
    }

    /**
     * 跳转添加额外需求
     */
    @OnClick(R.id.transport_extra_rl)
    public void skipExtraActivity(){
        startActivityForResult(new Intent(context, ExtraActivity.class), 200);
    }

    /**
     * 跳转价格明细
     */
    @OnClick(R.id.transport_price_detail_tv)
    public void skipPriceDetailActivity(){
        if (null == distanceMoneyOut){
            T.showShort(context, "计算金额异常");
            return;
        }
        Intent i = new Intent(context, PriceDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("money", distanceMoneyOut);
        i.putExtras(bundle);
        startActivity(i);
    }

    /**
     * 跳转个人中心界面
     */
    @OnClick(R.id.transport_center)
    public void skipCenterActivity(){
        startActivity(new Intent(context, CenterActivity.class));
        closeDrawer();
    }

    /**
     * 跳转订单界面
     */
    @OnClick(R.id.transport_order)
    public void skipOrderActivity(){
        startActivity(new Intent(context, OrderActivity.class));
        closeDrawer();
    }

    /**
     * 跳转51钱包界面
     */
    @OnClick(R.id.transport_purse)
    public void skipPurseActivity(){
        startActivity(new Intent(context, PurseActivity.class));
        closeDrawer();
    }

    /**
     * 跳转设置界面
     */
    @OnClick(R.id.transport_settings)
    public void skipSettingsActivity(){
        startActivity(new Intent(context, SettingsActivity.class));
        closeDrawer();
    }

    @OnClick(R.id.transport_begin_rl)
    void clickBeginLocation(){
        // 跳转页面
        Intent i = new Intent(MainActivity.this, LocationActivity.class);
        i.putExtra("flag", BEGIN_LOCATION);
        startActivityForResult(i, REQUEST_CODE);
    }

    @OnClick(R.id.transport_end_rl)
    void clickEndLocation(){
        // 跳转页面
        Intent i = new Intent(MainActivity.this, LocationActivity.class);
        i.putExtra("flag", END_LOCATION);
        startActivityForResult(i, REQUEST_CODE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //判断是否处于登录状态
        if (!UserBean.getInstance().checkUserLogin()){
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
            return;
        }

        //渲染用户
        getPresenter().refreshUserInfo();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 返回成功
        if (null != data){
            switch (resultCode){
                case RESULT_OK:
                    switch (requestCode){
                        case REQUEST_CODE://定位
                            int i = data.getIntExtra("flag", 1);
                            String position = data.getStringExtra("position");
                            String detail = data.getStringExtra("address");
                            Location location = new Location();
                            location.setLatitude(data.getDoubleExtra("latitude",0));
                            location.setLongitude(data.getDoubleExtra("longitude", 0));
                            if (1 == i){//起点
                                beginLocation.setText(position);
                                beginDetail.setText(detail);
                                beginLocation.setTag(location);
                            }else{
                                endLocation.setText(position);
                                endDetail.setText(detail);
                                endLocation.setTag(location);
                            }
                            requestDistanceMoney();
                            break;
                        case 200://额外需求
                            extra = data.getStringExtra("extra");
                            break;
                    }
                    break;
            }
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

    @Override
    public void showError(String error) {
        super.showError(error);
        T.showShort(context, error);
        //初始化价格栏
        distanceMoney.setVisibility(View.GONE);
    }

    @Override
    protected void onDestoryActivity() {

    }

    @OnClick(R.id.transport_confirm_order_tv)
    void clickTime(){
        //即时用车
        skipConfirmOrderActivity("1", "");
    }

    @OnClick(R.id.transport_order_rl)
    void clickOrder(){
        //预约
        DialogUtils.showDateDialog(context, new DialogUtils.IDatePickerDialog() {
            @Override
            public void showDate(String year, String month, String day) {
                skipConfirmOrderActivity("2", year + "-" + month + "-" + day);
            }
        });
    }

    /**
     * 跳转确认订单界面
     */
    private void skipConfirmOrderActivity(String type, String orderTime){
        this.need_use_time = orderTime;
        if (TextUtils.isEmpty(carType)){
            T.showShort(context, "请选择货运方式");
            return;
        }
        if (TextUtils.isEmpty(beginLocation.getText().toString())){
            T.showShort(context, "请选择出发地");
            return;
        }
        if (TextUtils.isEmpty(endLocation.getText().toString())){
            T.showShort(context, "请选择目的地");
            return;
        }
        Intent i = new Intent(this, ConfirmOrderActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("order", order(type));
        bundle.putSerializable("money", distanceMoneyOut);
        i.putExtras(bundle);
        startActivity(i);
    }

    /**
     * 下单
     */
    private OrderIn order(String type){
        OrderIn orderIn = new OrderIn();
        Location location = (Location) beginLocation.getTag();
        Location location1 = (Location) endLocation.getTag();
        orderIn.setCard_id(carType);
        orderIn.setFrom_address(beginLocation.getText().toString());
        orderIn.setFrom_latitude(location.getLatitude());
        orderIn.setFrom_longitude(location.getLongitude());
        orderIn.setOrder_type(type);
        orderIn.setOther_desc("");
        orderIn.setNeed_use_time(need_use_time);
        orderIn.setRemark(remark.getText().toString());
        orderIn.setTo_address(endLocation.getText().toString());
        orderIn.setTo_latitude(location1.getLatitude());
        orderIn.setTo_longitude(location1.getLongitude());
        orderIn.setFrom_detail(beginDetail.getText().toString());
        orderIn.setTo_detail(endDetail.getText().toString());
        orderIn.setOther_desc(extra);
        return orderIn;
    }

    @OnClick({R.id.transport_3_btn,R.id.transport_4_btn,R.id.transport_5_btn,R.id.transport_6_btn})
    void click(CarRaidoButton car){
        switch (car.getId()){
//            case R.id.transport_1_btn:
////                car1.setCheckedView(true);
////                car2.setCheckedView(false);
//                car3.setCheckedView(false);
//                car4.setCheckedView(false);
//                car5.setCheckedView(false);
//                car6.setCheckedView(false);
//                carType = (String) car1.getTag();
//                break;
//            case R.id.transport_2_btn:
////                car1.setCheckedView(false);
////                car2.setCheckedView(true);
//                car3.setCheckedView(false);
//                car4.setCheckedView(false);
//                car5.setCheckedView(false);
//                car6.setCheckedView(false);
//                carType = (String) car2.getTag();
//                break;
            case R.id.transport_3_btn:
//                car1.setCheckedView(false);
//                car2.setCheckedView(false);
                car3.setCheckedView(true);
                car4.setCheckedView(false);
                car5.setCheckedView(false);
                car6.setCheckedView(false);
                carType = (String) car3.getTag();
                break;
            case R.id.transport_4_btn:
//                car1.setCheckedView(false);
//                car2.setCheckedView(false);
                car3.setCheckedView(false);
                car4.setCheckedView(true);
                car5.setCheckedView(false);
                car6.setCheckedView(false);
                carType = (String) car4.getTag();
                break;
            case R.id.transport_5_btn:
//                car1.setCheckedView(false);
//                car2.setCheckedView(false);
                car3.setCheckedView(false);
                car4.setCheckedView(false);
                car5.setCheckedView(true);
                car6.setCheckedView(false);
                carType = (String) car5.getTag();
                break;
            case R.id.transport_6_btn:
//                car1.setCheckedView(false);
//                car2.setCheckedView(false);
                car3.setCheckedView(false);
                car4.setCheckedView(false);
                car5.setCheckedView(false);
                car6.setCheckedView(true);
                carType = (String) car6.getTag();
                break;
        }
        requestDistanceMoney();
    }

    @Override
    public void responseMoney(DistanceMoneyOut distanceMoneyOut) {
        this.distanceMoneyOut = distanceMoneyOut;
        ((TextView)distanceMoney.getChildAt(0)).setText("¥" + distanceMoneyOut.getMoney()/100);
        distanceMoney.setVisibility(View.VISIBLE);
    }

    @Override
    public void showUserInfo() {
        UserBean userBean = UserBean.getInstance();
        ImageHelper.getInstance().displayDefinedImage(userBean.getPic_url(), circleImageView, 0, 0);
        siderTitle.setText(userBean.getNick_name());
    }

    /**
     * 请求获取价格
     */
    void requestDistanceMoney(){
        if (TextUtils.isEmpty(carType)){
            T.showShort(context, "请选择货运方式");
            return;
        }
        if (TextUtils.isEmpty(beginLocation.getText().toString())){
            T.showShort(context, "请选择出发地");
            return;
        }
        if (TextUtils.isEmpty(endLocation.getText().toString())){
            T.showShort(context, "请选择目的地");
            return;
        }
        DistanceMoneyIn distanceMoneyIn = new DistanceMoneyIn();
        distanceMoneyIn.setCard_id(carType);
        Location location = (Location) beginLocation.getTag();
        Location location1 = (Location) endLocation.getTag();
        distanceMoneyIn.setFrom_latitude(location.getLatitude());
        distanceMoneyIn.setFrom_longitude(location.getLongitude());
        distanceMoneyIn.setTo_latitude(location1.getLatitude());
        distanceMoneyIn.setTo_longitude(location1.getLongitude());
        getPresenter().requestDistanceMoney(distanceMoneyIn);
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
