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
 * 我的订单适配器
 */
public class MyOrderAdaptor extends BaseRecyclerAdapter<OrderDetail> {
    protected Context mContext;
    private int mPage;

    /**
     * 数据 即我们的任何类型的bean
     */
    protected List<OrderDetail> list;

    public MyOrderAdaptor(Context context, int mPage, List<OrderDetail> list){
        super(context, list);
        this.mContext = context;
        this.list = list;
        this.mPage = mPage;
    }

    @Override
    public int getItemLayoutId(int viewType) {
        if (0 == mPage){
            return R.layout.driver_order_going;
        }else{
            return R.layout.driver_order_finished_or_cancel;
        }
    }

    @Override
    public void bindData(RecyclerViewHolder holder, final int position, OrderDetail item) {
        holder.setText(R.id.driver_user_name_tv, item.getUser_name());
        holder.setText(R.id.driver_publish_time_tv, "发布于 " + item.getCreate_time());
        holder.setText(R.id.driver_addr_tv, item.getFrom_address() + "-" + item.getTo_address());
        holder.setText(R.id.driver_money_tv, "¥" + AppUtils.parseDouble(item.getMoney()/100) + "元");
        holder.setText(R.id.driver_end_time_tv, item.getCreate_time() + " 截止");
        holder.setText(R.id.driver_distance_tv, item.getDistance() + "km");
        if (0 == mPage){
            holder.setClickListener(R.id.driver_finished_btn, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != onClickListener){
                        onClickListener.onClick(v, position);
                    }
                }
            });
        }
    }
}