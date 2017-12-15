package com.sky.shop.ui.activity.shop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sky.app.library.base.ui.BaseViewActivity;
import com.sky.app.library.utils.DialogUtils;
import com.sky.app.library.utils.T;
import com.sky.shop.R;
import com.sky.shop.adaptor.AreaGridviewAdapter;
import com.sky.shop.adaptor.RegisterAreaGridviewAdapter;
import com.sky.shop.adaptor.SeconCategoryViewPagerAdapter;
import com.sky.shop.adaptor.SecondCategoryGridviewAdapter;
import com.sky.shop.bean.AreaList;
import com.sky.shop.bean.Category;
import com.sky.shop.bean.CategoryList;
import com.sky.shop.bean.DecorationCityList;
import com.sky.shop.bean.SellMessageComplete;
import com.sky.shop.contract.ShopContract;
import com.sky.shop.custom.AutoHeightGridView;
import com.sky.shop.custom.AutoHeightViewPager;
import com.sky.shop.presenter.activity.SearchByPlaceActivityPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SellerSecondCategoryActivity extends BaseViewActivity<ShopContract
        .ISearchByPlacePresenter>
        implements ShopContract.ISearchByPlace {

    @BindView(R.id.second_category_viewpager)
    AutoHeightViewPager secondCategoryViewpager;

    @BindView(R.id.dot_container)
    LinearLayout dotContainer;
    @BindView(R.id.seller_second_category_gridview_area)
    AutoHeightGridView sellerSecondCategoryGridviewArea;
    @BindView(R.id.seller_second_category_gridview_decoratio)
    AutoHeightGridView sellerSecondCategoryGridviewDecoratio;
    @BindView(R.id.seller_second_category_next)
    TextView sellerSecondCategoryNext;
    @BindView(R.id.app_title)
    TextView appTitle;
    @BindView(R.id.normal_toolbar)
    Toolbar normalToolbar;
    @BindView(R.id.activity_seller_second_category)
    LinearLayout activitySellerSecondCategory;
//    @BindView(R.id.second_categroy_tv)
//    TextView secondCategroyTv;
//    @BindView(R.id.second_categroy_reala)
//    RelativeLayout secondCategroyReala;
    @BindView(R.id.area_tv)
    TextView areaTv;
    @BindView(R.id.area_reala)
    RelativeLayout areaReala;
    @BindView(R.id.decoration_tv)
    TextView decorationTv;
    @BindView(R.id.decoration_reala)
    RelativeLayout decorationReala;
    @BindView(R.id.company_type_rl)
    RelativeLayout company_type_rl;
    @BindView(R.id.company_type_tv)
    TextView company_type_tv;
    @BindView(R.id.app_keys_word)
    LinearLayout keys;

    @BindView(R.id.company_type)
    AutoHeightGridView autoHeightGridView;

    private String one_dir_id;
    private String one_dir_id_name;
    private String three_dir_id;
    private String decorationName;
    private String company;
    private String companyName;
    private String type;
    private List<String> keysString = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_second_category);
    }

    @Override
    protected ShopContract.ISearchByPlacePresenter presenter() {
        return new SearchByPlaceActivityPresenter(this, this);
    }

    @Override
    protected void initViewsAndEvents() {
    }

    @Override
    protected void init() {
        appTitle.setText("选择行业分类");
        normalToolbar.setNavigationIcon(R.mipmap.app_back_arrow_icon);
        normalToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getPresenter().getData();
        SellMessageComplete upload = (SellMessageComplete) getIntent().getSerializableExtra
                ("upload");
        type = getIntent().getStringExtra("type");
        getPresenter().getSecondCatogoryData(upload.getOne_dir_id());
        getPresenter().requestCompanyType();
    }

    @Override
    public void success(final AreaList areaList) {
        ArrayList<String> areaName = new ArrayList<>();
        for (int i = 0; i < areaList.getList().size(); i++) {
            areaName.add(areaList.getList().get(i).getTwo_dir_name());
        }
        sellerSecondCategoryGridviewArea.setAdapter(new AreaGridviewAdapter(areaName, SellerSecondCategoryActivity.this));
        sellerSecondCategoryGridviewArea.setOnItemClickListener(new AdapterView
                .OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                one_dir_id = areaList.getList().get(position).getOne_dir_id();
                one_dir_id_name = areaList.getList().get(position).getTwo_dir_name();
                areaReala.setVisibility(View.VISIBLE);
                areaTv.setText(one_dir_id_name);

                getPresenter().getDecrationCity(areaList.getList().get(position).getTwo_dir_id());
                decorationReala.setVisibility(View.GONE);
                decorationTv.setText("");
            }
        });
    }

    @Override
    public void successDecrationCity(final DecorationCityList categoryList) {
        ArrayList<String> areaName = new ArrayList<>();
        for (int i = 0; i < categoryList.getList().size(); i++) {
            areaName.add(categoryList.getList().get(i).getThree_dir_name());
        }
        sellerSecondCategoryGridviewDecoratio.setAdapter(new AreaGridviewAdapter(areaName,
                SellerSecondCategoryActivity.this));

        sellerSecondCategoryGridviewDecoratio.setOnItemClickListener(new AdapterView
                .OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                three_dir_id = categoryList.getList().get(position).getThree_dir_id();
                decorationName = categoryList.getList().get(position).getThree_dir_name();
                decorationReala.setVisibility(View.VISIBLE);
                decorationTv.setText(decorationName);
            }
        });
    }

    @Override
    public void secondCatogoryDataSuccess(final CategoryList categoryList) {
        ArrayList<View> mViewList = new ArrayList<>();

        int page = (categoryList.getList().size() % 8 == 0 ? categoryList.getList().size()/8 : categoryList.getList().size() / 8 +1);
        for (int x = 0; x < page; x++) {
            ImageView imageView = new ImageView(this);
            View inflate = getLayoutInflater().from(context).inflate(R.layout.item_gridview_second_category, null);
            mViewList.add(inflate);
            if (page > 1) {
                dotContainer.setVisibility(View.VISIBLE);
                if (x == 0) {
                    imageView.setBackgroundResource(R.drawable.select_dot);
                } else {
                    imageView.setBackgroundResource(R.drawable.normal_dot);
                }
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(30, 30);
                layoutParams.setMargins(0, 0, 20, 0);
                imageView.setLayoutParams(layoutParams);
                dotContainer.addView(imageView);
            } else {
                dotContainer.setVisibility(View.GONE);
            }
        }
        secondCategoryViewpager.setAdapter(new SeconCategoryViewPagerAdapter(context, mViewList));
        secondCategoryViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int
                    positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < dotContainer.getChildCount(); i++) {
                    View childAt = dotContainer.getChildAt(i);
                    if (position == i) {
                        if (childAt != null) {
                            childAt.setBackgroundResource(R.drawable.select_dot);
                        }
                    } else {
                        childAt.setBackgroundResource(R.drawable.normal_dot);
                    }

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        for (int x = 0; x < page; x++) {
            final List<Category> categories1 = new ArrayList<>();
            for (int y = x * 8; y < categoryList.getList().size(); y++) {
                if (categories1.size() == 8) {
                    break;
                }
                categories1.add(categoryList.getList().get(y));
            }

            AutoHeightGridView view = (AutoHeightGridView) mViewList.get(x).findViewById(R.id.gridview);
            view.setAdapter(new SecondCategoryGridviewAdapter(categories1, context));
            view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    if (isRepeat(categories1.get(position).getTwo_dir_id())){
                        T.showShort(context, "该分类已选择");
                        return;
                    }
                    //选中分类
                    T.showShort(SellerSecondCategoryActivity
                            .this, categories1.get(position).getTwo_dir_name());
                    final View layoutView = LayoutInflater.from(context).inflate(R.layout.app_keys_word, null);
                    ImageView imageView = (ImageView) layoutView.findViewById(R.id.app_delete);
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            keysString.remove(categories1.get(position).getTwo_dir_id());
                            keys.removeView(layoutView);
                        }
                    });
                    TextView textView = (TextView) layoutView.findViewById(R.id.second_categroy_tv);
                    textView.setText(categories1.get(position).getTwo_dir_name());
                    keysString.add(categories1.get(position).getTwo_dir_id());
                    keys.addView(layoutView);
                }
            });
        }
    }

    @Override
    public void responseCompanyType(CategoryList categoryList) {
        final List<Category> list = categoryList.getList();
        autoHeightGridView.setAdapter(new RegisterAreaGridviewAdapter(list, this));
        autoHeightGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                company = list.get(i).getOne_dir_id();
                companyName = list.get(i).getOne_dir_name();
                company_type_rl.setVisibility(View.VISIBLE);
                company_type_tv.setText(list.get(i).getOne_dir_name());
            }
        });

    }

    @OnClick(R.id.seller_second_category_next)
    public void onClick() {
        if ("".equals(type)){
            T.showShort(context, "请选择经营类型");
            return;
        }
        if (keysString.isEmpty()){
            T.showShort(context, "请选择行业分类");
            return;
        }
        if (TextUtils.isEmpty(one_dir_id)){
            T.showShort(context, "请选择所属区域");
            return;
        }
        /*if (TextUtils.isEmpty(decorationName)){
            T.showShort(context, "请选择所属装饰城");
            return;
        }*/
        if (TextUtils.isEmpty(company)){
            T.showShort(context, "请选择厂家类型");
            return;
        }
        if ("company".equals(type)){//企业
            Intent intent = new Intent(this, SellerUploadYinYeZhiZhaoActivity.class);
            SellMessageComplete upload = (SellMessageComplete) getIntent().getSerializableExtra
                    ("upload");
                upload.setKey_words(group(keysString));
            upload.setArea_id(one_dir_id);
            upload.setArea_name(one_dir_id_name);
            upload.setDecorative_id(three_dir_id);
            upload.setDecorative_name(decorationName);
            upload.setManufacturer_type_id(company);
            upload.setManufacturer_type_name(companyName);
            intent.putExtra("upload", upload);
            startActivity(intent);
        }else if ("person".equals(type)){//个人
            Intent intent = new Intent(this, SellerUploadShengFenZhenActivity.class);
            SellMessageComplete upload = (SellMessageComplete) getIntent().getSerializableExtra
                    ("upload");
            upload.setKey_words(group(keysString));
            upload.setArea_id(one_dir_id);
            upload.setArea_name(one_dir_id_name);
            upload.setDecorative_id(three_dir_id);
            upload.setDecorative_name(decorationName);
            upload.setManufacturer_type_id(company);
            upload.setManufacturer_type_name(companyName);
            intent.putExtra("upload", upload);
            startActivity(intent);
        }
    }

    @OnClick({/*R.id.second_categroy_reala, */R.id.area_reala, R.id.decoration_reala, R.id.company_type_rl})
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.second_categroy_reala:
//                secondCategroyReala.setVisibility(View.GONE);
//                break;
            case R.id.area_reala:
                areaReala.setVisibility(View.GONE);
                break;
            case R.id.decoration_reala:
                decorationReala.setVisibility(View.GONE);
                break;
            case R.id.company_type_rl:
                company_type_rl.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    protected void onDestoryActivity() {

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

    /**
     * 类型添加是否重复
     * @return
     */
    private boolean isRepeat(String s){
        for (String key : keysString){
            if (key.equals(s)){
                return true;
            }
        }
        return false;
    }

    /**
     * 逗号组合数据
     * @return
     */
    private String group(List<String> stringList){
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : stringList){
            stringBuilder.append(s + ",");
        }
        if (!stringList.isEmpty()){
            return stringBuilder.toString().substring(0, stringBuilder.toString().length()-1);
        }
        return "";
    }

    @Override
    public void showError(String error) {
        super.showError(error);
        T.showShort(context, error);
    }
}
