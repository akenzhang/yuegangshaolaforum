package com.yuegangshaola.home.activity;

import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yuegangshaola.R;
import com.yuegangshaola.common.BaseActivity;

/**
 * Created by new pc on 2016/7/19.
 */
public class HomeArticleCommentActivity extends BaseActivity {

    private LinearLayout home_article_comment_layout;
    private EditText home_article_comment_edittext;
    private TextView home_article_comment_textview;

    @Override
    protected int getLayout() {
        return R.layout.home_articile_comment_activity;
    }

    @Override
    protected void initView() {

        home_article_comment_layout = (LinearLayout) this.findViewById(R.id.id_home_article_comment_layout);
        home_article_comment_edittext = (EditText) this.findViewById(R.id.id_home_article_comment_edittext);
        home_article_comment_textview = (TextView) this.findViewById(R.id.id_home_article_comment_textview);
    }

    @Override
    protected void initVariable() {

    }

    @Override
    protected void initListener() {

        home_article_comment_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeArticleCommentActivity.this,"发  表",Toast.LENGTH_SHORT).show();
            }
        });

        /*
        当用户点击输入框外边的时候，隐藏输入框
         */
        home_article_comment_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //当点击到输入框的外边的时候，将输入框隐藏起来
                HomeArticleCommentActivity.this.finish();
            }
        });

    }

    @Override
    protected void bindData() {

    }
}
