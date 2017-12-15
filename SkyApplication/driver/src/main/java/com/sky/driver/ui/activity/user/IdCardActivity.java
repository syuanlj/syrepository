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
import com.sky.app.library.utils.L;
import com.sky.app.library.utils.T;
import com.sky.driver.R;
import com.sky.driver.bean.AppKey;
import com.sky.driver.bean.UserBean;
import com.sky.driver.contract.UserContract;
import com.sky.driver.presenter.IdCardActivityPresenter;
import com.sky.driver.ui.activity.MainActivity;
import com.sky.driver.utils.AppDialogUtils;
import com.sky.driver.utils.TakePhotoUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 身份证认证【司机端】
 */
public class IdCardActivity extends BaseViewActivity<UserContract.IIdCardPresenter> implements UserContract.IIdCardView{

    @BindView(R.id.normal_toolbar)
    Toolbar toolbar;
    @BindView(R.id.driver_title)
    TextView driverTitle;
    @BindView(R.id.driver_front_btn)
    Button frontBtn;
    @BindView(R.id.driver_back_btn)
    Button backBtn;
    @BindView(R.id.driver_front_iv)
    ImageView frontIv;
    @BindView(R.id.driver_back_iv)
    ImageView backIv;

    private int flag = -1;//当前选中按钮的标示

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_idcard);
    }

    @Override
    protected UserContract.IIdCardPresenter presenter() {
        return new IdCardActivityPresenter(this, this);
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

    @Override
    public void showFrontImage(final String url) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                L.msg("身份证正面照片：" + url);
                UserBean.getInstance().setIdcard_front(url);
            }
        });
    }

    @Override
    public void showBackImage(final String url) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                L.msg("身份证反面照片：" + url);
                UserBean.getInstance().setIdcard_back(url);
            }
        });
    }

    @Override
    public void showSuccess() {
        startActivity(new Intent(context, DriverActivity.class));
    }

    @OnClick(R.id.driver_front_btn)
    void clickFrontBtn(){
        flag = 1;
        takePoto();
    }

    @OnClick(R.id.driver_back_btn)
    void clickBackBtn(){
        flag = 2;
        takePoto();
    }

    @OnClick(R.id.driver_next_btn)
    void clickNext(){
        getPresenter().saveImage();
    }

    @Override
    public void showError(String error) {
        super.showError(error);
        T.showShort(context, error);
    }

    @Override
    protected void onDestoryActivity() {

    }

    /**
     * 拍照
     */
    void takePoto(){
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
            L.msg("图片上传地址：" + msg);
            switch (flag){
                case 1://正面
                    ImageHelper.getInstance().displayDefinedImage(Uri.fromFile(new File(msg)).toString(), frontIv, R.mipmap.driver_person_icon, R.mipmap.driver_person_icon);
                    getPresenter().showImageUrl(msg, 1);
                    break;
                case 2://反面
                    ImageHelper.getInstance().displayDefinedImage(Uri.fromFile(new File(msg)).toString(), backIv, R.mipmap.driver_person_icon, R.mipmap.driver_person_icon);
                    getPresenter().showImageUrl(msg, 2);
                    break;
            }
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
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(context, MainActivity.class);
        i.putExtra(AppKey.HomePage.DRIVER_TAB_LABEL, AppKey.HomePage.mime);
        startActivity(i);
    }
}
