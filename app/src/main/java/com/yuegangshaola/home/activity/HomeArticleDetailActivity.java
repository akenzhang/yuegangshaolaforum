package com.yuegangshaola.home.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.yuegangshaola.R;
import com.yuegangshaola.bean.Article;
import com.yuegangshaola.bean.Articledetail;
import com.yuegangshaola.bean.ArticledetailRoot;
import com.yuegangshaola.bean.Articleset;
import com.yuegangshaola.bean.Reply;
import com.yuegangshaola.bean.Root;
import com.yuegangshaola.common.BaseActivity;
import com.yuegangshaola.common.DialogUtil;
import com.yuegangshaola.common.HorizontalListView;
import com.yuegangshaola.common.ListViewForScrollView;
import com.yuegangshaola.common.LogUtil;
import com.yuegangshaola.common.OkHttpUtils;
import com.yuegangshaola.common.TextUtil;
import com.yuegangshaola.home.adapter.HomeArticleDetailCategoryAdapter;
import com.yuegangshaola.home.adapter.HomeArticleDetailRelatedArticlesAdapter;
import com.yuegangshaola.home.adapter.HomeArticleDetailRepliesAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by AKENZHANG on 2016/7/13.
 */
public class HomeArticleDetailActivity extends BaseActivity {

    private ImageView home_article_detail_backward;
    private TextView home_article_detail_backward_text;
    private WebView article_detail_webview;
    private TextView article_detail_title;
    private TextView article_detail_category;
    private TextView article_detail_postdatetime;
    private ListViewForScrollView article_detail_ListView;
    private ListViewForScrollView article_detail_relatednews_ListView;
    private ListViewForScrollView article_detail_replies_ListView;
    private TextView article_detail_views;
    private TextView article_detail_poster;

    @Override
    protected int getLayout() {
        return R.layout.home_articile_detail_activity;
    }

    @Override
    protected void initView() {
        home_article_detail_backward = (ImageView) this.findViewById(R.id.id_home_article_detail_backward);
        home_article_detail_backward_text = (TextView) this.findViewById(R.id.id_home_article_detail_backward_text);
        article_detail_webview = (WebView) this.findViewById(R.id.id_article_detail_webview);
        article_detail_title = (TextView) this.findViewById(R.id.id_article_detail_title);
        article_detail_category = (TextView) this.findViewById(R.id.id_article_detail_category);
        article_detail_postdatetime = (TextView) this.findViewById(R.id.id_article_detail_postdatetime);
        article_detail_ListView = (ListViewForScrollView) this.findViewById(R.id.id_article_detail_ListView);
        article_detail_relatednews_ListView = (ListViewForScrollView) this.findViewById(R.id.id_article_detail_relatednews_ListView);
        article_detail_replies_ListView = (ListViewForScrollView) this.findViewById(R.id.id_article_detail_replies_ListView);
        article_detail_views = (TextView) this.findViewById(R.id.id_article_detail_views);
        article_detail_poster = (TextView) this.findViewById(R.id.id_article_detail_poster);

    }


    @Override
    protected void initVariable() {

        WebSettings webSettings = article_detail_webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        article_detail_webview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
    }

    @Override
    protected void initListener() {
        home_article_detail_backward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeArticleDetailActivity.this.finish();
            }
        });

        home_article_detail_backward_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeArticleDetailActivity.this.finish();
            }
        });

    }

    @Override
    protected void bindData() {

        //////////////////////////////////////////////////////////
        ////////////// 加载文章的具体内容  ///////////////////////
        //////////////////////////////////////////////////////////
        //取出绑定的参数
        Bundle bundle = getIntent().getExtras();
        int intTid = Integer.valueOf(bundle.getString("tid"));

        //根据tid从数据库获取相应的数据
        //弹出提示对话框
        final DialogUtil dialog = new DialogUtil(HomeArticleDetailActivity.this,"正在加载数据......");

        OkHttpUtils.getAsync("http://www.1316818.com/jsonserver.aspx?tid="+String.valueOf(intTid), new OkHttpUtils.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {
                dialog.closeDialog();
            }

            @Override
            public void requestSuccess(String result) {
                Gson gson = new Gson();
                ArticledetailRoot root = gson.fromJson(result,ArticledetailRoot.class);
                List<Articledetail> detaillist = root.getArticledetail();

                Articledetail articleDetail = detaillist.get(0);
                String strLoadText = TextUtil.parseJason(articleDetail.getMContent());

                article_detail_title.setText(articleDetail.getMTitle());
                article_detail_category.setText("类别："+articleDetail.getMCategory());
                article_detail_postdatetime.setText("发布时间："+articleDetail.getMPostDatetime());
                article_detail_views.setText("浏览："+articleDetail.getMViews());
                article_detail_poster.setText("作者："+articleDetail.getMPoster());
                article_detail_webview.loadDataWithBaseURL(null,"<style>*{line-height:22px;color:#6c6666;}</style>"+strLoadText,"text/html","utf-8",null); //将字体设置成灰色

                dialog.closeDialog();
            }
        });

        //////////////////////////////////////////////////////////
        ////////////// 加载相关类别数据  ////////////////////////
        //////////////////////////////////////////////////////////
        List categoryList = new ArrayList<String>();
        categoryList.add("最新资讯");
        categoryList.add("烧腊技术分享");
        categoryList.add("培训现场点滴");
        categoryList.add("隆江猪脚");
        HomeArticleDetailCategoryAdapter categoryAdapter = new HomeArticleDetailCategoryAdapter(categoryList,R.layout.home_articile_detail_category,this);
        article_detail_ListView.setAdapter(categoryAdapter);


        //////////////////////////////////////////////////////////
        ////////////// 加载相关文章  /////////////////////////////
        //////////////////////////////////////////////////////////
        //参考showtopic.aspx内的相关SQL,获取某tid的相关新闻，并通过异步获得数据，明天实现   2016.7.16
        OkHttpUtils.getAsync("http://www.1316818.com/jsonserver.aspx?relatedtid="+String.valueOf(intTid), new OkHttpUtils.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {}

            @Override
            public void requestSuccess(String result) {
                Gson gson = new Gson();
                Root root = gson.fromJson(result,Root.class);
                Articleset  articleset = root.getArticleset();
                List<Article> listArticle = articleset.getArticle();

                HomeArticleDetailRelatedArticlesAdapter relatedArticlesAdapter = new HomeArticleDetailRelatedArticlesAdapter(
                        listArticle
                        ,R.layout.home_articile_detail_relatednews
                        ,HomeArticleDetailActivity.this);
                article_detail_relatednews_ListView.setAdapter(relatedArticlesAdapter);
            }
        });


        //////////////////////////////////////////////////////////
        ////////////// 加载相关回复  /////////////////////////////
        //////////////////////////////////////////////////////////
        /*
        需要实现的：
        HomeArticleDetailRepliesAdapter
        Reply
        home_articile_detail_reply.xml
        更改id_article_detail_replies_ListView
        服务器连接XXXXXXXXXXXXXXXX
        jasonserver.aspx数据获取
        home_article_detail_replyinner.xml
        */

        /*
        OkHttpUtils.getAsync("XXXXXXXXXXXXXXXX", new OkHttpUtils.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {}

            @Override
            public void requestSuccess(String result) {
                List<Reply> listReply = null;
                HomeArticleDetailRepliesAdapter repliesAdapter = new HomeArticleDetailRepliesAdapter(
                        listReply
                        ,R.layout.home_articile_detail_reply
                        ,HomeArticleDetailActivity.this
                );
                article_detail_replies_ListView.setAdapter(repliesAdapter);
            }
        });
        */
    }

}
