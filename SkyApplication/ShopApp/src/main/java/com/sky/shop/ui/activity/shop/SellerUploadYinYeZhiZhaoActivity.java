package com.sky.shop.ui.activity.shop;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.ui.BaseViewActivity;
import com.sky.app.library.utils.ImageHelper;
import com.sky.app.library.utils.T;
import com.sky.shop.R;
import com.sky.shop.bean.SellMessageComplete;
import com.sky.shop.bean.SellUpload;
import com.sky.shop.bean.UserBean;
import com.sky.shop.contract.ShopContract;
import com.sky.shop.presenter.activity.UploadActivityPresenter;
import com.sky.shop.ui.activity.user.LoginActivity;
import com.sky.shop.utils.AppDialogUtils;
import com.sky.shop.utils.TakePhotoUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

public class SellerUploadYinYeZhiZhaoActivity extends BaseViewActivity<UploadActivityPresenter>
        implements ShopContract.IUploadView {

    @BindView(R.id.app_title)
    TextView appTitle;
    @BindView(R.id.normal_toolbar)
    Toolbar normalToolbar;
    @BindView(R.id.sell_upload_yingye_pic)
    ImageView sellUploadYingyePic;
    @BindView(R.id.sell_upload_zhengmian)
    FrameLayout sellUploadZhengmian;
    @BindView(R.id.activity_seller_upload_sheng_fen_zhen)
    LinearLayout activitySellerUploadShengFenZhen;
    private String yinye;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_upload_yinyezhizhao);
        normalToolbar.setNavigationIcon(R.mipmap.app_back_arrow_icon);
        normalToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        appTitle.setText("实名认证");
    }

    @Override
    protected UploadActivityPresenter presenter() {
        return new UploadActivityPresenter(this, this);
    }

    @Override
    protected void initViewsAndEvents() {

    }

    @Override
    protected void init() {

    }

    @OnClick({R.id.sell_upload_yingye_pic, R.id.sell_upload_next_buttom})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sell_upload_yingye_pic:
                choosePicture();
                break;
            case R.id.sell_upload_next_buttom:
                if(TextUtils.isEmpty(yinye)){
                    T.showShort(SellerUploadYinYeZhiZhaoActivity.this,"请上传照片");
                    return;
                }else{
                    SellMessageComplete upload = (SellMessageComplete) getIntent().getSerializableExtra("upload");
                    SellUpload sellUpload = new SellUpload();
                    sellUpload.setBusiness_license(yinye);
                    upload.setOther_desc(new Gson().toJson(sellUpload));
                    getPresenter().sellRegister(upload);
                }

                break;
        }
    }

    @Override
    protected void onDestoryActivity() {

    }

    private void choosePicture() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission
                            .WRITE_EXTERNAL_STORAGE},
                    Constants.Permission.CAMERA_PERMISSION);
        } else {
            AppDialogUtils.showTakePhotoView(SellerUploadYinYeZhiZhaoActivity.this);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        final String msg = TakePhotoUtils.getInstance(this).onActivityResult(requestCode,
                resultCode, data, true);
        if (!TextUtils.isEmpty(msg)) {
            ImageHelper.getInstance().displayDefinedImage(Uri.fromFile(new File(msg)).toString(),
                    sellUploadYingyePic,
                    R.mipmap.app_person_icon, R.mipmap.app_person_icon);
            getPresenter().uploadFirstFile(msg);

        }
    }


    @Override
    public void getFirstImageUrl(String url) {
        Log.e("sssssssssss", "getFirstImageUrl: " + url);
        yinye = url;
    }

    @Override
    public void getSecondImageUrl(String url) {
        Log.e("sssssssssss", "getSecondImageUrl: " + url);
    }

    @Override
    public void registerSuccess(UserBean userBean) {
            T.showShort(SellerUploadYinYeZhiZhaoActivity.this,"商户注册成功");
        Intent i = new Intent(context, LoginActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void showError(String error) {
        super.showError(error);
        T.showShort(context, error);
    }
}
