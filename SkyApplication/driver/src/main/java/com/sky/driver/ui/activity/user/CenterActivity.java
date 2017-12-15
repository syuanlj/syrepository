package com.sky.driver.ui.activity.user;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.ui.BaseViewActivity;
import com.sky.app.library.component.CircleImageView;
import com.sky.app.library.utils.DialogUtils;
import com.sky.app.library.utils.ImageHelper;
import com.sky.app.library.utils.L;
import com.sky.app.library.utils.T;
import com.sky.driver.R;
import com.sky.driver.bean.UserBean;
import com.sky.driver.contract.UserContract;
import com.sky.driver.presenter.CenterActivityPresenter;
import com.sky.driver.utils.AppDialogUtils;
import com.sky.driver.utils.TakePhotoUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 个人中心【司机端】
 */
public class CenterActivity extends BaseViewActivity<UserContract.ICenterPresenter>
        implements UserContract.ICenterView{

    @BindView(R.id.normal_toolbar)
    Toolbar toolbar;
    @BindView(R.id.driver_title)
    TextView driverTitle;
    @BindView(R.id.driver_center_picture)
    CircleImageView centerPicture;
    @BindView(R.id.driver_center_nick_et)
    EditText centerNickEt;
    @BindView(R.id.driver_center_real_et)
    EditText centerRealEt;
    @BindView(R.id.driver_center_sex_et)
    Switch centerSexEt;
    @BindView(R.id.driver_center_birthday_tv)
    TextView centerBirthdayTv;
    @BindView(R.id.driver_center_addr_et)
    EditText centerAddrEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_center);
        getPresenter().getUserInfo();
    }

    @Override
    protected UserContract.ICenterPresenter presenter() {
        return new CenterActivityPresenter(this, this);
    }

    @Override
    protected void init() {
        driverTitle.setText(R.string.driver_center_string);
        toolbar.setNavigationIcon(R.mipmap.driver_back_arrow_icon);
        toolbar.inflateMenu(R.menu.driver_center_menu);
    }

    @Override
    protected void onDestoryActivity() {

    }

    @Override
    public void initViewsAndEvents() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.driver_save:
                        UserBean.getInstance().setNick_name(centerNickEt.getText().toString());
                        UserBean.getInstance().setReal_name(centerRealEt.getText().toString());
                        UserBean.getInstance().setGender(centerSexEt.isChecked() ? 1 : 2);
                        UserBean.getInstance().setBirthday(centerBirthdayTv.getText().toString());
                        UserBean.getInstance().setAddress(centerAddrEt.getText().toString());
                        getPresenter().setUserInfo();
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public void refresh(UserBean userBean) {
        ImageHelper.getInstance().displayDefinedImage(userBean.getPic_url(), centerPicture,
                R.mipmap.driver_person_icon, R.mipmap.driver_person_icon);
        centerNickEt.setText(userBean.getNick_name());
        centerRealEt.setText(TextUtils.isEmpty(userBean.getReal_name()) ? "未设置" : userBean.getReal_name());
//        centerSexEt.setText(2 == userBean.getGender() ? "女" : "男");
        centerSexEt.setChecked(2 == userBean.getGender() ? false : true);
        centerBirthdayTv.setText(TextUtils.isEmpty(userBean.getBirthday()) ? "未设置" : userBean.getBirthday());
        centerAddrEt.setText(TextUtils.isEmpty(userBean.getAddress()) ? "未设置" : userBean.getAddress());
    }

    @Override
    public void getImageUrl(final String url) {
        L.msg("图片上传地址:" + url);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                UserBean.getInstance().setPic_url(url);
            }
        });
    }

    @Override
    public void showOnSuccess(String msg) {
        T.showShort(context, msg);
        finish();
    }

    @OnClick(R.id.driver_center_picture)
    void clickPicture(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    Constants.Permission.CAMERA_PERMISSION);
        }else{
            AppDialogUtils.showTakePhotoView(this);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final String msg = TakePhotoUtils.getInstance(this).onActivityResult(requestCode, resultCode, data, true);
        if (!TextUtils.isEmpty(msg)){
            ImageHelper.getInstance().displayDefinedImage(Uri.fromFile(new File(msg)).toString(), centerPicture,
                    R.mipmap.driver_person_icon, R.mipmap.driver_person_icon);
            getPresenter().uploadFile(msg);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case Constants.Permission.CAMERA_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    AppDialogUtils.showTakePhotoView(this);
                }else{
                    T.showShort(context, "权限申请失败！");
                }
                break;
        }
    }

    @OnClick(R.id.driver_center_birthday_tv)
    void clickBirthday(){
        DialogUtils.showDateDialog(this, centerBirthdayTv);
    }
}
