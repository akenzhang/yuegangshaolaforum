package com.mantianhong.video.fragment;

import android.support.v4.app.Fragment;
import android.widget.ListView;

import com.google.gson.Gson;
import com.mantianhong.R;
import com.mantianhong.bean.Video;
import com.mantianhong.bean.VideoRoot;
import com.mantianhong.common.BaseFragment;
import com.mantianhong.common.OkHttpUtils;
import com.mantianhong.video.adapter.VideoHomeFragmentListVideoAdapter;
import com.squareup.okhttp.Request;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by new pc on 2016/7/3.
 */
public class VideoHomeFragment extends BaseFragment {

    private ListView video_fragment_listview;

    @Override
    protected int getLayout() {
        return R.layout.video_fragment_home;
    }

    @Override
    protected void initView() {

        video_fragment_listview = (ListView) this.getActivity().findViewById(R.id.id_video_fragment_listview);
    }

    @Override
    protected void initVariable() {

    }

    @Override
    protected void initListener() {

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
                List<Video> list = root.getVideo();

                VideoHomeFragmentListVideoAdapter adapter = new VideoHomeFragmentListVideoAdapter(list,R.layout.video_fragment_home_listviewdetails,VideoHomeFragment.this.getActivity());
                video_fragment_listview.setAdapter(adapter);
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
