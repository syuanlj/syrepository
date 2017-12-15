package com.sky.transport.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sky.app.library.base.contract.IBasePresenter;
import com.sky.app.library.base.ui.BaseViewActivity;
import com.sky.transport.R;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 添加额外需求【货运端】
 */
public class ExtraActivity extends BaseViewActivity{

    @BindView(R.id.normal_toolbar)
    Toolbar toolbar;
    @BindView(R.id.transport_title)
    TextView transportTitle;

    Set<String> set = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transport_extra);
    }

    @Override
    protected IBasePresenter presenter() {
        return null;
    }

    @Override
    protected void init() {
        transportTitle.setText(R.string.transport_extra_string);
        toolbar.setNavigationIcon(R.mipmap.transport_back_arrow_icon);
    }

    @Override
    protected void onDestoryActivity() {

    }

    @Override
    public void initViewsAndEvents() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @OnClick({R.id.transport_1_rl, R.id.transport_2_rl, /*R.id.transport_3_rl, */R.id.transport_4_rl, R.id.transport_5_rl})
    void clickType(RelativeLayout relativeLayout){
        CheckBox checkBox = (CheckBox) relativeLayout.getChildAt(1);
        TextView textView = (TextView) relativeLayout.getChildAt(0);
        if (checkBox.isChecked()){
            checkBox.setChecked(false);
            set.remove(textView.getText());
        }else{
            checkBox.setChecked(true);
            set.add(textView.getText().toString());
        }
    }

    @OnClick(R.id.transport_confirm_tv)
    void clickConfirm(){
        StringBuilder stringBuilder = new StringBuilder();
        for (Iterator<String> it = set.iterator(); it.hasNext();){
            stringBuilder.append(it.next() + "，");
        }
        String str = "";
        if (!TextUtils.isEmpty(stringBuilder.toString())){
            str = stringBuilder.toString().substring(0, stringBuilder.toString().length() - 1);
        }
        Intent i = new Intent();
        i.putExtra("extra", str);
        setResult(RESULT_OK, i);
        finish();
    }
}
