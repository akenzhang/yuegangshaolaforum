package com.yuegangshaola.home.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.yuegangshaola.R;
import com.yuegangshaola.bean.Articledetail;
import com.yuegangshaola.bean.ArticledetailRoot;
import com.yuegangshaola.common.BaseActivity;
import com.yuegangshaola.common.LogUtil;
import com.yuegangshaola.common.OkHttpUtils;
import com.yuegangshaola.common.TextUtil;

import java.io.IOException;
import java.util.List;

/**
 * Created by AKENZHANG on 2016/7/13.
 */
public class HomeArticleDetailActivity extends BaseActivity {

    private ImageView home_article_detail_backward;
    private TextView home_article_detail_backward_text;
    private WebView article_detail_webview;
    @Override
    protected int getLayout() {
        return R.layout.home_articile_detail_activity;
    }

    @Override
    protected void initView() {
        home_article_detail_backward = (ImageView) this.findViewById(R.id.id_home_article_detail_backward);
        home_article_detail_backward_text = (TextView) this.findViewById(R.id.id_home_article_detail_backward_text);
        article_detail_webview = (WebView) this.findViewById(R.id.id_article_detail_webview);
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

        //取出绑定的参数
        Bundle bundle = getIntent().getExtras();
        int intTid = Integer.valueOf(bundle.getString("tid"));

        Toast.makeText(this,String.valueOf(intTid),Toast.LENGTH_SHORT).show();
        //article_detail_webview.loadUrl("http://www.1316818.com/showtopic-"+ intTid +".aspx");
        //根据tid从数据库获取相应的数据
        OkHttpUtils.getAsync("http://www.1316818.com/jsonserver.aspx?tid="+String.valueOf(intTid), new OkHttpUtils.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {}

            @Override
            public void requestSuccess(String result) {
                String strResult = result;
                Gson gson = new Gson();
                ArticledetailRoot root = gson.fromJson(strResult,ArticledetailRoot.class);
                List<Articledetail> detaillist = root.getArticledetail();
                String strLoadText = TextUtil.parseJason(detaillist.get(0).getMContent());
                LogUtil.e(strLoadText);
                article_detail_webview.loadDataWithBaseURL(null,strLoadText,"text/html","utf-8",null);
            }
        });

    }


}
