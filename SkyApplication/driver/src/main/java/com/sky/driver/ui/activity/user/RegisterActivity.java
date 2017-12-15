package com.sky.driver.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sky.app.library.base.ui.BaseViewActivity;
import com.sky.app.library.utils.Captcha;
import com.sky.app.library.utils.Md5Util;
import com.sky.app.library.utils.StringUtil;
import com.sky.app.library.utils.T;
import com.sky.driver.R;
import com.sky.driver.bean.ForgetIn;
import com.sky.driver.bean.UserBean;
import com.sky.driver.contract.UserContract;
import com.sky.driver.presenter.CodeActivityPresenter;
import com.sky.driver.presenter.RegisterActivityPresenter;

import butterknife.BindView;
import butterknife.OnClick;

import static android.text.TextUtils.isEmpty;

/**
 * 注册【司机端】
 */
public class RegisterActivity extends BaseViewActivity<UserContract.IRegisterPresenter>
        implements UserContract.IRegisterView{

    @BindView(R.id.normal_toolbar)
    Toolbar toolbar;
    @BindView(R.id.driver_title)
    TextView driverTitle;
    @BindView(R.id.driver_register_tv)
    TextView registerBtn;//注册按钮
    @BindView(R.id.driver_send_code_bn)
    Button sendCode;//发送验证码
    @BindView(R.id.driver_code_et)
    EditText code;//验证码
    @BindView(R.id.driver_pwd_et)
    EditText pwd;//密码
    @BindView(R.id.driver_mobile_et)
    EditText mobile;//手机号

    private UserContract.ICodePresenter iCodePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_register);
    }

    @Override
    protected UserContract.IRegisterPresenter presenter() {
        iCodePresenter = new CodeActivityPresenter(this, this);
        return new RegisterActivityPresenter(this, this);
    }

    @Override
    protected void init() {
        driverTitle.setText(R.string.driver_register_string);
        toolbar.setNavigationIcon(R.mipmap.driver_back_arrow_icon);
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

    @OnClick(R.id.driver_register_tv)
    void clickRegister(){
        if (isEmpty(mobile.getText().toString())){
            T.showShort(context, "手机号不能为空！");
            return;
        }
        if (!StringUtil.isPhone(mobile.getText().toString())){
            T.showShort(context, "手机号格式不正确！");
            return;
        }
        if (isEmpty(pwd.getText().toString())){
            T.showShort(context, "密码不能为空！");
            return;
        }
        if (isEmpty(code.getText().toString())){
            T.showShort(context, "验证码不能为空！");
           return;
        }
        if (!UserBean.getInstance().getCode().equals(code.getText().toString())){
            T.showShort(context, "验证码不正确！");
            return;
        }
        ForgetIn forgetIn = new ForgetIn();
        forgetIn.setMobile(mobile.getText().toString());
        forgetIn.setPwd(Md5Util.md5(pwd.getText().toString()));
        forgetIn.setUser_type("2");
        getPresenter().register(forgetIn);
    }

    @OnClick(R.id.driver_send_code_bn)
    void clickSendCode(){
        if (isEmpty(mobile.getText().toString())){
            T.showShort(context, "手机号不能为空！");
            return;
        }
        if (!StringUtil.isPhone(mobile.getText().toString())){
            T.showShort(context, "手机号格式不正确！");
            return;
        }
        iCodePresenter.sendCode(mobile.getText().toString());
        new Captcha(60000, 1000, sendCode).start();
    }

    @Override
    public void showError(String error) {
        super.showError(error);
        T.showShort(context, error);
    }

    @Override
    protected void onDestoryActivity() {
        if (null != iCodePresenter){
            iCodePresenter.destroy();
        }
    }

    @Override
    public void showUserInfo() {
//        Intent i = new Intent(context, MainActivity.class);
//        i.putExtra(AppKey.HomePage.DRIVER_TAB_LABEL, AppKey.HomePage.mime);
//        startActivity(i);
//        finish();
        startActivity(new Intent(context, IdCardActivity.class));
    }
}
