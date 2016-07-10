package com.yuegangshaola.home.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.yuegangshaola.common.LogUtil;

/**
 * Created by new pc on 2016/7/5.
 */
public class HomeDefaultFragmentStatePagerAdapter extends FragmentStatePagerAdapter {

    //初始化变量
    private String tabTitles[];
    private Fragment fragments[];

    public HomeDefaultFragmentStatePagerAdapter(FragmentManager fm,String tabTitles[],Fragment fragments[]) {
        super(fm);

        this.tabTitles = tabTitles;
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
