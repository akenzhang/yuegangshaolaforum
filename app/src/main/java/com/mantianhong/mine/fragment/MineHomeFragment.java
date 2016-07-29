package com.mantianhong.mine.fragment;

import com.mantianhong.R;
import com.mantianhong.utiltools.LazyLoadBaseFragment;
import com.mantianhong.utiltools.LogUtil;

/**
 * Created by new pc on 2016/7/3.
 */
public class MineHomeFragment extends LazyLoadBaseFragment {

    @Override
    protected int getLayout() {
        return R.layout.mine_fragment_home;
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initVariable() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void lazyLoad() {
        LogUtil.e("ContactHomeFragment==>lazyLoad()");

    }

}
