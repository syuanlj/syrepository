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
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.ui.BaseViewActivity;
import com.sky.app.library.utils.ImageHelper;
import com.sky.app.library.utils.T;
import com.sky.driver.R;
import com.sky.driver.bean.AppKey;
import com.sky.driver.bean.UserBean;
import com.sky.driver.contract.UserContract;
import com.sky.driver.presenter.DriverActivityPresenter;
import com.sky.driver.ui.activity.MainActivity;
import com.sky.driver.utils.AppDialogUtils;
import com.sky.driver.utils.TakePhotoUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 驾驶证认证【司机端】
 */
public class DriverActivity extends BaseViewActivity<UserContract.IDriverPresenter> implements UserContract.IDriverView {

    @BindView(R.id.normal_toolbar)
    Toolbar toolbar;
    @BindView(R.id.driver_title)
    TextView driverTitle;
    @BindView(R.id.driver_driver_iv)
    ImageView driverIv;
    @BindView(R.id.driver_front_btn)
    Button frontBtn;
    @BindView(R.id.driver_submit_btn)
    TextView submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_driver);
    }

    @Override
    protected UserContract.IDriverPresenter presenter() {
        return new DriverActivityPresenter(this, this);
    }

    @Override
    protected void init() {
        driverTitle.setText(R.string.driver_driver_string);
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

    @OnClick(R.id.driver_front_btn)
    void clickUploadImage(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    Constants.Permission.CAMERA_PERMISSION);
        }else{
            AppDialogUtils.showTakePhotoView(this);
        }
    }

    @OnClick(R.id.driver_submit_btn)
    void clickSubmit(){
        getPresenter().saveImage();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final String msg = TakePhotoUtils.getInstance(this).onActivityResult(requestCode, resultCode, data, true);
        if (!TextUtils.isEmpty(msg)){
            ImageHelper.getInstance().displayDefinedImage(Uri.fromFile(new File(msg)).toString(), driverIv,
                    R.mipmap.driver_person_icon, R.mipmap.driver_person_icon);
            getPresenter().showImageUrl(msg);
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

    @Override
    public void showError(String error) {
        super.showError(error);
        T.showShort(context, error);
    }

    @Override
    protected void onDestoryActivity() {

    }

    @Override
    public void showDriverImage(final String url) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                UserBean.getInstance().setDriver_license(url);
            }
        });
    }

    @Override
    public void showSuccess() {
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra(AppKey.HomePage.DRIVER_TAB_LABEL, AppKey.HomePage.mime);
        startActivity(i);
        finish();
    }
}
