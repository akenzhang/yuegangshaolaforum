package com.mantianhong.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mantianhong.utiltools.SharedPreferencesUtils;
import com.squareup.okhttp.Request;
import com.mantianhong.R;
import com.mantianhong.bean.Article;
import com.mantianhong.bean.Articledetail;
import com.mantianhong.bean.ArticledetailRoot;
import com.mantianhong.bean.Articleset;
import com.mantianhong.bean.CommentRoot;
import com.mantianhong.bean.Commentset;
import com.mantianhong.bean.Root;
import com.mantianhong.utiltools.BaseActivity;
import com.mantianhong.utiltools.DialogUtil;
import com.mantianhong.bean.EventBusMessage;
import com.mantianhong.utiltools.ListViewForScrollView;
import com.mantianhong.utiltools.OkHttpUtils;
import com.mantianhong.utiltools.TextUtil;
import com.mantianhong.home.adapter.HomeArticleDetailRelatedArticlesAdapter;
import com.mantianhong.home.adapter.HomeArticleDetailRepliesAdapter;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import java.io.IOException;
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
    private TextView article_detail_views;
    private TextView article_detail_poster;
    private TextView article_detail_category_latest;
    private TextView article_detail_category_share;
    private TextView article_detail_category_diandi;
    private TextView article_detail_category_zhujiao;
    private TextView article_detail_comment;
    private ImageView article_detail_scrollcomment;
    private LinearLayout article_detail_author;
    private ImageView article_detail_share;
    private ImageView home_article_detail_share;
    private TextView home_article_detail_share_text;
    private ScrollView article_detail_scrollview;
    private ListViewForScrollView article_detail_relatednews_ListView;
    private ListViewForScrollView article_detail_replies_ListView;
    private LinearLayout home_article_detail_share_linearlayout;
    private LinearLayout home_article_detail_linearlayout;
    private TextView article_detail_share_text02;
    private LinearLayout article_detail_share_linearlayout;
    private TextView article_detail_scrollcomment_text;
    private LinearLayout article_detail_comment_linearlayout;

    private int mTid = 1;
    private Articledetail mArticleDetail;

    private HomeArticleDetailRepliesAdapter repliesAdapter;
    private List<Commentset> mListComments;

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
        article_detail_views = (TextView) this.findViewById(R.id.id_article_detail_views);
        article_detail_poster = (TextView) this.findViewById(R.id.id_article_detail_poster);
        article_detail_category_latest = (TextView) this.findViewById(R.id.id_article_detail_category_latest);
        article_detail_category_share = (TextView) this.findViewById(R.id.id_article_detail_category_share);
        article_detail_category_diandi = (TextView) this.findViewById(R.id.id_article_detail_category_diandi);
        article_detail_category_zhujiao = (TextView) this.findViewById(R.id.id_article_detail_category_zhujiao);
        article_detail_comment= (TextView) this.findViewById(R.id.id_article_detail_comment);
        article_detail_scrollcomment = (ImageView) this.findViewById(R.id.id_article_detail_scrollcomment);
        article_detail_author = (LinearLayout) this.findViewById(R.id.id_article_detail_author);
        article_detail_share = (ImageView) this.findViewById(R.id.id_article_detail_share);
        home_article_detail_share = (ImageView) this.findViewById(R.id.id_home_article_detail_share);
        home_article_detail_share_text = (TextView) this.findViewById(R.id.id_home_article_detail_share_text);
        article_detail_scrollview = (ScrollView) this.findViewById(R.id.id_article_detail_scrollview);
        article_detail_relatednews_ListView = (ListViewForScrollView) this.findViewById(R.id.id_article_detail_relatednews_ListView);
        article_detail_replies_ListView = (ListViewForScrollView) this.findViewById(R.id.id_article_detail_replies_ListView);
        home_article_detail_share_linearlayout = (LinearLayout) this.findViewById(R.id.id_home_article_detail_share_linearlayout);
        home_article_detail_linearlayout = (LinearLayout) this.findViewById(R.id.id_home_article_detail_linearlayout);
        article_detail_share_text02 = (TextView) this.findViewById(R.id.id_article_detail_share_text02);
        article_detail_share_linearlayout = (LinearLayout)this.findViewById(R.id.id_article_detail_share_linearlayout);
        article_detail_scrollcomment_text = (TextView) this.findViewById(R.id.id_article_detail_scrollcomment_text);
        article_detail_comment_linearlayout = (LinearLayout)this.findViewById(R.id.id_article_detail_comment_linearlayout);

        //注册EventBus
        EventBus.getDefault().register(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //注销EventBus
        EventBus.getDefault().unregister(this);
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

    private void share(Articledetail articledetail){
        //启动分享activity，需要传递相关的数据
        Bundle bundle = new Bundle();
        bundle.putString("SHARE_TO_QQ_TITLE",articledetail.getMTitle());
        bundle.putString("SHARE_TO_QQ_SUMMARY","关注:http://1316818.com,更多深入分析...");
        bundle.putString("SHARE_TO_QQ_TARGET_URL","http://www.1316818.com/showtopic-"+ articledetail.getMTid() +".aspx");
        bundle.putString("SHARE_TO_QQ_IMAGE_URL",articledetail.getmImageList());
        Intent intent = new Intent(HomeArticleDetailActivity.this,HomeArticleShareActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    protected void initListener() {
        /*
        以下6个注册事件干同样的事情：调用分享功能
         */
        home_article_detail_share_linearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share(mArticleDetail);
            }
        });
        home_article_detail_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share(mArticleDetail);
            }
        });
        home_article_detail_share_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share(mArticleDetail);
            }
        });
        article_detail_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(HomeArticleDetailActivity.this,"分享到QQ好友，QQ空间里去...",Toast.LENGTH_SHORT).show();
                share(mArticleDetail);
            }
        });
        article_detail_share_text02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share(mArticleDetail);
            }
        });
        article_detail_share_linearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share(mArticleDetail);
            }
        });

        //以下3个注册事件点击滚动到评论区域
        article_detail_scrollcomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                调到评论的位置
                 */
                int height = article_detail_author.getHeight()+article_detail_webview.getHeight()+article_detail_relatednews_ListView.getHeight()+article_detail_title.getHeight()+135;
                article_detail_scrollview.smoothScrollTo(0,height);
            }
        });
        article_detail_scrollcomment_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                调到评论的位置
                 */
                int height = article_detail_author.getHeight()+article_detail_webview.getHeight()+article_detail_relatednews_ListView.getHeight()+article_detail_title.getHeight()+135;
                article_detail_scrollview.smoothScrollTo(0,height);
            }
        });
        article_detail_comment_linearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                调到评论的位置
                 */
                int height = article_detail_author.getHeight()+article_detail_webview.getHeight()+article_detail_relatednews_ListView.getHeight()+article_detail_title.getHeight()+135;
                article_detail_scrollview.smoothScrollTo(0,height);
            }
        });

        /*
        以下三个界面回到上一界面
         */
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
        home_article_detail_linearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeArticleDetailActivity.this.finish();
            }
        });

        //////////////////////////////////////////////////////////
        ////////////// 加载相关类别数据点击事件  /////////////////
        //////////////////////////////////////////////////////////
        article_detail_category_latest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("category",String.valueOf(0));
                Intent intent = new Intent(HomeArticleDetailActivity.this, HomeActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        article_detail_category_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("category",String.valueOf(1));
                Intent intent = new Intent(HomeArticleDetailActivity.this, HomeActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        article_detail_category_diandi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("category",String.valueOf(2));
                Intent intent = new Intent(HomeArticleDetailActivity.this, HomeActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        article_detail_category_zhujiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("category",String.valueOf(3));
                Intent intent = new Intent(HomeArticleDetailActivity.this, HomeActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        //////////////////////////////////////////////////////////
        ////////////// 触发评论界面弹出  /////////////////////////
        //////////////////////////////////////////////////////////
        article_detail_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //这个时候判断是否用户为登录状态，否则弹出登录界面
                if(!SharedPreferencesUtils.isLoginConsiderlessVisitor(HomeArticleDetailActivity.this,"CANNOTCOMMENT")) return;

                Bundle bundle = new Bundle();
                bundle.putInt("tid",mTid);
                Intent intent = new Intent(HomeArticleDetailActivity.this, HomeArticleCommentActivity.class);
                intent.putExtras(bundle);
                HomeArticleDetailActivity.this.startActivity(intent);
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
        mTid = intTid;

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

                mArticleDetail = detaillist.get(0);
                String strLoadText = TextUtil.parseJason(mArticleDetail.getMContent());

                article_detail_title.setText(mArticleDetail.getMTitle());
                article_detail_category.setText("类别："+mArticleDetail.getMCategory());
                article_detail_postdatetime.setText("发布时间："+mArticleDetail.getMPostDatetime());
                article_detail_views.setText("浏览："+mArticleDetail.getMViews());
                article_detail_poster.setText("作者："+mArticleDetail.getMPoster());
                article_detail_webview.loadDataWithBaseURL(null,"<style>*{line-height:25px;font-size:1.0em;color:#6c6666;}</style>"+strLoadText,"text/html","utf-8",null); //将字体设置成灰色

                dialog.closeDialog();
            }
        });


        //////////////////////////////////////////////////////////
        ////////////// 加载相关文章  /////////////////////////////
        //////////////////////////////////////////////////////////
        //参考showtopic.aspx内的相关SQL,获取某tid的相关新闻，并通过异步获得数据
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
        OkHttpUtils.getAsync("http://www.1316818.com/jsonserver.aspx?commenttid="+ String.valueOf(intTid) +"", new OkHttpUtils.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {}

            @Override
            public void requestSuccess(String result) {
                Gson gson = new Gson();
                CommentRoot root = gson.fromJson(result,CommentRoot.class);
                mListComments = root.getCommentset();

                repliesAdapter = new HomeArticleDetailRepliesAdapter(
                        mListComments
                        ,R.layout.home_articile_detail_reply
                        ,HomeArticleDetailActivity.this
                );
                article_detail_replies_ListView.setAdapter(repliesAdapter);
            }
        });

    }

    @Subscribe
    public void onEvent(EventBusMessage event) {
        String strMsg = event.getMsg();
        if(strMsg.equals("DATA_CHANGED")){
            Intent intent = new Intent(HomeArticleDetailActivity.this,HomeArticleDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("tid",String.valueOf(event.getmTid()));
            intent.putExtras(bundle);
            HomeArticleDetailActivity.this.startActivity(intent);

            this.finish();
        }
    }

 }


