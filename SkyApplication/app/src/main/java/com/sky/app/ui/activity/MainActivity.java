package com.sky.app.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.alibaba.mobileim.YWAPI;
import com.alibaba.mobileim.YWIMKit;
import com.sky.app.R;
import com.sky.app.bean.AppKey;
import com.sky.app.bean.UserBean;
import com.sky.app.library.base.contract.IBasePresenter;
import com.sky.app.library.base.ui.BaseViewActivity;
import com.sky.app.library.component.TabRaidoButton;
import com.sky.app.library.utils.BaseAppManager;
import com.sky.app.library.utils.T;
import com.sky.app.ui.activity.openIM.OpenIMLoginActivity;
import com.sky.app.ui.activity.user.LoginActivity;
import com.sky.app.ui.fragment.HomePageFragment;
import com.sky.app.ui.fragment.MessageCenterFragment;
import com.sky.app.ui.fragment.MineFragment;
import com.sky.app.ui.fragment.OpenIMFragment;
import com.sky.app.ui.fragment.PublishFragment;
import com.trello.rxlifecycle.components.support.RxFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 主页
 */
public class MainActivity extends BaseViewActivity {

    @BindView(R.id.app_home_page)
    TabRaidoButton homePage;
    @BindView(R.id.app_publish)
    TabRaidoButton publish;
    @BindView(R.id.app_message)
    TabRaidoButton message;
    @BindView(R.id.app_mine)
    TabRaidoButton mine;
    @BindView(R.id.app_home_openIM)
    TabRaidoButton openIM;

    private RxFragment[] pages;

    private int lastIndexOfPage = -1;
    private int currentPageIndex = 0;
    private long exitTime;
    private YWIMKit mIMKit;
    private String target;

    int flag = -1;
    private Fragment conversationFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_main);
    }

    @Override
    protected IBasePresenter presenter() {
        return null;
    }

    @Override
    protected void init() {
        flag = getIntent().getIntExtra(AppKey.HomePage.APP_TAB_LABEL, AppKey.HomePage.index);
        pages = new RxFragment[5];
        pages[AppKey.HomePage.index] = getIndexFragment(flag);
        pages[AppKey.HomePage.publish] = new PublishFragment();
        pages[AppKey.HomePage.message] = new MessageCenterFragment();
        pages[AppKey.HomePage.mime] = new MineFragment();

    }

    @Override
    protected void onDestoryActivity() {

    }

    private Fragment getConversationFragment(int open){
        Bundle bundle = new Bundle();
        bundle.putInt(AppKey.HomePage.APP_TAB_LABEL, open);
        conversationFragment.setArguments(bundle);
        return conversationFragment;
    }




    /**
     * 获取
     *
     * @param flag 是否需要更新
     * @return 首页
     */
    private HomePageFragment getIndexFragment(int flag) {
        HomePageFragment indexFragment = new HomePageFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(AppKey.HomePage.APP_TAB_LABEL, flag);
        indexFragment.setArguments(bundle);
        return indexFragment;
    }

    @Override
    public void initViewsAndEvents() {
        switch (flag) {
            case AppKey.HomePage.index:
                homePage.performClick();
                break;
            case AppKey.HomePage.publish:
                publish.performClick();
                break;
            case AppKey.HomePage.message:
                message.performClick();
                break;
            case AppKey.HomePage.mime:
                mine.performClick();
                break;
//            case AppKey.HomePage.openIM:
//                openIM.performClick();
//                break;
        }
    }

    @OnClick({R.id.app_home_page, R.id.app_publish, R.id.app_message, R.id.app_mine,R.id.app_home_openIM})
    public void clickTab(TabRaidoButton btn) {
        switch (btn.getId()) {
            case R.id.app_home_page:
                displayPage(AppKey.HomePage.index);
                break;
            case R.id.app_publish:
                displayPage(AppKey.HomePage.publish);
                break;
            case R.id.app_message:
                displayPage(AppKey.HomePage.message);
                break;
            case R.id.app_mine:
                displayPage(AppKey.HomePage.mime);
                break;
            case R.id.app_home_openIM:
                startActivity(new Intent(context,OpenIMLoginActivity.class));
                break;
        }
    }

    /**
     * 显示Tab页
     *
     * @param pageIndex
     */
    public void displayPage(int pageIndex) {
        if (pageIndex == lastIndexOfPage) return;

        currentPageIndex = pageIndex;

        switchTab(pageIndex);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        if (pages[pageIndex].isAdded()) {
            ft.show(pages[pageIndex]);
        } else {
            ft.add(R.id.driver_content_fl, pages[pageIndex], String.valueOf(pageIndex));
        }
        if (lastIndexOfPage >= 0) {
            ft.hide(pages[lastIndexOfPage]);
        }
        ft.commitAllowingStateLoss();
        lastIndexOfPage = pageIndex;
    }

    /**
     * 切换标签选择
     *
     * @param position 位置
     */
    private void switchTab(int position) {

        switch (position) {
            case 0:
                homePage.setCheckedView(true);
                publish.setCheckedView(false);
                message.setCheckedView(false);
                openIM.setCheckedView(false);
                mine.setCheckedView(false);
                break;
            case 1:
                homePage.setCheckedView(false);
                publish.setCheckedView(true);
                message.setCheckedView(false);
                openIM.setCheckedView(false);
                mine.setCheckedView(false);
                break;
            case 2:
                homePage.setCheckedView(false);
                publish.setCheckedView(false);
                message.setCheckedView(true);
                openIM.setCheckedView(false);
                mine.setCheckedView(false);
                break;
            case 3:
                homePage.setCheckedView(false);
                publish.setCheckedView(false);
                message.setCheckedView(false);
                mine.setCheckedView(true);
                openIM.setCheckedView(false);
                break;
            case 4:
                homePage.setCheckedView(false);
                publish.setCheckedView(false);
                message.setCheckedView(false);
                mine.setCheckedView(false);
                openIM.setCheckedView(true);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            T.showShort(context, "再按一次退出应用");
            exitTime = System.currentTimeMillis();
        } else {
//            this.moveTaskToBack(true);
            BaseAppManager.getInstance().AppExit(context);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent); //这一句必须的，否则Intent无法获得最新的数据
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!UserBean.getInstance().checkUserLogin()) {
            startActivity(new Intent(context, LoginActivity.class));
        }
    }
}
