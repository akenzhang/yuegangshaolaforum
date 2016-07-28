package com.mantianhong.video.fragment;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.mantianhong.R;
import com.mantianhong.bean.Video;
import com.mantianhong.bean.VideoRoot;
import com.mantianhong.common.BaseFragment;
import com.mantianhong.common.DialogUtil;
import com.mantianhong.common.LogUtil;
import com.mantianhong.common.OkHttpUtils;
import com.mantianhong.video.activity.VideoHomeFragmentVideoPlayerActivity;
import com.mantianhong.video.adapter.VideoHomeFragmentListVideoAdapter;
import com.squareup.okhttp.Request;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by new pc on 2016/7/3.
 */
public class VideoHomeFragment extends BaseFragment {

    private ListView video_fragment_listview;
    private List<Video> mList = new ArrayList<>();
    private VideoHomeFragmentListVideoAdapter mAdapter;
    private Boolean isEnd= false;

    @Override
    protected int getLayout() {
        return R.layout.video_fragment_home;
    }

    @Override
    protected void initView() {
        video_fragment_listview = (ListView) this.getActivity().findViewById(R.id.id_video_fragment_listview);
    }

    @Override
    protected void initVariable() {}

    @Override
    protected void initListener() {

        video_fragment_listview.setOnScrollListener(new AbsListView.OnScrollListener() {

            int mListViewFirstItem = 0;
            int mScreenY = 0;
            int intMoving = -1;
            boolean isScrollToUp = false;
            int pageno=1;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

                int scrolled=view.getLastVisiblePosition();
                if(video_fragment_listview.getChildCount()>0 && !isEnd){
                    if(scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && intMoving==-1 && isScrollToUp){
                        //LogUtil.e("开始加载数据..");
                        //异步获得数据
                        String strUrl = "http://www.1316818.com/jsonserver.aspx?videopageno="+String.valueOf(pageno);
                        OkHttpUtils.getAsync(strUrl, new OkHttpUtils.DataCallBack() {
                            @Override
                            public void requestFailure(Request request, IOException e) {}

                            @Override
                            public void requestSuccess(String result) {

                                if(result.equals("{\"Video\":[]}")){
                                    //LogUtil.e("End of page");
                                    DialogUtil.showDialog(getActivity(),R.layout.home_fragment_loadall_dialogackground,2000);
                                    isEnd=true;
                                    return;
                                }

                                Gson gson = new Gson();
                                VideoRoot root = gson.fromJson(result,VideoRoot.class);
                                List<Video> moreList = root.getVideo();

                                mList.addAll(moreList);
                                mAdapter.notifyDataSetChanged();
                            }
                        });

                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                if(firstVisibleItem+visibleItemCount >=totalItemCount){
                    intMoving=-1;
                    pageno = totalItemCount/5+1;
                    //LogUtil.e(firstVisibleItem+"-"+ visibleItemCount+"-"+totalItemCount+"-"+pageno);

                }else {
                    intMoving=1;
                }

                if(video_fragment_listview.getChildCount()>0)
                {
                    int[] location = new int[2];

                    if(firstVisibleItem!=mListViewFirstItem) {
                        if(firstVisibleItem>mListViewFirstItem) {
                            isScrollToUp = true;
                        }else{
                            isScrollToUp = false;
                        }
                        mListViewFirstItem = firstVisibleItem;
                        mScreenY = location[1];
                    }else{
                        if(mScreenY>location[1]) {
                            isScrollToUp = true;
                        }
                        else if(mScreenY<location[1]) {
                            isScrollToUp = false;
                        }
                        mScreenY = location[1];
                    }
                }
            }
        });

    }

    @Override
    protected void bindData() {

        //异步获得数据
        String strUrl = "http://www.1316818.com/jsonserver.aspx?videopageno=1";
        OkHttpUtils.getAsync(strUrl, new OkHttpUtils.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {}

            @Override
            public void requestSuccess(String result) {
                Gson gson = new Gson();
                VideoRoot root = gson.fromJson(result,VideoRoot.class);
                mList = root.getVideo();

                mAdapter = new VideoHomeFragmentListVideoAdapter(mList,R.layout.video_fragment_home_listviewdetails,VideoHomeFragment.this.getActivity());
                video_fragment_listview.setAdapter(mAdapter);
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
