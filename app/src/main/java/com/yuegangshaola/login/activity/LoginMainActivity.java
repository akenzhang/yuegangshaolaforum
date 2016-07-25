package com.yuegangshaola.login.activity;

import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Request;
import com.yuegangshaola.R;
import com.yuegangshaola.common.BaseActivity;
import com.yuegangshaola.common.LogUtil;
import com.yuegangshaola.common.OkHttpUtils;
import com.yuegangshaola.home.activity.HomeActivity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by AKENZHANG on 2016/7/24.
 */
public class LoginMainActivity extends BaseActivity {

    private TextView login_main_entrance;
    private TextView login_main_sendyanzhengma;
    private ImageView login_main_weixinentrance;
    private ImageView login_main_qqentrance;
    private ImageView login_main_close;
    private EditText login_main_cellphonenum;
    private EditText login_main_yanzhengma_input;


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
        login_main_cellphonenum = (EditText) this.findViewById(R.id.id_login_main_cellphonenum);
        login_main_yanzhengma_input = (EditText) this.findViewById(R.id.id_login_main_yanzhengma_input);

    }

    @Override
    protected void initVariable() {

    }

    @Override
    protected void initListener() {

        //进入论坛
        login_main_entrance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //将用户信息保存如数据库中去
                //http://www.1316818.com/jsonserver.aspx?nusername=huiyidao&npassword=123456&nemail=automail@qq.com
                Map<String,String> paramsAppRegister = new HashMap<String,String>();
                paramsAppRegister.put("nusername",login_main_cellphonenum.getText().toString());
                paramsAppRegister.put("npassword",login_main_yanzhengma_input.getText().toString());
                paramsAppRegister.put("nemail","auto@qq.com");
                OkHttpUtils.postAsync("http://www.1316818.com/jsonserver.aspx", paramsAppRegister, new OkHttpUtils.DataCallBack() {
                    @Override
                    public void requestFailure(Request request, IOException e) {
                        LogUtil.e(e.getMessage());
                    }

                    @Override
                    public void requestSuccess(String result) {
                        LogUtil.e(result);
                        if(result.contains("注册成功") || result.contains("注册失败，该用户名已经存在")){
                            Intent intent = new Intent(LoginMainActivity.this, HomeActivity.class);
                            LoginMainActivity.this.startActivity(intent);
                        }
                    }
                });
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
                //Toast.makeText(LoginMainActivity.this,"正在开发中...",Toast.LENGTH_SHORT).show();

                final String strCode = generateCode();

                //发送的内容需要编码成gb2312
                String strPhone = login_main_cellphonenum.getText().toString();
                if(strPhone.equals("") || TextUtils.isEmpty(strPhone)){
                    Toast.makeText(LoginMainActivity.this,"手机号码不能为空！",Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    String strSend = URLEncoder.encode("请输入验证码："+ strCode +"","gb2312");

                    //向如下链接发送短信请求
                    String strRequestUrl = "http://service.winic.org/sys_port/gateway/?id=13509611303&pwd=2WSX2wsx&to="+ strPhone +"&content="+ strSend +"&time=\"";

                    //通过OkHttpUtils发送请求
                    OkHttpUtils.getAsync(strRequestUrl, new OkHttpUtils.DataCallBack() {

                        @Override
                        public void requestFailure(Request request, IOException e) {}

                        @Override
                        public void requestSuccess(String result) {
                            //LogUtil.e("已发送短信--->"+result);
                            //判断是否正确发送
                            if(result.startsWith("000",0)){
                                //将验证码自动填写到输入框中
                                login_main_yanzhengma_input.setText(strCode);
                                login_main_entrance.setEnabled(true);
                                login_main_entrance.setBackgroundResource(R.drawable.login_main_entrance_bg2);
                                login_main_entrance.setText("进入论坛");
                                //login_main_yanzhengma_input.setEnabled(false);
                                login_main_cellphonenum.setEnabled(false);
                            }else{
                                Toast.makeText(LoginMainActivity.this,"输入的手机号码不正确，如若其它问题，请联系：13509611303",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
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

    private String generateCode(){
        String[] beforeShuffle = new String[] {"0","1", "2", "3", "4", "5", "6", "7", "8", "9"};
        List list = Arrays.asList(beforeShuffle);
        Collections.shuffle(list); //洗牌，混乱
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
        }
        String afterShuffle = sb.toString();
        String result = afterShuffle.substring(5, 9);
        return result;
    }
}
