package com.yuegangshaola.home.activity;

import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.yuegangshaola.R;
import com.yuegangshaola.common.BaseActivity;

/**
 * Created by new pc on 2016/7/19.
 */
public class HomeArticleCommentActivity extends BaseActivity {

    private RelativeLayout home_article_comment_layout;

    @Override
    protected int getLayout() {
        return R.layout.home_articile_comment_activity;
    }

    @Override
    protected void initView() {

        home_article_comment_layout = (RelativeLayout) this.findViewById(R.id.id_home_article_comment_layout);

    }

    @Override
    protected void initVariable() {

    }

    @Override
    protected void initListener() {

        /*
        当用户点击输入框外边的时候，隐藏输入框
         */
        home_article_comment_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int intClicked = v.getId();
                if(intClicked==R.id.id_home_article_comment_layout
                        && intClicked!=R.id.id_home_article_comment_innerlayout
                        && intClicked!=R.id.id_home_article_comment_edittext
                        && intClicked!=R.id.id_home_article_comment_textview
                        ) {
                    HomeArticleCommentActivity.this.finish();
                }
            }
        });

    }

    @Override
    protected void bindData() {

    }
}
