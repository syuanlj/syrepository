package com.sky.app.ui.activity.search;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.sky.app.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DecorationTwoShopActivity extends AppCompatActivity {

    @BindView(R.id.app_search_toolbar)
    Toolbar appSearchToolbar;
    @BindView(R.id.app_edit_content)
    EditText appEditContent;
    @BindView(R.id.app_search_tv)
    TextView appSearchTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decoration_two_shop);
        ButterKnife.bind(this);
        appSearchToolbar.setNavigationIcon(R.mipmap.app_back_arrow_icon);
        init();
    }

    private void init() {
        appSearchToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
