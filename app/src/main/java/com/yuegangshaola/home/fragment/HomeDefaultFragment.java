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
        return R.layout.home_fragment_home_layout;
    }

    @Override
    protected void initView() {}

    @Override
    protected void initVariable() {

        mHome_fragment_toptab_viewpager = (CustomViewPager) this.getActivity().findViewById(R.id.id_home_fragment_top_tab);
        mTabLayout = (TabLayout) this.getActivity().findViewById(R.id.id_home_fragment_tablayout);

        tabTitles = new String[]{
                "烧鸭烧鹅" //HomeDefaultFragmentShaoya
                ,"隆江猪脚" //HomeDefaultFragmentZhujiao
                ,"烧猪烧肉" //HomeDefaultFragmentShaozhu
                ,"叉烧烧骨" //HomeDefaultFragmentChashao
                ,"各种鸡类" //HomeDefaultFragmentJilei
                ,"其它品类" //HomeDefaultFragmentQita
                ,"精彩视频" //HomeDefaultFragmentShipin
                ,"学员作品" //HomeDefaultFragmentZuopin
                ,"现场点滴" //HomeDefaultFragmentXianchang
                ,"有问必答" //HomeDefaultFragmentYinan
                ,"潮州肉卷" //HomeDefaultFragmentRoujuan
                ,"经验分享" //HomeDefaultFragmentJingyan
                ,"街头巷尾"  //HomeDefaultFragmentJietou
        };

        fragments = new Fragment[]{
                new HomeDefaultFragmentShaoya()
                ,new HomeDefaultFragmentZhujiao()
                ,new HomeDefaultFragmentShaozhu()
                ,new HomeDefaultFragmentChashao()
                ,new HomeDefaultFragmentJilei()
                ,new HomeDefaultFragmentQita()
                ,new HomeDefaultFragmentShipin()
                ,new HomeDefaultFragmentZuopin()
                ,new HomeDefaultFragmentXianchang()
                ,new HomeDefaultFragmentYinan()
                ,new HomeDefaultFragmentRoujuan()
                ,new HomeDefaultFragmentJingyan()
                ,new HomeDefaultFragmentJietou()
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
