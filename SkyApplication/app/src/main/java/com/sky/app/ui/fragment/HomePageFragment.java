package com.sky.app.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.sky.app.R;
import com.sky.app.bean.FirstCategoryDetail;
import com.sky.app.bean.GoodShop;
import com.sky.app.bean.Goods;
import com.sky.app.bean.HeadlinearsDetail;
import com.sky.app.bean.SupplyDetail;
import com.sky.app.contract.HomeContract;
import com.sky.app.library.base.ui.BaseViewFragment;
import com.sky.app.library.component.banner.LoopViewPagerLayout;
import com.sky.app.library.component.banner.listener.OnBannerItemClickListener;
import com.sky.app.library.component.banner.listener.OnLoadImageViewListener;
import com.sky.app.library.component.banner.modle.BannerInfo;
import com.sky.app.library.component.banner.modle.IndicatorLocation;
import com.sky.app.library.component.banner.modle.LoopStyle;
import com.sky.app.library.component.scroll_txt.VerticalBannerView;
import com.sky.app.library.utils.AppUtils;
import com.sky.app.library.utils.ImageHelper;
import com.sky.app.library.utils.T;
import com.sky.app.presenter.HomeActivityPresenter;
import com.sky.app.ui.activity.product.ProductDetailActivity;
import com.sky.app.ui.activity.publish.MyTouActivity;
import com.sky.app.ui.activity.publish.PublishActivity;
import com.sky.app.ui.activity.publish.PublishDetailActivity;
import com.sky.app.ui.activity.search.GoodShopGridviewAdapter;
import com.sky.app.ui.activity.search.SearchByDecorationCityActivity;
import com.sky.app.ui.activity.search.SearchByFactoryActivity;
import com.sky.app.ui.activity.search.SearchByInputActivity;
import com.sky.app.ui.activity.search.SearchByPlaceNewActivity;
import com.sky.app.ui.activity.search.SearchSecondActivity;
import com.sky.app.ui.activity.search.SearchSecondActivity1234;
import com.sky.app.ui.activity.search.SearchShopActivity;
import com.sky.app.ui.activity.seller.ShopCenterActivity;
import com.sky.app.ui.adapter.AppHomeCategoryViewPagerAdapter;
import com.sky.app.ui.adapter.AppHomeMyGridViewAdapter;
import com.sky.app.ui.adapter.GoodsGridViewAdapter;
import com.sky.app.ui.adapter.ScrollTextAdaptor;
import com.sky.app.ui.adapter.ScrollTextAdaptor1;
import com.sky.app.ui.custom.AutoHeightGridView;
import com.sky.app.ui.custom.AutoHeightHomeViewPager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * 主页
 * Created by Administrator on 2017/2/30.
 */
public class HomePageFragment extends BaseViewFragment<HomeContract.IHomePresenter>
        implements HomeContract.IHomeView, SwipeRefreshLayout.OnRefreshListener {

    //    @BindView(R.id.app_homepage_toolbar)
//    Toolbar toolbar;
    @BindView(R.id.app_swipe)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.mLoopViewPagerLayout1)
    LoopViewPagerLayout mLoopViewPagerLayout1;
    //    @BindView(R.id.app_home_toutiao)
//    TextSwitcher textSwitcher;
    @BindView(R.id.app_home_toutiao)
    VerticalBannerView toutiao;
    @BindView(R.id.app_supply_rl)
    VerticalBannerView supply;
    @BindView(R.id.app_buy_rl)
    VerticalBannerView buy;
    @BindView(R.id.viewpager)
    AutoHeightHomeViewPager viewPager;
    @BindView(R.id.points)
    LinearLayout group;
    //    @BindView(R.id.points1)
//    RadioButton points1;
//    @BindView(R.id.points2)
//    RadioButton points2;
    //    @BindView(R.id.goodshop_gridview)
//    AutoHeightGridView goodshopGridView;
//    @BindView(R.id.good_gridview)
//    AutoHeightGridView goodgridview;
//    @BindView(R.id.app_home_listview)
//    RecyclerView apphomelistview;
    @BindView(R.id.app_home_1)
    ImageView apphome1;
    @BindView(R.id.app_home_2)
    ImageView apphome2;
    @BindView(R.id.app_home_3)
    ImageView apphome3;
    @BindView(R.id.app_home_4)
    ImageView apphome4;
    @BindView(R.id.app_home_5)
    ImageView apphome5;
    @BindView(R.id.app_home_6)
    ImageView apphome6;

    @BindView(R.id.app_1)
    LinearLayout app1;
    @BindView(R.id.app_2)
    LinearLayout app2;
    @BindView(R.id.app_3)
    LinearLayout app3;
    @BindView(R.id.app_4)
    LinearLayout app4;
    @BindView(R.id.app_5)
    LinearLayout app5;
    @BindView(R.id.app_6)
    LinearLayout app6;
    @BindView(R.id.app_7)
    LinearLayout app7;
    @BindView(R.id.app_8)
    LinearLayout app8;


    @BindView(R.id.moreshops)
    TextView moreshop;
    //     private FirstCategoryAdapter firstCategoryAdapter;
    List<FirstCategoryDetail> firstCategoryDetailList = new ArrayList<>();
    Unbinder unbinder;

    private ImageView[] ivPoints;//小圆点图片的集合
    private int totalPage; //总的页数
    private int mPageSize = 10; //每页显示的最大的数量
    private List<View> viewPagerList;//GridView作为一个View对象添加到ViewPager集合中
    //private int currentPage;//当前页
    private int switcherCount = 0;
    private Handler mHandler = new Handler();
    private GoodShopGridviewAdapter goodShopGridviewAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void init() {
        //设置刷新时动画的颜色，可以设置4个
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setProgressViewOffset(false, 0, AppUtils.dip2px(context, 48));
            mSwipeRefreshLayout.setColorSchemeResources(R.color.main_colorPrimary);
            mSwipeRefreshLayout.setOnRefreshListener(this);
        }

        /*第一个banner*/
        mLoopViewPagerLayout1.setLoop_ms(3000);//轮播的速度(毫秒)
        mLoopViewPagerLayout1.setLoop_duration(800);//滑动的速率(毫秒)
        mLoopViewPagerLayout1.setLoop_style(LoopStyle.Empty);//轮播的样式-默认empty
        mLoopViewPagerLayout1.setIndicatorLocation(IndicatorLocation.Right);
        mLoopViewPagerLayout1.initializeData(context);//初始化数据
        mLoopViewPagerLayout1.setOnLoadImageViewListener(new OnLoadImageViewListener() {
            @Override
            public void onLoadImageView(ImageView imageView, Object parameter) {
                ImageHelper.getInstance().displayDefinedImage(String.valueOf(parameter), imageView,
                        R.mipmap.app_default_icon_1, R.mipmap.app_default_icon_1);
            }

            @Override
            public ImageView createImageView(Context context) {
                ImageView imageView = new ImageView(context);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                return imageView;
            }
        });
        mLoopViewPagerLayout1.setOnBannerItemClickListener(new OnBannerItemClickListener() {
            @Override
            public void onBannerClick(int index, ArrayList<BannerInfo> banner) {
                if (!TextUtils.isEmpty(banner.get(index).skipUrl)) {
                    AppUtils.skipBrowser(context, banner.get(index).skipUrl);
                }
            }
        });

        //一级分类
    }

    private void initData() {
        //总的页数向上取整
        totalPage = (firstCategoryDetailList.size() % 10 == 0 ? firstCategoryDetailList.size() / 10 : firstCategoryDetailList.size() / 10 + 1);
        viewPagerList = new ArrayList<>();
        //totalPage是整数,1,2
        for (int x = 0; x < totalPage; x++) {
            ImageView imageView = new ImageView(context);
            View inflate = LayoutInflater.from(context).inflate(R.layout.item_gridview_apphome_category, null);
            viewPagerList.add(inflate);
            if (totalPage == 0) {
                inflate.setVisibility(View.GONE);
            }
            if (totalPage > 1) {
                group.setVisibility(View.VISIBLE);
                if (x == 0) {
                    imageView.setBackgroundResource(R.drawable.select_dot);
                } else {
                    imageView.setBackgroundResource(R.drawable.normal_dot);
                }
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(15, 15);
                layoutParams.setMargins(0, 0, 15, 0);
                imageView.setLayoutParams(layoutParams);
                group.addView(imageView);
            } else {
                group.setVisibility(View.GONE);
            }

//            if (totalPage > 1) {
//                group.setVisibility(View.VISIBLE);
//                if (x == 0) {
//                    imageView.setImageResource(R.drawable.select_dot);
//                } else {
//                    imageView.setImageResource(R.drawable.normal_dot);
//
//                }
//                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(15, 15);
//                layoutParams.setMargins(0, 0, 15, 0);
//                imageView.setLayoutParams(layoutParams);
//                group.addView(imageView);
//            } else {
//                group.setVisibility(View.GONE);
//            }
        }
        //设置ViewPager适配器
        final AppHomeCategoryViewPagerAdapter adapter1 = new AppHomeCategoryViewPagerAdapter(viewPagerList);
        viewPager.setAdapter(adapter1);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < group.getChildCount(); i++) {
                    View childAt = group.getChildAt(i);
                    if (position == i) {
                        if (childAt != null) {
                            childAt.setBackgroundResource(R.drawable.select_dot);
                        }
                    } else {
                        childAt.setBackgroundResource(R.drawable.normal_dot);
                    }
                    adapter1.notifyDataSetChanged();

                }

//                for (int i = 0; i < group.getChildCount(); i++) {
//                    View childAt = group.getChildAt(i);
//                    childAt.setBackgroundResource(position == i ? R.drawable.dot_select : R.drawable.dot_unselect);
//                    if (position == i) {
//                    } else {
//                        childAt.setBackgroundResource(R.drawable.normal_dot);
//                    }
//                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        for (int x = 0; x < totalPage; x++) {
            final List<FirstCategoryDetail> categories1 = new ArrayList<>();

            for (int y = x * 9; y < firstCategoryDetailList.size(); y++) {
                if (categories1.size() == 9) {
                    break;
                }
                categories1.add(firstCategoryDetailList.get(y));
            }
            AutoHeightGridView gridView = (AutoHeightGridView) viewPagerList.get(x).findViewById(R.id.app_type);
            AppHomeMyGridViewAdapter adapter = new AppHomeMyGridViewAdapter(context, firstCategoryDetailList, x, mPageSize);
            gridView.setAdapter(adapter);
            final int finalX = x;
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    //==1的时候，表示第二页，i要加上10,表示第10个数据
                    if (finalX == 1) {
                        i += 10;
                    }
                    String one_dir_id = firstCategoryDetailList.get(i).getOne_dir_id();
                    if ("FW500007".equals(one_dir_id)) {//货运
                        Intent p = getActivity().getPackageManager().getLaunchIntentForPackage("com.sky.transport");
                        if (null == p) {
                            T.showShort(context, "没有安装货运app");
                            return;
                        } else {
                            startActivity(p);
                        }
                    } else if ("FW500003".equals(one_dir_id) || "FW500004".equals(one_dir_id) || "FW500006".equals(one_dir_id) || "FW500008".equals(one_dir_id) ||
                            "FW500009".equals(one_dir_id) || "FW500010".equals(one_dir_id) || "FW500011".equals(one_dir_id) ||
                            "FW500013".equals(one_dir_id) || "FW500014".equals(one_dir_id) || "FW500015".equals(one_dir_id) || "FW500016".equals(one_dir_id)
                            || "FW500017".equals(one_dir_id) || "FW500018".equals(one_dir_id)) {
                        Intent intent = new Intent(getActivity(), SearchSecondActivity1234.class);
                        intent.putExtra("one_dir_id", one_dir_id);
                        getActivity().startActivity(intent);
                    } else {
                        Intent intent = new Intent(getActivity(), SearchSecondActivity.class);
                        intent.putExtra("one_dir_id", one_dir_id);
                        getActivity().startActivity(intent);
                    }
                }
            });
        }
    }

    @Override
    protected void initViewsAndEvents() {
        onRefresh();
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.app_home_page;
    }

    @Override
    protected void onDestoryFragment() {

    }

    @Override
    protected HomeContract.IHomePresenter presenter() {
        return new HomeActivityPresenter(context, this);
    }

    @Override
    public void onStart() {
        super.onStart();
        mLoopViewPagerLayout1.startLoop();
    }

    @Override
    public void onStop() {
        super.onStop();
        mLoopViewPagerLayout1.stopLoop();
//        toutiao.stopNestedScroll();
        supply.stop();
        buy.stop();
    }

    /**
     * 地区查找
     */
    @OnClick(R.id.area_rel)
    void jumpToArea() {
        getActivity().startActivity(new Intent(getActivity(), SearchByPlaceNewActivity.class));
    }

    /**
     * 生产厂家查找
     */
    @OnClick(R.id.factory_rel)
    void jumpToFactory() {
        getActivity().startActivity(new Intent(getActivity(), SearchByFactoryActivity.class));
    }

    @OnClick(R.id.app_search_tv)
    void jumpToSearch() {
        //获得用户输入的
        getActivity().startActivity(new Intent(getActivity(), SearchByInputActivity.class));
    }

    @OnClick({R.id.skip_supply, R.id.skip_buy})
    void skipPublishActivity() {
        getActivity().startActivity(new Intent(getActivity(), PublishActivity.class));
    }

    @Override
    public void showBannerSuccess(List<BannerInfo> list, int flag) {
        mSwipeRefreshLayout.setRefreshing(false);
        switch (flag) {
            case 1:
                mLoopViewPagerLayout1.setLoopData((ArrayList<BannerInfo>) list);
                break;
        }
    }

    @Override
    public void showGoodsSuccess(List<Goods.ListBean> goods1ist) {
        GoodsGridViewAdapter goodsGridViewAdapter = new GoodsGridViewAdapter(goods1ist, context);
//        goodgridview.setAdapter(goodsGridViewAdapter);
    }


    @Override
    public void showFirstCategorySuccess(List<FirstCategoryDetail> list) {
        mSwipeRefreshLayout.setRefreshing(false);
        firstCategoryDetailList.clear();
        firstCategoryDetailList.addAll(list);
        initData();

    }


    @Override
    public void showSupplySuccess(List<SupplyDetail> list) {
        ScrollTextAdaptor sj = new ScrollTextAdaptor(list);
        sj.setOnItemClick(new ScrollTextAdaptor.OnItemClick() {
            @Override
            public void click(String s) {
                skipPublishDetail(s);
            }
        });
        supply.setAdapter(sj);
        supply.start();
    }

    @Override
    public void showBuySuccess(List<SupplyDetail> list) {
        ScrollTextAdaptor sj = new ScrollTextAdaptor(list);
        sj.setOnItemClick(new ScrollTextAdaptor.OnItemClick() {
            @Override
            public void click(String s) {
                skipPublishDetail(s);
            }
        });
        buy.setAdapter(sj);
        buy.start();
    }


    @Override
    public void showHeadlinearsSuccess(final HeadlinearsDetail[] headlinearsDetail) {
        final List<HeadlinearsDetail> list = new ArrayList<>();
        list.addAll(Arrays.asList(headlinearsDetail));

        ScrollTextAdaptor1 scrollTextAdaptor1 = new ScrollTextAdaptor1(list);
        scrollTextAdaptor1.setOnItemClick(new ScrollTextAdaptor1.OnItemClick() {
            @Override
            public void click(String s) {
                String noticeContent = list.get(headlinearsDetail.length).getNoticeContent();
                Intent intent = new Intent(context, MyTouActivity.class);
                intent.putExtra("noticeContent", noticeContent);
                getActivity().startActivity(intent);
            }
        });
        toutiao.setAdapter(scrollTextAdaptor1);
        toutiao.start();
    }
//        textSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
//            public View makeView() {
//                TextView tv = new TextView(getActivity());
//                return tv;
//            }
//        });
//        textSwitcher.setInAnimation(getContext(),
//                R.anim.enter);
//        textSwitcher.setOutAnimation(getContext(), R.anim.out);
//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                String strA = headlinearsDetail[switcherCount % headlinearsDetail.length].getNoticeContent();
//                String strB = switcherCount % headlinearsDetail.length + 1 < headlinearsDetail.length ? headlinearsDetail[switcherCount % headlinearsDetail.length + 1].getNoticeContent() : headlinearsDetail[0].getNoticeContent();
//                switcherCount += 2;
//                textSwitcher.setText(strA + "\n" + strB);
//                start();
//            }
//        }, 2000);
//        start();
//}


    @OnClick(R.id.moreshops)
    void moreShops() {
        //获得用户输入的
        getActivity().startActivity(new Intent(getActivity(), SearchShopActivity.class));
    }


    /**
     * 展示品质好店
     */
    @Override
    public void showGoodShop(List<GoodShop.ListBean> goodList) {
//        apphomelistview.setLayoutManager(new GridLayoutManager(context, 2));
//        apphomelistview.setHasFixedSize(true);
//        goodShopGridviewAdapter = new GoodShopGridviewAdapter(goodList, context);
//        apphomelistview.setAdapter(goodShopGridviewAdapter);

    }

    /**
     * 跳转供货详情
     */
    private void skipPublishDetail(String s) {
        Intent i = new Intent(context, PublishDetailActivity.class);
        i.putExtra("product_id", s);
        getActivity().startActivity(i);
    }

    /**
     * 装饰城
     */
    @OnClick(R.id.zhuangshicheng_rel)
    void jumpToDecorationCity() {
        getActivity().startActivity(new Intent(getActivity(), SearchByDecorationCityActivity.class));
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onRefresh() {
        //请求一级分类
        getPresenter().requestFirstCategory();
        //第一banner请求
        getPresenter().requestBanner();
        //获取采购信息
        getPresenter().requestBuy();
        //获取供应信息
        getPresenter().requestSupply();
        //获取工匠头条
        getPresenter().requestHeadlines();
        //获取品质好店
        getPresenter().requestGoodShop(1);
        //获取精品好货
        getPresenter().requestGoods(1);
    }

    @Override
    public void showError(String error) {
        super.showError(error);
        T.showShort(context, error);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.app_home_1)
    void goodShop1() {
        Intent intent = new Intent(getActivity(), ShopCenterActivity.class);
        intent.putExtra("seller_id", "170802000028");
        getActivity().startActivity(intent);
    }

    @OnClick(R.id.app_home_2)
    void goodShop2() {

        Intent intent = new Intent(getActivity(), ShopCenterActivity.class);
        intent.putExtra("seller_id", "170917007887");
        getActivity().startActivity(intent);
    }

    @OnClick(R.id.app_home_3)
    void goodShop3() {
        Intent intent = new Intent(getActivity(), ShopCenterActivity.class);
        intent.putExtra("seller_id", "171113022464");
        getActivity().startActivity(intent);
    }

    @OnClick(R.id.app_home_4)
    void goodShop4() {
        Intent intent = new Intent(getActivity(), ShopCenterActivity.class);
        intent.putExtra("seller_id", "171122026206");
        getActivity().startActivity(intent);
    }

    @OnClick(R.id.app_home_5)
    void goodShop5() {
        Intent intent = new Intent(getActivity(), ShopCenterActivity.class);
        intent.putExtra("seller_id", "170917007929");
        getActivity().startActivity(intent);
    }

    @OnClick(R.id.app_home_6)
    void goodShop6() {
        Intent intent = new Intent(getActivity(), ShopCenterActivity.class);
        intent.putExtra("seller_id", "171116024531");
        getActivity().startActivity(intent);
    }


    @OnClick(R.id.app_1)
    void good() {
        Intent i = new Intent(context, ProductDetailActivity.class);
        i.putExtra("product_id", "171127028718");
        startActivity(i);
    }

    @OnClick(R.id.app_2)
    void good2() {
        Intent i = new Intent(context, ProductDetailActivity.class);
        i.putExtra("product_id", "170904004043");
        startActivity(i);
    }

    @OnClick(R.id.app_3)
    void good3() {
        Intent i = new Intent(context, ProductDetailActivity.class);
        i.putExtra("product_id", "170919008938");
        startActivity(i);
    }

    @OnClick(R.id.app_4)
    void good4() {
        Intent i = new Intent(context, ProductDetailActivity.class);
        i.putExtra("product_id", "170919009021");
        startActivity(i);
    }

    @OnClick(R.id.app_5)
    void good5() {
        Intent i = new Intent(context, ProductDetailActivity.class);
        i.putExtra("product_id", "171115023463");
        startActivity(i);
    }

    @OnClick(R.id.app_6)
    void good6() {
        Intent i = new Intent(context, ProductDetailActivity.class);
        i.putExtra("product_id", "171123027400");
        startActivity(i);
    }

    @OnClick(R.id.app_7)
    void good7() {
        Intent i = new Intent(context, ProductDetailActivity.class);
        i.putExtra("product_id", "171122026110");
        startActivity(i);
    }

    @OnClick(R.id.app_8)
    void good8() {
        Intent i = new Intent(context, ProductDetailActivity.class);
        i.putExtra("product_id", "171123027137 ");
        startActivity(i);
    }
}