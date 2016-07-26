package com.mantianhong.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.mantianhong.R;
import com.mantianhong.bean.Article;
import com.mantianhong.common.CommonAdapter;
import com.mantianhong.common.ViewHolder;
import com.mantianhong.home.activity.HomeArticleDetailActivity;

import java.util.List;

/**
 * Created by new pc on 2016/7/15.
 */
public class HomeArticleDetailRelatedArticlesAdapter extends CommonAdapter<Article> {

    private List<Article> mList;
    private Context mContext;

    public HomeArticleDetailRelatedArticlesAdapter(List<Article> list, int resId, Context context) {
        super(list, resId, context);

        this.mList = list;
        this.mContext = context;

    }

    @Override
    public void setContent(ViewHolder vh, final Article item) {

        TextView view = (TextView)vh.getViews(R.id.id_article_detail_relatednews_item);
        view.setText(subTitle(item.getMTitle()));

        //每个Item的点击事件
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,HomeArticleDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("tid",item.getMTid().toString());
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
    }

    private String subTitle(String strTitle){
        if(strTitle.length()<=19){
            return strTitle;
        }else {
            return strTitle.substring(0, 19)+"...";
        }
    }
}
