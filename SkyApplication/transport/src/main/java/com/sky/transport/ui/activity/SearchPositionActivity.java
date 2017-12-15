package com.sky.transport.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.sky.app.library.base.contract.IBasePresenter;
import com.sky.app.library.base.ui.BaseViewActivity;
import com.sky.app.library.component.CustomTextWatcher;
import com.sky.app.library.utils.T;
import com.sky.transport.R;
import com.sky.transport.adapter.SearchPositionAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * 用百度地图(搜索页面)
 */
public class SearchPositionActivity extends BaseViewActivity implements AdapterView.OnItemClickListener {
    private Context mContext;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    /**
     * 附近地点列表
     */
    @BindView(R.id.transport_search_lv)
    ListView searchList;

    /**
     * 列表适配器
     */
    private SearchPositionAdapter locatorAdapter;

    /**
     * 列表数据
     */
    private List<SuggestionResult.SuggestionInfo> datas;

    /**
     * 进度条
     */
    @BindView(R.id.transport_search_loading)
    ProgressBar progressBar;

    /**
     * 建议查询
     */
    private SuggestionSearch mSuggestionSearch;

    /**
     * 输入框
     */
    @BindView(R.id.transport_search_et)
    EditText et_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transport_location_search);
    }

    @Override
    protected void initViewsAndEvents() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //搜索按钮
        et_search.addTextChangedListener(new CustomTextWatcher(et_search, new CustomTextWatcher.ICustomTextWatcher() {
            @Override
            public void beforeChange(EditText e, CharSequence s, int start, int count, int after) {

            }

            @Override
            public void changing(EditText e, CharSequence s, int start, int before, int count) {

            }

            @Override
            public void changed(EditText e, Editable s) {
                if (!TextUtils.isEmpty(e.getText().toString())) {
                    progressBar.setVisibility(View.VISIBLE);
                    // 根据输入框的内容，进行搜索
                    mSuggestionSearch.requestSuggestion(new SuggestionSearchOption().keyword(et_search.getText().toString()).city(""));
                } else {
                    T.showShort(context, "请输入地点");
                }
            }
        }));

        // 注册监听
        searchList.setOnItemClickListener(this);
    }

    @Override
    protected void init() {
        mContext = this;
        toolbar.setNavigationIcon(R.mipmap.transport_back_arrow_icon);

        // 建议查询
        mSuggestionSearch = SuggestionSearch.newInstance();
        mSuggestionSearch.setOnGetSuggestionResultListener(mSuggestionResultListener);

        // 列表初始化
        datas = new ArrayList();
        locatorAdapter = new SearchPositionAdapter(this, datas);
        searchList.setAdapter(locatorAdapter);
    }

    @Override
    protected void onDestoryActivity() {
        if (null != mSuggestionSearch){
            mSuggestionSearch.destroy();
        }
    }

    /**
     * 获取搜索的内容
     */
    private OnGetSuggestionResultListener mSuggestionResultListener = new OnGetSuggestionResultListener() {
        public void onGetSuggestionResult(SuggestionResult res) {
            progressBar.setVisibility(View.GONE);
            if (res == null || res.getAllSuggestions() == null) {
                T.showShort(context, "没找到结果");
                return;
            }
            //获取在线建议检索结果
            if (datas != null) {
                datas.clear();
                for (SuggestionResult.SuggestionInfo suggestionInfos : res.getAllSuggestions()) {
                    datas.add(suggestionInfos);
                }
                locatorAdapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    protected IBasePresenter presenter() {
        return null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        locatorAdapter.setSelectSearchItemIndex(position);
        locatorAdapter.notifyDataSetChanged();

        Intent intent = new Intent();
        // 设置坐标
        intent.putExtra("LatLng", datas.get(position).pt);
        setResult(RESULT_OK, intent);
        SearchPositionActivity.this.finish();
    }
}