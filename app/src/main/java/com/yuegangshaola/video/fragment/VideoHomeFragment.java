package com.yuegangshaola.video.fragment;

import android.support.v4.app.Fragment;

import com.yuegangshaola.R;
import com.yuegangshaola.common.BaseFragment;

import java.lang.reflect.Field;

/**
 * Created by new pc on 2016/7/3.
 */
public class VideoHomeFragment extends BaseFragment {
    @Override
    protected int getLayout() {
        return R.layout.video_fragment_home;
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
    protected void bindData() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
