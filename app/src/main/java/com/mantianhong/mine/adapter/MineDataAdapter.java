package com.mantianhong.mine.adapter;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mantianhong.R;
import com.mantianhong.utiltools.CommonAdapter;
import com.mantianhong.utiltools.LogUtil;
import com.mantianhong.utiltools.ViewHolder;

import java.util.List;

/**
 * Created by AKENZHANG on 2016/7/31.
 */
public class MineDataAdapter extends CommonAdapter<String> {

    public MineDataAdapter(List<String> list, int resId, Context context) {
        super(list, resId, context);
    }

    @Override
    public void setContent(ViewHolder vh, String item) {

        TextView text = (TextView)vh.getViews(R.id.id_mine_fragment_detail_text);
        text.setText(item);

        LinearLayout line = (LinearLayout)vh.getViews(R.id.id_mine_fragment_detail_linelayout);
        line.setTag(item);
        line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = (String) v.getTag();
                switch (text){
                    case "我的关注":
                        LogUtil.e("我的关注");
                        break;

                    case "我的文章":
                        LogUtil.e("我的文章");
                        break;

                    case "我的评论":
                        LogUtil.e("我的评论");
                        break;
                };
            }
        });
    }
}
