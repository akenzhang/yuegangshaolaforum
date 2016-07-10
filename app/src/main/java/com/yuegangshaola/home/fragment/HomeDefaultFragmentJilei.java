package com.yuegangshaola.home.fragment;

import com.yuegangshaola.R;
import com.yuegangshaola.common.BaseFragment;

/**
 * Created by new pc on 2016/7/5.
 */
public class HomeDefaultFragmentJilei extends YuegangshaolaBaseFragment {

    @Override
    protected int getRecyclerViewID() {
        return R.id.id_home_fragment_jilei_recyclerview;
    }

    @Override
    protected int getFid() {
        return 20;
    }

    @Override
    protected int getLayout() {
        return R.layout.home_fragment_jilei_layout;
    }
}
