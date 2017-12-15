package com.sky.transport.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sky.transport.R;

/**
 * 主页头部汽车列表
 */
public class CarRaidoButton extends RelativeLayout {

    RelativeLayout container;
    TextView mRadioButton;
    ImageView customRadioButton;
    Drawable keyDown;
    Drawable keyUp;

    boolean isChecked = false;//是否选中
    
    public CarRaidoButton(Context context) {
        super(context);
    }
    
    public CarRaidoButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        initAttr(context, attrs);
    }

    /**
     * 初始化视图
     * @param context
     */
    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.transport_car_raidobutton_layout, this, true);
        container = (RelativeLayout)view.findViewById(R.id.transport_car_1_rl);
        mRadioButton = (TextView)view.findViewById(R.id.transport_car_1_txt);
        customRadioButton = (ImageView)view.findViewById(R.id.transport_car_1_icon);
    }
    
    private void initAttr(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.transport_car_list);
        
        boolean isChecked = ta.getBoolean(R.styleable.transport_car_list_isChecked, false);
        this.isChecked = isChecked;
        CharSequence txt = ta.getString(R.styleable.transport_car_list_txt);
        keyDown = ta.getDrawable(R.styleable.transport_car_list_image_down);
        keyUp = ta.getDrawable(R.styleable.transport_car_list_image_up);

        if (!TextUtils.isEmpty(txt)){
            mRadioButton.setText(txt);
        }
        setCheckedView(isChecked);
        ta.recycle();
    }
    
    public void setCheckedView(Boolean checked) {
        mRadioButton.setSelected(checked);
        if (checked) {
            customRadioButton.setImageDrawable(keyDown);
        } else {
            customRadioButton.setImageDrawable(keyUp);
        }
    }
}
