package com.sky.app.ui.activity.seller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.sky.app.R;
import com.sky.app.bean.CollectPubIn;
import com.sky.app.bean.ProductCategory;
import com.sky.app.bean.ProductCategoryIn;
import com.sky.app.bean.ProductIntroduceIn;
import com.sky.app.bean.ProductIntroduceOut;
import com.sky.app.bean.ProductResponse;
import com.sky.app.bean.SearchProductRequest;
import com.sky.app.bean.Seller;
import com.sky.app.bean.ShopProductDetail;
import com.sky.app.contract.ShopContract;
import com.sky.app.library.base.ui.BaseViewActivity;
import com.sky.app.library.utils.ImageHelper;
import com.sky.app.library.utils.T;
import com.sky.app.presenter.CollectPresenter;
import com.sky.app.presenter.ShopCenterPresenter;
import com.sky.app.ui.activity.openIM.OpenIMLoginActivity;
import com.sky.app.ui.activity.product.ProductIntroduceActivity;
import com.sky.app.ui.adapter.ShopCenterPagerAdapter;
import com.sky.app.ui.custom.CategoryMenuDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.sky.app.R.id.app_edit_content;
import static com.sky.app.R.id.app_search_tv;

/**
 * 商铺个人中心
 */
public class ShopCenterActivity extends BaseViewActivity<ShopContract.IShopCenterPresenter>
        implements ShopContract.IShopCenterView, ShopContract.ICollectionView {

    @BindView(R.id.app_search_toolbar)
    Toolbar toolbar;
    @BindView(app_edit_content)
    EditText editContent;

    @BindView(R.id.app_shop_list)
    ViewPager viewPager;
    @BindView(R.id.app_category_list)
    TabLayout tabLayout;

    @BindView(R.id.shop_background)
    ImageView shopBackground;
    //    @BindView(R.id.shop_img)
//    ImageView shopImg;
//    @BindView(R.id.app_name)
//    TextView appName;
    @BindView(R.id.shop_collect)
    Button shopCollect;

    private ShopCenterPagerAdapter pagerAdapter;
    private ShopContract.ICollectionPresenter iCollectionPresenter;
    private List<ProductCategory> productCategories;
    private SearchProductRequest searchProductRequest = new SearchProductRequest();

    private String seller_id;
    private ProductIntroduceOut productIntroduceOut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_shop_center);

    }


    @Override
    protected ShopContract.IShopCenterPresenter presenter() {
        iCollectionPresenter = new CollectPresenter(context, this);
        return new ShopCenterPresenter(context, this);
    }

    @Override
    protected void init() {
        toolbar.setNavigationIcon(R.mipmap.app_back_arrow_icon);
        editContent.setHint(R.string.app_search_shop_string);
        this.seller_id = getIntent().getStringExtra("seller_id");
    }

    @Override
    public void initViewsAndEvents() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //请求商品分类
        ProductCategoryIn productCategoryIn = new ProductCategoryIn();
        productCategoryIn.setLevel("1");
        productCategoryIn.setUser_id(seller_id);
        getPresenter().requestProductCategory(productCategoryIn);

        //请求店铺信息
        ProductIntroduceIn productIntroduceIn = new ProductIntroduceIn();
        productIntroduceIn.setUser_id(seller_id);
        getPresenter().requestDescData(productIntroduceIn);
    }


    @Override
    public void showError(String error) {
        super.showError(error);
        T.showShort(context, error);
    }

    @Override
    protected void onDestoryActivity() {

    }

    private int tag = -1;

    @OnClick(R.id.shop_collect)
    void clickCollect() {
        if (null == productIntroduceOut) {
            T.showShort(context, "数据错误");
            return;
        }
        CollectPubIn collectPubIn = new CollectPubIn();
        collectPubIn.setType("2");
        collectPubIn.setCollect_value(seller_id);
        if ("收藏店铺".equals(shopCollect.getText())) {
            tag = 1;
            iCollectionPresenter.requestCollect(collectPubIn);
        } else {
            tag = 2;
            iCollectionPresenter.cancelCollect(collectPubIn);
        }
    }

    @Override
    public void showCollectView(String msg) {
        T.showShort(context, msg);
        if (tag == 1) {
            shopCollect.setText("已收藏");
        } else if (tag == 2) {
            shopCollect.setText("收藏店铺");
        }
    }

    @Override
    public void showCollectError(String error) {
        T.showShort(context, error);
        if (tag == 1) {
            shopCollect.setText("收藏店铺");
        } else if (tag == 2) {
            shopCollect.setText("已收藏");
        }
    }

    @Deprecated
    @Override
    public void showHomeResponse(List<ShopProductDetail> list) {

    }

    @Deprecated
    @Override
    public void getRefreshData(List<ProductResponse> list) {

    }

    @Deprecated
    @Override
    public void getLoadMoreData(List<ProductResponse> list) {

    }

    @Override
    public void responseProductCategory(List<ProductCategory> productCategories) {
        this.productCategories = productCategories;
    }

    @Override
    public void responseDescData(ProductIntroduceOut productIntroduceOut) {
        this.productIntroduceOut = productIntroduceOut;

        ImageHelper.getInstance().displayDefinedImage(productIntroduceOut.getPersonal_pic(),
                shopBackground, R.mipmap.app_default_icon_1, R.mipmap.app_default_icon_1);
//        ImageHelper.getInstance().displayDefinedImage(productIntroduceOut.getPic_url(),
//                shopImg, R.mipmap.app_default_icon, R.mipmap.app_default_icon);

//        appName.setText(productIntroduceOut.getNick_name());
        if (1 == productIntroduceOut.getIs_collect()) {
            shopCollect.setText("已收藏");
        } else {
            shopCollect.setText("收藏店铺");
        }

        Seller seller = new Seller();
        seller.setUser_id(seller_id);
        pagerAdapter = new ShopCenterPagerAdapter(getSupportFragmentManager(), seller, searchProductRequest);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
    }

    @OnClick(R.id.shop_category)
    void clickCategory() {
        if (null == productCategories || productCategories.isEmpty()) {
            T.showShort(context, "还没有商品分类");
            return;
        }
        new CategoryMenuDialog(context, productCategories, new CategoryMenuDialog.CallBack() {
            @Override
            public void data(String one, String two) {
                searchProductRequest.setOne_dir_id(one);
                searchProductRequest.setTwo_dir_id(two);
                searchProductRequest.setProduct_name("");
                sendData(searchProductRequest);
            }
        }).show();
    }

    @OnClick(R.id.shop_introduce)
    void clickIntroduce() {
        Intent i = new Intent(context, ProductIntroduceActivity.class);
        i.putExtra("seller_id", seller_id);
        startActivity(i);
    }

    @OnClick(R.id.shop_contact)
//联系商家
    void clickContact() {
//        getLogin();
        SharedPreferences sp = getSharedPreferences("seller_id", Context.MODE_PRIVATE);
        sp.edit().putString("seller_id", seller_id).commit();
        Intent intent = new Intent(context, OpenIMLoginActivity.class);
        startActivity(intent);
    }


    @OnClick(app_search_tv)
    void clickSearch() {
        searchProductRequest.setProduct_name(editContent.getText().toString().trim());
        searchProductRequest.setTwo_dir_id("");
        searchProductRequest.setOne_dir_id("");
        sendData(searchProductRequest);
    }


    /**
     * 发送广播数据
     */
    private void sendData(SearchProductRequest searchProductRequest) {
        Intent intent = new Intent();
        intent.setAction("com.sky.app.refresh");
        intent.putExtra("product_name", searchProductRequest.getProduct_name());
        intent.putExtra("one_dir_id", searchProductRequest.getOne_dir_id());
        intent.putExtra("two_dir_id", searchProductRequest.getTwo_dir_id());
        sendBroadcast(intent);
        tabLayout.getTabAt(1).select();
    }
}
