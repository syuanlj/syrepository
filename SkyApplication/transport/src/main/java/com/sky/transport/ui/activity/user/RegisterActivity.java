package com.sky.transport.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sky.app.library.base.ui.BaseViewActivity;
import com.sky.app.library.utils.Captcha;
import com.sky.app.library.utils.DialogUtils;
import com.sky.app.library.utils.Md5Util;
import com.sky.app.library.utils.StringUtil;
import com.sky.app.library.utils.T;
import com.sky.transport.R;
import com.sky.transport.bean.ForgetIn;
import com.sky.transport.bean.UserBean;
import com.sky.transport.contract.UserContract;
import com.sky.transport.presenter.CodeActivityPresenter;
import com.sky.transport.presenter.RegisterActivityPresenter;
import com.sky.transport.ui.activity.MainActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 注册【货运端】
 */
public class RegisterActivity extends BaseViewActivity<UserContract.IRegisterPresenter>
        implements UserContract.IRegisterView{

    @BindView(R.id.normal_toolbar)
    Toolbar toolbar;
    @BindView(R.id.transport_title)
    TextView title;
    @BindView(R.id.transport_register_tv)
    TextView registerBtn;//注册按钮
    @BindView(R.id.transport_send_code_bn)
    Button sendCode;//发送验证码
    @BindView(R.id.transport_code_et)
    EditText code;//验证码
    @BindView(R.id.transport_pwd_et)
    EditText pwd;//密码
    @BindView(R.id.transport_mobile_et)
    EditText mobile;//手机号

    private UserContract.ICodePresenter iCodePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transport_register);
    }

    @Override
    protected UserContract.IRegisterPresenter presenter() {
        iCodePresenter = new CodeActivityPresenter(this, this);
        return new RegisterActivityPresenter(this, this);
    }

    @Override
    protected void init() {
        title.setText(R.string.transport_register_string);
        toolbar.setNavigationIcon(R.mipmap.transport_back_arrow_icon);
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

    @OnClick(R.id.transport_register_tv)
    void clickRegister(){
        if (TextUtils.isEmpty(mobile.getText().toString())){
            T.showShort(context, "手机号不能为空！");
            return;
        }
        if (!StringUtil.isPhone(mobile.getText().toString())){
            T.showShort(context, "手机号格式不正确！");
            return;
        }
        if (TextUtils.isEmpty(pwd.getText().toString())){
            T.showShort(context, "密码不能为空！");
            return;
        }
        if (TextUtils.isEmpty(code.getText().toString())){
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
        forgetIn.setUser_type("1");
        getPresenter().register(forgetIn);
    }

    @OnClick(R.id.transport_send_code_bn)
    void clickSendCode(){
        if (TextUtils.isEmpty(mobile.getText().toString())){
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
        Intent i = new Intent(context, MainActivity.class);
        startActivity(i);
        finish();
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
