package com.mantianhong.home.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by new pc on 2016/7/4.
 */
public class HomeActivityPagerAdapter extends FragmentStatePagerAdapter {

    private Fragment fragments[];
    public HomeActivityPagerAdapter(FragmentManager fm, Fragment fragments[]){
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }
}
