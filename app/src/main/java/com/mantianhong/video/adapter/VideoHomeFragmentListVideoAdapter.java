package com.mantianhong.video.adapter;

import android.content.Context;

import com.mantianhong.bean.Video;
import com.mantianhong.common.CommonAdapter;
import com.mantianhong.common.ViewHolder;

import java.util.List;


/*
Video等bean的创建
jsonserver.aspx.cs里Jason生成
VideoHomeFragment内List<Video> list = null实例化
 */



/**
 * Created by AKENZHANG on 2016/7/27.
 */
public class VideoHomeFragmentListVideoAdapter extends CommonAdapter<Video> {
    public VideoHomeFragmentListVideoAdapter(List<Video> list, int resId, Context context) {
        super(list, resId, context);
    }

    @Override
    public void setContent(ViewHolder vh, Video item) {

    }
}
