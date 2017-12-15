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
import com.sky.driver.model.IdCardModel;

/**
 * Created by sky on 2017/2/10.
 * 处理业务逻辑
 * 身份证
 */

public class IdCardActivityPresenter extends BasePresenter<UserContract.IIdCardView>
        implements UserContract.IIdCardPresenter {

    private UserContract.IIdCardModel iIdCardModel;

    /**
     * 构造
     */
    public IdCardActivityPresenter(Context context,
                                   UserContract.IIdCardView iIdCardView) {
        super(context, iIdCardView);
        this.iIdCardModel = new IdCardModel(context, this);
    }

    @Override
    public void destroy() {
        if (null != iIdCardModel){
            iIdCardModel.destroy();
        }
        super.destroy();
    }

    @Override
    public void showImageUrl(final String url, final int flag) {
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
                                    switch (flag){
                                        case 1://正面
                                            getView().showFrontImage(Constants.Url.BASE_IMAGE_URL + request.getObjectKey());
                                            break;
                                        case 2://反面
                                            getView().showBackImage(Constants.Url.BASE_IMAGE_URL + request.getObjectKey());
                                            break;
                                    }
                                }
                            }

                            @Override
                            public void onFailure() {
                                getView().showError("身份证上传失败");
                            }
                        });
            }
        });
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
        iIdCardModel.idCard();
    }
}