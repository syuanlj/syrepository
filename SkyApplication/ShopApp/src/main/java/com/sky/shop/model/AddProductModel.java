package com.sky.shop.model;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.model.BaseModel;
import com.sky.app.library.utils.http.HttpUtils;
import com.sky.shop.api.ApiService;
import com.sky.shop.bean.ProductCategoryIn;
import com.sky.shop.bean.ProductCategoryOut;
import com.sky.shop.bean.ProductDeatilResponse;
import com.sky.shop.bean.UserBean;
import com.sky.shop.contract.ProductContract;
import com.sky.shop.presenter.AddProductPresenter;

/**
 * 添加商品
 * Created by sky on 2017/2/18.
 */

public class AddProductModel extends BaseModel<AddProductPresenter> implements ProductContract.IAddProModel{

    public AddProductModel(Context context, AddProductPresenter addProductPresenter){
        super(context, addProductPresenter);
    }

    @Override
    public void requestAddProduct(ProductDeatilResponse productDeatilResponse) {
        productDeatilResponse.setUser_id(UserBean.getInstance().getCacheUid());
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .publishProduct(UserBean.getInstance().getCacheUid(), productDeatilResponse),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        getPresenter().responseAddProduct("操作商品成功");
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }
        ));
    }

    @Override
    public void requestProductCategory(ProductCategoryIn productCategoryIn) {
        productCategoryIn.setUser_id(UserBean.getInstance().getCacheUid());
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class).requestProductCategory(productCategoryIn),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        getPresenter().responseProductCategory(new Gson().fromJson(success, ProductCategoryOut.class));
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }
        ));
    }

    @Override
    public void requestEditProduct(ProductDeatilResponse productDeatilResponse) {
        productDeatilResponse.setUser_id(UserBean.getInstance().getCacheUid());
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .updateProduct(UserBean.getInstance().getCacheUid(), productDeatilResponse),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        ProductDeatilResponse productDeatilResponse1 = new Gson().fromJson(success, ProductDeatilResponse.class);
                        getPresenter().responseEditProduct(productDeatilResponse1.toString());
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }
        ));
    }
}
