package com.mantianhong.home.fragment;

import com.mantianhong.R;

/**
 * Created by new pc on 2016/7/5.
 */
public class HomeDefaultFragmentRedian extends YuegangshaolaBaseFragment {

    @Override
    protected int getRecyclerViewID() {
        return R.id.id_home_fragment_redian_recyclerview;
    }

    @Override
    protected String getFids() {
        return "99"; //表示最新的前30条新闻
    }

    @Override
    protected int getLayout() {
        return R.layout.home_fragment_redian;
    }
}
