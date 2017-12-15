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
import com.sky.driver.bean.AppKey;
import com.sky.driver.bean.ForgetIn;
import com.sky.driver.bean.UserBean;
import com.sky.driver.contract.UserContract;
import com.sky.driver.presenter.CodeActivityPresenter;
import com.sky.driver.presenter.ForgetActivityPresenter;
import com.sky.driver.ui.activity.MainActivity;

import butterknife.BindView;
import butterknife.OnClick;

import static android.text.TextUtils.isEmpty;

/**
 * 找回密码【司机端】
 */
public class ForgetPwdActivity extends BaseViewActivity<UserContract.IForgetPresenter> implements UserContract.IForgetView{

    @BindView(R.id.normal_toolbar)
    Toolbar toolbar;
    @BindView(R.id.driver_title)
    TextView driverTitle;
    @BindView(R.id.driver_mobile_et)
    EditText mobile;
    @BindView(R.id.driver_code_et)
    EditText code;
    @BindView(R.id.driver_send_code_bn)
    Button sendCode;
    @BindView(R.id.driver_new_pwd_et)
    EditText newPwd;

    private UserContract.ICodePresenter iCodePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_forget_pwd);
    }

    @Override
    protected UserContract.IForgetPresenter presenter() {
        iCodePresenter = new CodeActivityPresenter(this, this);
        return new ForgetActivityPresenter(this, this);
    }

    @Override
    protected void init() {
        driverTitle.setText(R.string.driver_forget_pwd_string);
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

    @OnClick(R.id.driver_forget_tv)
    void clickForget(){
        if (isEmpty(mobile.getText().toString())){
            T.showShort(context, "手机号不能为空！");
            return;
        }
        if (!StringUtil.isPhone(mobile.getText().toString())){
            T.showShort(context, "手机号格式不正确！");
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
        if (isEmpty(newPwd.getText().toString())){
            T.showShort(context, "新密码不能为空！");
            return;
        }
        ForgetIn forgetIn = new ForgetIn();
        forgetIn.setMobile(mobile.getText().toString());
        forgetIn.setPwd(Md5Util.md5(newPwd.getText().toString()));
        getPresenter().forget(forgetIn);
    }

    @Override
    public void showSuccess() {
        Intent i = new Intent(context, MainActivity.class);
        i.putExtra(AppKey.HomePage.DRIVER_TAB_LABEL, AppKey.HomePage.mime);
        startActivity(i);
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
}
