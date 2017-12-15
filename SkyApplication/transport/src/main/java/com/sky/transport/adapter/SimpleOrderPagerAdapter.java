package com.sky.transport.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.sky.transport.ui.fragment.OrderPageFragment;

/**
 * 订单tab
 * Created by Administrator on 2015/7/30.
 */
public class SimpleOrderPagerAdapter extends FragmentPagerAdapter {

    public static final String APP_ARG_PAGE = "ARG_PAGE";
    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[]{"进行中", "已完成", "已取消"};

    public SimpleOrderPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (0 == position){
            OrderPageFragment orderPageFragment = new OrderPageFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(APP_ARG_PAGE, 98);
            orderPageFragment.setArguments(bundle);
            return orderPageFragment;
        } else if (1 == position){
            OrderPageFragment orderPageFragment = new OrderPageFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(APP_ARG_PAGE, 1);
            orderPageFragment.setArguments(bundle);
            return orderPageFragment;
        } else if (2 == position){
            OrderPageFragment orderPageFragment = new OrderPageFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(APP_ARG_PAGE, 2);
            orderPageFragment.setArguments(bundle);
            return orderPageFragment;
        } else {
            return null;
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}