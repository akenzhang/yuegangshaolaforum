package com.yuegangshaola.contact.fragment;

import android.support.v4.app.Fragment;

import com.yuegangshaola.R;
import com.yuegangshaola.common.BaseFragment;

import java.lang.reflect.Field;

/**
 * Created by new pc on 2016/7/4.
 */
public class ContactHomeFragment extends BaseFragment {
    @Override
    protected int getLayout() {
        return R.layout.contact_fragment_home_layout;
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
