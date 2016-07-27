package com.mantianhong.video.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.mantianhong.R;
import com.mantianhong.bean.Video;
import com.mantianhong.common.CommonAdapter;
import com.mantianhong.common.SingletonImageCollection;
import com.mantianhong.common.ViewHolder;

import java.util.List;


/*
VideoHomeFragment内List<Video> list = null实例化

 */


/**
 * Created by AKENZHANG on 2016/7/27.
 */
public class VideoHomeFragmentListVideoAdapter extends CommonAdapter<Video> {

    private Context mContext;

    public VideoHomeFragmentListVideoAdapter(List<Video> list, int resId, Context context) {
        super(list, resId, context);

        this.mContext = context;
    }

    @Override
    public void setContent(ViewHolder vh, Video item) {

        ImageView iv = ((ImageView)vh.getViews(R.id.id_video_fragment_listview_videoabbre));
        SingletonImageCollection.loadImageNormal(2,mContext,"http://www.1316818.com/upload/video_abbre/"+item.getPic(),iv);

        TextView tvDesc = ((TextView)vh.getViews(R.id.id_video_fragment_listview_textabbre));
        tvDesc.setText(item.getDesc());

        TextView tvViews = ((TextView)vh.getViews(R.id.id_video_fragment_listview_views));
        tvViews.setText(item.getViews());
    }
}
