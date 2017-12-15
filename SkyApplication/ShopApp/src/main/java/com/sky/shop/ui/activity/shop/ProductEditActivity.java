package com.sky.shop.ui.activity.shop;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.ui.BaseViewActivity;
import com.sky.app.library.utils.AppUtils;
import com.sky.app.library.utils.DialogUtils;
import com.sky.app.library.utils.T;
import com.sky.shop.R;
import com.sky.shop.adaptor.ProductImageGridviewAdapter;
import com.sky.shop.bean.ProductAttribute;
import com.sky.shop.bean.ProductCategory;
import com.sky.shop.bean.ProductCategoryIn;
import com.sky.shop.bean.ProductDeatilResponse;
import com.sky.shop.bean.ProductDetailRequest;
import com.sky.shop.contract.ImageContract;
import com.sky.shop.contract.ProductContract;
import com.sky.shop.custom.AutoHeightGridView;
import com.sky.shop.custom.CategoryMenuDialog;
import com.sky.shop.presenter.AddProductPresenter;
import com.sky.shop.presenter.ProductDetailPresenter;
import com.sky.shop.presenter.UploadImagePresenter;
import com.sky.shop.utils.AppDialogUtils;
import com.sky.shop.utils.TakePhotoUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 添加商品
 */
public class ProductEditActivity extends BaseViewActivity<ProductContract.IAddProPresenter>
        implements ProductContract.IAddProView, ImageContract.IUploadView, ProductContract.IProDetailView {

    @BindView(R.id.normal_toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_title)
    TextView title;

    @BindView(R.id.product_price_now)
    EditText productPriceNow;
    @BindView(R.id.product_price_old)
    EditText productPriceOld;
    @BindView(R.id.app_product_name)
    EditText productName;
    @BindView(R.id.app_store_activity)
    EditText storeActivity;
    @BindView(R.id.app_product_desc)
    EditText productDesc;
    @BindView(R.id.other_flag)
    Switch otherFlag;

    @BindView(R.id.attr_list)
    TableLayout attrList;
    @BindView(R.id.gridview)
    AutoHeightGridView autoHeightGridView;

    private ProductImageGridviewAdapter productImageGridviewAdapter;
    private ImageContract.IUploadPresenter iUploadPresenter;
    private List<ProductCategory> productCategories;

    ProductDeatilResponse productDeatilResponse = new ProductDeatilResponse();

    List<String> imageData = new ArrayList<String>();
    List<ProductAttribute> attrs = null;

    private String product_id;

    private ProductContract.IProDetailPresenter iProDetailPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_add_product);
    }

    @Override
    protected ProductContract.IAddProPresenter presenter() {
        iUploadPresenter = new UploadImagePresenter(context, this);
        iProDetailPresenter = new ProductDetailPresenter(context, this);
        return new AddProductPresenter(context, this);
    }

    @Override
    protected void initViewsAndEvents() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        productImageGridviewAdapter = new ProductImageGridviewAdapter(imageData, context);
        autoHeightGridView.setAdapter(productImageGridviewAdapter);
        autoHeightGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (position < 4 && position == parent.getChildCount() - 1) {
                    if (ContextCompat.checkSelfPermission(ProductEditActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(ProductEditActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                Constants.Permission.CAMERA_PERMISSION);
                    } else {
                        AppDialogUtils.showTakePhotoView(ProductEditActivity.this);
                    }
                }

            }
        });
    }

    @Override
    protected void init() {
        title.setText(R.string.app_edit_product_string);
        toolbar.setNavigationIcon(R.mipmap.app_back_arrow_icon);

        product_id = getIntent().getStringExtra("product_id");
        ProductDetailRequest productDetailRequest = new ProductDetailRequest();
        productDetailRequest.setProduct_id(product_id);
        iProDetailPresenter.getProductDetail(productDetailRequest);

        //请求商品分类
        ProductCategoryIn productCategoryIn = new ProductCategoryIn();
        productCategoryIn.setLevel("1");
        getPresenter().requestProductCategory(productCategoryIn);


        productDesc.append(Html.fromHtml("<img src="+ productDeatilResponse.getProduct_image_urls() +"/>", null, null));

//        productDesc.setText(Html.fromHtml(productDeatilResponse.getProduct_image_urls().toString()));

    }


    @Override
    public void getImageUrl(String url) {
        imageData.add(url);
        productImageGridviewAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final String msg = TakePhotoUtils.getInstance(this).onActivityResult(requestCode, resultCode, data, true);
        if (!TextUtils.isEmpty(msg)) {
            iUploadPresenter.uploadFile(msg);
            productImageGridviewAdapter.notifyDataSetChanged();
        }
    }

    @OnClick(R.id.add_price_attr)
    void clickPriceAttr() {
        addRow();
    }

    @Override
    public void showError(String error) {
        super.showError(error);
        T.showShort(context, error);
    }

    @Override
    protected void onDestoryActivity() {

    }

    @Override
    public void showAddSuccess(String msg) {
        T.showShort(context, msg);
        finish();
    }

    @OnClick(R.id.confirm)
    void clickConfirm() {
        if (null == imageData || imageData.isEmpty()) {
            T.showShort(context, "请上传相应的商品图片");
            return;
        }
        if (TextUtils.isEmpty(productPriceNow.getText().toString().trim())) {
            T.showShort(context, "请输入商品价格");
            return;
        }
        if (TextUtils.isEmpty(productPriceOld.getText().toString().trim())) {
            T.showShort(context, "请输入商品原价");
            return;
        }
        if (TextUtils.isEmpty(productName.getText().toString().trim())) {
            T.showShort(context, "请输入商品名称");
            return;
        }
        if (TextUtils.isEmpty(storeActivity.getText().toString().trim())) {
            T.showShort(context, "请输入店铺活动");
            return;
        }
        if (TextUtils.isEmpty(productDesc.getText().toString().trim())) {
            T.showShort(context, "请输入商品描述");
            return;
        }
        boolean tag = true;
        attrs = new ArrayList<>();
        for (int i = 0; i < attrList.getChildCount(); i++) {
            ProductAttribute productAttribute = new ProductAttribute();
            TableRow layout = (TableRow) attrList.getChildAt(i);
            EditText app_attr_name = (EditText) layout.findViewById(R.id.app_attr_name);
            EditText app_price = (EditText) layout.findViewById(R.id.app_price);
            EditText app_num = (EditText) layout.findViewById(R.id.app_num);
            if (TextUtils.isEmpty(app_attr_name.getText().toString().trim())) {
                T.showShort(context, "属性名称不能为空");
                tag = false;
            } else {
                productAttribute.setAttr_name(app_attr_name.getText().toString().trim());
            }
            if (TextUtils.isEmpty(app_price.getText().toString().trim())) {
                T.showShort(context, "金额不能为空");
                tag = false;
            } else {
                productAttribute.setAttr_price_now(Double.parseDouble(app_price.getText().toString()) * 100);
            }
            if (TextUtils.isEmpty(app_num.getText().toString().trim())) {
                T.showShort(context, "数量不能为空");
                tag = false;
            } else {
                productAttribute.setInventory_num(Integer.parseInt(app_num.getText().toString()));
            }
            attrs.add(productAttribute);
        }
        if (null == attrs || attrs.isEmpty() || !tag) {
            T.showShort(context, "请输入相应商品价格属性");
            return;
        }
        productDeatilResponse.setProduct_image_urls(imageData);
        productDeatilResponse.setProduct_name(productName.getText().toString().trim());
        productDeatilResponse.setProduct_desc(productDesc.getText().toString().trim());
        productDeatilResponse.setAttrs(attrs);
        productDeatilResponse.setOther_flag(otherFlag.isChecked() ? 1 : 0);
        productDeatilResponse.setProduct_key_words(storeActivity.getText().toString().trim());
        productDeatilResponse.setProduct_price_now(Double.parseDouble(productPriceNow.getText().toString().trim())*100);
        productDeatilResponse.setProduct_price_old(Double.parseDouble(productPriceOld.getText().toString().trim())*100);
        getPresenter().requestAddProduct(productDeatilResponse);
//        productDeatilResponse.setProduct_image_urls(imageData);
//        productDeatilResponse.setProduct_name(productName.getText().toString().trim());
//        productDeatilResponse.setAttrs(attrs);
//        productDeatilResponse.setOther_flag(otherFlag.isChecked() ? 1 : 0);
//        productDeatilResponse.setProduct_key_words(storeActivity.getText().toString().trim());
//        productDeatilResponse.setProduct_price_now(Double.parseDouble(productPriceNow.getText().toString().trim()) * 100);
//        productDeatilResponse.setProduct_price_old(Double.parseDouble(productPriceOld.getText().toString().trim()) * 100);
//        getPresenter().requestAddProduct(productDeatilResponse);


    }

    @OnClick(R.id.choose_product_category)
    void clickCategory() {
        new CategoryMenuDialog(context, productCategories, new CategoryMenuDialog.CallBack() {
            @Override
            public void data(String one, String two) {
                productDeatilResponse.setOne_dir_id(one);
                productDeatilResponse.setTwo_dir_id(two);
            }
        }).show();
    }

    @Override
    public void responseProductCategory(List<ProductCategory> productCategories) {
        this.productCategories = productCategories;
    }

    @Override
    public void responseEditSuccess(String msg) {
        T.showShort(context, msg);
        finish();
    }

    /**
     * 添加行
     */
    private void addRow() {
        TableRow tableRow = (TableRow) LayoutInflater.from(context).inflate(R.layout.app_add_product_item, null);
        tableRow.findViewById(R.id.del).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TableRow tableRow = (TableRow) v.getParent();
                attrList.removeView(tableRow);
            }
        });
        attrList.addView(tableRow);
    }

    @Override
    public void productDetailSuccess(ProductDeatilResponse productList) {
        imageData.addAll(productList.getProduct_image_urls());
        productImageGridviewAdapter.notifyDataSetChanged();

        productPriceNow.setText(AppUtils.parseDouble(productList.getProduct_price_now() / 100));
        productPriceOld.setText(AppUtils.parseDouble(productList.getProduct_price_old() / 100));
        productName.setText(productList.getProduct_name());
        storeActivity.setText(productList.getProduct_key_words());
        productDesc.setText(productList.getProduct_desc());
        productDeatilResponse.setOne_dir_id(productList.getOne_dir_id());
        productDeatilResponse.setTwo_dir_id(productList.getTwo_dir_id());

        for (ProductAttribute productAttribute : productList.getAttrs()) {
            TableRow tableRow = (TableRow) LayoutInflater.from(context).inflate(R.layout.app_add_product_item, null);
            ((EditText) tableRow.findViewById(R.id.app_attr_name)).setText(productAttribute.getAttr_name());
            ((EditText) tableRow.findViewById(R.id.app_price)).setText(AppUtils.parseDouble(productAttribute.getAttr_price_now() / 100));
            ((EditText) tableRow.findViewById(R.id.app_num)).setText(productAttribute.getInventory_num() + "");
            tableRow.findViewById(R.id.del).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TableRow tableRow = (TableRow) v.getParent();
                    attrList.removeView(tableRow);
                }
            });
            attrList.addView(tableRow);
        }
        productDesc.setText(productList.getProduct_desc());
        otherFlag.setChecked(1 == productList.getOther_flag() ? true : false);
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
}
