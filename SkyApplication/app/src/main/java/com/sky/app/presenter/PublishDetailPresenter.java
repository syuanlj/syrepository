package com.sky.app.presenter;

import android.content.Context;

import com.sky.app.bean.CollectPubIn;
import com.sky.app.bean.ProductIn;
import com.sky.app.bean.SupplyDetail;
import com.sky.app.bean.UserBean;
import com.sky.app.contract.PublishContract;
import com.sky.app.library.base.presenter.BasePresenter;
import com.sky.app.model.PublishDetailModel;

/**
 * Created by sky on 2017/2/10.
 * 发布详情处理业务逻辑
 */
public class PublishDetailPresenter extends BasePresenter<PublishContract.IPublishDetailView>
        implements PublishContract.IPublishDetailPresenter {

    private PublishContract.IPublishDetailModel iPublishDetailModel;

    /**
     * 构造
     */
    public PublishDetailPresenter(Context context, PublishContract.IPublishDetailView iPublishDetailView) {
        super(context, iPublishDetailView);
        iPublishDetailModel = new PublishDetailModel(context, this);
    }

    @Override
    public void destroy() {
        super.destroy();
        iPublishDetailModel.destroy();
    }

    @Override
    public void showError(String error) {
        getView().showError(error);
    }

    @Override
    public void requestDetail(ProductIn productIn) {
        iPublishDetailModel.requestDetail(productIn);
    }

    @Override
    public void responseDetail(SupplyDetail supplyDetail) {
        getView().responseDetail(supplyDetail);
    }

    @Override
    public void requestCollect(CollectPubIn collectPubIn) {
        collectPubIn.setUser_id(UserBean.getInstance().getCacheUid());
        iPublishDetailModel.requestCollect(collectPubIn);
    }

    @Override
    public void responseCollect(String msg) {
        getView().responseCollect(msg);
    }
}