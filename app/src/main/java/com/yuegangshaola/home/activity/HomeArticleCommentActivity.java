package com.yuegangshaola.home.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Request;
import com.yuegangshaola.R;
import com.yuegangshaola.common.BaseActivity;
import com.yuegangshaola.common.IPUtil;
import com.yuegangshaola.common.LogUtil;
import com.yuegangshaola.common.OkHttpUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
    protected void initVariable() {}

    @Override
    protected void initListener() {

        home_article_comment_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String strComment = home_article_comment_edittext.getText().toString();
                //Toast.makeText(HomeArticleCommentActivity.this,strComment,Toast.LENGTH_SHORT).show();
                Intent intent = getIntent();
                int intTid = intent.getIntExtra("tid",1);

                //Toast.makeText(HomeArticleCommentActivity.this,"获得用书的输入回复，也获得tid,接下来是将该回复保存到数据库内",Toast.LENGTH_SHORT).show();

                //获得用书的输入回复，也获得tid,接下来是将该回复保存到数据库内
                String strUrlPost = "http://www.1316818.com/jsonserver.aspx";
                String struniqueCode=String.valueOf(java.util.Calendar.getInstance().getTimeInMillis());
                Map<String,String> parms = new HashMap<String, String>();
                parms.put("tid",String.valueOf(intTid));
                parms.put("message",strComment);
                parms.put("ip", IPUtil.getIP(HomeArticleCommentActivity.this));
                parms.put("city","匿名");
                parms.put("parentpid","-1");

                //发邮件通知我有匿名的新信息
                String MailTitle = "【粤港烧腊论坛手机匿名评论】";
                String strobjniminghuifu = strComment + "<br/><br/>原文链接：<a target=_blank href='http://www.1316818.com/showtopic-" + String.valueOf(intTid) + ".aspx'>showtopic-" + String.valueOf(intTid) + ".aspx</a><br/>";
                strobjniminghuifu = strobjniminghuifu + "<br/>快速删除：<a target=_blank href=http://www.1316818.com/DeleteNimingPost.aspx?tid=" + String.valueOf(intTid) + "&uniqueCode='" + struniqueCode + "'>点击删除该回复</a>";
                strobjniminghuifu = strobjniminghuifu + "<br/>更改城市：<a target=_blank href=http://www.1316818.com/DeleteNimingPost.aspx?tid=" + String.valueOf(intTid) + "&uniqueCode='" + struniqueCode + "'&mycode=100>更改成满天红</a>";


                OkHttpUtils.postAsync(strUrlPost,parms, new OkHttpUtils.DataCallBack() {
                    @Override
                    public void requestFailure(Request request, IOException e) {}

                    @Override
                    public void requestSuccess(String result) {
                        String str="";
                    }
                });

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
        判断输入框的输入内容，如果为空，保存按钮不能输入
         */
        home_article_comment_edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().length()>0){
                    home_article_comment_textview.setEnabled(true);
                    home_article_comment_textview.setBackgroundColor(Color.parseColor("#828285"));

                }else {
                    home_article_comment_textview.setEnabled(false);
                    home_article_comment_textview.setBackgroundColor(Color.parseColor("#d1d1d5"));
                }
            }
        });

    }

    @Override
    protected void bindData() {

    }
}
