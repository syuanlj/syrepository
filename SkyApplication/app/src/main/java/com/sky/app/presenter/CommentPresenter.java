package com.sky.app.presenter;

import android.content.Context;

import com.sky.app.bean.AddCommentInfo;
import com.sky.app.bean.UserBean;
import com.sky.app.contract.OrderContract;
import com.sky.app.library.base.presenter.BasePresenter;
import com.sky.app.model.CommentModel;

/**
 * Created by zhanglf on 2017/6/10.
 * 评论页面
 */
public class CommentPresenter extends BasePresenter<OrderContract.CommentView>
        implements OrderContract.CommentPresenter {

    private OrderContract.CommentModel mModel;

    /**
     * 构造
     */
    public CommentPresenter(Context context, OrderContract.CommentView iCommentView) {
        super(context, iCommentView);
        mModel = new CommentModel(context, this);
    }

    @Override
    public void destroy() {
        super.destroy();
        mModel.destroy();
    }

    @Override
    public void showError(String error) {
        getView().showError(error);
    }

    @Override
    public void getData(AddCommentInfo info) {
        info.setUser_id(UserBean.getInstance().getCacheUid());
        mModel.getData(info);
    }

    @Override
    public void getResult() {
        getView().Succec();
    }
}