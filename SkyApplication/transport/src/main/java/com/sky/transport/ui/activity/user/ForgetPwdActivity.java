package com.sky.transport.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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
import com.sky.transport.bean.UserBean;
import com.sky.transport.contract.UserContract;
import com.sky.transport.presenter.CodeActivityPresenter;
import com.sky.transport.presenter.ForgetActivityPresenter;
import com.sky.transport.ui.activity.MainActivity;

import butterknife.BindView;
import butterknife.OnClick;

import static android.text.TextUtils.isEmpty;

/**
 * 找回密码【货运端】
 */
public class ForgetPwdActivity extends BaseViewActivity<UserContract.IForgetPresenter> implements UserContract.IForgetView{

    @BindView(R.id.normal_toolbar)
    Toolbar toolbar;
    @BindView(R.id.transport_title)
    TextView title;
    @BindView(R.id.transport_mobile_et)
    EditText mobile;
    @BindView(R.id.transport_code_et)
    EditText code;
    @BindView(R.id.driver_send_code_bn)
    Button sendCode;
    @BindView(R.id.transport_new_pwd_et)
    EditText newPwd;

    private UserContract.ICodePresenter iCodePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transport_forget_pwd);
    }

    @Override
    protected UserContract.IForgetPresenter presenter() {
        iCodePresenter = new CodeActivityPresenter(this, this);
        return new ForgetActivityPresenter(this, this);
    }

    @Override
    protected void init() {
        title.setText(R.string.transport_forget_pwd_string);
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

    @OnClick(R.id.transport_forget_tv)
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
        UserBean userBean = UserBean.getInstance();
        userBean.setMobile(mobile.getText().toString());
        userBean.setPwd(Md5Util.md5(newPwd.getText().toString()));
        getPresenter().forget(userBean);
    }

    @Override
    public void showSuccess() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
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
