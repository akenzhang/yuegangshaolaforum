package com.yuegangshaola.home.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yuegangshaola.R;
import com.yuegangshaola.bean.Replies;
import com.yuegangshaola.common.CommonAdapter;
import com.yuegangshaola.common.ViewHolder;

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
            message.setText(item.getMessage2() + "(" + item.getPostdatetime2() + ")");
        }else
        {
            LinearLayout linearLayout = (LinearLayout) vh.getViews(R.id.id_home_article_detail_replyinner_linearlayout);
            linearLayout.setVisibility(View.GONE);
            message.setVisibility(View.GONE);
        }
    }
}
