package com.mantianhong.me.activity;

import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mantianhong.R;
import com.mantianhong.bean.Mycomment;
import com.mantianhong.bean.MycommentRoot;
import com.mantianhong.me.adapter.MineMycommentAdapter;
import com.mantianhong.utiltools.BaseActivity;
import com.mantianhong.utiltools.DBUtils;
import com.mantianhong.utiltools.SharedPreferencesUtils;

import java.util.List;

/**
 * Created by AKENZHANG on 2016/8/3.
 */
public class MineHomeMycommentActivity extends BaseActivity {

    private TextView mine_fragment_mycomment_back;
    private ListView mine_fragment_mycomment;

    private List<Mycomment> mList;
    private int mLastItem = 1;
    private static int mPageNo = 1;
    private static int mPagesize = 15;
    private MineMycommentAdapter mAdapter;
    private String mUserID = "";

    @Override
    protected int getLayout() {
        return R.layout.mine_fragment_mycomment;
    }

    @Override
    protected void initView() {

        mine_fragment_mycomment_back = (TextView) this.findViewById(R.id.id_mine_fragment_mycomment_back);
        mine_fragment_mycomment = (ListView) this.findViewById(R.id.id_mine_fragment_mycomment);

    }

    @Override
    protected void initVariable() {

        mUserID = SharedPreferencesUtils.getUserIdConsiderlessVisitor(this);
    }

    @Override
    protected void initListener() {

        //返回上一级菜单
        mine_fragment_mycomment_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MineHomeMycommentActivity.this.finish();
            }
        });

        //为mine_fragment_mycomment注册滚动时间
        mine_fragment_mycomment.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if(scrollState== AbsListView.OnScrollListener.SCROLL_STATE_IDLE && mLastItem == mAdapter.getCount() ){
                    mPageNo=mAdapter.getCount()/mPagesize+1; //当前页码

                    String url = "http://www.1316818.com/jsonserver.aspx?mycomment_userid="+ mUserID +"&mycomment_pageno="+ String.valueOf(mPageNo) +"&mycomment_pagesize="+String.valueOf(mPagesize);
                    new DBUtils() {
                        @Override
                        protected void successRequest(String result) {
                            if(!result.contains("找不到记录")) {
                                Gson gson = new Gson();
                                MycommentRoot root = gson.fromJson(result,MycommentRoot.class);

                                List<Mycomment> mListNew = root.getMycomment(); //将新的数据存入mListNew内
                                mList.addAll(mListNew); //将新的数据mListNew加入到mList内
                                mAdapter.notifyDataSetChanged(); //通知adapter更新数据
                            }
                        }
                    }.getAsync(url);
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

        mPageNo = 1;
        String url = "http://www.1316818.com/jsonserver.aspx?mycomment_userid="+ mUserID +"&mycomment_pageno="+ String.valueOf(mPageNo) +"&mycomment_pagesize="+String.valueOf(mPagesize);
        new DBUtils() {
            @Override
            protected void successRequest(String result) {

                if(!result.contains("找不到记录")) {
                    Gson gson = new Gson();
                    MycommentRoot root = gson.fromJson(result, MycommentRoot.class);
                    mList = root.getMycomment();

                    //将数据绑定到mine_fragment_mycomment
                    mAdapter = new MineMycommentAdapter(mList,R.layout.mine_fragment_mycommentdetails,MineHomeMycommentActivity.this);
                    mine_fragment_mycomment.setAdapter(mAdapter);
                }

            }
        }.getAsync(url);
    }
}
