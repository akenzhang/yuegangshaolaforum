package com.yuegangshaola.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.yuegangshaola.R;
import com.yuegangshaola.common.CommonAdapter;
import com.yuegangshaola.common.ViewHolder;
import com.yuegangshaola.home.activity.HomeActivity;

import java.util.List;

/**
 * Created by new pc on 2016/7/15.
 */
public class HomeArticleDetailCategoryAdapter extends CommonAdapter<String> {

    private Context mContext;
    private List<String> list;
    public HomeArticleDetailCategoryAdapter(List<String> list, int resId, Context context) {
        super(list, resId, context);

        this.mContext = context;
        this.list = list;
    }

    @Override
    public void setContent(ViewHolder vh, String item) {

        TextView view = (TextView)vh.getViews(R.id.id_article_detail_category);
        view.setText(item);
        view.setTag(item);

        //设置点击事件
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到主页，同时传递参数，明确具体跳转的fragment
                String strCategory = v.getTag().toString();
                for(int intIndex=0;intIndex<list.size();intIndex++){
                    if(list.get(intIndex).equals(strCategory)){
                        Bundle bundle = new Bundle();
                        bundle.putString("category",String.valueOf(intIndex));
                        Intent intent = new Intent(mContext, HomeActivity.class);
                        intent.putExtras(bundle);
                        mContext.startActivity(intent);

                        break;
                    }
                }
            }
        });

    }
}
