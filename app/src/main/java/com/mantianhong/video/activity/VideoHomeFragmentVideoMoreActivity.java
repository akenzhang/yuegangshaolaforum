package com.mantianhong.video.activity;

import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.mantianhong.R;
import com.mantianhong.common.BaseActivity;

/**
 * Created by AKENZHANG on 2016/7/27.
 */
public class VideoHomeFragmentVideoMoreActivity extends BaseActivity {

    private WebView video_fragment_videomore;
    private TextView video_fragment_more_close;

    @Override
    protected int getLayout() {
        return R.layout.video_fragment_home_videomore;
    }

    @Override
    protected void initView() {

        video_fragment_more_close = (TextView) this.findViewById(R.id.id_video_fragment_more_close);
        video_fragment_videomore = (WebView) this.findViewById(R.id.id_video_fragment_videomore);

        video_fragment_more_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VideoHomeFragmentVideoMoreActivity.this.finish();
            }
        });

        //初始化Webview
        WebSettings webSettings = video_fragment_videomore.getSettings();
        webSettings.setJavaScriptEnabled(true);
        video_fragment_videomore.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }
        });

        video_fragment_videomore.loadUrl("http://www.weishi.com/u/25690423");
    }

    @Override
    protected void initVariable() {
    }

    @Override
    protected void initListener() {}

    @Override
    protected void bindData() {
    }
}
