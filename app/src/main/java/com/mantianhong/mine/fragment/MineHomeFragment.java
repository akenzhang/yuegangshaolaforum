package com.mantianhong.mine.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mantianhong.R;
import com.mantianhong.common.BaseFragment;
import com.mantianhong.common.LazyLoadBaseFragment;
import com.mantianhong.common.LogUtil;

import java.lang.reflect.Field;

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
