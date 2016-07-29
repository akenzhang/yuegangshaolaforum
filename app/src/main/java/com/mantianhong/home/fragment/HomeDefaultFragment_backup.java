package com.mantianhong.home.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import com.mantianhong.R;
import com.mantianhong.common.BaseFragment;
import com.mantianhong.common.CustomViewPager;
import com.mantianhong.common.LogUtil;
import com.mantianhong.home.adapter.HomeDefaultFragmentCategoriesAdapter;

import java.lang.reflect.Field;

/**
 * Created by new pc on 2016/7/3.
 */
public class HomeDefaultFragment_backup extends BaseFragment {

    private CustomViewPager mHome_fragment_toptab_viewpager;
    private TabLayout mTabLayout;

    //初始化数据
    private String tabTitles[];
    private Fragment fragments[];

    @Override
    protected int getLayout() {
        return R.layout.home_fragment_home;
    }

    @Override
    protected void initView() {}

    @Override
    protected void initVariable() {

        mHome_fragment_toptab_viewpager = (CustomViewPager) this.getActivity().findViewById(R.id.id_home_fragment_top_tab);
        mTabLayout = (TabLayout) this.getActivity().findViewById(R.id.id_home_fragment_tablayout);

        tabTitles = new String[]{
                "最新资讯" //HomeDefaultFragmentRedian
                ,"烧腊技术分享" //HomeDefaultFragmentShaolajishu   7,8,9,20,21,17
                ,"培训现场点滴" //HomeDefaultFragmentXianchangdiandi  13,11,22,15,18
                ,"隆江猪脚" //HomeDefaultFragmentLongjiangzhujiao  22,19
        };

        fragments = new Fragment[]{
                new HomeDefaultFragmentRedian()
                ,new HomeDefaultFragmentShaolajishu()
                ,new HomeDefaultFragmentXianchangdiandi()
                ,new HomeDefaultFragmentLongjiangzhujiao()
        };

    }

    @Override
    protected void initListener() {

        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                LogUtil.e(String.valueOf(tabTitles[tab.getPosition()]));

                mHome_fragment_toptab_viewpager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

    }

    @Override
    protected void bindData() {
        try {
            HomeDefaultFragmentCategoriesAdapter adapter = new HomeDefaultFragmentCategoriesAdapter(
                    this.getChildFragmentManager()
                    , tabTitles
                    , fragments
            );
            //mHome_fragment_toptab_viewpager.setOffscreenPageLimit(1);
            mHome_fragment_toptab_viewpager.setAdapter(adapter);
            mTabLayout.setupWithViewPager(mHome_fragment_toptab_viewpager);
            mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

            //从文章页面接过传递的参数，决定跳转到那个Fragment页面
            Intent intent = this.getActivity().getIntent();
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                String strCategory = bundle.get("category").toString();
                mHome_fragment_toptab_viewpager.setCurrentItem(Integer.valueOf(strCategory));
            }
        }catch (Exception ex){
            LogUtil.e(ex.getMessage());
        }
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
