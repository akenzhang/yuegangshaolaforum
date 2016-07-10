package com.yuegangshaola.home.fragment;

import android.content.Context;

import com.yuegangshaola.R;
import com.yuegangshaola.common.BaseFragment;

/**
 * Created by new pc on 2016/7/5.
 */
public class HomeDefaultFragmentChashao extends YuegangshaolaBaseFragment {

    @Override
    protected int getRecyclerViewID() {
        return R.id.id_home_fragment_chashao_recyclerview;
    }

    @Override
    protected int getFid() {
        return 9;
    }

    @Override
    protected int getLayout() {
        return R.layout.home_fragment_chashao_layout;
    }
}
