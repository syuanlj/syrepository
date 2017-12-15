package com.sky.driver.component;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.sky.app.library.utils.AppUtils;
import com.sky.driver.R;

public class DropdownButtonsController{
    public Context context = null;
    private static DropdownButtonsController instance = null;

    private View driverBgLayout;//背景
    private DropdownButton driverDefaultSortBtn, driverDistanceSortBtn, driverFilterBtn;//按钮
    private ListView driverListSortDv;//排序
    private RelativeLayout driverFilterLayout;//筛选下拉框
    OnClickItem onClickItem;
    private int currentTab = -1;

    //默认动画
    Animation driverDropdownIn, driverDropdownOut, driverDropdownBgOut;

    public static DropdownButtonsController getInstance(){
        if (null == instance){
            synchronized (DropdownButtonsController.class){
                if (null == instance){
                    instance = new DropdownButtonsController();
                }
            }
        }
        return instance;
    }

    public DropdownButtonsController(){
    }

    /**
     * 设置排序文字
     */
    public void setSortTxt(String word){
        driverDefaultSortBtn.setText(word);
    }

    /**
     * 重置窗口效果
     */
    private void reset() {
        //设置文字
        driverDefaultSortBtn.setText("默认排序");
        driverDistanceSortBtn.setText("距离优先");
        driverFilterBtn.setText("筛选");

        //按钮恢复
        driverDefaultSortBtn.setChecked(ContextCompat.getDrawable(context, R.mipmap.driver_sort_arrow_icon), false, true);
        driverDistanceSortBtn.setChecked(null, false, true);
        driverFilterBtn.setChecked(ContextCompat.getDrawable(context, R.mipmap.driver_filter_icon), false, true);

        //背景
        driverBgLayout.setVisibility(View.GONE);

        //排序下拉框/筛选下拉框
        driverListSortDv.setVisibility(View.GONE);
        driverFilterLayout.setVisibility(View.GONE);

        //清除动画
        driverListSortDv.clearAnimation();
        driverFilterLayout.clearAnimation();
        driverBgLayout.clearAnimation();
    }

    /**
     * 显示下拉框
     */
    public void show(int i) {
        if (currentTab == i) return;

        //隐藏当前的控件
        hide();

        currentTab = i;
        switch (i){
            case 0://排序
                driverBgLayout.clearAnimation();
                driverBgLayout.setVisibility(View.VISIBLE);
                driverDefaultSortBtn.setChecked(ContextCompat.getDrawable(context, R.mipmap.driver_sort_arrow_icon), true, true);
                driverListSortDv.clearAnimation();
                driverListSortDv.startAnimation(driverDropdownIn);
                driverListSortDv.setVisibility(View.VISIBLE);
                driverFilterBtn.setChecked(ContextCompat.getDrawable(context, R.mipmap.driver_filter_icon), false, true);
                break;
            case 1://距离优先
                driverDistanceSortBtn.setChecked(null, true, true);
                driverFilterBtn.setChecked(ContextCompat.getDrawable(context, R.mipmap.driver_filter_icon), false, true);
                driverDefaultSortBtn.setChecked(ContextCompat.getDrawable(context, R.mipmap.driver_sort_arrow_icon), false, true);
                break;
            case 2://筛选
                driverBgLayout.clearAnimation();
                driverBgLayout.setVisibility(View.VISIBLE);
                driverFilterBtn.setChecked(ContextCompat.getDrawable(context, R.mipmap.driver_filter_icon), true, true);
                driverFilterLayout.clearAnimation();
                driverFilterLayout.startAnimation(driverDropdownIn);
                driverFilterLayout.setVisibility(View.VISIBLE);
                driverDefaultSortBtn.setChecked(ContextCompat.getDrawable(context, R.mipmap.driver_sort_arrow_icon), false, true);
                break;
        }
    }

    /**
     * 隐藏下拉框
     */
    public void hide() {
        if (-1 == currentTab) return;
        switch (currentTab){
            case 0://排序
                //tab按钮
                driverListSortDv.clearAnimation();
                driverListSortDv.startAnimation(driverDropdownOut);
                driverListSortDv.setVisibility(View.GONE);

                //背景
                driverBgLayout.clearAnimation();
                driverBgLayout.startAnimation(driverDropdownBgOut);
                driverBgLayout.setVisibility(View.GONE);
                break;
            case 1://距离优先
                driverDistanceSortBtn.setChecked(null, false, true);
                break;
            case 2://筛选
                //按钮
                driverFilterLayout.clearAnimation();
                driverFilterLayout.startAnimation(driverDropdownOut);
                driverFilterLayout.setVisibility(View.GONE);

                //背景
                driverBgLayout.clearAnimation();
                driverBgLayout.startAnimation(driverDropdownBgOut);
                driverBgLayout.setVisibility(View.GONE);
                break;
        }
        currentTab = -1;
        AppUtils.hideSoftInput(driverBgLayout);
    }

    /**
     * 初始化窗口动画
     */
    public void initAnim(){
        driverDropdownIn = AnimationUtils.loadAnimation(context, R.anim.driver_dropdown_in);
        driverDropdownOut = AnimationUtils.loadAnimation(context, R.anim.driver_dropdown_out);
        driverDropdownBgOut = AnimationUtils.loadAnimation(context, R.anim.driver_dropdown_mask_out);
    }

    /**
     * 初始化操作
     * @param context
     */
    public void init(Context context) {
        this.context = context;
        showListView();

        //重置视图
        reset();

        //初始化动画
        initAnim();
    }

    /**
     * 回调接口
     */
    public interface OnClickItem{
        void item(String[] arrays, int position);
    }
    /**
     * 展示默认排序列表
     */
    private void showListView(){
        final String[] arrays = {"默认排序", "价格最高", "价格最低"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.driver_dropdown_tab_list_item, arrays);
        driverListSortDv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (null != onClickItem){
                    onClickItem.item(arrays, position);
                }
            }
        });
        driverListSortDv.setAdapter(adapter);
    }

    public void setDriverBgLayout(View driverBgLayout) {
        this.driverBgLayout = driverBgLayout;
    }
    public void setDriverDefaultSortBtn(DropdownButton driverDefaultSortBtn) {
        this.driverDefaultSortBtn = driverDefaultSortBtn;
    }
    public void setDriverDistanceSortBtn(DropdownButton driverDistanceSortBtn) {
        this.driverDistanceSortBtn = driverDistanceSortBtn;
    }
    public void setDriverFilterBtn(DropdownButton driverFilterBtn) {
        this.driverFilterBtn = driverFilterBtn;
    }
    public void setDriverListSortDv(ListView driverListSortDv) {
        this.driverListSortDv = driverListSortDv;
    }
    public void setDriverFilterLayout(RelativeLayout driverFilterLayout) {
        this.driverFilterLayout = driverFilterLayout;
    }
    public void setOnClickItem(OnClickItem onClickItem) {
        this.onClickItem = onClickItem;
    }
}