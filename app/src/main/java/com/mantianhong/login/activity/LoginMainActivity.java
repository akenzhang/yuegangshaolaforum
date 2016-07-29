package com.mantianhong.login.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.okhttp.Request;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.mantianhong.R;
import com.mantianhong.utiltools.BaseActivity;
import com.mantianhong.utiltools.MyConstants;
import com.mantianhong.utiltools.LogUtil;
import com.mantianhong.utiltools.OkHttpUtils;
import com.mantianhong.utiltools.SharedPreferencesUtils;
import com.mantianhong.home.activity.HomeActivity;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

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

    private static String YANZHENGMA="";
    private static int SISTY_SECOND=60;

    private Handler mHandler;
    private static final int UPDATE_TEXT = 0;
    private Bundle bundle=new Bundle();

    //定时器
    private Timer timer = new Timer();

    //QQ互联
    private Tencent mTencent;
    private UserInfo mUserInfo;

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

        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case UPDATE_TEXT:
                        login_main_sendyanzhengma.setText(msg.getData().getString("data"));
                        break;
                }
            }
        };

    }

    @Override
    protected void initListener() {

        //会用微信登录
        login_main_weixinentrance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginMainActivity.this,"微信登录方式暂不可用,请使用其它方式登录",Toast.LENGTH_SHORT).show();

                //将来实现微信登录的时候，需要将微信的用户名存入本地，以便日后获取
                SharedPreferencesUtils.saveData(LoginMainActivity.this,MyConstants.WEIXIN_USER_NAME,"");
            }
        });

        //使用QQ登录
        login_main_qqentrance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(LoginMainActivity.this,"正在开发中...",Toast.LENGTH_SHORT).show();
                //实例化QQ对象
                if(mTencent==null) {
                    mTencent = Tencent.createInstance(MyConstants.APP_ID, LoginMainActivity.this.getApplicationContext());
                }
                mTencent.login(LoginMainActivity.this, "get_user_info", new LoginUiListener());
            }
        });

        //发送验证码
        login_main_sendyanzhengma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(login_main_sendyanzhengma.getText().toString().contains("秒后再试")){
                    Toast.makeText(LoginMainActivity.this,"请稍后再试...",Toast.LENGTH_SHORT).show();
                    return;
                }

                final String strCode = generateCode();
                YANZHENGMA = strCode;

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

                            //判断是否正确发送
                            if(result.startsWith("000",0)){

                                SISTY_SECOND=60;

                                //允许用户点击“进入论坛”
                                login_main_entrance.setEnabled(true);
                                login_main_entrance.setBackgroundResource(R.drawable.login_main_entrance_bg2);
                                login_main_entrance.setText("进入论坛");
                                login_main_cellphonenum.setEnabled(false);
                                login_main_sendyanzhengma.setText(String.valueOf(SISTY_SECOND)+"秒后再试...");

                                if(timer==null){
                                    timer = new Timer();
                                    timer.schedule(new UpdateYanzhengmaStateTask(), 1 * 1000, 1000);
                                }else {
                                    timer.schedule(new UpdateYanzhengmaStateTask(), 1 * 1000, 1000);
                                }

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

        //进入论坛
        login_main_entrance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //检查输入的验证码是否正确
                String strUserCellphone = login_main_cellphonenum.getText().toString();
                String strYanzhengma = login_main_yanzhengma_input.getText().toString();
                if(!strYanzhengma.equals(YANZHENGMA)){
                    Toast.makeText(LoginMainActivity.this,"请输入正确的验证码...",Toast.LENGTH_SHORT).show();
                    return;
                }

                //将用户信息保存到SharedPreferences中去
                SharedPreferencesUtils.saveData(LoginMainActivity.this, MyConstants.CELLPHONE_USER_NAME,strUserCellphone);

                //将用户信息保存如数据库中去
                Map<String,String> paramsAppRegister = new HashMap<String,String>();
                paramsAppRegister.put("nusername",strUserCellphone);
                paramsAppRegister.put("npassword",strYanzhengma);
                paramsAppRegister.put("nemail","auto@qq.com");
                OkHttpUtils.postAsync("http://www.1316818.com/jsonserver.aspx", paramsAppRegister, new OkHttpUtils.DataCallBack() {
                    @Override
                    public void requestFailure(Request request, IOException e) {
                        LogUtil.e(e.getMessage());
                    }
                    @Override
                    public void requestSuccess(String result) {
                        //LogUtil.e(result);
                        if(result.contains("注册成功") || result.contains("注册失败，该用户名已经存在")){
                            Intent intent = new Intent(LoginMainActivity.this, HomeActivity.class);
                            LoginMainActivity.this.startActivity(intent);
                        }
                    }
                });
            }
        });

        //关闭登录界面
        login_main_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //保存临时的用户名
                String strTempUsername = SharedPreferencesUtils.getData(LoginMainActivity.this, MyConstants.TEMP_USER_NAME);
                if(TextUtils.isEmpty(strTempUsername)){
                    String strDate = String.valueOf(Calendar.getInstance().getTimeInMillis());
                    SharedPreferencesUtils.saveData(LoginMainActivity.this, MyConstants.TEMP_USER_NAME,"过客("+ strDate+")");
                }

                Intent intent = new Intent(LoginMainActivity.this, HomeActivity.class);
                LoginMainActivity.this.startActivity(intent);
                //LoginMainActivity.this.finish();
            }
        });

    }

    @Override
    protected void bindData() {}

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //调用QQ注销接口
        mTencent.logout(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_LOGIN) {
            mTencent.onActivityResultData(requestCode, resultCode, data, new LoginUiListener());
        }

        super.onActivityResult(requestCode, resultCode, data);
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

    class UpdateYanzhengmaStateTask extends TimerTask
    {
        @Override
        public void run() {
            SISTY_SECOND--;
            String strData = "";
            if(SISTY_SECOND>0){
                strData = String.valueOf(SISTY_SECOND)+"秒后再试...";
            }
            if(SISTY_SECOND<=0){
                strData="重发送验证码";
                timer.cancel();
                timer=null;
            }
            Message msg = new Message();
            bundle.putString("data",strData);
            msg.setData(bundle);
            mHandler.sendMessage(msg);
        }
    }

    //实现QQ登陆的回调接口
    class LoginUiListener implements IUiListener {
        @Override
        public void onComplete(Object o) {
            JSONObject jsonObject = (JSONObject)o;

            try {
                String openid = jsonObject.getString("openid");
                String expires_in = jsonObject.getString("expires_in");
                String token = jsonObject.getString("access_token");
                if (!TextUtils.isEmpty(openid)&&!TextUtils.isEmpty(expires_in)&&!TextUtils.isEmpty(token)){
                    mTencent.setOpenId(openid);
                    mTencent.setAccessToken(token,expires_in);

                    //拿到QQ用户的数据
                    mUserInfo = new UserInfo(LoginMainActivity.this,mTencent.getQQToken());
                    mUserInfo.getUserInfo(new IUiListener() {
                        @Override
                        public void onComplete(Object o) {
                            JSONObject json = (JSONObject)o;

                            try {
                                String nickname = json.getString("nickname");
                                String image = json.getString("figureurl_qq_2");
                                String gender = json.getString("gender");

                                //将获取到的用户信息保存起来
                                SharedPreferencesUtils.saveData(LoginMainActivity.this,MyConstants.QQ_USER_NAME,nickname);
                                SharedPreferencesUtils.saveData(LoginMainActivity.this,MyConstants.QQ_USER_IMAGE,image);
                                SharedPreferencesUtils.saveData(LoginMainActivity.this,MyConstants.QQ_USER_GENDER,gender);

                                //登录后跳转
                                Intent intent = new Intent(LoginMainActivity.this, HomeActivity.class);
                                startActivity(intent);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(UiError uiError) { }

                        @Override
                        public void onCancel() {}
                    });
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(UiError e) {
            Toast.makeText(LoginMainActivity.this,e.errorMessage,Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel() {}
    }
}
