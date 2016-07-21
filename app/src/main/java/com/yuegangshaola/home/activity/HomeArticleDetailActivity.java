package com.yuegangshaola.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.yuegangshaola.R;
import com.yuegangshaola.bean.Article;
import com.yuegangshaola.bean.Articledetail;
import com.yuegangshaola.bean.ArticledetailRoot;
import com.yuegangshaola.bean.Articleset;
import com.yuegangshaola.bean.CommentRoot;
import com.yuegangshaola.bean.Commentset;
import com.yuegangshaola.bean.Root;
import com.yuegangshaola.common.BaseActivity;
import com.yuegangshaola.common.DialogUtil;
import com.yuegangshaola.common.ListViewForScrollView;
import com.yuegangshaola.common.OkHttpUtils;
import com.yuegangshaola.common.TextUtil;
import com.yuegangshaola.home.adapter.HomeArticleDetailRelatedArticlesAdapter;
import com.yuegangshaola.home.adapter.HomeArticleDetailRepliesAdapter;

import org.json.JSONObject;

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
    private ListViewForScrollView article_detail_relatednews_ListView;
    private ListViewForScrollView article_detail_replies_ListView;
    private TextView article_detail_views;
    private TextView article_detail_poster;
    private TextView article_detail_category_latest;
    private TextView article_detail_category_share;
    private TextView article_detail_category_diandi;
    private TextView article_detail_category_zhujiao;
    private EditText article_detail_comment;
    private ImageView article_detail_scrollcomment;
    private ScrollView article_detail_scrollview;
    private LinearLayout article_detail_author;
    private ImageView article_detail_share;

    private int mTid = 1;
    private static String APP_ID="1105560564";
    private Tencent mTencent;

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
        article_detail_relatednews_ListView = (ListViewForScrollView) this.findViewById(R.id.id_article_detail_relatednews_ListView);
        article_detail_replies_ListView = (ListViewForScrollView) this.findViewById(R.id.id_article_detail_replies_ListView);
        article_detail_views = (TextView) this.findViewById(R.id.id_article_detail_views);
        article_detail_poster = (TextView) this.findViewById(R.id.id_article_detail_poster);
        article_detail_category_latest = (TextView) this.findViewById(R.id.id_article_detail_category_latest);
        article_detail_category_share = (TextView) this.findViewById(R.id.id_article_detail_category_share);
        article_detail_category_diandi = (TextView) this.findViewById(R.id.id_article_detail_category_diandi);
        article_detail_category_zhujiao = (TextView) this.findViewById(R.id.id_article_detail_category_zhujiao);
        article_detail_comment= (EditText) this.findViewById(R.id.id_article_detail_comment);
        article_detail_scrollcomment = (ImageView) this.findViewById(R.id.id_article_detail_scrollcomment);
        article_detail_scrollview = (ScrollView) this.findViewById(R.id.id_article_detail_scrollview);
        article_detail_author = (LinearLayout) this.findViewById(R.id.id_article_detail_author);
        article_detail_share = (ImageView) this.findViewById(R.id.id_article_detail_share);

        mTencent = Tencent.createInstance(APP_ID, this.getApplicationContext());

    }

    /*
    QQ回调的需要
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mTencent.onActivityResult(requestCode, resultCode, data);
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

        /*
        分享功能
         */
        article_detail_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(HomeArticleDetailActivity.this,"分享到QQ好友，QQ空间里去...",Toast.LENGTH_SHORT).show();

                final Bundle params = new Bundle();
                /*
                分享给别的QQ
                 */

                /*
                params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
                params.putString(QQShare.SHARE_TO_QQ_TITLE, "要分享的标题");
                params.putString(QQShare.SHARE_TO_QQ_SUMMARY,  "要分享的摘要");
                params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,  "http://www.qq.com/news/1.html");
                params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,"http://imgcache.qq.com/qzone/space_item/pre/0/66768.gif");
                params.putString(QQShare.SHARE_TO_QQ_APP_NAME,  "测试应用222222");
                params.putString(QQShare.SHARE_TO_QQ_EXT_INT,  "其他附加功能");
                mTencent.shareToQQ(HomeArticleDetailActivity.this, params, new BaseUiListener());
             */

                /*
                分享给QQ空间
                 */
                params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE,QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
                params.putString(QzoneShare.SHARE_TO_QQ_TITLE, "Test");
                params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY,  "content infro");
                params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL,  "http://www.hicsg.com");
                ArrayList imageUrls = new ArrayList();
                imageUrls.add("http://media-cdn.tripadvisor.com/media/photo-s/01/3e/05/40/the-sandbar-that-links.jpg");
                params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imageUrls);
                params.putInt(QzoneShare.SHARE_TO_QQ_EXT_INT,  QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
                mTencent.shareToQzone(HomeArticleDetailActivity.this, params, new BaseUiListener());

            }
        });

        //点击滚动到评论区域
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

        /*
        回到上一界面
         */
        home_article_detail_backward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeArticleDetailActivity.this.finish();
            }
        });

        /*
        回到上一界面
         */
        home_article_detail_backward_text.setOnClickListener(new View.OnClickListener() {
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
        article_detail_comment.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    article_detail_comment.clearFocus();

                    Bundle bundle = new Bundle();
                    bundle.putInt("tid",mTid);
                    Intent intent = new Intent(HomeArticleDetailActivity.this, HomeArticleCommentActivity.class);
                    intent.putExtras(bundle);
                    HomeArticleDetailActivity.this.startActivity(intent);
                }
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

                Articledetail articleDetail = detaillist.get(0);
                String strLoadText = TextUtil.parseJason(articleDetail.getMContent());

                article_detail_title.setText(articleDetail.getMTitle());
                article_detail_category.setText("类别："+articleDetail.getMCategory());
                article_detail_postdatetime.setText("发布时间："+articleDetail.getMPostDatetime());
                article_detail_views.setText("浏览："+articleDetail.getMViews());
                article_detail_poster.setText("作者："+articleDetail.getMPoster());
                article_detail_webview.loadDataWithBaseURL(null,"<style>*{line-height:25px;font-size:1.0em;color:#6c6666;}</style>"+strLoadText,"text/html","utf-8",null); //将字体设置成灰色

                dialog.closeDialog();
            }
        });


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
        OkHttpUtils.getAsync("http://www.1316818.com/jsonserver.aspx?commenttid="+ String.valueOf(intTid) +"", new OkHttpUtils.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {}

            @Override
            public void requestSuccess(String result) {
                Gson gson = new Gson();
                CommentRoot root = gson.fromJson(result,CommentRoot.class);
                List<Commentset> listComments = root.getCommentset();

                HomeArticleDetailRepliesAdapter repliesAdapter = new HomeArticleDetailRepliesAdapter(
                        listComments
                        ,R.layout.home_articile_detail_reply
                        ,HomeArticleDetailActivity.this
                );
                article_detail_replies_ListView.setAdapter(repliesAdapter);
            }
        });

    }


    class BaseUiListener implements IUiListener {

        protected void doComplete(JSONObject values) {
            //这里实现业务逻辑

        }

        @Override
        public void onComplete(Object response) {
            doComplete((JSONObject)response);
        }

        @Override
        public void onError(UiError e) {}

        @Override
        public void onCancel() {}
    }


}


