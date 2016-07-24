package com.yuegangshaola.login.activity;

import android.content.Intent;
import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yuegangshaola.R;
import com.yuegangshaola.common.BaseActivity;
import com.yuegangshaola.home.activity.HomeActivity;

/**
 * Created by AKENZHANG on 2016/7/24.
 */
public class LoginMainActivity extends BaseActivity {

    private TextView login_main_entrance;
    private TextView login_main_sendyanzhengma;
    private ImageView login_main_weixinentrance;
    private ImageView login_main_qqentrance;
    private ImageView login_main_close;


    @Override
    protected int getLayout() {
        return R.layout.login_main_activity;
    }

    @Override
    protected void initView() {

        login_main_entrance = (TextView) this.findViewById(R.id.id_login_main_entrance);
        login_main_sendyanzhengma = (TextView) this.findViewById(R.id.id_login_main_sendyanzhengma);
        login_main_weixinentrance = (ImageView) this.findViewById(R.id.id_login_main_weixinentrance);
        login_main_qqentrance = (ImageView) this.findViewById(R.id.id_login_main_qqentrance);
        login_main_close = (ImageView) this.findViewById(R.id.id_login_main_close);

    }

    @Override
    protected void initVariable() {

    }

    @Override
    protected void initListener() {

        //直接登录，不用注册
        login_main_entrance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginMainActivity.this, HomeActivity.class);
                LoginMainActivity.this.startActivity(intent);
            }
        });

        //会用微信登录
        login_main_weixinentrance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginMainActivity.this,"微信登录方式暂不可用,请使用其它方式登录",Toast.LENGTH_SHORT).show();
            }
        });

        //使用QQ登录
        login_main_qqentrance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginMainActivity.this,"正在开发中...",Toast.LENGTH_SHORT).show();
            }
        });

        //发送验证码
        login_main_sendyanzhengma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginMainActivity.this,"正在开发中...",Toast.LENGTH_SHORT).show();
            }
        });

        //关闭登录界面
        login_main_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginMainActivity.this, HomeActivity.class);
                LoginMainActivity.this.startActivity(intent);
                //LoginMainActivity.this.finish();
            }
        });

    }

    @Override
    protected void bindData() {

    }
}
