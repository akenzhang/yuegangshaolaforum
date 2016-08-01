package com.mantianhong.me.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mantianhong.R;
import com.mantianhong.utiltools.CommonAdapter;
import com.mantianhong.utiltools.ViewHolder;

import java.util.List;

/**
 * Created by AKENZHANG on 2016/7/31.
 */
public class MineDataAdapter extends CommonAdapter<String> {

    private Context mContext;
    private Boolean mBlLogin;
    public MineDataAdapter(boolean blLogin,List<String> list, int resId, Context context) {
        super(list, resId, context);

        this.mBlLogin = blLogin;
        this.mContext = context;
    }

    @Override
    public void setContent(ViewHolder vh, String item) {

        TextView text = (TextView)vh.getViews(R.id.id_mine_fragment_detail_text);
        ImageView image = (ImageView) vh.getViews(R.id.id_mine_fragment_detail_image);
        LinearLayout line = (LinearLayout)vh.getViews(R.id.id_mine_fragment_detail_linelayout);

        text.setText(item);
        if(!mBlLogin) {
            text.setEnabled(false);
            image.setImageResource(R.drawable.like_arrow_textpage_press);
        }

        line.setTag(item);
        line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = (String) v.getTag();

                if(!mBlLogin) {
                    Toast.makeText(mContext,"请登陆再试...",Toast.LENGTH_SHORT).show();
                    return;
                }

                switch (text){
                    case "我的关注":
                        Toast.makeText(mContext,"我的关注",Toast.LENGTH_SHORT).show();
                        break;

                    case "我的文章":
                        Toast.makeText(mContext,"我的文章",Toast.LENGTH_SHORT).show();
                        break;

                    case "我的评论":
                        Toast.makeText(mContext,"我的评论",Toast.LENGTH_SHORT).show();
                        break;
                };
            }
        });
    }
}
