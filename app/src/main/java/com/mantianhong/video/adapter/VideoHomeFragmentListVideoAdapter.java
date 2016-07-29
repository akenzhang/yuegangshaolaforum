package com.mantianhong.video.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.mantianhong.R;
import com.mantianhong.bean.Video;
import com.mantianhong.utiltools.CommonAdapter;
import com.mantianhong.utiltools.OkHttpUtils;
import com.mantianhong.utiltools.SingletonImageCollection;
import com.mantianhong.utiltools.ViewHolder;
import com.mantianhong.video.activity.VideoHomeFragmentVideoPlayerActivity;
import com.squareup.okhttp.Request;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;


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
    public void setContent(ViewHolder vh, final Video item) {

        ImageView iv = ((ImageView)vh.getViews(R.id.id_video_fragment_listview_videoabbre));
        SingletonImageCollection.loadImageNormal(2,mContext,"http://www.1316818.com/upload/video_abbre/"+item.getPic(),iv);

        TextView tvDesc = ((TextView)vh.getViews(R.id.id_video_fragment_listview_textabbre));
        tvDesc.setText(item.getDesc());

        TextView tvViews = ((TextView)vh.getViews(R.id.id_video_fragment_listview_views));
        String strViews = item.getViews();
        if(TextUtils.isEmpty(strViews)){
            strViews="0";
        }
        tvViews.setText("浏览："+strViews);

        //点击跳转到播放视频界面
        LinearLayout linearLayout = (LinearLayout)vh.getViews(R.id.id_video_fragment_listview_linareout);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出播放视频的窗体
                Bundle bundle = new Bundle();
                bundle.putString("url", item.getLink());
                Intent intent = new Intent(mContext, VideoHomeFragmentVideoPlayerActivity.class);
                intent.putExtras(bundle);
                mContext.startActivity(intent);

                //这里发起保存记录
                //update dbo.dnt_videolist set views = views +1 where id=1
                HashMap<String,String> videomap = new HashMap<String, String>();
                videomap.put("videoid",String.valueOf(item.getId()));
                OkHttpUtils.postAsync("http://www.1316818.com/jsonserver.aspx", videomap, new OkHttpUtils.DataCallBack() {
                    @Override
                    public void requestFailure(Request request, IOException e) {}

                    @Override
                    public void requestSuccess(String result) {}
                });

            }
        });


    }
}
