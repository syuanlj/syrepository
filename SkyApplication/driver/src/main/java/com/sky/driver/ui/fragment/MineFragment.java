package com.sky.driver.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.sky.app.library.base.ui.BaseViewFragment;
import com.sky.app.library.component.CircleImageView;
import com.sky.app.library.utils.ImageHelper;
import com.sky.app.library.utils.T;
import com.sky.driver.R;
import com.sky.driver.bean.UserBean;
import com.sky.driver.contract.UserContract;
import com.sky.driver.presenter.MineActivityPresenter;
import com.sky.driver.ui.activity.AccountActivity;
import com.sky.driver.ui.activity.PurseActivity;
import com.sky.driver.ui.activity.SettingsActivity;
import com.sky.driver.ui.activity.user.CenterActivity;
import com.sky.driver.ui.activity.user.LoginActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 订单Tab页
 * Created by Administrator on 2017/2/30.
 */
public class MineFragment extends BaseViewFragment<UserContract.IMinePresenter> implements UserContract.IMineView {

    private boolean isLogin;//登录显示

    @BindView(R.id.driver_person_icon)
    CircleImageView circleImageView;//头像
    @BindView(R.id.driver_person_name)
    TextView personName;//用户名
    @BindView(R.id.driver_center_purse_tv)
    TextView centerPurse;//钱包
    @BindView(R.id.driver_center_account_tv)
    TextView centerAccount;//账户
    @BindView(R.id.driver_center_setting_tv)
    TextView centerSetting;//设置

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
 
    @Override
    protected void init() {

    }

    @Override
    protected void initViewsAndEvents() {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.driver_mine;
    }

    @Override
    protected void onDestoryFragment() {

    }

    @Override
    protected UserContract.IMinePresenter presenter() {
        return new MineActivityPresenter(context, this);
    }

    @Override
    public void showUserInfo() {
        UserBean userBean = UserBean.getInstance();
        ImageHelper.getInstance().displayDefinedImage(userBean.getPic_url(), circleImageView, 0, 0);
        personName.setText(userBean.getNick_name());
    }

    @Override
    public void onResume() {
        super.onResume();

        isLogin = UserBean.getInstance().checkUserLogin();
        if (isLogin) {
            getPresenter().refreshUserInfo();
        } else {
            circleImageView.setImageBitmap(null);
            personName.setText("");
        }
    }

    @OnClick(R.id.driver_person_fl)
    void clickPhoto(){
        if (isLogin){
            getActivity().startActivity(new Intent(getActivity(), CenterActivity.class));
        }else{
            getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
        }
    }

    @OnClick(R.id.driver_center_purse_tv)
    void clickCenterPurse(){
        if (isLogin){
            getActivity().startActivity(new Intent(getActivity(), PurseActivity.class));
        }else{
            getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
        }
    }

    @OnClick(R.id.driver_center_account_tv)
    void clickCenterAccount(){
        if (isLogin){
            getActivity().startActivity(new Intent(getActivity(), AccountActivity.class));
        }else{
            getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
        }
    }

    @OnClick(R.id.driver_center_setting_tv)
    void clickCenterSetting(){
        if (isLogin){
            getActivity().startActivity(new Intent(getActivity(), SettingsActivity.class));
        }else{
            getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
        }
    }

    @Override
    public void showError(String error) {
        super.showError(error);
        T.showShort(context, error);
    }
}