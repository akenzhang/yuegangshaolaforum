package com.mantianhong.video.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;
import com.google.gson.Gson;
import com.mantianhong.R;
import com.mantianhong.bean.Video;
import com.mantianhong.bean.VideoRoot;
import com.mantianhong.utiltools.DialogUtil;
import com.mantianhong.utiltools.LazyLoadBaseFragment;
import com.mantianhong.utiltools.LogUtil;
import com.mantianhong.utiltools.OkHttpUtils;
import com.mantianhong.video.activity.VideoHomeFragmentVideoMoreActivity;
import com.mantianhong.video.adapter.VideoHomeFragmentListVideoAdapter;
import com.squareup.okhttp.Request;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by new pc on 2016/7/3.
 */
public class VideoHomeFragment extends LazyLoadBaseFragment {

    private ListView video_fragment_listview;
    private List<Video> mList = new ArrayList<>();
    private VideoHomeFragmentListVideoAdapter mAdapter;
    private Boolean isEnd= false;
    private TextView video_fragment_more;

    @Override
    protected int getLayout() {
        return R.layout.video_fragment_home;
    }

    @Override
    protected void initView() {
        try {
            video_fragment_listview = (ListView) root.findViewById(R.id.id_video_fragment_listview);
            video_fragment_more = (TextView) root.findViewById(R.id.id_video_fragment_more);
        }catch (Exception ex){
            LogUtil.e(ex.getMessage());
        }
    }

    @Override
    protected void initVariable() {}

    @Override
    protected void initListener() {
        try {
            video_fragment_listview.setOnScrollListener(new AbsListView.OnScrollListener() {

                int mListViewFirstItem = 0;
                int mScreenY = 0;
                int intMoving = -1;
                boolean isScrollToUp = false;
                int pageno = 1;

                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {

                    int scrolled = view.getLastVisiblePosition();
                    if (video_fragment_listview.getChildCount() > 0 && !isEnd) {
                        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && intMoving == -1 && isScrollToUp) {
                            //LogUtil.e("开始加载数据..");
                            //异步获得数据
                            String strUrl = "http://www.1316818.com/jsonserver.aspx?videopageno=" + String.valueOf(pageno);
                            OkHttpUtils.getAsync(strUrl, new OkHttpUtils.DataCallBack() {
                                @Override
                                public void requestFailure(Request request, IOException e) {
                                }

                                @Override
                                public void requestSuccess(String result) {

                                    if (result.equals("{\"Video\":[]}")) {
                                        //LogUtil.e("End of page");
                                        DialogUtil.showDialog(getActivity(), R.layout.home_fragment_loadall_dialogackground, 2000);
                                        isEnd = true;
                                        return;
                                    }

                                    Gson gson = new Gson();
                                    VideoRoot root = gson.fromJson(result, VideoRoot.class);
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

                    if (firstVisibleItem + visibleItemCount >= totalItemCount) {
                        intMoving = -1;
                        pageno = totalItemCount / 5 + 1;
                        //LogUtil.e(firstVisibleItem+"-"+ visibleItemCount+"-"+totalItemCount+"-"+pageno);

                    } else {
                        intMoving = 1;
                    }

                    if (video_fragment_listview.getChildCount() > 0) {
                        int[] location = new int[2];

                        if (firstVisibleItem != mListViewFirstItem) {
                            if (firstVisibleItem > mListViewFirstItem) {
                                isScrollToUp = true;
                            } else {
                                isScrollToUp = false;
                            }
                            mListViewFirstItem = firstVisibleItem;
                            mScreenY = location[1];
                        } else {
                            if (mScreenY > location[1]) {
                                isScrollToUp = true;
                            } else if (mScreenY < location[1]) {
                                isScrollToUp = false;
                            }
                            mScreenY = location[1];
                        }
                    }
                }
            });

            video_fragment_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //更多微视 http://www.weishi.com/u/25690423
                    Intent intent = new Intent(root.getContext(), VideoHomeFragmentVideoMoreActivity.class);
                    root.getContext().startActivity(intent);
                }
            });
        }catch (Exception ex){
            LogUtil.e(ex.getMessage());
        }
    }

    @Override
    protected void lazyLoad() {
        LogUtil.e("VideoHomeFragment==>lazyLoad()");

        try {
            //异步获得数据
            String strUrl = "http://www.1316818.com/jsonserver.aspx?videopageno=1";
            OkHttpUtils.getAsync(strUrl, new OkHttpUtils.DataCallBack() {
                @Override
                public void requestFailure(Request request, IOException e) {}

                @Override
                public void requestSuccess(String result) {
                    Gson gson = new Gson();
                    VideoRoot root = gson.fromJson(result, VideoRoot.class);
                    mList = root.getVideo();

                    mAdapter = new VideoHomeFragmentListVideoAdapter(mList, R.layout.video_fragment_home_listviewdetails, VideoHomeFragment.this.getActivity());
                    video_fragment_listview.setAdapter(mAdapter);
                }
            });
        }catch (Exception ex){
            LogUtil.e(ex.getMessage());
        }
    }

}
