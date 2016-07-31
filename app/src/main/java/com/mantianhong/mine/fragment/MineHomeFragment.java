package com.mantianhong.mine.fragment;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mantianhong.R;
import com.mantianhong.mine.adapter.MineDataAdapter;
import com.mantianhong.utiltools.LazyLoadBaseFragment;
import com.mantianhong.utiltools.LogUtil;
import com.mantianhong.utiltools.SharedPreferencesUtils;

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

        blLoginState = SharedPreferencesUtils.getLoginState(this.getContext());
        if(blLoginState==true){
            mine_fragment_loginpic.setVisibility(View.INVISIBLE);
            mine_fragment_loginpic.setVisibility(View.GONE);
            mine_fragment_loginstate.setVisibility(View.VISIBLE);

            //显示登陆的用户名和图像类别
            mine_fragment_username.setText(SharedPreferencesUtils.getUserName(this.getContext()));
            String strLoginMode = SharedPreferencesUtils.getLoginMode(this.getContext());
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
        }else{
            mine_fragment_loginpic.setVisibility(View.VISIBLE);
            mine_fragment_loginstate.setVisibility(View.INVISIBLE);
            mine_fragment_loginstate.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initListener() {

        qqicon_login_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MineHomeFragment.this.getContext(),"正在开发...",Toast.LENGTH_SHORT).show();
            }
        });

        weixinicon_login_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MineHomeFragment.this.getContext(),"正在开发...",Toast.LENGTH_SHORT).show();
            }
        });

        cellphoneicon_login_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MineHomeFragment.this.getContext(),"正在开发...",Toast.LENGTH_SHORT).show();
            }
        });

        mine_fragment_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferencesUtils.logout(MineHomeFragment.this.getContext());

                mine_fragment_loginpic.setVisibility(View.VISIBLE);
                mine_fragment_loginstate.setVisibility(View.INVISIBLE);
                mine_fragment_loginstate.setVisibility(View.GONE);

                blLoginState = SharedPreferencesUtils.getLoginState(MineHomeFragment.this.getContext());
                adapter = new MineDataAdapter(blLoginState,datalist,R.layout.mine_fragment_home_listviewdetail,root.getContext());
                mine_fragment_listview.setAdapter(adapter);
            }
        });

    }

    @Override
    protected void lazyLoad() {
        LogUtil.e("ContactHomeFragment==>lazyLoad()");

        adapter = new MineDataAdapter(blLoginState,datalist,R.layout.mine_fragment_home_listviewdetail,root.getContext());
        mine_fragment_listview.setAdapter(adapter);

    }

}
