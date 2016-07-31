package com.mantianhong.mine.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.mantianhong.R;
import com.mantianhong.mine.adapter.MineDataAdapter;
import com.mantianhong.utiltools.LazyLoadBaseFragment;
import com.mantianhong.utiltools.LogUtil;
import com.mantianhong.utiltools.MyConstants;
import com.mantianhong.utiltools.SharedPreferencesUtils;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

/**
 * Created by new pc on 2016/7/3.
 */
public class MineHomeFragment extends LazyLoadBaseFragment {

    private ListView mine_fragment_listview;
    private MineDataAdapter adapter;
    private ArrayList<String> datalist;
    private LinearLayout mine_fragment_loginpic;
    private LinearLayout mine_fragment_loginstate;
    private TextView mine_fragment_username;
    private ImageView mine_fragment_loginmypic;
    private TextView mine_fragment_logout;
    private ImageView qqicon_login_profile;
    private ImageView weixinicon_login_profile;
    private ImageView cellphoneicon_login_profile;

    private boolean blLoginState=false;

    //QQ互联
    private Tencent mTencent;
    private UserInfo mUserInfo;

    @Override
    protected int getLayout() {
        return R.layout.mine_fragment_home;
    }

    @Override
    protected void initView() {
        mine_fragment_listview = (ListView) root.findViewById(R.id.id_mine_fragment_listview);
        mine_fragment_loginpic = (LinearLayout) root.findViewById(R.id.id_mine_fragment_loginpic);
        mine_fragment_loginstate = (LinearLayout) root.findViewById(R.id.id_mine_fragment_loginstate);
        mine_fragment_username = (TextView) root.findViewById(R.id.id_mine_fragment_username);
        mine_fragment_loginmypic = (ImageView) root.findViewById(R.id.id_mine_fragment_loginmypic);
        mine_fragment_logout = (TextView) root.findViewById(R.id.id_mine_fragment_logout);
        qqicon_login_profile = (ImageView) root.findViewById(R.id.id_qqicon_login_profile);
        weixinicon_login_profile = (ImageView) root.findViewById(R.id.id_weixinicon_login_profile);
        cellphoneicon_login_profile = (ImageView) root.findViewById(R.id.id_cellphoneicon_login_profile);
    }

    @Override
    protected void initVariable() {
        datalist = new ArrayList<>();
        datalist.add("我的关注");
        datalist.add("我的文章");
        datalist.add("我的评论");

        blLoginState = SharedPreferencesUtils.getLoginStateConsiderlessVisitor(this.getContext());
        if(blLoginState==true){
            initForLogined();
        }else{
            initForNotLogined();
        }

    }

    private void initForLogined(){
        mine_fragment_loginpic.setVisibility(View.INVISIBLE);
        mine_fragment_loginpic.setVisibility(View.GONE);
        mine_fragment_loginstate.setVisibility(View.VISIBLE);

        //显示登陆的用户名和图像类别
        mine_fragment_username.setText(SharedPreferencesUtils.getUserNameIncludingVisitor(this.getContext()));
        String strLoginMode = SharedPreferencesUtils.getLoginModeIncludingVisitor(this.getContext());
        switch (strLoginMode){
            case "CELLPHONE":
                mine_fragment_loginmypic.setImageResource(R.drawable.cellphoneicon_login_profile);
                break;
            case "QQ":
                mine_fragment_loginmypic.setImageResource(R.drawable.qqicon_login_profile);
                break;
            case "WEIXIN":
                mine_fragment_loginmypic.setImageResource(R.drawable.weixinicon_login_profile);
                break;
            default:
                break;
        };

        blLoginState = SharedPreferencesUtils.getLoginStateConsiderlessVisitor(MineHomeFragment.this.getContext());
        adapter = new MineDataAdapter(blLoginState,datalist,R.layout.mine_fragment_home_listviewdetail,root.getContext());
        mine_fragment_listview.setAdapter(adapter);

    }

    private void initForNotLogined(){
        mine_fragment_loginpic.setVisibility(View.VISIBLE);
        mine_fragment_loginstate.setVisibility(View.INVISIBLE);
        mine_fragment_loginstate.setVisibility(View.GONE);

        blLoginState = SharedPreferencesUtils.getLoginStateConsiderlessVisitor(MineHomeFragment.this.getContext());
        adapter = new MineDataAdapter(blLoginState,datalist,R.layout.mine_fragment_home_listviewdetail,root.getContext());
        mine_fragment_listview.setAdapter(adapter);
    }

    @Override
    protected void lazyLoad() {
        LogUtil.e("MineHomeFragment==>lazyLoad()");
    }

    @Override
    protected void initListener() {

        //注销
        mine_fragment_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferencesUtils.logoutConsiderlessVisitor(MineHomeFragment.this.getContext());

                mine_fragment_loginpic.setVisibility(View.VISIBLE);
                mine_fragment_loginstate.setVisibility(View.INVISIBLE);
                mine_fragment_loginstate.setVisibility(View.GONE);

                blLoginState = SharedPreferencesUtils.getLoginStateConsiderlessVisitor(MineHomeFragment.this.getContext());
                adapter = new MineDataAdapter(blLoginState,datalist,R.layout.mine_fragment_home_listviewdetail,root.getContext());
                mine_fragment_listview.setAdapter(adapter);
            }
        });

        //实现QQ登录功能
        qqicon_login_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //实例化QQ对象
                if(mTencent==null) {
                    mTencent = Tencent.createInstance(MyConstants.APP_ID, MineHomeFragment.this.getContext());
                }
                mTencent.login(MineHomeFragment.this, "get_user_info", new LoginUiListener());
            }
        });

        //实现微信登录功能
        weixinicon_login_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MineHomeFragment.this.getContext(),"暂时不能使用微信登录，正在开发进程中...",Toast.LENGTH_SHORT).show();
            }
        });

        //实现短信登录功能
        cellphoneicon_login_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MineHomeFragment.this.getContext(),"正在开发...",Toast.LENGTH_SHORT).show();

                //Intent intent = new Intent(MineHomeFragment.this.getContext(),LoginCellphoneActivity.class);
                //startActivity(intent);
                if(!SharedPreferencesUtils.isLoginConsiderlessVisitor(MineHomeFragment.this.getContext(),"CANNOTCOMMENT")) return;

            }
        });

    }

    /*
    QQ登录必须的代码
     */
    @Override
    public void onDestroy() {
        super.onDestroy();

        //调用QQ注销接口
        if(mTencent!=null) {
            mTencent.logout(MineHomeFragment.this.getContext());
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_LOGIN) {
            mTencent.onActivityResultData(requestCode, resultCode, data, new LoginUiListener());
        }

        super.onActivityResult(requestCode, resultCode, data);
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
                    mUserInfo = new UserInfo(MineHomeFragment.this.getContext(),mTencent.getQQToken());
                    mUserInfo.getUserInfo(new IUiListener() {
                        @Override
                        public void onComplete(Object o) {
                            JSONObject json = (JSONObject)o;

                            try {
                                String nickname = json.getString("nickname");
                                String image = json.getString("figureurl_qq_2");
                                String gender = json.getString("gender");

                                //将获取到的用户信息保存起来
                                SharedPreferencesUtils.saveData(MineHomeFragment.this.getContext(), MyConstants.QQ_USER_NAME,nickname);
                                SharedPreferencesUtils.saveData(MineHomeFragment.this.getContext(),MyConstants.QQ_USER_IMAGE,image);
                                SharedPreferencesUtils.saveData(MineHomeFragment.this.getContext(),MyConstants.QQ_USER_GENDER,gender);

                                //登录后跳转
                                //Intent intent = new Intent(MineHomeFragment.this.getContext(), MineHomeFragment.class);
                                //startActivity(intent);
                                initForLogined();

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
            Toast.makeText(MineHomeFragment.this.getContext(),e.errorMessage,Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel() {}
    }

}
