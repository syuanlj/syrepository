package com.sky.app.ui;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.sky.app.R;
import com.sky.app.bean.UpdateIn;
import com.sky.app.bean.UpdateOut;
import com.sky.app.contract.UpdateContract;
import com.sky.app.library.base.ui.BaseViewActivity;
import com.sky.app.library.utils.AppUtils;
import com.sky.app.library.utils.DialogUtils;
import com.sky.app.library.utils.DownloadUtil;
import com.sky.app.library.utils.T;
import com.sky.app.presenter.UpdatePresenter;
import com.sky.app.ui.activity.MainActivity;
import com.sky.app.utils.ApkDownloadManager;

/**
 * 闪屏
 */
public class SplashActivity extends BaseViewActivity<UpdateContract.IUpdatePresenter>
        implements UpdateContract.IUpdateView{

    private String packageUrl = "/com.sky.app/package/";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash_layout);
    }

    @Override
    protected UpdateContract.IUpdatePresenter presenter() {
        return new UpdatePresenter(context, this);
    }

    @Override
    protected void initViewsAndEvents() {
        if (ContextCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(SplashActivity.this,
                    new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
    }

    @Override
    protected void init() {
        UpdateIn updateIn = new UpdateIn();
        updateIn.setApp_type("android_gj");//工匠
        getPresenter().getVersion(updateIn);
    }

    @Override
    protected void onDestoryActivity() {

    }

    @Override
    public void showDownloadSuccess(final UpdateOut updateOut) {


        if (null != updateOut && AppUtils.getAppVersion(this) < Double.parseDouble(updateOut.getVersion())){
            //需要升级家
            if (TextUtils.isEmpty(updateOut.getDownload_url())) {//有下载地址
                T.showShort(context, "下载地址错误");
                return;
            }
            new AlertDialog.Builder(this).setTitle("软件升级" + AppUtils.getVersionName(context) + "("
                    + AppUtils.getAppVersion(context) + ")")
                    .setPositiveButton("升级", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(final DialogInterface dialog, int which) {
                            DialogUtils.showCancelLoading(SplashActivity.this, "正在升级中，请稍后...");
                            DownloadUtil.get().download(updateOut.getDownload_url(),
                                    packageUrl, new DownloadUtil.OnDownloadListener() {
                                @Override
                                public void onDownloadSuccess() {
                                    DialogUtils.hideLoading();
                                    AppUtils.installApk(context, DownloadUtil.get().localPath(
                                            updateOut.getDownload_url(), packageUrl));
                                }
                                @Override
                                public void onDownloading(int progress) {
//                                    progressBar.setProgress(progress);
                                }
                                @Override
                                public void onDownloadFailed() {
                                    DialogUtils.hideLoading();
                                    T.showShort(context, "下载失败");
                                }
                            });
                            ApkDownloadManager.getInstance(context).startDownload(updateOut.getDownload_url());
                        }

                    }).setCancelable(false).show();
        } else {
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    Intent intent = new Intent();
                    intent.setClass(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, 1000);
        }
    }

    @Override
    public void showError(String error) {
        super.showError(error);
//        Intent intent = new Intent(context, MainActivity.class);
//        startActivity(intent);

        T.showShort(context, error);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "权限被拒绝", Toast.LENGTH_SHORT).show();
                finish();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
