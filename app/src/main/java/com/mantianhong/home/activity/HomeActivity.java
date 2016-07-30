package com.mantianhong.home.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.widget.RadioGroup;

import com.mantianhong.R;
import com.mantianhong.utiltools.BaseActivity;
import com.mantianhong.utiltools.MyConstants;
import com.mantianhong.utiltools.CustomViewPager;
import com.mantianhong.utiltools.LogUtil;
import com.mantianhong.utiltools.SharedPreferencesUtils;
import com.mantianhong.contact.fragment.ContactHomeFragment;
import com.mantianhong.home.adapter.HomeActivityPagerAdapter;
import com.mantianhong.home.fragment.HomeDefaultFragment;
import com.mantianhong.login.activity.LoginMainActivity;
import com.mantianhong.mine.fragment.MineHomeFragment;
import com.mantianhong.video.fragment.VideoHomeFragment;

public class HomeActivity extends BaseActivity {

    private RadioGroup home_tab;
    private CustomViewPager home_customviewpage;
    private Fragment fragments[];
    private Boolean isLogin=false;

    @Override
    protected int getLayout() {
        //这里判断是否需要用户登录，首先从本地的记录提取用户名
        isLogin();

        return R.layout.activity_home;
    }

    @Override
    protected void initView() {
        if(isLogin) return;
    }

    @Override
    protected void initVariable() {
        if(isLogin) return;

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
        //String strUsername = SharedPreferencesUtils.getData(this,MyConstants.WEIXIN_USER_NAME)+"   "+SharedPreferencesUtils.getData(this,MyConstants.CELLPHONE_USER_NAME)+"   "+SharedPreferencesUtils.getData(this, MyConstants.QQ_USER_NAME) +"   "+SharedPreferencesUtils.getData(this, MyConstants.TEMP_USER_NAME);
        //LogUtil.e(strUsername);
    }

    @Override
    protected void initListener() {
        if(isLogin) return;

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
        if(isLogin) return;

        LogUtil.e("HomeActivity==>bindData()==>创建主页、视频、联系和我菜单");

        //实例化 HomeActivityPagerAdapter
        HomeActivityPagerAdapter adapter = new HomeActivityPagerAdapter(getSupportFragmentManager(),fragments);

        home_customviewpage.setOffscreenPageLimit(0);
        //将HomeActivityPagerAdapter实力赋值给相应的ViewPager
        home_customviewpage.setAdapter(adapter);

    }

    private Boolean isLogin(){
        isLogin=false;
        //如能找到曾经登录的痕迹，就默认登录，不再需要提示登录界面
        String strUserName = SharedPreferencesUtils.getUserName(this);
        if(TextUtils.isEmpty(strUserName)){
            isLogin=false;
            Intent intent = new Intent(HomeActivity.this,LoginMainActivity.class);
            this.startActivity(intent);
            return false;
        }

        return true;
    }

}
