package com.yuegangshaola.home.fragment;

import com.yuegangshaola.R;

/**
 * Created by new pc on 2016/7/5.
 */
public class HomeDefaultFragmentXianchangdiandi extends YuegangshaolaBaseFragment {

    @Override
    protected int getRecyclerViewID() {
        return R.id.id_home_fragment_xianchangdiandi_recyclerview;
    }

    @Override
    protected String getFids() {
        return "13,11,22,15,18";
    }

    @Override
    protected int getLayout() {
        return R.layout.home_fragment_xianchangdiandi_layout;
    }
}
