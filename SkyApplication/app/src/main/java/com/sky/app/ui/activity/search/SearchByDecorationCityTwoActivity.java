package com.sky.app.ui.activity.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.sky.app.R;
import com.sky.app.bean.DecorationCityList;
import com.sky.app.bean.DecorationTwoButique;
import com.sky.app.bean.SearchDecorationTwoLeft;
import com.sky.app.bean.UserBeanDetail;
import com.sky.app.bean.UserBeanList;
import com.sky.app.contract.UserContract;
import com.sky.app.library.base.ui.BaseViewActivity;
import com.sky.app.library.component.banner.modle.BannerInfo;
import com.sky.app.library.component.pulltorefresh.ILoadingLayout;
import com.sky.app.library.component.pulltorefresh.PullToRefreshBase;
import com.sky.app.library.component.pulltorefresh.PullToRefreshScrollView;
import com.sky.app.library.utils.DialogUtils;
import com.sky.app.presenter.SearchByDecorationCityTwoPresenter;
import com.sky.app.ui.activity.baidumap.BaiduMapActivity;
import com.sky.app.ui.activity.seller.SellerCenterActivity;
import com.sky.app.ui.activity.seller.ShopCenterActivity;
import com.sky.app.ui.activity.shop.CardActivity;
import com.sky.app.ui.adapter.MyPagerAdapter;
import com.sky.app.ui.adapter.SearchCustomAdapter;
import com.sky.app.ui.adapter.SearchDecortionTwoAdapter;
import com.sky.app.ui.custom.AutoHeightListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 根据装饰城查找查找
 */
public class SearchByDecorationCityTwoActivity extends BaseViewActivity<UserContract
        .ISearchByDecorationCityTwoPresenter>
        implements UserContract.ISearchByDecorationCityTwo,
        PullToRefreshBase.OnRefreshListener2<ScrollView> {

    @BindView(R.id.app_edit_content)
    EditText editText;
    @BindView(R.id.app_search_toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_search_tv)
    TextView appSearchTv;
    @BindView(R.id.decoration_listview_left)
    ListView decorationListviewLeft;
    @BindView(R.id.decoration_listview)
    AutoHeightListView listview;
    @BindView(R.id.scrollview1)
    PullToRefreshScrollView scrollview;
    @BindView(R.id.mLoopViewPagerLayout11)
    ViewPager mLoopViewPagerLayout11;
    @BindView(R.id.app_city_1)
    ImageView appCity1;
    @BindView(R.id.textView1)
    TextView textView1;

    @BindView(R.id.app_city_3)
    ImageView appCity3;
    @BindView(R.id.dot_layout)
    LinearLayout dotlayout;
    @BindView(R.id.app_city_4)
    ImageView appCity4;
    @BindView(R.id.zhuangshicheng_zhaodian)
    RelativeLayout zhuangshichengZhaodian;
    @BindView(R.id.zhuangshicheng_pingmiantu)
    RelativeLayout zhuangshichengPingmiantu;
    @BindView(R.id.zhuangshicheng_action)
    RelativeLayout zhuangshichengaction;
    @BindView(R.id.decoration_pingmian)
    LinearLayout decorationPingmian;
    @BindView(R.id.ll_navi)
    LinearLayout llNavi;

    private String decorateCity = "";
    private int page = 1;
    private int total = -1;
    private String two_dir_id = "";
    private SearchCustomAdapter searchCustomAdapter;
    private UserBeanList userBeanList = new UserBeanList();
    private List<SearchDecorationTwoLeft.ListBean> listLeft;
    private SearchDecortionTwoAdapter searchDecortionTwoAdapter;
    List<ImageView> secondlist = new ArrayList<>();
    private String three_dir_id;
    private String one_dir_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_decoration_city1);
        ButterKnife.bind(this);
    }

    @Override
    protected UserContract.ISearchByDecorationCityTwoPresenter presenter() {
        return new SearchByDecorationCityTwoPresenter(this, this);
    }

    @Override
    protected void initViewsAndEvents() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
//        getPresenter().requestBanner();
        scrollview.setOnRefreshListener(this);
    }


    @Override
    protected void init() {
        toolbar.setNavigationIcon(R.mipmap.app_back_arrow_icon);
        ImageView mImageView = null;
        mImageView = (ImageView) getLayoutInflater().inflate(R.layout.lead_activity_item, null);
        //给控件设置图片
        mImageView.setImageResource(R.mipmap.guanggao1);
        //把数据添加到集合
        secondlist.add(mImageView);
        mImageView = (ImageView) getLayoutInflater().inflate(R.layout.lead_activity_item, null);
        //给控件设置图片
        mImageView.setImageResource(R.mipmap.guanggao2);
        //把数据添加到集合
        secondlist.add(mImageView);
        mImageView = (ImageView) getLayoutInflater().inflate(R.layout.lead_activity_item, null);
        //给控件设置图片
        mImageView.setImageResource(R.mipmap.guanggao3);
        //把数据添加到集合
        secondlist.add(mImageView);
        mImageView = (ImageView) getLayoutInflater().inflate(R.layout.lead_activity_item, null);
        //给控件设置图片
        mImageView.setImageResource(R.mipmap.guangao4);
        //把数据添加到集合
        secondlist.add(mImageView);
        //初始化所有的点
        initDotView();
        updateDotView();
        mLoopViewPagerLayout11.setAdapter(new MyPagerAdapter(secondlist));
        mLoopViewPagerLayout11.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                updateDotView();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

//        mLoopViewPagerLayout11.setLoop_ms(3000);//轮播的速度(毫秒)
//        mLoopViewPagerLayout11.setLoop_duration(800);//滑动的速率(毫秒)
//        mLoopViewPagerLayout11.setLoop_style(LoopStyle.Empty);//轮播的样式-默认empty
//        mLoopViewPagerLayout11.setIndicatorLocation(IndicatorLocation.Right);
//        mLoopViewPagerLayout11.initializeData(context);//初始化数据
//        mLoopViewPagerLayout11.setOnLoadImageViewListener(new OnLoadImageViewListener() {
//            @Override
//            public void onLoadImageView(ImageView imageView, Object parameter) {
//                ImageHelper.getInstance().displayDefinedImage(String.valueOf(parameter), imageView,
//                        R.mipmap.app_default_icon_1, R.mipmap.app_default_icon_1);
//            }
//
//            @Override
//            public ImageView createImageView(Context context) {
//                ImageView imageView = new ImageView(context);
//                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//                imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//                return imageView;
//            }
//        });
//        mLoopViewPagerLayout11.setOnBannerItemClickListener(new OnBannerItemClickListener() {
//            @Override
//            public void onBannerClick(int index, ArrayList<BannerInfo> banner) {
//                if (!TextUtils.isEmpty(banner.get(index).skipUrl)) {
//                    AppUtils.skipBrowser(context, banner.get(index).skipUrl);
//                }
//            }
//        });
        //获取装饰城中的二级分类
        one_dir_id = getIntent().getStringExtra("one_dir_id");
        three_dir_id = getIntent().getStringExtra("three_dir_id");

        getPresenter().getSearchTwo();
//        getPresenter().getData(decorateCity);
//        getUser();
        initDecortion();
        onPullDownToRefresh(scrollview);
    }

    private void updateDotView() {
        int mCurrentItem = mLoopViewPagerLayout11.getCurrentItem();
        for (int i = 0; i < dotlayout.getChildCount(); i++) {
            View mChildAt = dotlayout.getChildAt(i);
            mChildAt.setBackgroundResource(mCurrentItem == i ? R.drawable.dot_select : R.drawable.dot_unselect);
        }
    }

    private void initDotView() {
        for (int i = 0; i < secondlist.size(); i++) {
            View mDotView = new View(SearchByDecorationCityTwoActivity.this);
            LinearLayout.LayoutParams mLayoutParams = new LinearLayout.LayoutParams(18, 18);
            if (i > 0) {
                mLayoutParams.leftMargin = 10;
            }
            mDotView.setLayoutParams(mLayoutParams);
            dotlayout.addView(mDotView);
        }
    }

    private void initDecortion() {
        userBeanList.setList(new ArrayList<UserBeanDetail>());
        searchCustomAdapter = new SearchCustomAdapter(this.userBeanList, this);

        listview.setAdapter(searchCustomAdapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (SearchByDecorationCityTwoActivity.this.userBeanList.getList().get(position).getSeller_type()) {
                    case "店铺":
                        Intent j = new Intent(context, ShopCenterActivity.class);
                        j.putExtra("seller_id", SearchByDecorationCityTwoActivity.this.userBeanList.getList().get(position).getUser_id());
                        startActivity(j);
                        break;
                    case "个人主页":
                        Intent i = new Intent(context, SellerCenterActivity.class);
                        i.putExtra("seller_id", SearchByDecorationCityTwoActivity.this.userBeanList.getList().get(position).getUser_id());
                        startActivity(i);
                        break;
                    case "名片":
                        Intent k = new Intent(context, CardActivity.class);
                        k.putExtra("seller_id", SearchByDecorationCityTwoActivity.this.userBeanList.getList().get(position).getUser_id());
                        startActivity(k);
                        break;
                }
            }
        });
        getUser();
    }

    @Override
    protected void onDestoryActivity() {
//        mLoopViewPagerLayout11.stopLoop();

    }


    @Override
    public void showBannerSuccess(List<BannerInfo> list) {
//        mLoopViewPagerLayout11.setLoopData((ArrayList<BannerInfo>) list);
    }

    @Override
    public void success(DecorationCityList categoryList) {

    }


    @Override
    public void userDataSuccess(UserBeanList userBeanList) {
        scrollview.onRefreshComplete();
        if ("0".equals(userBeanList.getAll_page() + "")) {
            total = 3;
        } else {
            total = userBeanList.getAll_page();
        }

        if (isHaveMore()) {
            ILoadingLayout endLabels = scrollview.getLoadingLayoutProxy(false, true);
            endLabels.setPullLabel("上拉可以加载");// 刚下拉时，显示的提示
            endLabels.setRefreshingLabel("正在加载...");// 刷新时
            endLabels.setReleaseLabel("松开即可加载");// 下来达到一定距离时，显示的提示
        } else {
            ILoadingLayout endLabels = scrollview.getLoadingLayoutProxy(false, true);
            endLabels.setPullLabel("已经到底");// 刚下拉时，显示的提示
            endLabels.setRefreshingLabel("已经到底");// 刷新时
            endLabels.setReleaseLabel("已经到底");// 下来达到一定距离时，显示的提示
        }

        if (page == 1) {
            searchCustomAdapter.refreshData(userBeanList);
        } else {
            searchCustomAdapter.appendData(userBeanList);
        }

    }

    //展示装饰城中的二级分类
    @Override
    public void showDecorationTwoLeft(SearchDecorationTwoLeft list) {
        listLeft = new ArrayList<>();
        for (int i = 0; i < list.getList().size(); i++) {
            listLeft.add(list.getList().get(i));
        }
        searchDecortionTwoAdapter = new SearchDecortionTwoAdapter(listLeft, context);
        decorationListviewLeft.setAdapter(searchDecortionTwoAdapter);
        itemOnclick();
    }

    @Override
    public void showBoutiquesuccess(List<DecorationTwoButique.ListBean> butiqueList) {


    }


    private void itemOnclick() {
        decorationListviewLeft.setOnItemClickListener(mLeftListOnItemClick);
        searchDecortionTwoAdapter.notifyDataSetChanged();
    }


    AdapterView.OnItemClickListener mLeftListOnItemClick = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            searchDecortionTwoAdapter.setSelectItem(arg2);
            searchDecortionTwoAdapter.notifyDataSetInvalidated();
            searchDecortionTwoAdapter.notifyDataSetChanged();
            searchCustomAdapter.notifyDataSetChanged();
            two_dir_id = listLeft.get(arg2).getTwo_dir_id();
            page = 1;
            getUser();
        }
    };

    private boolean isHaveMore() {
        if (page >= total) {
            page = total;

            return false;
        }
        return true;
    }

    private void setRefresh() {
        if (isHaveMore()) {
            ILoadingLayout endLabels = scrollview.getLoadingLayoutProxy(false, true);
            endLabels.setPullLabel("上拉可以加载");// 刚下拉时，显示的提示
            endLabels.setRefreshingLabel("正在加载...");// 刷新时
            endLabels.setReleaseLabel("松开即可加载");// 下来达到一定距离时，显示的提示
            page++;
            Log.e("123456", page + "");

            getUser();
        } else {
            ILoadingLayout endLabels = scrollview.getLoadingLayoutProxy(false, true);
            endLabels.setPullLabel("已经到底");// 刚下拉时，显示的提示
            endLabels.setRefreshingLabel("已经到底");// 刷新时
            endLabels.setReleaseLabel("已经到底");// 下来达到一定距离时，显示的提示
            scrollview.onRefreshComplete();
        }
    }

    @OnClick(R.id.app_search_tv)
    public void searchByInput(TextView view) {
        onPullDownToRefresh(scrollview);
    }


    private void getUser() {
        SearchShopUser searchShopUser = new SearchShopUser();
        three_dir_id = getIntent().getStringExtra("three_dir_id");
        searchShopUser.setDecorative_id(three_dir_id);
        searchShopUser.setTwo_dir_id(two_dir_id);
        searchShopUser.setOne_dir_id(one_dir_id);
        searchShopUser.setTp(1);
        searchShopUser.setPage(page);
        Log.e("123456", page + "");

        getPresenter().getUserData(searchShopUser);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        page = 1;
        getUser();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        setRefresh();
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

    public void Action(View view) {
        startActivity(new Intent(this, DecorationTwoShopActivity.class));

    }

    public void plane(View view) {
        startActivity(new Intent(this, DecorationTwoPingActivity.class));
    }

    public void boutique(View view) {
        Intent intent = new Intent(SearchByDecorationCityTwoActivity.this, DecorationTwoBoutiqueActiviy.class);
        intent.putExtra("decorateCity", three_dir_id);
        startActivity(intent);
    }

    //跳转到地图页面
    @OnClick(R.id.ll_navi)
    public void onViewClicked() {
        Intent intent=new Intent(this, BaiduMapActivity.class);
        startActivity(intent);
    }
}
