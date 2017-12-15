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

public class DecorationTwoPingActivity extends AppCompatActivity {

    @BindView(R.id.app_search_toolbar)
    Toolbar appSearchToolbar1;
    @BindView(R.id.app_edit_content)
    EditText appEditContent1;
    @BindView(R.id.app_search_tv)
    TextView appSearchTv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decoration_two_ping_mian);
        ButterKnife.bind(this);

        appSearchToolbar1.setNavigationIcon(R.mipmap.app_back_arrow_icon);
        init();
    }

    private void init() {
        appSearchToolbar1.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
