package com.mantianhong.me.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.mantianhong.R;
import com.mantianhong.bean.Myarticle;
import com.mantianhong.home.activity.HomeArticleDetailActivity;
import com.mantianhong.utiltools.CommonAdapter;
import com.mantianhong.utiltools.ViewHolder;

import java.util.List;

/**
 * Created by AKENZHANG on 2016/7/31.
 */
public class MineMyArticleAdapter extends CommonAdapter<Myarticle> {

    private Context mContext;
    public MineMyArticleAdapter(List<Myarticle> list, int resId, Context context) {
        super(list, resId, context);

        this.mContext = context;
    }

    @Override
    public void setContent(ViewHolder vh, final Myarticle item) {

        TextView title = (TextView) vh.getViews(R.id.id_mine_fragment_myarticle_title);
        TextView postdatetime = (TextView) vh.getViews(R.id.id_mine_fragment_myarticle_postdatetime);
        LinearLayout mine_fragment_myarticle_linelayout = (LinearLayout) vh.getViews(R.id.id_mine_fragment_myarticle_linelayout);

        title.setText(item.getTitle());
        postdatetime.setText(item.getPostdatetime());

        mine_fragment_myarticle_linelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,HomeArticleDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("tid",item.getTid());
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
    }
}
