package com.mantianhong.home.adapter;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mantianhong.R;
import com.mantianhong.bean.Replies;
import com.mantianhong.utiltools.CommonAdapter;
import com.mantianhong.utiltools.ViewHolder;

import java.util.List;

/**
 * Created by new pc on 2016/7/17.
 */
public class HomeArticleDetailRepliesInnerAdapter extends CommonAdapter<Replies> {

    private List<Replies> listReplies;

    public HomeArticleDetailRepliesInnerAdapter(List<Replies> list, int resId, Context context) {
        super(list, resId, context);

        this.listReplies = list;

    }

    @Override
    public void setContent(ViewHolder vh, Replies item) {
        TextView message = (TextView) vh.getViews(R.id.id_home_article_detail_replyinner_message);
        if(!item.getMessage2().equals("")) {
            message.setText("【"+item.getCity2()+"】 "+item.getMessage2() + "(" + item.getPostdatetime2() + ")");
        }else
        {
            LinearLayout linearLayout = (LinearLayout) vh.getViews(R.id.id_home_article_detail_replyinner_linearlayout);
            linearLayout.setVisibility(View.GONE);
            message.setVisibility(View.GONE);
        }
    }
}
