package com.yuegangshaola.home.fragment;

import com.yuegangshaola.R;
import com.yuegangshaola.common.BaseFragment;

/**
 * Created by new pc on 2016/7/5.
 */
public class HomeDefaultFragmentZuopin extends YuegangshaolaBaseFragment {

    @Override
    protected int getRecyclerViewID() {
        return R.id.id_home_fragment_zuopin_recyclerview;
    }

    @Override
    protected int getFid() {
        return 10;
    }

    @Override
    protected int getLayout() {
        return R.layout.home_fragment_zuopin_layout;
    }
}
