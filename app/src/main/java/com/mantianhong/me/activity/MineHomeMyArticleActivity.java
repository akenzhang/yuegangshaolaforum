package com.mantianhong.me.activity;

import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;
import com.google.gson.Gson;
import com.mantianhong.R;
import com.mantianhong.bean.MyArticleRoot;
import com.mantianhong.bean.Myarticle;
import com.mantianhong.me.adapter.MineMyArticleAdapter;
import com.mantianhong.utiltools.BaseActivity;
import com.mantianhong.utiltools.DBUtils;
import com.mantianhong.utiltools.SharedPreferencesUtils;
import java.util.List;

/**
 * Created by AKENZHANG on 2016/8/3.
 */
public class MineHomeMyArticleActivity extends BaseActivity {

    private TextView mine_fragment_myarticle_back;
    private ListView mine_fragment_myarticle;

    private List<Myarticle> mList;
    private MineMyArticleAdapter mAdapter;
    private String mUserId;

    private static int mPagesize  = 15;
    private static int mPageNo = 1;
    private static int mPrePageNo = 1;
    private boolean mHasmore=false;
    private int mLastItem = 1;

    @Override
    protected int getLayout() {
        return R.layout.mine_fragment_myarticle;
    }

    @Override
    protected void initView() {

        mine_fragment_myarticle_back = (TextView) this.findViewById(R.id.id_mine_fragment_myarticle_back);
        mine_fragment_myarticle = (ListView) this.findViewById(R.id.id_mine_fragment_myarticle);

    }

    @Override
    protected void initVariable() {
        mUserId = SharedPreferencesUtils.getUserIdConsiderlessVisitor(MineHomeMyArticleActivity.this);
    }

    @Override
    protected void initListener() {

        mine_fragment_myarticle_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MineHomeMyArticleActivity.this.finish();
            }
        });

        mine_fragment_myarticle.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if(scrollState== AbsListView.OnScrollListener.SCROLL_STATE_IDLE && mLastItem == mAdapter.getCount()  && mHasmore
                        ){
                    mPageNo=mAdapter.getCount()/mPagesize+1;  //当前页码

                    //除了数据，也从页码的提增上判断是否有更多数据
                    if(mPageNo==mPrePageNo){
                        mHasmore =false;
                    }

                    String strGetMyArticleMore = "http://www.1316818.com/jsonserver_getmyArticle.ashx?android_userpwd="+ mUserId +"&pagesize="+ mPagesize +"&pageno="+String.valueOf(mPageNo);
                    new DBUtils() {
                        @Override
                        protected void successRequest(String result) {

                            Gson gson = new Gson();
                            MyArticleRoot root = gson.fromJson(result, MyArticleRoot.class);
                            List<Myarticle> mListNew =root.getMyarticle(); //将新的数据存入mListNew内
                            mList.addAll(mListNew); //将新的数据mListNew加入到mList内
                            mAdapter.notifyDataSetChanged(); //通知adapter更新数据

                            //判断是否有未加载的数据
                            if(mListNew.size()<mPagesize){
                                mHasmore =false;
                            }else{
                                mHasmore =true;
                            }
                            mPrePageNo = mPageNo; //对前也页码进行标识

                        }
                    }.getAsync(strGetMyArticleMore);

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

        String strGetMyArticle = "http://www.1316818.com/jsonserver_getmyArticle.ashx?android_userpwd="+ mUserId +"&pagesize="+ mPagesize +"&pageno="+String.valueOf(mPageNo);
        new DBUtils() {
            @Override
            protected void successRequest(String result) {

                Gson gson = new Gson();
                MyArticleRoot root = gson.fromJson(result,MyArticleRoot.class);
                mList = root.getMyarticle();
                mAdapter = new MineMyArticleAdapter(mList,R.layout.mine_fragment_myarticledetails,MineHomeMyArticleActivity.this);
                mine_fragment_myarticle.setAdapter(mAdapter);

                //判断是否有未加载的数据
                if(mList.size()<mPagesize){
                    mHasmore =false;
                }else{
                    mHasmore =true;
                }
                mPrePageNo = mPageNo;
            }
        }.getAsync(strGetMyArticle);

    }
}
