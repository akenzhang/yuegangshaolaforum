package com.yuegangshaola.home.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import com.yuegangshaola.R;
import com.yuegangshaola.common.BaseFragment;
import com.yuegangshaola.common.CustomViewPager;
import com.yuegangshaola.common.LogUtil;
import com.yuegangshaola.home.adapter.HomeDefaultFragmentStatePagerAdapter;

import java.lang.reflect.Field;

/**
 * Created by new pc on 2016/7/3.
 */
public class HomeDefaultFragment extends BaseFragment {

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

        HomeDefaultFragmentStatePagerAdapter adapter = new HomeDefaultFragmentStatePagerAdapter(
                this.getChildFragmentManager()
                ,tabTitles
                ,fragments
        );
        mHome_fragment_toptab_viewpager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mHome_fragment_toptab_viewpager);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        //mHome_fragment_toptab_viewpager.setCurrentItem(2);
        String strCategory = this.getActivity().getIntent().getExtras().getString("cagegory");
        switch (strCategory){
            case "最新资讯":
                mHome_fragment_toptab_viewpager.setCurrentItem(0);
                break;

            case "烧腊技术分享":
                mHome_fragment_toptab_viewpager.setCurrentItem(1);
                break;

            case "培训现场点滴":
                mHome_fragment_toptab_viewpager.setCurrentItem(2);
                break;

            case "隆江猪脚":
                mHome_fragment_toptab_viewpager.setCurrentItem(3);
                break;
            default:
                mHome_fragment_toptab_viewpager.setCurrentItem(0);
                break;
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
