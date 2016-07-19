package com.yuegangshaola.home.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
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
                String strComment = home_article_comment_edittext.getText().toString();
                //Toast.makeText(HomeArticleCommentActivity.this,strComment,Toast.LENGTH_SHORT).show();
                Intent intent = getIntent();
                int intTid = intent.getIntExtra("tid",1);

                Toast.makeText(HomeArticleCommentActivity.this,"获得用书的输入回复，也获得tid,接下来是将该回复保存到数据库内",Toast.LENGTH_SHORT).show();

                //获得用书的输入回复，也获得tid,接下来是将该回复保存到数据库内
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

        /*
        home_article_comment_edittext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    //home_article_comment_edittext.requestFocus();
                    //InputMethodManager imm = (InputMethodManager) home_article_comment_edittext.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    //imm.toggleSoftInput(0,InputMethodManager.SHOW_FORCED);
                    //Toast.makeText(HomeArticleCommentActivity.this,"输入框获得焦点",Toast.LENGTH_SHORT).show();
                }
            }
        });
        */



    }

    @Override
    protected void bindData() {

    }
}
