package com.sky.app.ui.activity.shop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.sky.app.R;
import com.sky.app.bean.CollectPubIn;
import com.sky.app.bean.CommentRequest;
import com.sky.app.bean.CommentResponse;
import com.sky.app.bean.ProductIntroduceIn;
import com.sky.app.bean.ProductIntroduceOut;
import com.sky.app.contract.SellerContract;
import com.sky.app.contract.ShopContract;
import com.sky.app.library.base.ui.BaseViewActivity;
import com.sky.app.library.component.RecycleViewDivider;
import com.sky.app.library.component.recycler.interfaces.OnLoadMoreListener;
import com.sky.app.library.component.recycler.recyclerview.LuRecyclerView;
import com.sky.app.library.component.recycler.recyclerview.LuRecyclerViewAdapter;
import com.sky.app.library.utils.AppUtils;
import com.sky.app.library.utils.DialogUtils;
import com.sky.app.library.utils.ImageHelper;
import com.sky.app.library.utils.T;
import com.sky.app.presenter.AllCommentPresenter;
import com.sky.app.presenter.CollectPresenter;
import com.sky.app.presenter.shop.CardPresenter;
import com.sky.app.ui.activity.baidumap.BaiduMapActivity;
import com.sky.app.ui.activity.order.CommentActivity;
import com.sky.app.ui.adapter.AllCommentAdaptor;
import com.sky.app.utils.ShareUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 个人名片
 */
public class CardActivity extends BaseViewActivity<SellerContract.ICardPresenter>
    implements SellerContract.ICardView, ShopContract.ICollectionView,
        ShopContract.IAllCommentView, SwipeRefreshLayout.OnRefreshListener{

    @BindView(R.id.normal_toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_title)
    TextView title;

    @BindView(R.id.app_swipe)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.app_comment_list)
    LuRecyclerView mRecyclerView;

    AllCommentAdaptor allCommentAdaptor;
    List<CommentResponse> commentResponses = new ArrayList<>();
    private LuRecyclerViewAdapter mLuRecyclerViewAdapter = null;
    private CommentRequest commentRequest = new CommentRequest();
    private String seller_id;
    private ProductIntroduceOut productIntroduceOut;

    @BindView(R.id.comment_type)
    RadioGroup radioGroup;

    private ShopContract.ICollectionPresenter iCollectionPresenter;
    private ShopContract.IAllCommentPresenter iAllCommentPresenter;

    @BindView(R.id.app_collect)
    TextView collect;

    @BindView(R.id.app_icon)
    ImageView appIcon;
    @BindView(R.id.app_user_name)
    TextView userName;
    @BindView(R.id.app_time)
    TextView appTime;
    @BindView(R.id.app_main_sell)
    TextView mainSell;
    @BindView(R.id.shop_collect)
    Button collectBtn;

    @BindView(R.id.navigate)
    LinearLayout navigate;
    @BindView(R.id.all)
    RadioButton allBtn;
    @BindView(R.id.has_pic)
    RadioButton picBtn;
//
//    @BindView(R.id.qq)
//    TextView qq;
//    @BindView(R.id.weixin)
//    TextView weixin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_card);
    }

    @Override
    protected SellerContract.ICardPresenter presenter() {
        iCollectionPresenter = new CollectPresenter(context, this);
        iAllCommentPresenter = new AllCommentPresenter(context, this);
        return new CardPresenter(context, this);
    }

    @Override
    protected void initViewsAndEvents() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.app_share:
                        ShareUtils.share(CardActivity.this, "", "", "");
                        break;
                }
                return false;
            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL, AppUtils.dip2px(context, 1),
                AppUtils.getSystemColor(context, R.color.sky_color_f2f2f2)));
        allCommentAdaptor = new AllCommentAdaptor(context, commentResponses);
        mLuRecyclerViewAdapter = new LuRecyclerViewAdapter(allCommentAdaptor);
        mRecyclerView.setAdapter(mLuRecyclerViewAdapter);
        mRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (iAllCommentPresenter.hasMore()){
                    loadData();
                } else {
                    mRecyclerView.setNoMore(true);
                }
            }
        });

        //设置底部加载颜色
        mRecyclerView.setFooterViewColor(R.color.main_colorAccent, R.color.main_colorAccent ,android.R.color.white);
        //设置底部加载文字提示
        mRecyclerView.setFooterViewHint("拼命加载中", "已经全部加载完", "网络不给力啊，点击再试一次吧");

        onRefresh();

        //请求个人主页信息
        ProductIntroduceIn productIntroduceIn = new ProductIntroduceIn();
        productIntroduceIn.setUser_id(seller_id);
        getPresenter().requestCardInfo(productIntroduceIn);

        //查询评论数量
        searchCommentNum();
    }

    @Override
    protected void init() {
        title.setText(R.string.app_card_string);
        toolbar.setNavigationIcon(R.mipmap.app_back_arrow_icon);
        toolbar.inflateMenu(R.menu.app_share_menu);

        //设置刷新时动画的颜色，可以设置4个
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setProgressViewOffset(false, 0, AppUtils.dip2px(context, 48));
            mSwipeRefreshLayout.setColorSchemeResources(R.color.main_colorPrimary);
            mSwipeRefreshLayout.setOnRefreshListener(this);
        }
        seller_id = getIntent().getStringExtra("seller_id");
        commentRequest.setType(1);//商户编号
        commentRequest.setValue(seller_id);
        radioGroup.check(R.id.all);
    }

    @Override
    public void showError(String error) {
        super.showError(error);
        T.showShort(context, error);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    protected void onDestoryActivity() {

    }

    @Override
    public void showCollectView(String msg) {
        T.showShort(context, msg);
        switch (tag){
            case 1:
                collectBtn.setText("收藏");
                break;
            case 2:
                collectBtn.setText("已收藏");
                break;
            case 3:
                collect.setText("收藏");
                AppUtils.changeTextViewIcon(context, collect, R.mipmap.app_product_detail_star_icon, 2);
                break;
            case 4:
                collect.setText("已收藏");
                AppUtils.changeTextViewIcon(context, collect, R.mipmap.app_product_detail_selected_star_icon, 2);
                break;
        }
    }

    @Override
    public void showCollectError(String error) {
        T.showShort(context, error);
        switch (tag){
            case 1:
                collectBtn.setText("已收藏");
                break;
            case 2:
                collectBtn.setText("收藏");
                break;
            case 3:
                collect.setText("已收藏");
                AppUtils.changeTextViewIcon(context, collect, R.mipmap.app_product_detail_selected_star_icon, 2);
                break;
            case 4:
                collect.setText("收藏");
                AppUtils.changeTextViewIcon(context, collect, R.mipmap.app_product_detail_star_icon, 2);
                break;
        }
    }

    @Override
    public void getRefreshData(List<CommentResponse> list, int tag, int num) {
        switch (tag){
            case 1:
                allBtn.setText("全部(" + num + ")");
                break;
            case 2:
                picBtn.setText("有图(" + num + ")");
                break;
        }

        allCommentAdaptor.add(list);
        if(mSwipeRefreshLayout.isRefreshing()){
            mSwipeRefreshLayout.setRefreshing(false);
        }
        mRecyclerView.refreshComplete(20);
        mLuRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void getLoadMoreData(List<CommentResponse> list, int tag, int num) {
        switch (tag){
            case 1:
                allBtn.setText("全部(" + num + ")");
                break;
            case 2:
                picBtn.setText("有图(" + num + ")");
                break;
        }

        allCommentAdaptor.addAll(list);
        mSwipeRefreshLayout.setRefreshing(false);
        mRecyclerView.refreshComplete(20);
        mLuRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        mRecyclerView.setRefreshing(true);
        refresh();
    }

    @OnClick({R.id.all, R.id.has_pic})
    void clickSelectPic(RadioButton radioButton){
        switch (radioButton.getId()){
            case R.id.all:
                commentRequest.setPic_type("");
                onRefresh();
                break;
            case R.id.has_pic:
                commentRequest.setPic_type("1");
                onRefresh();
                break;
        }
    }

    @OnClick({R.id.navigate})
    void turnToMap(){
        Intent intent=new Intent(this, BaiduMapActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.app_call_phone)
    void call(){
        if (null == productIntroduceOut || TextUtils.isEmpty(productIntroduceOut.getMobile())){
            T.showShort(context, "找不到号码");
            return;
        }
        AppUtils.callPhone(context, productIntroduceOut.getMobile());
    }

    private int tag = -1;
    @OnClick(R.id.app_collect)
    void clickCollect(){
        CollectPubIn collectPubIn = new CollectPubIn();
        collectPubIn.setType("2");
        collectPubIn.setCollect_value(seller_id);
        if ("已收藏".equals(collect.getText().toString())){
            iCollectionPresenter.cancelCollect(collectPubIn);
            tag = 3;
        } else {
            tag = 4;
            iCollectionPresenter.requestCollect(collectPubIn);
        }
    }

    @OnClick(R.id.shop_collect)
    void collect(){
        CollectPubIn collectPubIn = new CollectPubIn();
        collectPubIn.setType("2");
        collectPubIn.setCollect_value(seller_id);
        if ("已收藏".equals(collectBtn.getText().toString())){
            tag = 1;
            iCollectionPresenter.cancelCollect(collectPubIn);
        } else {
            tag = 2;
            iCollectionPresenter.requestCollect(collectPubIn);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ShareUtils.onActivityResult(this, requestCode, resultCode, data);
    }

    @Override
    public void responseCardInfo(ProductIntroduceOut productIntroduceOut) {
        this.productIntroduceOut = productIntroduceOut;

        ImageHelper.getInstance().displayDefinedImage(productIntroduceOut.getPic_url(), appIcon,
                R.mipmap.app_default_icon, R.mipmap.app_default_icon);
        userName.setText(productIntroduceOut.getNick_name());
        appTime.setText("从业时间：" + (TextUtils.isEmpty(productIntroduceOut.getWorktime()) ? "" :
                productIntroduceOut.getWorktime()));
        mainSell.setText("主营业务：" + (TextUtils.isEmpty(productIntroduceOut.getMain_business_desc()) ? "" :
                productIntroduceOut.getMain_business_desc()));

        if (1 == productIntroduceOut.getIs_collect()){
            collect.setText("已收藏");
            AppUtils.changeTextViewIcon(context, collect, R.mipmap.app_product_detail_selected_star_icon, 2);
            collectBtn.setText("已收藏");
        }else{
            collect.setText("收藏");
            collectBtn.setText("收藏");
            AppUtils.changeTextViewIcon(context, collect, R.mipmap.app_product_detail_star_icon, 2);
        }
//        qq.setText(productIntroduceOut.getQq());
//        weixin.setText(productIntroduceOut.getWeixin());
    }

    /**
     * 刷新数据
     */
    private void refresh(){
        if (TextUtils.isEmpty(commentRequest.getPic_type())){
            iAllCommentPresenter.loadData(commentRequest, 1);
        }else{
            iAllCommentPresenter.loadData(commentRequest, 2);
        }
    }

    /**
     * 加载数据
     */
    private void loadData(){
        if (TextUtils.isEmpty(commentRequest.getPic_type())) {
            iAllCommentPresenter.loadMore(commentRequest, 1);
        }else{
            iAllCommentPresenter.loadMore(commentRequest, 2);
        }
    }

    /**
     * 搜索评论数量
     */
    private void searchCommentNum() {
        iAllCommentPresenter.requestAllCommentNum(commentRequest);
    }

    @Override
    public void responseAllCommentNum(int all) {
        allBtn.setText("全部(" + all + ")");
    }

    @Override
    public void responsePicNum(int pic) {
        picBtn.setText("有图(" + pic + ")");
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

    @OnClick(R.id.publish_comment)
    void skipComment(){
        Intent r = new Intent(context, CommentActivity.class);
        r.putExtra("image", productIntroduceOut.getPic_url());
        r.putExtra("type", 1);//0 订单 1 商户
        r.putExtra("about_user_id", seller_id);
        startActivity(r);
    }
}
