package com.mantianhong.home.fragment;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.mantianhong.utiltools.LazyLoadBaseFragment;
import com.mantianhong.utiltools.LogUtil;
import com.squareup.okhttp.Request;
import com.mantianhong.R;
import com.mantianhong.bean.Article;
import com.mantianhong.bean.Articleset;
import com.mantianhong.bean.Root;
import com.mantianhong.utiltools.DialogUtil;
import com.mantianhong.utiltools.OkHttpUtils;
import com.mantianhong.home.adapter.HomeDefaultFragmentListArticlesAdapter;
import java.io.IOException;
import java.util.List;

/**
 * Created by new pc on 2016/7/9.
 */
public abstract class YuegangshaolaBaseFragment extends LazyLoadBaseFragment {

    private RecyclerView mRecyclerview;
    private List<Article> mListArticle;
    private LinearLayoutManager mLayoutManager;
    private static int NEWS_PAGE_SIZE = 6;
    private Articleset articleset=null;
    private Boolean hasmore=true;
    private int mydy=0;

    private int lastItem;//最后一条数据position
    private HomeDefaultFragmentListArticlesAdapter adapter=null;

    private static int intPageNext=1;

    /**
     * 获取RecyclerView id
     * @return
     */
    protected abstract int getRecyclerViewID();

    /*
    获取 Fragment Context
     */
    protected  Context getFragmentContext(){
        //return getContext();
        return this.getActivity();
    }

    /*
    获取 fid
     */
    protected abstract String getFids();

    @Override
    protected void initView() {
        mRecyclerview = (RecyclerView) root.findViewById(getRecyclerViewID());
    }

    @Override
    protected void initVariable() {
        intPageNext = 1;
    }

    @Override
    protected void initListener() {

        mRecyclerview.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                //mydy>4表示需要拖动到一定的高度才提示
                if(mydy>=4){
                    if(newState==RecyclerView.SCROLL_STATE_IDLE) {
                        //LogUtil.e("findLastVisibleItemPosition() + a1:"+(mLayoutManager.findLastVisibleItemPosition() + a1)+"  getTotalrowcont():"+articleset.getTotalrowcont());
                        if (!hasmore && newState == RecyclerView.SCROLL_STATE_IDLE && mLayoutManager.findLastVisibleItemPosition() + 1 >= Integer.valueOf(articleset.getTotalrowcont())) {
                            //弹出提示对话框
                            DialogUtil.showDialog(YuegangshaolaBaseFragment.this.getFragmentContext(),R.layout.home_fragment_loadall_dialogackground,2000);
                        }
                    }
                }

                if(hasmore && newState == RecyclerView.SCROLL_STATE_IDLE && lastItem+1==mLayoutManager.getItemCount()){

                    if(articleset!=null) {
                        int intTotalrowcont = Integer.valueOf(articleset.getTotalrowcont());
                        int intTotalPagers = intTotalrowcont%NEWS_PAGE_SIZE>0?intTotalrowcont/NEWS_PAGE_SIZE+1:intTotalrowcont/NEWS_PAGE_SIZE;
                        intPageNext++;
                        if(intPageNext<=intTotalPagers) {
                            if(intPageNext==intTotalPagers) {
                                hasmore=false;
                            }

                            //加载数据，提示对话框
                            //final DialogUtil dialog2 = new DialogUtil(YuegangshaolaBaseFragment.this.getFragmentContext(),"正在加载数据......");
                            //LogUtil.e("intPageNext:"+String.valueOf(intPageNext));
                            String strRequestUrl = "http://www.1316818.com/jsonserver.aspx?fid=" + getFids() + "&newspageno=" + String.valueOf(intPageNext) + "&newspagesize=" + NEWS_PAGE_SIZE;
                            OkHttpUtils.getAsync(strRequestUrl, new OkHttpUtils.DataCallBack() {
                                @Override
                                public void requestFailure(Request request, IOException e) {
                                    //数据加载失败，也要关闭对话框
                                    //if(dialog2!=null) {
                                    //    dialog2.closeDialog();
                                    //}
                                }

                                @Override
                                public void requestSuccess(String result) {
                                    //通过Gson解析返回的字符串，并将数据存入mListArticle内
                                    Gson gson = new Gson();
                                    Root root = gson.fromJson(result, Root.class);
                                    articleset = root.getArticleset();
                                    List<Article> moreListArticle = articleset.getArticle();
                                    mListArticle.addAll(moreListArticle);
                                    adapter.notifyDataSetChanged();

                                    //if(dialog2!=null) {
                                    //    dialog2.closeDialog();
                                    //}
                                }
                            });
                        }
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastItem = mLayoutManager.findLastVisibleItemPosition();
                mydy = dy;
            }
        });
    }

    @Override
    protected void lazyLoad(){

        LogUtil.e("YuegangshaolaBaseFragment==>lazyLoad()==>加载fid="+getFids()+"的数据");

        try {
            String strFid = getFids();
            String strRequestUrl = "http://www.1316818.com/jsonserver.aspx?fid=" + strFid + "&newspageno=" + intPageNext + "&newspagesize=" + NEWS_PAGE_SIZE;  //为了加快速度，第一次加载6条

            //弹出提示对话框
            final DialogUtil dialog1 = new DialogUtil(YuegangshaolaBaseFragment.this.getFragmentContext(), "正在加载数据......");

            OkHttpUtils.getAsync(strRequestUrl, new OkHttpUtils.DataCallBack() {
                @Override
                public void requestFailure(Request request, IOException e) {
                    //数据加载失败，就关闭先前的提示框
                    if (dialog1 != null) {
                        dialog1.closeDialog();
                    }

                    //弹出异常对话框
                    DialogUtil.showDialog(YuegangshaolaBaseFragment.this.getFragmentContext(), "数据加载异常......", 1000);
                }

                @Override
                public void requestSuccess(String result) {

                    //通过Gson解析返回的字符串，并将数据存入mListArticle内
                    Gson gson = new Gson();
                    Root root = gson.fromJson(result, Root.class);
                    articleset = root.getArticleset();
                    mListArticle = articleset.getArticle();

                    //设置大小
                    mRecyclerview.setHasFixedSize(true);
                    //设置布局管理器
                    mLayoutManager = new LinearLayoutManager(getActivity(), OrientationHelper.VERTICAL, false);
                    mRecyclerview.setLayoutManager(mLayoutManager);

                    //将数据绑定给RecyclerView
                    adapter = new HomeDefaultFragmentListArticlesAdapter(mListArticle, getFragmentContext(), 2);
                    mRecyclerview.setAdapter(adapter);

                    hasmore = true;

                    //关闭对话框
                    if (dialog1 != null) {
                        dialog1.closeDialog();
                    }
                }
            });
        }catch (Exception ex){
            LogUtil.e(ex.getMessage());
        }
    }

}
