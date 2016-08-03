package com.mantianhong.me.activity;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AbsListView;
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
    private static int mPageNo = 1;
    private int mLastItem = 1;
    private MineTakenoteAdapter mAdapter;
    private String mUserID;

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
    protected void initVariable() {
        mUserID  =  SharedPreferencesUtils.getUserIdConsiderlessVisitor(this);
    }

    @Override
    protected void initListener() {

        //返回按钮事件
        mine_fragment_takenote_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MineHomeTakenoteActivity.this.finish();
            }
        });

        //滑动事件
        mine_fragment_takenote.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if(scrollState== AbsListView.OnScrollListener.SCROLL_STATE_IDLE && mLastItem == mAdapter.getCount() ){
                    try {
                        mPageNo=1;
                        String url = "http://www.1316818.com/jsonserver.aspx?takenote_userid="+ mUserID +"&takenote_pageno="+ mPageNo +"&takenote_pagesize=15";
                        new DBUtils() {
                            @Override
                            protected void successRequest(String result) {
                                if(!result.contains("找不到记录")) {
                                    Gson gson = new Gson();
                                    TakenoteRoot root = gson.fromJson(result, TakenoteRoot.class);
                                    mList = root.getTakenote();

                                    mAdapter.notifyDataSetChanged();
                                }
                            }
                        }.getAsync(url);

                    }catch (Exception e){
                        LogUtil.e(e.getMessage());
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                LogUtil.e(firstVisibleItem+"  "+visibleItemCount+"  "+totalItemCount);
                mLastItem = firstVisibleItem + visibleItemCount-1;
            }
        });
    }

    @Override
    protected void bindData() {

        //获得数据
        try {
            mPageNo=1;
            String url = "http://www.1316818.com/jsonserver.aspx?takenote_userid="+ mUserID +"&takenote_pageno="+ mPageNo +"&takenote_pagesize=15";
            new DBUtils() {
                @Override
                protected void successRequest(String result) {
                    if(!result.contains("找不到记录")) {
                        Gson gson = new Gson();
                        TakenoteRoot root = gson.fromJson(result, TakenoteRoot.class);
                        mList = root.getTakenote();

                        //绑定数据
                        mAdapter = new MineTakenoteAdapter(mList, R.layout.mine_fragment_takenotedetails, MineHomeTakenoteActivity.this);
                        mine_fragment_takenote.setAdapter(mAdapter);
                    }
                }
            }.getAsync(url);

        }catch (Exception e){
            LogUtil.e(e.getMessage());
        }
    }
}
