package com.yuegangshaola.home.activity;

import android.support.v4.app.Fragment;
import android.widget.RadioGroup;

import com.yuegangshaola.R;
import com.yuegangshaola.common.BaseActivity;
import com.yuegangshaola.common.MyConstants;
import com.yuegangshaola.common.CustomViewPager;
import com.yuegangshaola.common.LogUtil;
import com.yuegangshaola.common.SharedPreferencesUtils;
import com.yuegangshaola.contact.fragment.ContactHomeFragment;
import com.yuegangshaola.home.adapter.HomeActivityPagerAdapter;
import com.yuegangshaola.home.fragment.HomeDefaultFragment;
import com.yuegangshaola.mine.fragment.MineHomeFragment;
import com.yuegangshaola.video.fragment.VideoHomeFragment;


public class HomeActivity extends BaseActivity {

    private RadioGroup home_tab;
    private CustomViewPager home_customviewpage;

    private Fragment fragments[];

    @Override
    protected int getLayout() {

        return R.layout.activity_home;
    }

    @Override
    protected void initView() {}

    @Override
    protected void initVariable() {

        home_tab = (RadioGroup) this.findViewById(R.id.id_home_tab);
        home_customviewpage = (CustomViewPager) this.findViewById(R.id.id_home_customviewpage);

        //初始化Fragment
        fragments = new Fragment[]{
                new HomeDefaultFragment()
                ,new VideoHomeFragment()
                ,new ContactHomeFragment()
                ,new MineHomeFragment()
        };

        //代码实现让RadioButton默认选中
        //RadioButton home_radiobutton_homepage = (RadioButton) this.findViewById(R.id.id_home_radiobutton_homepage);
        //home_radiobutton_homepage.setChecked(true);

        //打印用户名
        String strUsername = SharedPreferencesUtils.getData(this, MyConstants.CELLPHONE_USER_NAME)+"   "+SharedPreferencesUtils.getData(this, MyConstants.QQ_USER_NAME) +"   "+SharedPreferencesUtils.getData(this, MyConstants.TEMP_USER_NAME);
        LogUtil.e(strUsername);
    }

    @Override
    protected void initListener() {

        //注册主页低下按钮的点击事件
        home_tab.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId)
                {
                    case R.id.id_home_radiobutton_homepage:
                        home_customviewpage.setCurrentItem(0);
                        break;

                    case R.id.id_home_radiobutton_video:
                        home_customviewpage.setCurrentItem(1);
                        break;

                    case R.id.id_home_radiobutton_contact:
                        home_customviewpage.setCurrentItem(2);
                        break;

                    case R.id.id_home_radiobutton_me:
                        home_customviewpage.setCurrentItem(3);
                        break;

                    default:
                        break;
                }
            }
        });
    }

    @Override
    protected void bindData() {

        //实例化 HomeActivityPagerAdapter
        HomeActivityPagerAdapter adapter = new HomeActivityPagerAdapter(getSupportFragmentManager(),fragments);

        //将HomeActivityPagerAdapter实力赋值给相应的ViewPager
        home_customviewpage.setAdapter(adapter);

    }

}
