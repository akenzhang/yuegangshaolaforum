package com.yuegangshaola.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuegangshaola.R;
import com.yuegangshaola.bean.Commentset;
import com.yuegangshaola.bean.Replies;
import com.yuegangshaola.common.CommonAdapter;
import com.yuegangshaola.common.ListViewForScrollView;
import com.yuegangshaola.common.ViewHolder;
import com.yuegangshaola.home.activity.HomeArticleCommentActivity;

import java.io.InputStream;
import java.util.List;

/**
 * Created by new pc on 2016/7/15.
 */
public class HomeArticleDetailRepliesAdapter extends CommonAdapter<Commentset> {

    private List<Commentset> mList;
    private Context mContext;
    private ListViewForScrollView listReplies;
    private HomeArticleDetailRepliesInnerAdapter repliesInnerAdapter;

    public HomeArticleDetailRepliesAdapter(List<Commentset> list, int resId, Context context) {
        super(list, resId, context);

        this.mList = list;
        this.mContext = context;

    }

    @Override
    public void setContent(ViewHolder vh, final Commentset item) {
        TextView ip = (TextView) vh.getViews(R.id.id_home_article_detail_reply_ip);
        TextView date = (TextView) vh.getViews(R.id.id_home_article_detail_reply_date);
        TextView message = (TextView) vh.getViews(R.id.id_home_article_detail_reply_message);
        TextView replythis = (TextView) vh.getViews(R.id.id_home_article_detail_replythis);
        listReplies = (ListViewForScrollView) vh.getViews(R.id.id_home_article_detail_reply_replies);
        ImageView img = (ImageView) vh.getViews(R.id.id_home_article_detail_reply_imgid);

        ip.setText(item.getCity());
        date.setText(item.getPostdatetime());
        message.setText(item.getMessage());
        List<Replies> listinnerReplies = item.getReplies();

        /*
        读取本地assets的图片
         */
        AssetManager assetManager=mContext.getAssets();
        try {
            InputStream in=assetManager.open(item.getImgid()+".jpg");
            Bitmap bmp=BitmapFactory.decodeStream(in);
            img.setImageBitmap(bmp);
        } catch (Exception e) {}

        //取出该comment的相关回复
        repliesInnerAdapter = new HomeArticleDetailRepliesInnerAdapter(listinnerReplies,R.layout.home_articile_detail_replyinner,mContext);
        listReplies.setAdapter(repliesInnerAdapter);
        //listReplies.deferNotifyDataSetChanged();

        /*
        为“回复本评论”注册事件
         */
        replythis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int intPid = Integer.valueOf(item.getPid());
                int intTid = Integer.valueOf(item.getTid());

                //弹出评论框框
                Bundle bundle = new Bundle();
                bundle.putInt("pid",intPid);
                bundle.putInt("tid",intTid);
                Intent intent = new Intent(mContext, HomeArticleCommentActivity.class);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
    }
}
