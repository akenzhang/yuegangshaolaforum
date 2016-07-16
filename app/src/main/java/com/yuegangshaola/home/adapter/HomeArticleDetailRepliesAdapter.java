package com.yuegangshaola.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yuegangshaola.R;
import com.yuegangshaola.bean.Article;
import com.yuegangshaola.bean.Reply;
import com.yuegangshaola.common.CommonAdapter;
import com.yuegangshaola.common.ViewHolder;
import com.yuegangshaola.home.activity.HomeArticleDetailActivity;

import java.util.List;

/**
 * Created by new pc on 2016/7/15.
 */
public class HomeArticleDetailRepliesAdapter extends CommonAdapter<Reply> {

    private List<Reply> mList;
    private Context mContext;

    public HomeArticleDetailRepliesAdapter(List<Reply> list, int resId, Context context) {
        super(list, resId, context);

        this.mList = list;
        this.mContext = context;

    }

    @Override
    public void setContent(ViewHolder vh, Reply item) {

    }
}
