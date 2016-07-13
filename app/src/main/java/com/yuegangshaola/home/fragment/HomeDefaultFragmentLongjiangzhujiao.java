package com.yuegangshaola.home.fragment;

import com.yuegangshaola.R;

/**
 * Created by new pc on 2016/7/5.
 */
public class HomeDefaultFragmentLongjiangzhujiao extends YuegangshaolaBaseFragment {

    @Override
    protected int getRecyclerViewID() {
        return R.id.id_home_fragment_longjiangzhujiao_recyclerview;
    }

    @Override
    protected String getFids() {
        return "22,19";
    }

    @Override
    protected int getLayout() {
        return R.layout.home_fragment_longjiangzhujiao;
    }
}
