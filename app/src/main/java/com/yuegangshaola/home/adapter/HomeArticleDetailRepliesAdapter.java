package com.yuegangshaola.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yuegangshaola.R;
import com.yuegangshaola.bean.Article;
import com.yuegangshaola.bean.Commentset;
import com.yuegangshaola.bean.Replies;
import com.yuegangshaola.common.CommonAdapter;
import com.yuegangshaola.common.ListViewForScrollView;
import com.yuegangshaola.common.ViewHolder;
import com.yuegangshaola.home.activity.HomeArticleDetailActivity;

import java.util.List;

/**
 * Created by new pc on 2016/7/15.
 */
public class HomeArticleDetailRepliesAdapter extends CommonAdapter<Commentset> {

    private List<Commentset> mList;
    private Context mContext;

    public HomeArticleDetailRepliesAdapter(List<Commentset> list, int resId, Context context) {
        super(list, resId, context);

        this.mList = list;
        this.mContext = context;

    }

    @Override
    public void setContent(ViewHolder vh, Commentset item) {
        TextView ip = (TextView) vh.getViews(R.id.id_home_article_detail_reply_ip);
        TextView message = (TextView) vh.getViews(R.id.id_home_article_detail_reply_message);
        ListViewForScrollView listReplies = (ListViewForScrollView) vh.getViews(R.id.id_home_article_detail_reply_replies);

        ip.setText(item.getIp());
        message.setText(item.getMessage());
        List<Replies> listinnerReplies = item.getReplies();

        //取出该comment的相关回复
        HomeArticleDetailRepliesInnerAdapter repliesInnerAdapter = new HomeArticleDetailRepliesInnerAdapter(listinnerReplies,R.layout.home_articile_detail_replyinner,mContext);
        listReplies.setAdapter(repliesInnerAdapter);
    }
}
