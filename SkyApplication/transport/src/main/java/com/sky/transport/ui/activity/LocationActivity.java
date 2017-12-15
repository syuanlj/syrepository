package com.sky.transport.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.sky.app.library.base.contract.IBasePresenter;
import com.sky.app.library.base.ui.BaseViewActivity;
import com.sky.app.library.utils.T;
import com.sky.transport.R;
import com.sky.transport.adapter.LocationAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 显示百度地图页面
 */
public class LocationActivity extends BaseViewActivity
        implements AdapterView.OnItemClickListener, OnGetGeoCoderResultListener,
        BaiduMap.OnMapStatusChangeListener, BaiduMap.OnMapTouchListener, BDLocationListener {

    @BindView(R.id.normal_toolbar)
    Toolbar toolbar;
    @BindView(R.id.transport_title)
    TextView title;

    /**
     * 显示的地图
     */
    @BindView(R.id.transport_mapview)
    MapView mapView;

    /**
     * 附近地点列表
     */
    @BindView(R.id.transport_location_position)
    ListView listView;

    /**
     * 列表适配器
     */
    private LocationAdapter locatorAdapter;

    /**
     * 列表数据
     */
    private List<PoiInfo> datas = new ArrayList();;

    /**
     * 百度地图对象
     */
    private BaiduMap mBaiduMap;

    /**
     * 地理编码
     */
    private GeoCoder mSearch;

    /**
     * 定位
     */
    private LocationClient mLocClient;

    /**
     * MapView 中央对于的屏幕坐标
     */
    private android.graphics.Point mCenterPoint = null;

    /**
     * 当前经纬度
     */
    private LatLng mLoactionLatLng;

    /**
     * 是否第一次定位
     */
    private boolean isFirstLoc = true;

    /**
     * 进度条
     */
    @BindView(R.id.transport_progress_bar)
    ProgressBar progressBar;

    /**
     * 按钮：回到原地
     */
    @BindView(R.id.transport_img_location_back_origin)
    ImageView back;

    /**
     * 请求码
     */
    private final static int REQUEST_CODE = 0x123;
    private int flag = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transport_location_view);
        Intent i = getIntent();
        this.flag = i.getIntExtra("flag", 1);
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
                    case R.id.transport_search://搜索
                        startActivityForResult(new Intent(LocationActivity.this, SearchPositionActivity.class),
                                REQUEST_CODE);
                        break;
                }
                return false;
            }
        });

        // 注册监听
        listView.setOnItemClickListener(this);
        mBaiduMap.setOnMapTouchListener(this);
        mSearch.setOnGetGeoCodeResultListener(this);

        // 地图状态监听
        mBaiduMap.setOnMapStatusChangeListener(this);
        mLocClient.registerLocationListener(this);
    }

    @Override
    protected void init() {
        title.setText(R.string.transport_location_string);
        toolbar.setNavigationIcon(R.mipmap.transport_back_arrow_icon);
        toolbar.inflateMenu(R.menu.transport_mapview_menu);

        // 地图初始化
        mBaiduMap = mapView.getMap();
        // 设置为普通矢量图地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        mapView.setPadding(10, 0, 0, 10);
        mapView.showZoomControls(false);
        // 设置缩放比例(500米)
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(15.0f);
        mBaiduMap.setMapStatus(msu);

        // 初始化当前 MapView 中心屏幕坐标
        mCenterPoint = mBaiduMap.getMapStatus().targetScreen;
        mLoactionLatLng = mBaiduMap.getMapStatus().target;

        // 地理编码
        mSearch = GeoCoder.newInstance();

        // 定位初始化
        mLocClient = new LocationClient(getApplicationContext());
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(5000);
        mLocClient.setLocOption(option);
        mLocClient.start();
        // 可定位
        mBaiduMap.setMyLocationEnabled(true);

        //数据适配
        locatorAdapter = new LocationAdapter(this, datas);
        listView.setAdapter(locatorAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        back.setImageResource(R.mipmap.transport_back_origin_normal);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            locatorAdapter.setSelectItemIndex(0);

            // 获取经纬度
            LatLng latLng = data.getParcelableExtra("LatLng");
            if (null == latLng){
                T.showShort(context, "找不到对应的地名");
                return;
            }

            // 实现动画跳转
            MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(latLng);
            mBaiduMap.animateMapStatus(u);
            mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(latLng));
        }
    }

    @OnClick(R.id.transport_img_location_back_origin)
    void clickOrigin(){
        if (mLoactionLatLng != null) {
            // 实现动画跳转
            back.setImageResource(R.mipmap.transport_back_origin_select);
            MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(mLoactionLatLng);
            mBaiduMap.animateMapStatus(u);
            mSearch.reverseGeoCode((new ReverseGeoCodeOption()).location(mLoactionLatLng));
        }
    }

    /**
     * 显示列表，查找附近的地点
     */
    public void searchPoi() {
        if (mCenterPoint == null) return;

        // 获取当前 MapView 中心屏幕坐标对应的地理坐标
        LatLng currentLatLng = mBaiduMap.getProjection().fromScreenLocation(mCenterPoint);

        // 发起反地理编码检索
        mSearch.reverseGeoCode((new ReverseGeoCodeOption()).location(currentLatLng));
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected IBasePresenter presenter() {
        return null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        PoiInfo info = (PoiInfo) locatorAdapter.getItem(position);
        LatLng la = info.location;

        //选中位置
        Intent intent = new Intent();
        intent.putExtra("city", info.city);
        intent.putExtra("address", info.address);
        intent.putExtra("position", info.name);
        intent.putExtra("longitude", la.longitude);
        intent.putExtra("latitude", la.latitude);
        intent.putExtra("flag", flag);
        setResult(RESULT_OK, intent);
        LocationActivity.this.finish();
    }

    @OnClick(R.id.transport_img_location_back_origin)
    void clickBackPosition(){
        if (mLoactionLatLng != null) {
            // 实现动画跳转
            back.setImageResource(R.mipmap.transport_back_origin_select);
            MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(mLoactionLatLng);
            mBaiduMap.animateMapStatus(u);
            mSearch.reverseGeoCode((new ReverseGeoCodeOption()).location(mLoactionLatLng));
        }
    }

    @Override
    public void onGetGeoCodeResult(GeoCodeResult result) {
        // 正向地理编码指的是由地址信息转换为坐标点的过程
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            return;
        }
    }

    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            return;
        }
        // 获取反向地理编码结果
        PoiInfo mCurrentInfo = new PoiInfo();
        mCurrentInfo.address = result.getAddress();
        mCurrentInfo.location = result.getLocation();
        mCurrentInfo.name = result.getAddress();
        datas.clear();
        if (!TextUtils.isEmpty(result.getAddress())) {
            datas.add(mCurrentInfo);
        }
        if (result.getPoiList() != null && result.getPoiList().size() > 0) {
            datas.addAll(result.getPoiList());
        }
        locatorAdapter.notifyDataSetChanged();
        progressBar.setVisibility(View.GONE);
    }

    /**
     * 手势操作地图，设置地图状态等操作导致地图状态开始改变。
     *
     * @param status 地图状态改变开始时的地图状态
     */
    public void onMapStatusChangeStart(MapStatus status) {

    }

    /**
     * 地图状态变化中
     *
     * @param status 当前地图状态
     */
    public void onMapStatusChange(MapStatus status) {
        datas.clear();
        mSearch.reverseGeoCode(new ReverseGeoCodeOption()
                .location(status.target));
        listView.setSelection(0);
        locatorAdapter.setSelectItemIndex(0);
    }

    /**
     * 地图状态改变结束
     *
     * @param status 地图状态改变结束后的地图状态
     */
    public void onMapStatusChangeFinish(MapStatus status) {

    }

    @Override
    public void onTouch(MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            // 显示列表，查找附近的地点
            searchPoi();
            back.setImageResource(R.mipmap.transport_back_origin_normal);
        }
    }

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        //定位监听
        if (bdLocation == null || mapView == null) return;
        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(bdLocation.getRadius())
                .latitude(bdLocation.getLatitude())
                .longitude(bdLocation.getLongitude()).build();
        mBaiduMap.setMyLocationData(locData);

        Double mLatitude = bdLocation.getLatitude();
        Double mLongitude = bdLocation.getLongitude();

        LatLng currentLatLng = new LatLng(mLatitude, mLongitude);
        mLoactionLatLng = new LatLng(mLatitude, mLongitude);

        // 是否第一次定位
        if (isFirstLoc) {
            isFirstLoc = false;

            // 实现动画跳转
            MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(currentLatLng);
            mBaiduMap.animateMapStatus(u);
            mSearch.reverseGeoCode((new ReverseGeoCodeOption()).location(currentLatLng));
            return;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestoryActivity() {
        // 退出时销毁定位
        mLocClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mapView.onDestroy();
        mapView = null;
    }
}