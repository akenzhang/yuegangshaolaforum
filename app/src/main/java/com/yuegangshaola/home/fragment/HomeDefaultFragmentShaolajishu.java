package com.yuegangshaola.home.fragment;

import com.yuegangshaola.R;

/**
 * Created by new pc on 2016/7/5.
 */
public class HomeDefaultFragmentShaolajishu extends YuegangshaolaBaseFragment {

    @Override
    protected int getRecyclerViewID() {
        return R.id.id_home_fragment_shaolajishu_recyclerview;
    }

    @Override
    protected String getFids() {
        return "7,8,9,20,21,17";
    }

    @Override
    protected int getLayout() {
        return R.layout.home_fragment_shaolajishu;
    }
}
