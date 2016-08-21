package com.mantianhong.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mantianhong.R;
import com.mantianhong.bean.Mysearch;
import com.mantianhong.home.activity.HomeArticleDetailActivity;
import com.mantianhong.home.activity.HomeSearchActivity;
import com.mantianhong.utiltools.CommonAdapter;
import com.mantianhong.utiltools.ViewHolder;

import java.util.List;

/**
 * Created by new pc on 2016/7/4.
 */
public class HomeSearchActivityAdapter extends CommonAdapter<Mysearch> {

    private Context mContext;
    public HomeSearchActivityAdapter(List<Mysearch> list, int resId, Context context) {
        super(list, resId, context);

        mContext = context;
    }

    @Override
    public void setContent(ViewHolder vh, final Mysearch item) {

        TextView home_search_detail_title = (TextView) vh.getViews(R.id.id_home_search_detail_title);
        TextView home_search_detail_postdatetime = (TextView) vh.getViews(R.id.id_home_search_detail_postdatetime);
        LinearLayout home_search_detail_linelayout = (LinearLayout) vh.getViews(R.id.id_home_search_detail_linelayout);

        home_search_detail_title.setText(item.getTitle());
        home_search_detail_postdatetime.setText(item.getPostdatetime());

        home_search_detail_linelayout.setOnClickListener(new View.OnClickListener() {
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
