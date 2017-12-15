package com.sky.app.model;

import android.content.Context;
import com.sky.app.api.ApiService;
import com.sky.app.bean.AddCommentInfo;
import com.sky.app.bean.UserBean;
import com.sky.app.contract.OrderContract;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.model.BaseModel;
import com.sky.app.library.utils.http.HttpUtils;
import com.sky.app.presenter.CommentPresenter;

/**
 * Created by zhanglf on 2017/6/10.
 * 评论页面
 */

public class CommentModel extends BaseModel<CommentPresenter> implements OrderContract.CommentModel{

    public CommentModel(Context context, CommentPresenter commentPresenter){
        super(context, commentPresenter);
    }
    @Override
    public void getData(AddCommentInfo info) {
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .addComment(UserBean.getInstance().getCacheUid(), info),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        getPresenter().getResult();
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }
        ));
    }
}
