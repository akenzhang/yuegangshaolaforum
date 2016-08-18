package com.mantianhong.me.activity;

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
import java.util.List;

/**
 * Created by AKENZHANG on 2016/8/2.
 */
public class MineHomeTakenoteActivity extends BaseActivity {

    private TextView mine_fragment_takenote_back;
    private ListView mine_fragment_takenote;
    private TextView mine_fragment_norecord;

    private List<Takenote> mList;
    private static int mPageNo = 1;
    private static int mPrePageNo = 1;
    private int mLastItem = 1;
    private MineTakenoteAdapter mAdapter;
    private String mUserID;
    private int mPagesize=15;
    private boolean hasmore=false;

    @Override
    protected int getLayout() {
        return R.layout.mine_fragment_takenote;
    }

    @Override
    protected void initView() {

        mine_fragment_takenote_back = (TextView) this.findViewById(R.id.id_mine_fragment_takenote_back);
        mine_fragment_takenote = (ListView) this.findViewById(R.id.id_mine_fragment_takenote);
        mine_fragment_norecord = (TextView) this.findViewById(R.id.id_mine_fragment_norecord03);
        mine_fragment_norecord.setVisibility(View.GONE);

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
                if(scrollState== AbsListView.OnScrollListener.SCROLL_STATE_IDLE && mLastItem == mAdapter.getCount() && hasmore){
                    try {
                        mPageNo=mAdapter.getCount()/mPagesize+1;
                        if(mPageNo==mPrePageNo){
                            hasmore=false;
                        }
                        String url = "http://www.1316818.com/jsonserver.aspx?takenote_userid="+ mUserID +"&takenote_pageno="+ mPageNo +"&takenote_pagesize="+String.valueOf(mPagesize);
                        new DBUtils() {
                            @Override
                            protected void successRequest(String result) {
                                if(!result.contains("找不到记录")) {
                                    Gson gson = new Gson();
                                    TakenoteRoot root = gson.fromJson(result, TakenoteRoot.class);
                                    List<Takenote> mListNew = root.getTakenote();
                                    if(mListNew.size()<mPagesize){
                                        hasmore=false;
                                    }else{
                                        hasmore=true;
                                    }
                                    mPrePageNo = mPageNo;
                                    mList.addAll(mListNew);
                                    mAdapter.notifyDataSetChanged();
                                }else{
                                    mine_fragment_norecord.setVisibility(View.VISIBLE);
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
                mLastItem = firstVisibleItem + visibleItemCount;
            }
        });
    }

    @Override
    protected void bindData() {

        //获得数据
        try {
            mPageNo=1;
            String url = "http://www.1316818.com/jsonserver.aspx?takenote_userid="+ mUserID +"&takenote_pageno="+ mPageNo +"&takenote_pagesize="+String.valueOf(mPagesize);;
            new DBUtils() {
                @Override
                protected void successRequest(String result) {
                    if(!result.contains("找不到记录")) {
                        Gson gson = new Gson();
                        TakenoteRoot root = gson.fromJson(result, TakenoteRoot.class);
                        mList = root.getTakenote();
                        if(mList.size()<mPagesize){
                            hasmore=false;
                        }else{
                            hasmore=true;
                        }
                        mPrePageNo = mPageNo;

                        //绑定数据
                        mAdapter = new MineTakenoteAdapter(mList, R.layout.mine_fragment_takenotedetails, MineHomeTakenoteActivity.this);
                        mine_fragment_takenote.setAdapter(mAdapter);
                    }else{
                        mine_fragment_norecord.setVisibility(View.VISIBLE);
                    }
                }
            }.getAsync(url);

        }catch (Exception e){
            LogUtil.e(e.getMessage());
        }
    }
}
