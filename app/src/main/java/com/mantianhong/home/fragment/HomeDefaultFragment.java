package com.mantianhong.home.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.mantianhong.R;
import com.mantianhong.home.activity.HomeSearchActivity;
import com.mantianhong.utiltools.CustomViewPager;
import com.mantianhong.utiltools.LazyLoadBaseFragment;
import com.mantianhong.utiltools.LogUtil;
import com.mantianhong.home.adapter.HomeDefaultFragmentCategoriesAdapter;

/**
 * Created by new pc on 2016/7/3.
 */
public class HomeDefaultFragment extends LazyLoadBaseFragment {

    private CustomViewPager mHome_fragment_toptab_viewpager;
    private TabLayout mTabLayout;
    private ImageButton home_fragment_search;

    //初始化数据
    private String tabTitles[];
    private Fragment fragments[];

    @Override
    protected int getLayout() {
        return R.layout.home_fragment_home;
    }

    @Override
    protected void initView() {
        mHome_fragment_toptab_viewpager = (CustomViewPager) root.findViewById(R.id.id_home_fragment_top_tab);
        mTabLayout = (TabLayout) root.findViewById(R.id.id_home_fragment_tablayout);
        home_fragment_search = (ImageButton) root.findViewById(R.id.id_home_fragment_search);

    }

    @Override
    protected void initVariable() {

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
                //LogUtil.e(String.valueOf(tabTitles[tab.getPosition()]));
                mHome_fragment_toptab_viewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        //搜索功能
        home_fragment_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),HomeSearchActivity.class);
                getActivity().startActivity(intent);
            }
        });
    }

    @Override
    protected void lazyLoad() {
        LogUtil.e("HomeDefaultFragment==>lazyLoad()==>创建最新资讯、烧腊技术分享、培训现场点滴、隆江猪脚顶部菜单");

        try {
            HomeDefaultFragmentCategoriesAdapter adapter = new HomeDefaultFragmentCategoriesAdapter(
                    this.getChildFragmentManager()
                    , tabTitles
                    , fragments
            );

            mHome_fragment_toptab_viewpager.setOffscreenPageLimit(0);
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

}
