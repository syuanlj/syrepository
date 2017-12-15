package com.sky.transport.presenter;

import android.content.Context;

import com.sky.app.library.base.presenter.BasePresenter;
import com.sky.transport.bean.UpdateIn;
import com.sky.transport.bean.UpdateOut;
import com.sky.transport.contract.UpdateContract;
import com.sky.transport.model.UpdateModel;

/**
 * Created by sky on 2017/2/10.
 * 选择区域
 */
public class UpdatePresenter extends BasePresenter<UpdateContract.IUpdateView>
        implements UpdateContract.IUpdatePresenter {

    private UpdateContract.IUpdateModel iUpdateModel;

    /**
     * 构造
     */
    public UpdatePresenter(Context context, UpdateContract.IUpdateView iUpdateView) {
        super(context, iUpdateView);
        iUpdateModel = new UpdateModel(context, this);
    }

    @Override
    public void destroy() {
        super.destroy();
        iUpdateModel.destroy();
    }

    @Override
    public void showError(String error) {
        getView().showError(error);
        getView().hideProgress();
    }

    @Override
    public void showDownloadSuccess(UpdateOut updateOut) {
        getView().showDownloadSuccess(updateOut);
        getView().hideProgress();
    }

    @Override
    public void getVersion(UpdateIn updateIn) {
        getView().showProgress();
        iUpdateModel.getVersion(updateIn);
    }
}