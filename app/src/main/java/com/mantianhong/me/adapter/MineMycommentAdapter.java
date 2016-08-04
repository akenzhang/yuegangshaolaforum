package com.mantianhong.me.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mantianhong.R;
import com.mantianhong.bean.Mycomment;
import com.mantianhong.bean.Takenote;
import com.mantianhong.home.activity.HomeArticleDetailActivity;
import com.mantianhong.utiltools.CommonAdapter;
import com.mantianhong.utiltools.SharedPreferencesUtils;
import com.mantianhong.utiltools.ViewHolder;

import java.util.List;

/**
 * Created by AKENZHANG on 2016/7/31.
 */
public class MineMycommentAdapter extends CommonAdapter<Mycomment> {

    private Context mContext;
    public MineMycommentAdapter(List<Mycomment> list, int resId, Context context) {
        super(list, resId, context);

        this.mContext = context;
    }

    @Override
    public void setContent(ViewHolder vh, final Mycomment item) {

        TextView message = (TextView) vh.getViews(R.id.id_mine_fragment_mycomment_message);
        TextView title = (TextView) vh.getViews(R.id.id_mine_fragment_mycomment_title);
        TextView postdatetime = (TextView) vh.getViews(R.id.id_mine_fragment_mycomment_postdatetime);
        TextView commentposter = (TextView) vh.getViews(R.id.id_mine_fragment_mycomment_commentposter);
        ImageView img = (ImageView) vh.getViews(R.id.id_mine_fragment_mycomment_img);
        LinearLayout linearLayout = (LinearLayout) vh.getViews(R.id.id_mine_fragment_mycomment_linearlayout);

        title.setText("原文："+item.getTitle());
        message.setText(item.getMessage());
        postdatetime.setText("评论日期:"+item.getPostdatetime());
        commentposter.setText("评论人:"+ SharedPreferencesUtils.getUserNameConsiderlessVisitor(mContext));

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,HomeArticleDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("tid",item.getTid());
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });

        //img尚未处理

    }
}
