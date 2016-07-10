package com.yuegangshaola.home.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.yuegangshaola.R;
import com.yuegangshaola.common.BaseFragment;
import com.yuegangshaola.common.LogUtil;

/**
 * Created by new pc on 2016/7/5.
 */
public class HomeDefaultFragmentZhujiao extends YuegangshaolaBaseFragment {

    @Override
    protected int getRecyclerViewID() {
        return R.id.id_home_fragment_zhujiao_recyclerview;
    }

    @Override
    protected int getFid() {
        return 22;
    }

    @Override
    protected int getLayout() {
        return R.layout.home_fragment_zhujiao_layout;
    }
}
