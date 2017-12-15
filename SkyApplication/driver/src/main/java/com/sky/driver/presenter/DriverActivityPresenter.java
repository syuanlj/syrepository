package com.sky.driver.presenter;

import android.content.Context;

import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.presenter.BasePresenter;
import com.sky.app.library.utils.Md5Util;
import com.sky.app.library.utils.OSSUtils;
import com.sky.app.library.utils.StringUtil;
import com.sky.app.library.utils.ThreadPoolManager;
import com.sky.driver.contract.UserContract;
import com.sky.driver.model.DriverModel;

/**
 * Created by sky on 2017/2/10.
 * 处理业务逻辑
 * 驾驶证
 */

public class DriverActivityPresenter extends BasePresenter<UserContract.IDriverView> implements UserContract.IDriverPresenter {

    private UserContract.IDriverModel iDriverModel;

    /**
     * 构造
     */
    public DriverActivityPresenter(Context context,
                                   UserContract.IDriverView iDriverView) {
        super(context, iDriverView);
        this.iDriverModel = new DriverModel(context, this);
    }

    @Override
    public void destroy() {
        if (null != iDriverModel){
            iDriverModel.destroy();
        }
        super.destroy();
    }

    @Override
    public void showSuccess() {
        getView().showSuccess();
    }

    @Override
    public void showError(String error) {
        getView().showError(error);
    }

    @Override
    public void saveImage() {
        iDriverModel.driver();
    }

    @Override
    public void showImageUrl(final String url) {
        ThreadPoolManager.getInstance().addTask(new Runnable() {

            @Override
            public void run() {
                OSSUtils.getInstance(context).uploadFile(Md5Util.md5(System.currentTimeMillis() + StringUtil.randStr(10)), url,
                        new OSSUtils.OSSCallBack() {
                            @Override
                            public void progress(PutObjectRequest request, long currentSize, long totalSize) {

                            }

                            @Override
                            public void onSuccess(final PutObjectRequest request, final PutObjectResult result) {
                                if (200 == result.getStatusCode()){
                                    getView().showDriverImage(Constants.Url.BASE_IMAGE_URL + request.getObjectKey());
                                }
                            }

                            @Override
                            public void onFailure() {
                                getView().showError("驾驶证上传失败");
                            }
                        });
            }
        });
    }
}