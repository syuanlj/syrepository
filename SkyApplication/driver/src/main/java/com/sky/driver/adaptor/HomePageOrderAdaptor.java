package com.sky.driver.adaptor;

import android.content.Context;
import android.view.View;

import com.sky.app.library.base.adaptor.BaseRecyclerAdapter;
import com.sky.app.library.base.adaptor.holder.RecyclerViewHolder;
import com.sky.app.library.utils.AppUtils;
import com.sky.driver.R;
import com.sky.driver.bean.OrderDetail;

import java.util.List;

/**
 * RecyclerView通用适配器
 */
public class HomePageOrderAdaptor extends BaseRecyclerAdapter<OrderDetail> {
    protected Context mContext;

    /**
     * 数据 即我们的任何类型的bean
     */
    protected List<OrderDetail> list;

    public HomePageOrderAdaptor(Context context, List<OrderDetail> list){
        super(context, list);
        this.mContext = context;
        this.list = list;
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.driver_home_page_item;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, final int position, OrderDetail item) {
        holder.setText(R.id.driver_user_name_tv, item.getUser_name());
        holder.setText(R.id.driver_publish_time_tv, "发布于 " + item.getCreate_time());
        holder.setText(R.id.driver_addr_tv, item.getFrom_address() + "-" + item.getTo_address());
        holder.setText(R.id.driver_money_tv, "¥" + AppUtils.parseDouble(item.getMoney()/100) + "元");
        holder.setText(R.id.driver_end_time_tv, item.getCreate_time() + " 截止");
        holder.setText(R.id.driver_distance_tv, item.getDistance() + "km");
        holder.setClickListener(R.id.driver_order_btn, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onClickListener){
                    onClickListener.onClick(v, position);
                }
            }
        });
    }
}