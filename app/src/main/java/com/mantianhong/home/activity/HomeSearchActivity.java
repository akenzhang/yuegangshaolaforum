package com.mantianhong.home.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mantianhong.R;
import com.mantianhong.bean.Mysearch;
import com.mantianhong.bean.MysearchRoot;
import com.mantianhong.home.adapter.HomeSearchActivityAdapter;
import com.mantianhong.utiltools.BaseActivity;
import com.mantianhong.utiltools.DBUtils;
import com.mantianhong.utiltools.TextUtil;

import java.util.List;

/**
 * Created by AKENZHANG on 2016/8/21.
 */
public class HomeSearchActivity extends BaseActivity {

    private TextView home_search_back;
    private EditText home_search_edittext;
    private Button home_search_button;
    private ListView home_search_listview;
    private TextView home_search_status;

    private List<Mysearch> mList;
    private int mPagesize = 12;
    private int mPageNo = 1;
    private static int mPrePageNo = 1;
    private boolean mHasmore=false;
    private int mLastItem = 1;
    private HomeSearchActivityAdapter mAdapter;

    @Override
    protected int getLayout() {
        return R.layout.home_search_activity;
    }

    @Override
    protected void initView() {
        home_search_back = (TextView) this.findViewById(R.id.id_home_search_back);
        home_search_edittext = (EditText) this.findViewById(R.id.id_home_search_edittext);
        home_search_button = (Button) this.findViewById(R.id.id_home_search_button);
        home_search_listview = (ListView) this.findViewById(R.id.id_home_search_listview);
        home_search_status = (TextView) this.findViewById(R.id.id_home_search_status);
    }

    @Override
    protected void initVariable() {
        home_search_status.setVisibility(View.VISIBLE);
        home_search_listview.setVisibility(View.GONE);
    }

    @Override
    protected void initListener() {

        home_search_listview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if(scrollState== AbsListView.OnScrollListener.SCROLL_STATE_IDLE && mLastItem == mAdapter.getCount()  && mHasmore){
                    mPageNo=mAdapter.getCount()/mPagesize+1;  //当前页码

                    if(mPageNo==mPrePageNo){  //除了数据，也从页码的提增上判断是否有更多数据
                        mHasmore =false;
                    }

                    //获得mList数据
                    String strKeyword = home_search_edittext.getText().toString();
                    String strSQL ="http://www.1316818.com/jsonserver_search.ashx?keyword="+ strKeyword +"&pageno="+ String.valueOf(mPageNo) +"&pagesize="+String.valueOf(mPagesize);
                    new DBUtils() {
                        @Override
                        protected void successRequest(String result) {
                            if(!result.contains("找不到记录")){
                                Gson gson = new Gson();
                                MysearchRoot root = gson.fromJson(result,MysearchRoot.class);
                                List<Mysearch> mListNew = root.getMysearch();

                                mList.addAll(mListNew); //将新的数据mListNew加入到mList内
                                mAdapter.notifyDataSetChanged(); //通知adapter更新数据;

                                //判断是否有未加载的数据
                                if(mListNew.size()<mPagesize){
                                    mHasmore =false;
                                }else{
                                    mHasmore =true;
                                }
                                mPrePageNo = mPageNo; //对前也页码进行标识

                            }
                        }
                    }.getAsync(strSQL);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                mLastItem = firstVisibleItem + visibleItemCount;
            }
        });

        home_search_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeSearchActivity.this.finish();
            }
        });

        home_search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String strKeyword = home_search_edittext.getText().toString();
                if(TextUtils.isEmpty(strKeyword)){
                    Toast.makeText(HomeSearchActivity.this,strKeyword,Toast.LENGTH_SHORT).show();
                    return;
                }

                //获得mList数据
                String strSQL ="http://www.1316818.com/jsonserver_search.ashx?keyword="+ strKeyword +"&pageno=1&pagesize="+String.valueOf(mPagesize);
                new DBUtils() {
                    @Override
                    protected void successRequest(String result) {

                        if(!result.contains("找不到记录")){
                            home_search_status.setVisibility(View.GONE);
                            home_search_listview.setVisibility(View.VISIBLE);

                            Gson gson = new Gson();
                            MysearchRoot root = gson.fromJson(result,MysearchRoot.class);
                            mList = root.getMysearch();

                            mAdapter = new HomeSearchActivityAdapter(mList,R.layout.home_search_detail_activity,HomeSearchActivity.this);
                            home_search_listview.setAdapter(mAdapter);

                            mPageNo = 1;

                            //判断是否有未加载的数据
                            if(mList.size()<mPagesize){
                                mHasmore =false;
                            }else{
                                mHasmore =true;
                            }
                        }else{
                            home_search_status.setVisibility(View.VISIBLE);
                            home_search_listview.setVisibility(View.GONE);
                            home_search_status.setText("找不到相关记录，请从新换一个新的关键词尝试下");
                        }

                    }
                }.getAsync(strSQL);
            }
        });

    }

    @Override
    protected void bindData() {}
}
