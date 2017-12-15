package com.sky.shop.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.sky.app.library.base.ui.BaseViewActivity;
import com.sky.app.library.utils.BaseAppManager;
import com.sky.app.library.utils.DialogUtils;
import com.sky.app.library.utils.Md5Util;
import com.sky.app.library.utils.QQLogin;
import com.sky.app.library.utils.T;
import com.sky.shop.R;
import com.sky.shop.bean.AppKey;
import com.sky.shop.bean.ForgetIn;
import com.sky.shop.bean.QQWeixinIn;
import com.sky.shop.bean.UserBean;
import com.sky.shop.contract.QQContract;
import com.sky.shop.contract.UserContract;
import com.sky.shop.presenter.CodeActivityPresenter;
import com.sky.shop.presenter.activity.LoginActivityPresenter;
import com.sky.shop.ui.activity.MineShopCenterActivity;
import com.sky.shop.ui.activity.shop.CardActivity;
import com.sky.shop.ui.activity.shop.SellerCenterActivity;
import com.sky.shop.ui.activity.shop.SellerChoseTypeActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 登录
 */
public class LoginActivity extends BaseViewActivity<UserContract.ILoginPresenter>
        implements UserContract.ILoginView{

    @BindView(R.id.normal_toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_title)
    TextView title;
    @BindView(R.id.driver_login_btn)
    TextView login;//登录按钮
    @BindView(R.id.driver_login_name_et)
    EditText loginName;//登录名称
    @BindView(R.id.driver_login_pwd_et)
    EditText loginPwd;//登录密码
    private long exitTime;

    private QQLogin qqLogin = null;
    private QQContract.IQQPresenter iqqPresenter = null;
    private UserContract.ICodePresenter iCodePresenter = null;
    private QQWeixinIn qqWeixinIn;
    private int type = -1;//1：qq登录 2：微信登录


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_login);
//        this.qqLogin = new QQLogin(this, QQLogin.SHOP_APP_ID);
    }

    @Override
    protected UserContract.ILoginPresenter presenter() {
//        iqqPresenter = new QQActivityPresenter(context, this);
        iCodePresenter = new CodeActivityPresenter(this, this);
        return new LoginActivityPresenter(this, this);
    }

    @Override
    protected void init() {
        title.setText(R.string.app_login_string);
    }

    @Override
    public void initViewsAndEvents() {
    }

//    @OnClick(R.id.driver_qq_rl)
//    void clickQQLogin(){
//        DialogUtils.showLoading(this);
//        this.qqLogin.login(this);
//        type = 1;
//    }

//    @OnClick(R.id.driver_weixin_rl)
//    void clickWeixinLogin(){
//        WeixinLogin.getInstance(context).loginWeixin(this);
//        type = 2;
//    }

    @OnClick(R.id.driver_login_btn)
    void clickLogin(){
        if (TextUtils.isEmpty(loginName.getText().toString())){
            T.showShort(context, "用户名不能为空！");
            return;
        }
        if (TextUtils.isEmpty(loginPwd.getText().toString())){
            T.showShort(context, "用户密码不能为空！");
            return;
        }
        ForgetIn forgetIn = new ForgetIn();
        forgetIn.setMobile(loginName.getText().toString());
        forgetIn.setUser_type("2");
        forgetIn.setPwd(Md5Util.md5(loginPwd.getText().toString()));
        getPresenter().login(forgetIn);

    }

    @OnClick(R.id.driver_no_account)
    void clickRegister(){
        startActivity(new Intent(context, RegisterActivity.class));
    }

    @OnClick(R.id.driver_forget_pwd)
    void clickForgetPwd(){
        startActivity(new Intent(context, ForgetPwdActivity.class));
    }

    @Override
    public void showUserInfo() {
        onResume();
    }

//    @Override
//    public void responseIsBindMobile(boolean isBind, String mobile) {
//        if (isBind){
//            qqWeixinIn.setMobile(mobile);
//            if (type == 1){//qq
//                iqqPresenter.weixinLogin(qqWeixinIn);
//            } else if (type == 2){//微信
//                iqqPresenter.qqLogin(qqWeixinIn);
//            }
//        }else{
//            thirdLogin();
//        }
//    }

    /**
     * 第三方登录
     */
//    private void thirdLogin(){
//        //绑定手机号码
//        AppDialogUtils.showBindMobileDialog(this, new SafeCenterActivity.IBindMobileCallBack() {
//            @Override
//            public void bind(String mobile) {
//                qqWeixinIn.setMobile(mobile);
//                switch (type){
//                    case 1:
//                        iqqPresenter.qqLogin(qqWeixinIn);
//                        break;
//                    case 2:
//                        iqqPresenter.weixinLogin(qqWeixinIn);
//                        break;
//                }
//            }
//
//            @Override
//            public void send(String mobile) {
//                iCodePresenter.sendCode(mobile);
//            }
//        }, false);
//    }

    @Override
    public void showError(String error) {
        super.showError(error);
        DialogUtils.hideLoading();
        T.showShort(context, error);
    }

    @Override
    protected void onDestoryActivity() {

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
    protected void onResume() {
        super.onResume();
        if (UserBean.getInstance().checkUserLogin()){
            String s = UserBean.getInstance().getUserCache().getSeller_type();
            if (TextUtils.isEmpty(s)){//没有类型
                startActivity(new Intent(context, SellerChoseTypeActivity.class));
                finish();
            }else{
                switch (s) {
                    case "店铺":
                        Intent i = new Intent(context, MineShopCenterActivity.class);
                        i.putExtra(AppKey.HomePage.APP_TAB_LABEL, AppKey.HomePage.message_center);
                        startActivity(i);
                        finish();
                        break;
                    case "名片":
                        startActivity(new Intent(context, CardActivity.class));
                        finish();
                        break;
                    case "个人主页":
                        startActivity(new Intent(context, SellerCenterActivity.class));
                        finish();
                        break;
                }
            }

        }
    }

//    @Override
//    public void onSuccess(JSONObject jsonObject) {
//        DialogUtils.hideLoading();
//        L.msg("微信===》" + jsonObject.toString());
//        qqWeixinIn = new QQWeixinIn();
//        try{
//            qqWeixinIn.setOpen_id(jsonObject.has("openid") ? jsonObject.getString("openid") : "");
//            qqWeixinIn.setNickname(jsonObject.has("nickname") ? jsonObject.getString("nickname") : "");
//            qqWeixinIn.setGender(jsonObject.has("sex") ? (1 == jsonObject.getInt("sex") ? "男" : "女") : "男");
//            qqWeixinIn.setFigureurl(jsonObject.has("headimgurl") ? jsonObject.getString("headimgurl") : "");
//            qqWeixinIn.setUser_type("2");
//        }catch (JSONException e){
//            e.printStackTrace();
//        }
//        ThidBindIn thidBindIn = new ThidBindIn();
//        thidBindIn.setType(0);
//        thidBindIn.setUser_type(2);
//        thidBindIn.setOpen_id(qqWeixinIn.getOpen_id());
//        getPresenter().queryUserIsBindMobile(thidBindIn);
//    }

//    @Override
//    public void onSuccess(String token, JSONObject jsonObject) {
//        DialogUtils.hideLoading();
//        L.msg("qq返回值：" + token + ", " + jsonObject.toString());
//        qqWeixinIn = new QQWeixinIn();
//        try{
//            qqWeixinIn.setOpen_id(token);
//            qqWeixinIn.setGender(jsonObject.has("gender") ? jsonObject.getString("gender") : "");
//            qqWeixinIn.setFigureurl(jsonObject.has("figureurl_qq_2") ? jsonObject.getString("figureurl_qq_2") : "");
//            qqWeixinIn.setNickname(jsonObject.has("nickname") ? jsonObject.getString("nickname") : "");
//            qqWeixinIn.setUser_type("2");
//        }catch (JSONException e){
//            e.printStackTrace();
//        }
//        ThidBindIn thidBindIn = new ThidBindIn();
//        thidBindIn.setType(1);
//        thidBindIn.setUser_type(2);
//        thidBindIn.setOpen_id(qqWeixinIn.getOpen_id());
//        getPresenter().queryUserIsBindMobile(thidBindIn);
//    }

//    @Override
//    public void onFailure(String msg) {
//        DialogUtils.hideLoading();
//        T.showShort(context, msg);
//    }
}
