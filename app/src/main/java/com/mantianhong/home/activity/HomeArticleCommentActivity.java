package com.mantianhong.home.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mantianhong.utiltools.LogUtil;
import com.mantianhong.utiltools.SharedPreferencesUtils;
import com.mantianhong.utiltools.TextUtil;
import com.squareup.okhttp.Request;
import com.mantianhong.R;
import com.mantianhong.utiltools.EmailUtils;
import com.mantianhong.utiltools.BaseActivity;
import com.mantianhong.bean.EventBusMessage;
import com.mantianhong.utiltools.IPUtil;
import com.mantianhong.utiltools.OkHttpUtils;
import org.greenrobot.eventbus.EventBus;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by new pc on 2016/7/19.
 */
public class HomeArticleCommentActivity extends BaseActivity {

    private LinearLayout home_article_comment_layout;
    private EditText home_article_comment_edittext;
    private TextView home_article_comment_textview;
    private TextView home_article_comment_status;

    @Override
    protected int getLayout() {
        return R.layout.home_articile_comment_activity;
    }

    @Override
    protected void initView() {

        home_article_comment_layout = (LinearLayout) this.findViewById(R.id.id_home_article_comment_layout);
        home_article_comment_edittext = (EditText) this.findViewById(R.id.id_home_article_comment_edittext);
        home_article_comment_textview = (TextView) this.findViewById(R.id.id_home_article_comment_textview);
        home_article_comment_status = (TextView) this.findViewById(R.id.id_home_article_comment_status);
    }

    @Override
    protected void initVariable() {}

    @Override
    protected void initListener() {

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int intTempTid=1;
        int intTempPid=-1;
        if(bundle==null){
            intTempTid=1;
            intTempPid=-1;
        }else {
            intTempTid = bundle.getInt("tid",1);
            intTempPid = bundle.getInt("pid",-1);
        }

        final int intTid = intTempTid;
        final int intPid = intTempPid;

        /*
        看看评论来自哪里，如果是新的评论，即不用传送pid,如果是“回复本评论”，那么需要传动pid,同时更改一下相关的文本
         */
        if(intPid!=-1 && intPid>0){
            home_article_comment_edittext.setHint("对本评论，你想说什么?");
            home_article_comment_textview.setText("回 复");
        }

        home_article_comment_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String strComment = home_article_comment_edittext.getText().toString();

                //获得用书的输入回复，也获得tid,接下来是将该回复保存到数据库内
                String strUrlPost = "http://www.1316818.com/jsonserver.aspx";
                String struniqueCode=String.valueOf(java.util.Calendar.getInstance().getTimeInMillis());
                Map<String,String> parms = new HashMap<String, String>();
                if(intPid!=-1 && intPid>0){
                    /*
                    看看评论来自哪里，如果是新的评论，即不用传送pid,如果是“回复本评论”，那么需要传动pid
                     */
                    parms.put("pid_comment",String.valueOf(intPid));
                }
                parms.put("tid_comment",String.valueOf(intTid));
                parms.put("message_comment", TextUtil.urlEncode(strComment));
                parms.put("uniqueCode_comment",struniqueCode);
                parms.put("ip_comment", IPUtil.getIP(HomeArticleCommentActivity.this));
                parms.put("city_comment",  TextUtil.urlEncode(SharedPreferencesUtils.getUserNameIncludingVisitor(HomeArticleCommentActivity.this))); // 取得用户名字
                parms.put("android_userpwd_comment", TextUtil.urlEncode(SharedPreferencesUtils.getUserIdConsiderlessVisitor(HomeArticleCommentActivity.this))); //取得密码

                //发邮件通知我有匿名的新信息
                final String strMailTitle = "【粤港烧腊论坛手机匿名评论】";
                String strobjniminghuifu = strComment + "<br/><br/>原文链接：<a target=_blank href='http://www.1316818.com/showtopic-" + String.valueOf(intTid) + ".aspx'>showtopic-" + String.valueOf(intTid) + ".aspx</a><br/>";
                strobjniminghuifu = strobjniminghuifu + "<br/>快速删除：<a target=_blank href=http://www.1316818.com/DeleteNimingPost.aspx?tid=" + String.valueOf(intTid) + "&uniqueCode='" + struniqueCode + "'>点击删除该回复</a>";
                strobjniminghuifu = strobjniminghuifu + "<br/>更改城市：<a target=_blank href=http://www.1316818.com/DeleteNimingPost.aspx?tid=" + String.valueOf(intTid) + "&uniqueCode='" + struniqueCode + "'&mycode=100>更改成满天红</a>";
                final String strContent = strobjniminghuifu;

                OkHttpUtils.postAsync(strUrlPost,parms, new OkHttpUtils.DataCallBack() {
                    @Override
                    public void requestFailure(Request request, IOException e) {
                        LogUtil.e(e.getMessage());
                    }

                    @Override
                    public void requestSuccess(String result) {
                        //重新加载详情页面
                        reload(intTid,intPid);
                        //HomeArticleCommentActivity.this.finish();

                        if(result.contains("添加成功") && result.contains("1")){

                            //发送邮件
                            EmailUtils.sendMail(strMailTitle,strContent);

                            Toast.makeText(HomeArticleCommentActivity.this,"评论成功发布...",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(HomeArticleCommentActivity.this,"评论发生异常...",Toast.LENGTH_SHORT).show();
                        }
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
                //重新加载详情页面
                reload(intTid,intPid);
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
                if(s.toString().length()>=5){
                    home_article_comment_textview.setEnabled(true);
                    home_article_comment_textview.setBackgroundColor(Color.parseColor("#828285"));
                    home_article_comment_status.setText("");
                }else {
                    home_article_comment_textview.setEnabled(false);
                    home_article_comment_textview.setBackgroundColor(Color.parseColor("#d1d1d5"));
                    home_article_comment_status.setText("还差 "+ String.valueOf(5-s.toString().length()) +" 个字...");
                }
            }
        });

    }

    @Override
    protected void bindData() {}

    private void reload(int intTid,int intPid){
        //重新加载详情页面:未来考虑是否需要这个代码

        String strComment = home_article_comment_edittext.getText().toString();

        //发一条消息给HomeArticleDetailActivity，通知更新界面
        if(!TextUtils.isEmpty(strComment)){
            EventBus.getDefault().post(new EventBusMessage("DATA_CHANGED",intTid));
        }

        //当点击到输入框的外边的时候，将输入框隐藏起来
        HomeArticleCommentActivity.this.finish();
    }
}
