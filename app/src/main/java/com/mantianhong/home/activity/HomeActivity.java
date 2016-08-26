package com.mantianhong.home.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.mantianhong.R;
import com.mantianhong.post.activity.PostNewArticleActivity;
import com.mantianhong.utiltools.BaseActivity;
import com.mantianhong.utiltools.CustomViewPager;
import com.mantianhong.utiltools.LogUtil;
import com.mantianhong.utiltools.MyConstants;
import com.mantianhong.utiltools.SharedPreferencesUtils;
import com.mantianhong.contact.fragment.ContactHomeFragment;
import com.mantianhong.home.adapter.HomeActivityPagerAdapter;
import com.mantianhong.home.fragment.HomeDefaultFragment;
import com.mantianhong.login.LoginMainActivity;
import com.mantianhong.me.fragment.MineHomeFragment;
import com.mantianhong.utiltools.SingletonImageCollection;
import com.mantianhong.version.DoUpdate;
import com.mantianhong.version.VersionHelper;
import com.mantianhong.video.fragment.VideoHomeFragment;

public class HomeActivity extends BaseActivity {

    private RadioGroup home_tab;
    private CustomViewPager home_customviewpage;
    private Fragment fragments[];
    private Boolean isLogin=false;

    private RadioButton home_radiobutton_newpost;

    @Override
    protected int getLayout() {
        //这里判断是否需要用户登录，首先从本地的记录提取用户名
        isLogin();

        return R.layout.activity_home;
    }

    @Override
    protected void initView() {
        if(isLogin) return;

        home_radiobutton_newpost = (RadioButton) this.findViewById(R.id.id_home_radiobutton_newpost);
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
        //String strUsername = SharedPreferencesUtils.getData(this,MyConstants.WEIXIN_USER_NAME)+"   "+SharedPreferencesUtils.getData(this,MyConstants.CELLPHONE_USER_NAME)+"   "+SharedPreferencesUtils.getData(this, MyConstants.QQ_USER_NAME) +"   "+SharedPreferencesUtils.getData(this, MyConstants.VISITOR_USER_NAME);
        //LogUtil.e(strUsername);
    }

    @Override
    protected void initListener() {
        if(isLogin) return;

        home_radiobutton_newpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SharedPreferencesUtils.isLoginConsiderlessVisitor(HomeActivity.this)){
                    Intent intent = new Intent(HomeActivity.this, PostNewArticleActivity.class);
                    startActivity(intent);
                }
            }
        });

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

                    case R.id.id_home_radiobutton_newpost:
                        break;

                    default:
                        break;
                }
            }
        });
    }

    private Boolean isLogin(){
        isLogin=false;
        //如能找到曾经登录的痕迹，就默认登录，不再需要提示登录界面
        String strUserName = SharedPreferencesUtils.getUserNameIncludingVisitor(this);
        if(TextUtils.isEmpty(strUserName)){
            isLogin=false;
            Intent intent = new Intent(HomeActivity.this,LoginMainActivity.class);
            this.startActivity(intent);
            return false;
        }

        return true;
    }

    @Override
    protected void bindData() {

        //检查现在的版本号
        DoUpdate.update(this,handler);

        if(isLogin) return;

        LogUtil.e("HomeActivity==>bindData()==>创建主页、视频、联系和我菜单");

        //实例化 HomeActivityPagerAdapter
        HomeActivityPagerAdapter adapter = new HomeActivityPagerAdapter(getSupportFragmentManager(),fragments);

        home_customviewpage.setOffscreenPageLimit(0);
        //将HomeActivityPagerAdapter实力赋值给相应的ViewPager
        home_customviewpage.setAdapter(adapter);

    }


    //////////////////////////////////////////////////////////////////////////////
    //////////////////////// 更新版本的代码 //////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////

    public final Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MyConstants.UPDATA_CLIENT:
                    //对话框通知用户升级程序
                    DoUpdate.showUpdataDialog(HomeActivity.this,handler);
                    break;
                case MyConstants.GET_UNDATAINFO_ERROR:
                    //服务器超时
                    Toast.makeText(getApplicationContext(), "获取服务器更新信息失败", Toast.LENGTH_SHORT).show();
                    break;
                case MyConstants.DOWN_ERROR:
                    //下载apk失败
                    Toast.makeText(getApplicationContext(), "下载新版本失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    //////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////

}
