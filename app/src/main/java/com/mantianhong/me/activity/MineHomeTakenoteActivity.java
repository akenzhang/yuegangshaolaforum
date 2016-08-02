package com.mantianhong.me.activity;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mantianhong.R;
import com.mantianhong.bean.Takenote;
import com.mantianhong.bean.TakenoteRoot;
import com.mantianhong.me.adapter.MineTakenoteAdapter;
import com.mantianhong.utiltools.BaseActivity;
import com.mantianhong.utiltools.DBUtils;
import com.mantianhong.utiltools.LogUtil;
import com.mantianhong.utiltools.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/**
 * Created by AKENZHANG on 2016/8/2.
 */
public class MineHomeTakenoteActivity extends BaseActivity {

    private TextView mine_fragment_takenote_back;
    private ListView mine_fragment_takenote;
    private List<Takenote> mList =  new ArrayList<>();

    @Override
    protected int getLayout() {
        return R.layout.mine_fragment_takenote;
    }

    @Override
    protected void initView() {

        mine_fragment_takenote_back = (TextView) this.findViewById(R.id.id_mine_fragment_takenote_back);
        mine_fragment_takenote = (ListView) this.findViewById(R.id.id_mine_fragment_takenote);

    }

    @Override
    protected void initVariable() {}

    @Override
    protected void initListener() {

        //返回按钮事件
        mine_fragment_takenote_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MineHomeTakenoteActivity.this.finish();
            }
        });
    }

    @Override
    protected void bindData() {

        //获得数据
        Map<String,String> hasMapParams = new HashMap<>();
        hasMapParams.put("takenote_userid",SharedPreferencesUtils.getUserIdConsiderlessVisitor(this));

        try {
            //异步获得数据并处理
            new DBUtils() {
                @Override
                public void successRequest(String result) {
                   if(!result.contains("找不到记录")) {
                       Gson gson = new Gson();
                       TakenoteRoot root = gson.fromJson(result, TakenoteRoot.class);
                       mList = root.getTakenote();

                       //绑定数据
                       MineTakenoteAdapter adapter = new MineTakenoteAdapter(mList, R.layout.mine_fragment_takenotedetails, MineHomeTakenoteActivity.this);
                       mine_fragment_takenote.setAdapter(adapter);
                   }
                }
            }.getAsync(hasMapParams);
        }catch (Exception e){
            LogUtil.e(e.getMessage());
        }
    }
}
